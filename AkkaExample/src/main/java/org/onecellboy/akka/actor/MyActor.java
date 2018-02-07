package org.onecellboy.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.FI.UnitApply;
import akka.japi.pf.Match;

public class MyActor extends AbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	

	
	/**
	 * 
	 * 이 부분이 메세지를 받는 부분이다.
	 * */
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder()
				.match(String.class,  s->{
					 log.info("Received String message: {}", s);
				}  )
				.matchAny(o->{
					log.info("Received unKnown message");
				})
				
				.build();
		
	}

}
