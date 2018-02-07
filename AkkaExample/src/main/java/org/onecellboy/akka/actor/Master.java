package org.onecellboy.akka.actor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jboss.netty.channel.socket.Worker;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;

public class Master extends AbstractActor {
	
	public final static class Work implements Serializable {
		  private static final long serialVersionUID = 1L;
		  public final String payload;
		  public Work(String payload) {
		    this.payload = payload;
		  }
		}
	
	
	public static class MyRoutee extends AbstractActor
	{
		private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
		@Override
		public Receive createReceive() {
			return receiveBuilder()
				      .match(Work.class, message -> {
				    	  log.info("work = "+message.payload);
				      }).match(Exception.class, e->{
				    	  log.info("exception ");
				    	  throw e;
				      })
				      .build();
		}
		 
	}
	
	
	  Router router;
	  {
	    List<Routee> routees = new ArrayList<Routee>();
	    for (int i = 0; i < 5; i++) {
	      ActorRef r = getContext().actorOf(Props.create(MyRoutee.class));
	      getContext().watch(r);
	      routees.add(new ActorRefRoutee(r));
	    }
	    router = new Router(new RoundRobinRoutingLogic(), routees);
	  }

	  @Override
	  public Receive createReceive() {
	    return receiveBuilder()
	      .match(Work.class, message -> {
	        router.route(message, getSender());
	      }).match(Exception.class, e->{
	    	  
	    	  router.route(e, getSender());
	      })
	      .match(Terminated.class, message -> {
	        router = router.removeRoutee(message.actor());
	        ActorRef r = getContext().actorOf(Props.create(Worker.class));
	        getContext().watch(r);
	        router = router.addRoutee(new ActorRefRoutee(r));
	      })
	      .build();
	  }

}
