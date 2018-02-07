package org.onecellboy.akka.actor;

import java.text.MessageFormat;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.Await;

public class CreatorActor  extends AbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	
	
	/**
	 * 
	 * 이 부분이 메세지를 받는 부분이다.
	 * */
	@Override
	public Receive createReceive() {
		
		return receiveBuilder()
				.match(String.class,  s->{
					 Props props = Props.create(this.getClass());
					 ActorRef child = getContext().actorOf(props,s);
					 System.out.println(MessageFormat.format("actorRef = {0}", child));
				}  )
				.matchAny(o->{
					log.info("Received unKnown message");
				})
				.build();
	}

}
