package org.onecellboy.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Identify;
import akka.actor.Props;
import akka.actor.AbstractActor.Receive;
import akka.actor.ActorIdentity;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ActorIdentityActor extends AbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	 final Integer identifyId = 1;
	
	public ActorIdentityActor() {
	
	
	}
	
	
	
	
	@Override
	public void preStart() throws Exception {
		for(int i = 0; i<10; i++)
		{
			Props props = Props.create(this.getClass());
			ActorRef child = getContext().actorOf(props,"child"+i);
		}
		
	}




	/**
	 * 
	 * 이 부분이 메세지를 받는 부분이다.
	 * */
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder().match(String.class, s->s.equals("Identify"),s->{
			String path = this.getSelf().path()+"/child*";
			log.info("selection path = {}",path);
			  ActorSelection selection = getContext().actorSelection(path);
			  selection.tell(new Identify(identifyId), getSelf());
		})
		.match(ActorIdentity.class, id -> id.getActorRef().isPresent(), id -> {
			log.info("ActorIdentityActor : isPresent {}", id);
		}).match(ActorIdentity.class, id -> !id.getActorRef().isPresent(), id -> {
			log.info("ActorIdentityActor :is not Present {}", id);
		}).build();
		
		/*
		 * ActorIdentity.getActorRef()는 Optional<ActorRef> 로 isPresent()를 통해 값이 존재하는지 유부를 확인해야한다.
		 * */
		
	}

}
