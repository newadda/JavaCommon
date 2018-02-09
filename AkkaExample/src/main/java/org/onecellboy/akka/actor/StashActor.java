package org.onecellboy.akka.actor;

import akka.actor.AbstractActorWithStash;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class StashActor extends AbstractActorWithStash {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder()
			      .matchEquals("open", s -> {
			    	  log.info("open and unstashAll");
			    	  
			          unstashAll();
			          getContext().become(receiveBuilder()
			            .matchEquals("close", cs -> {
			            	log.info("close");
			              getContext().unbecome();
			            })
			            .matchAny(msg -> {
			            	log.info("mesg = {}",msg);
			            })
			            .build(), false);
			        })
			        .matchAny(msg ->{
			        	log.info("stash({})",msg);
			        	stash();
			        	})
			        .build();
		
	}

	
	
	
}
