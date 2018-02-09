package org.onecellboy.akka.test;


import static org.junit.Assert.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.onecellboy.akka.actor.MyActor;
import org.onecellboy.akka.actor.StashActor;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import akka.testkit.javadsl.TestKit;
import scala.concurrent.duration.Duration;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StashTest {
	
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
	  public void stashTest()
	  {
		  
		  System.out.println("=================Stash Test=================");
			
			final String testMsg = "test_mesg";
			final TestKit probe = new TestKit(system);

			final Props props = Props.create(StashActor.class);
			
			
			ActorRef target = system.actorOf(props);
			
			
			target.tell("1", ActorRef.noSender());
			target.tell("2", ActorRef.noSender());
			target.tell("3", ActorRef.noSender());
			target.tell("4", ActorRef.noSender());
			target.tell("5", ActorRef.noSender());
			
			
			target.tell("open", ActorRef.noSender());
			
			target.tell("11", ActorRef.noSender());
			target.tell("12", ActorRef.noSender());
			target.tell("13", ActorRef.noSender());
			
			
			target.tell("close", ActorRef.noSender());
			
			
			
			
			
			
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
