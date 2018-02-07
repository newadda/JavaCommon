package org.onecellboy.akka.actor;

import java.util.concurrent.TimeUnit;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.duration.Duration;

public class TimerActor extends AbstractActor{
	
	public static class FirstTick{
		
	}
	
	
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	
	public TimerActor()
	{
		
		
		timer();
		/*
		getTimers().startSingleTimer(TICK_KEY, new FirstTick(), 
		        Duration.create(500, TimeUnit.MILLISECONDS));
		        */
	}
	
	private void timer()
	{
		
		
		getContext().getSystem().scheduler().scheduleOnce(Duration.create(5, TimeUnit.SECONDS),
				  new Runnable() {
		    @Override
		    public void run() {
		      getSelf().tell(new FirstTick(), ActorRef.noSender());
		    }
		}, getContext().getSystem().dispatcher());
	}
	
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
		.match(FirstTick.class, x->{
			timer();
			log.info("FirstTick Timeout");
		})
		
		.build();
	}
	

}
