package org.onecellboy.akka.actor;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;


import static akka.actor.SupervisorStrategy.resume;
import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.stop;
import static akka.actor.SupervisorStrategy.escalate;

public class SupervisorActor extends AbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	/*
	private static SupervisorStrategy strategy =
		      new OneForOneStrategy(10, Duration.create(1, TimeUnit.MINUTES), DeciderBuilder.
		        match(ArithmeticException.class, e ->  resume()).
		        match(NullPointerException.class, e -> restart()).
		        match(IllegalArgumentException.class, e -> stop()).
		        matchAny(o -> escalate()).build());

		    @Override
		    public SupervisorStrategy supervisorStrategy() {
		      return strategy;
		    }
		    */
	
	@Override
	public void postRestart(Throwable reason) throws Exception {
		log.info("{} {}  postRestart",getSelf(),this);
		super.postRestart(reason);
	}




	@Override
	public void postStop() throws Exception {
		log.info("{} {} postStop",getSelf(),this);
		super.postStop();
	}




	@Override
	public void preRestart(Throwable reason, Optional<Object> message) throws Exception {
		log.info("{} {} preRestart",getSelf(),this);
		super.preRestart(reason, message);
	}




	@Override
	public void preStart() throws Exception {
		log.info("{} {} preStart",getSelf(),this);
		super.preStart();
	}

	
	
	
	
	
	
	
	private  SupervisorStrategy strategy =
			new OneForOneStrategy(10, Duration.create("1 minute"),new Function<Throwable, SupervisorStrategy.Directive>() {

				@Override
				public Directive apply(Throwable t) throws Exception {
					if (t instanceof ArithmeticException) {
						SupervisorActor.this.log.info("resume()");
						return resume();
						} else if (t instanceof NullPointerException) {
							SupervisorActor.this.log.info("restart()");
						return restart();
						} else if (t instanceof IllegalArgumentException) {
							SupervisorActor.this.log.info("stop()");
						return stop();
						} else {
							SupervisorActor.this.log.info("escalate()");
						return escalate();
						}
				}
			});

	
    @Override
    public SupervisorStrategy supervisorStrategy() {
      return strategy;
    }
	
	
	
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder()
		        .match(Props.class, props -> {
		            getSender().tell(getContext().actorOf(props), getSelf());
		          })
		        .match(Exception.class, e->{
		        	throw e;
		        })
		          .build();
	}

}
