package org.onecellboy.akka.actor;

import java.text.MessageFormat;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.AbstractActor.Receive;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class AnswerActor extends AbstractActor {
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
					switch (s) {
					
					case "fail":
						
						break;
					case "timeout":
						Thread.sleep(3000);
					break;

					default:
						getSender().tell("answer", self());
						break;
					}

					getSender().tell(new akka.actor.Status.Success("answer"), self());
					getSender().tell(new akka.actor.Status.Failure(new Exception("fail...")), self());
					
				}  )
				.matchAny(o->{
					log.info("Received unKnown message");
				})
				
				.build();
	}


}
