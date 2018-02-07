package org.onecellboy.akka.actor;

import java.util.Optional;

import akka.actor.AbstractActor;
import akka.actor.AbstractActor.Receive;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class HookActor  extends AbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	
	
	
	@Override
	public void postRestart(Throwable reason) throws Exception {
		log.info("postRestart");
		super.postRestart(reason);
	}




	@Override
	public void postStop() throws Exception {
		log.info("postStop");
		super.postStop();
	}




	@Override
	public void preRestart(Throwable reason, Optional<Object> message) throws Exception {
		log.info("preRestart");
		super.preRestart(reason, message);
	}




	@Override
	public void preStart() throws Exception {
		log.info("preStart");
		super.preStart();
	}




	/**
	 * 
	 * 이 부분이 메세지를 받는 부분이다.
	 * */
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder()
		
				
				.build();
	}

}
