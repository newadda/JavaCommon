package org.onecellboy.akka.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.onecellboy.akka.actor.BecomeUnbecomActor;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;

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
		public void _0061becomeUnbecomeTest()
		{
			
			System.out.println("=================Become ,  Unbecome Å×½ºÆ®=================");
			
			
			final TestKit probe = new TestKit(system);

			final Props props = Props.create(BecomeUnbecomActor.class);
			
			ActorRef target = system.actorOf(props,"come");
			
			target.tell("become one", ActorRef.noSender());
			
			target.tell("mesg", ActorRef.noSender());
			
target.tell("unbecome", ActorRef.noSender());
			

			
target.tell("unbecome", ActorRef.noSender());
			
			target.tell("mesg", ActorRef.noSender());
			
		}

}
