package org.onecellboy.akka.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.onecellboy.akka.actor.MyActor;
import org.onecellboy.akka.cluster.SimpleClusterListener;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorPath;
import akka.actor.ActorPaths;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.client.ClusterClient;
import akka.cluster.client.ClusterClientReceptionist;
import akka.cluster.client.ClusterClientSettings;
import akka.routing.FromConfig;
import akka.routing.RoundRobinPool;
import akka.testkit.javadsl.TestKit;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClusterTest {

	static ActorSystem system1;
	static ActorSystem system2;
	
	@BeforeClass
	  public static void setup() {
		Config load = ConfigFactory.load("application1.conf");
		
		system1 = ActorSystem.create("ClusterSystem",load);
		
		
		
		 load = ConfigFactory.load("application2.conf");
		system2 = ActorSystem.create("ClusterSystem",load);
	  }
	  
	  @AfterClass
	  public static void teardown() {
	    TestKit.shutdownActorSystem(system1);
	    TestKit.shutdownActorSystem(system2);

	    system1 = null;
	    system2 = null;

	  }

	
	@Test
	public void _01test() {
		final TestKit probe = new TestKit(system1);

		final Props props = Props.create(SimpleClusterListener.class,"111");
		ActorRef actorOf = system1.actorOf(props, "zzz");
/*
		ActorRef router2 = system2.actorOf(
				Props.create(SimpleClusterListener.class).withRouter(new FromConfig()),
				"serviceRouter");*/
		ActorRef router1 =
				system1.actorOf(FromConfig.getInstance().props(
				    Props.create(SimpleClusterListener.class,"3333")), "serviceRouter");
	
		actorOf = system1.actorOf(props, "w1");
		actorOf = system1.actorOf(props, "w2");
	
		actorOf = system1.actorOf(props, "w3");
		
		
		final Props props2 = Props.create(SimpleClusterListener.class,"222");
		actorOf = system2.actorOf(props2, "w1");
		actorOf = system2.actorOf(props2, "w2");
	
		actorOf = system2.actorOf(props2, "w3");
	
		/*
		ActorRef w1 = system1.actorOf(Props.create(SimpleClusterListener.class), "w1");
		ClusterClientReceptionist.get(system1).registerService(w1);
		*/
		/*
		ActorRef w11 = system2.actorOf(Props.create(SimpleClusterListener.class), "w1");
		ClusterClientReceptionist.get(system2).registerService(w1);
		*/
		
		
/*
		final ActorRef c = system1.actorOf(ClusterClient.props(
			    ClusterClientSettings.create(system1).withInitialContacts(new HashSet<ActorPath>(Arrays.asList(
			    		  ActorPaths.fromString("akka.tcp://ClusterSystem@127.0.0.1:2551/system/receptionist"),
			    		  ActorPaths.fromString("akka.tcp://ClusterSystem@127.0.0.1:2552/system/receptionist"))))),
			    "client");
		 pause();
		 c.tell(new ClusterClient.SendToAll("/user/w1", "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"), ActorRef.noSender());
		 c.tell(new ClusterClient.SendToAll("/user/w1", "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"), ActorRef.noSender());
		 c.tell(new ClusterClient.SendToAll("/user/w1", "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"), ActorRef.noSender());
		 c.tell(new ClusterClient.SendToAll("/user/w1", "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"), ActorRef.noSender());
		 c.tell(new ClusterClient.SendToAll("/user/w1", "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"), ActorRef.noSender());
		 c.tell(new ClusterClient.SendToAll("/user/w1", "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"), ActorRef.noSender());
		 c.tell(new ClusterClient.SendToAll("/user/w1", "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"), ActorRef.noSender());
		 c.tell(new ClusterClient.SendToAll("/user/w1", "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"), ActorRef.noSender());
		 c.tell(new ClusterClient.SendToAll("/user/w1", "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"), ActorRef.noSender());
		 c.tell(new ClusterClient.SendToAll("/user/w1", "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"), ActorRef.noSender());
		 c.tell(new ClusterClient.SendToAll("/user/w1", "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"), ActorRef.noSender());
		 c.tell(new ClusterClient.SendToAll("/user/w1", "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"), ActorRef.noSender());
		 c.tell(new ClusterClient.SendToAll("/user/w1", "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"), ActorRef.noSender());
		 c.tell(new ClusterClient.SendToAll("/user/w1", "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"), ActorRef.noSender());
		*/
		 
		 /*
		ActorRef router2 =
				system2.actorOf(FromConfig.getInstance().props(
				    Props.create(SimpleClusterListener.class)), "serviceRouter");*/
		/*
		ActorRef router1 = system1.actorOf(
				Props.create(SimpleClusterListener.class).withRouter(new FromConfig()),
				"serviceRouter");*/
		actorOf = system2.actorOf(props, "zzz");

		 pause();
		 router1.tell("ttttttttttttttttttttttttttttttt",ActorRef.noSender());
		 router1.tell("ttttttttttttttttttttttttttttttt",ActorRef.noSender());
		 router1.tell("ttttttttttttttttttttttttttttttt",ActorRef.noSender());
		 router1.tell("ttttttttttttttttttttttttttttttt",ActorRef.noSender());
		 router1.tell("ttttttttttttttttttttttttttttttt",ActorRef.noSender());
		 router1.tell("ttttttttttttttttttttttttttttttt",ActorRef.noSender());
		 router1.tell("ttttttttttttttttttttttttttttttt",ActorRef.noSender());
		 router1.tell("ttttttttttttttttttttttttttttttt",ActorRef.noSender());
		  
		 router1.tell("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz",ActorRef.noSender());
		 router1.tell("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz",ActorRef.noSender());
		 router1.tell("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz",ActorRef.noSender());
		 router1.tell("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz",ActorRef.noSender());
		 router1.tell("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz",ActorRef.noSender());
		 router1.tell("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz",ActorRef.noSender());
		 router1.tell("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz",ActorRef.noSender());
		 router1.tell("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz",ActorRef.noSender());
		 router1.tell("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz",ActorRef.noSender());
	/*		router2.tell("ggggggggggggggggggggggggg",ActorRef.noSender());
			router2.tell("ggggggggggggggggggggggggg",ActorRef.noSender());
			router2.tell("ggggggggggggggggggggggggg",ActorRef.noSender());
			router2.tell("ggggggggggggggggggggggggg",ActorRef.noSender());
			router2.tell("ggggggggggggggggggggggggg",ActorRef.noSender());
			router2.tell("ggggggggggggggggggggggggg",ActorRef.noSender());
			router2.tell("ggggggggggggggggggggggggg",ActorRef.noSender());
			router2.tell("ggggggggggggggggggggggggg",ActorRef.noSender());
			router2.tell("ggggggggggggggggggggggggg",ActorRef.noSender());
			router2.tell("ggggggggggggggggggggggggg",ActorRef.noSender());*/
	    TestKit.shutdownActorSystem(system2);
		pause();
		
		router1.tell("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz",ActorRef.noSender());
		router1.tell("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz",ActorRef.noSender());
		router1.tell("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz",ActorRef.noSender());
		router1.tell("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz",ActorRef.noSender());
		router1.tell("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz",ActorRef.noSender());
		 
		  Config load = ConfigFactory.load("application2.conf");
		system2 = ActorSystem.create("ClusterSystem",load);
		router1.tell("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",ActorRef.noSender());
		router1.tell("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",ActorRef.noSender());
		pause();
		
		
		
		router1.tell("ddddddddddddddddddddddddddddddddddddd",ActorRef.noSender());
		router1.tell("ddddddddddddddddddddddddddddddddddddd",ActorRef.noSender());
		router1.tell("ddddddddddddddddddddddddddddddddddddd",ActorRef.noSender());
		router1.tell("ddddddddddddddddddddddddddddddddddddd",ActorRef.noSender());
		router1.tell("ddddddddddddddddddddddddddddddddddddd",ActorRef.noSender());
		router1.tell("ddddddddddddddddddddddddddddddddddddd",ActorRef.noSender());
			router1.tell("ddddddddddddddddddddddddddddddddddddd",ActorRef.noSender());
			router1.tell("ddddddddddddddddddddddddddddddddddddd",ActorRef.noSender());
			router1.tell("ddddddddddddddddddddddddddddddddddddd",ActorRef.noSender());
			 pause();
			 
	}

	
	
	
	

	private void pause()
	{
		try {
			System.in.read();
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
