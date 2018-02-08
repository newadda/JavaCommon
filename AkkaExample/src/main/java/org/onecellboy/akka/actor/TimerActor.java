package org.onecellboy.akka.actor;

import java.util.concurrent.TimeUnit;

import akka.actor.AbstractActor;
import akka.actor.AbstractActorWithTimers;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.duration.Duration;

public class TimerActor extends AbstractActorWithTimers {
	
	public static class ScheduleTick{
		
	}
	
	 private static Object TICK_KEY = "TickKey";
	public static class TimerTick{
		
	}
	
	
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	
	
	Cancellable scheduleCancellable;
	
	
	
	public TimerActor()
	{
		
		timer();
		
		schedule();
	}

	private void timer() {
		/* AbstractActorWithTimers */
		getTimers().startSingleTimer(TICK_KEY, new TimerTick(), Duration.create(500, TimeUnit.MILLISECONDS));

	}
	
	public void schedule()
	{
		scheduleCancellable = getContext().getSystem().scheduler().scheduleOnce(Duration.create(5, TimeUnit.SECONDS),
				new Runnable() {
					@Override
					public void run() {
						getSelf().tell(new ScheduleTick(), ActorRef.noSender());
					}
				}, getContext().getSystem().dispatcher());
	}
	
	
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(ScheduleTick.class, x -> {
			log.info("Schedule Tick");
			scheduleCancellable = null;
			schedule();
		}).match(TimerTick.class, x -> {
			log.info("Timer Tick");
			timer();
		})
		.build();
	}
	

}
