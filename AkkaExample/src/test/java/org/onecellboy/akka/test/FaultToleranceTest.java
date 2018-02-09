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
import org.onecellboy.akka.actor.BecomeUnbecomeActor;
import org.onecellboy.akka.actor.SupervisorActor;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import scala.concurrent.duration.Duration;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FaultToleranceTest {
	static ActorSystem system;

	@BeforeClass
	public static void setup() {

		Config load = ConfigFactory.load("application.conf");
		system = ActorSystem.create("akka", load);

	}

	@AfterClass
	public static void teardown() {
		TestKit.shutdownActorSystem(system);
		system = null;
	}

	@After
	public void testAfter() {
		System.out.println("================================================");
		System.out.println();
		System.out.println();
	}

	@Test
	public void test() {
	
		
		System.out.println("=================Fault Tolerance Test=================");
		
		
		final TestKit probe = new TestKit(system);

		final Props props = Props.create(SupervisorActor.class);
		
		ActorRef first = system.actorOf(props);
		
		first.tell(props, probe.getRef());
		
		ActorRef second = probe.expectMsgClass(ActorRef.class);
		
		second.tell(props, probe.getRef());
		
		ActorRef third = probe.expectMsgClass(ActorRef.class);
		
		// resume
		third.tell(new ArithmeticException(),ActorRef.noSender());
		
		// restart
		third.tell(new NullPointerException(),ActorRef.noSender());
		
		// escalate
		third.tell(new Exception(),ActorRef.noSender());
		
		
		
		// stop
		third.tell(new IllegalArgumentException(),ActorRef.noSender());
		
		
	
		
		
		
		
		
	}

	@Test
	public void zzz() {
		System.out.println("end");
		pause();
	}

	private void pause() {
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
