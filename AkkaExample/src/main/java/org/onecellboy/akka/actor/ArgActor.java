package org.onecellboy.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ArgActor extends AbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	
	public ArgActor(String arg)
	{
		log.info("Constructor arg = {} ",arg);
	}
	
	
	private ArgActor(String arg1, String arg2)
	{
		log.info("Constructor arg1 = {}, arg2 = {} ",arg1,arg2);
	}
	
	
	
	public static Props props(String arg1, String arg2) {
		// You need to specify the actual type of the returned actor
		// since Java 8 lambdas have some runtime type information erased
		return Props.create(ArgActor.class, () ->{ return new ArgActor(arg1,arg2);});
	}

	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return null;
	}

}
