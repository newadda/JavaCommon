package org.onecellboy.akka.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.onecellboy.akka.actor.MyActor;
import org.onecellboy.akka.persistence.ExamplePersistentActor;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersistenceTest {

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
	public void test() throws InterruptedException {
		final TestKit probe = new TestKit(system);

		final Props props = Props.create(ExamplePersistentActor.class);
		// final Props props = Props.create(MyActor.class,()-> new MyActor());

		ActorRef target =null;

		System.out.println("-------- Actor Create -------");
		target= system.actorOf(props,"persis");
		System.out.println();
		
		target.tell(new ExamplePersistentActor.Cmd("cmd0"),probe.getRef());
		target.tell(new ExamplePersistentActor.Cmd("cmd1"), probe.getRef());
		target.tell(new ExamplePersistentActor.Cmd("cmd2"), probe.getRef());
		target.tell(new ExamplePersistentActor.Cmd("cmd3"), probe.getRef());
		target.tell(new ExamplePersistentActor.Cmd("cmd4"), probe.getRef());
		target.tell(new ExamplePersistentActor.Cmd("cmd5"),probe.getRef());
		target.tell(new ExamplePersistentActor.Cmd("cmd6"), probe.getRef());
		target.tell(new ExamplePersistentActor.Cmd("cmd7"), probe.getRef());
		target.tell(new ExamplePersistentActor.Cmd("cmd8"), probe.getRef());
		target.tell(new ExamplePersistentActor.Cmd("cmd9"),probe.getRef());
		
		
		
		Thread.sleep(3000);
		
		System.out.println("-------- Actor eat PoisonPill -------");
		System.out.println();
		
		target.tell(akka.actor.PoisonPill.getInstance(),probe.getRef());
		
		
		Thread.sleep(3000);
		
		System.out.println("-------- Actor Create -------");
		System.out.println();
		
		target= system.actorOf(props,"persis");
		
		Thread.sleep(3000);
		
		
		System.out.println("-------- Actor Delete Journal and Snapshot -------");
		System.out.println();
		
		
		target.tell("delete",probe.getRef());
		
		
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
