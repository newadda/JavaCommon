package org.onecellboy.akka.actor;



import java.util.concurrent.TimeUnit;

import akka.actor.AbstractActor;
import akka.actor.ReceiveTimeout;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.duration.Duration;

public class ReceiveTimeoutActor extends AbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	public ReceiveTimeoutActor() {
	    // To set an initial delay
	    getContext().setReceiveTimeout(Duration.create(10, TimeUnit.SECONDS));
	  }
	
	@Override
	public Receive createReceive() {
		  return receiveBuilder()
				  .match(String.class, s->{})
				  .match(ReceiveTimeout.class, r -> {
			  log.info("Timeout");
		        //getContext().setReceiveTimeout(Duration.Undefined());
		      }).build();
	}

}
