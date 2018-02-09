package org.onecellboy.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.AbstractActor.Receive;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class BecomeUnbecomeDiscardOldActor extends AbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	  private AbstractActor.Receive stepOne;
	  private AbstractActor.Receive stepTwo;
	  
	  public BecomeUnbecomeDiscardOldActor()
	  {
		  stepOne = receiveBuilder()
					.match(String.class, s-> s.equals("become one"), s->{
						log.info("become one ");
						getContext().become(stepOne); 
					}  )
					.match(String.class, s-> s.equals("become two"), s->{
						log.info("become two ");
						getContext().become(stepTwo); 
					}  )
					.match(String.class, s-> s.equals("unbecome"), s->{
						log.info("unbecome ");
						 getContext().unbecome();
						
					}  )
					.match(String.class, s->{
						log.info("(stepOne) Received  message : {}",s);
					}  )
					.build();
		  
		  stepTwo = receiveBuilder()
					.match(String.class, s-> s.equals("become one"), s->{
						log.info("become one ");
						getContext().become(stepOne); 
					}  )
					.match(String.class, s-> s.equals("become two"), s->{
						log.info("become two ");
						getContext().become(stepTwo); 
					}  )
					.match(String.class, s-> s.equals("unbecome"), s->{
						log.info("unbecome ");
						 getContext().unbecome();
						 
					}  )
					.match(String.class, s->{
						log.info("(stepTwo) Received  message : {}",s);
					}  )
					.build();
	  }
	  
	/**
	 * 
	 * 이 부분이 메세지를 받는 부분이다.
	 * */
	@Override
	public Receive createReceive() {
		
		return  receiveBuilder()
				.match(String.class, s-> s.equals("become one"), s->{
					log.info("become one ");
					getContext().become(stepOne); 
				}  )
				.match(String.class, s-> s.equals("become two"), s->{
					log.info("become two ");
					getContext().become(stepTwo); 
				}  )
				.match(String.class, s-> s.equals("unbecome"), s->{
					 getContext().unbecome();
				}  )
				.match(String.class, s->{
					log.info("(default) Received  message : {}",s);
				}  )
				.build();
	}
	
	
	
}
