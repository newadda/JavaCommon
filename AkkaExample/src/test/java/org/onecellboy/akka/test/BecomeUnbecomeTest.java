package org.onecellboy.akka.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.onecellboy.akka.actor.BecomeUnbecomeActor;
import org.onecellboy.akka.actor.BecomeUnbecomeDiscardOldActor;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BecomeUnbecomeTest {

static ActorSystem system;
	
	@BeforeClass
	  public static void setup() {
		
		
		Config load = ConfigFactory.load("application.conf");
		system = ActorSystem.create("akka",load);
	    
	    
	    
	    
	  }
	  
	  @AfterClass
	  public static void teardown() {
	    TestKit.shutdownActorSystem(system);
	    system = null;
	  }
	  
	  
	  @After
		public void testAfter()
		{
		    System.out.println("================================================");
			System.out.println();
			System.out.println();
		}
	  
	  
	
	
		@Test
		public void _01becomeUnbecomeTest()
		{
			
			System.out.println("=================Become ,  Unbecome , discardOld=false 테스트=================");
			
			
			final TestKit probe = new TestKit(system);

			final Props props = Props.create(BecomeUnbecomeActor.class);
			
			ActorRef target = system.actorOf(props,"come");
			
			target.tell("mesg", ActorRef.noSender());
			
			
			target.tell("become two", ActorRef.noSender());
			target.tell("mesg", ActorRef.noSender());
			
			
			target.tell("become one", ActorRef.noSender());
			target.tell("mesg", ActorRef.noSender());
			
			
			target.tell("become one", ActorRef.noSender());
			target.tell("mesg", ActorRef.noSender());
			
			target.tell("unbecome", ActorRef.noSender());
			target.tell("mesg", ActorRef.noSender());
			
			target.tell("unbecome", ActorRef.noSender());
			target.tell("mesg", ActorRef.noSender());
			
			target.tell("unbecome", ActorRef.noSender());
			target.tell("mesg", ActorRef.noSender());
			
		}
		
		
		
		@Test
		public void _02discardOldBecomeUnbecomeTest()
		{
			
			System.out.println("=================Become ,  Unbecome  discardOld=true  테스트=================");
			
			
			final TestKit probe = new TestKit(system);

			final Props props = Props.create(BecomeUnbecomeDiscardOldActor.class);
			
			ActorRef target = system.actorOf(props,"come");
			
			target.tell("mesg", ActorRef.noSender());
			
			target.tell("become two", ActorRef.noSender());
			
			target.tell("mesg", ActorRef.noSender());
			
			
			target.tell("become one", ActorRef.noSender());
			
			target.tell("mesg", ActorRef.noSender());
			
			
			target.tell("become one", ActorRef.noSender());
			
			target.tell("mesg", ActorRef.noSender());
			
			target.tell("unbecome", ActorRef.noSender());
			
			target.tell("mesg", ActorRef.noSender());
			
			target.tell("unbecome", ActorRef.noSender());
			
			target.tell("mesg", ActorRef.noSender());
			
			target.tell("unbecome", ActorRef.noSender());
			
			target.tell("mesg", ActorRef.noSender());
			
		}
		
		
		@Test
		public void zzz()
		{
			System.out.println("end");
			pause();
		}
		
		
		private void pause()
		{
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

}
