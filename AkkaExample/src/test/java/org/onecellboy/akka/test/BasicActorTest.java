package org.onecellboy.akka.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.onecellboy.akka.actor.ActorIdentityActor;
import org.onecellboy.akka.actor.AnswerActor;
import org.onecellboy.akka.actor.ArgActor;
import org.onecellboy.akka.actor.BecomeUnbecomActor;
import org.onecellboy.akka.actor.CreatorActor;
import org.onecellboy.akka.actor.Master;
import org.onecellboy.akka.actor.MyActor;
import org.onecellboy.akka.actor.ReceiveTimeoutActor;
import org.onecellboy.akka.actor.TimerActor;

import com.google.common.primitives.UnsignedInts;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.dsl.Inbox.Inbox;
import akka.japi.Creator;
import akka.pattern.PatternsCS;
import akka.testkit.javadsl.TestKit;
import akka.util.Timeout;
import scala.Function1;
import scala.Option;
import scala.collection.mutable.Seq;
import scala.collection.mutable.StringBuilder;
import scala.concurrent.Await;
import scala.concurrent.CanAwait;
import scala.concurrent.Future;
import scala.util.Try;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BasicActorTest {

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
	public void _001basicTest() {
		 
		
		System.out.println("=================�⺻ ���� �׽�Ʈ ActorRef, Tell, Receive=================");
		
		final String testMsg = "test_mesg";
		final TestKit probe = new TestKit(system);

		final Props props = Props.create(MyActor.class);
		// final Props props = Props.create(MyActor.class,()-> new MyActor());

		ActorRef target =null;

		target= system.actorOf(props);
		System.out.println(MessageFormat.format("actor ref = {0}", target));
		System.out.println(MessageFormat.format("actor uid = {0}", String.valueOf(UnsignedInts.toLong(target.hashCode()))));
		System.out.println(MessageFormat.format("actor path = {0}", target.path()));
		
		System.out.println();
		
		target= system.actorOf(props,"test");
		System.out.println(MessageFormat.format("actor ref = {0}", target));
		System.out.println(MessageFormat.format("actor uid = {0}", String.valueOf(UnsignedInts.toLong(target.hashCode()))));
		System.out.println(MessageFormat.format("actor path = {0}", target.path()));
		
		
		// target���� "Hello" �޼��� ����
		target.tell("hello~", ActorRef.noSender());
		
		// target actor �� ���� �� Terminated �޼��� ���� �� �ִ�. �� actor�� �������� �����ϴ� ���̴�.
		probe.watch(target);

		// target actor���� kill �޼����� ������. �ش� actor�� kill �޼����� �޴´ٸ� ��� ����Ѵ�. �����ϴ� �۾��� �ִٸ� �ش� �۾��� �� ������ ����Ѵ�. �۾��߰��� �����Ű�� ���� �ƴϴ�.
		target.tell(akka.actor.Kill.getInstance(), ActorRef.noSender());
		final Terminated msg = probe.expectMsgClass(Terminated.class);
		assertEquals(msg.actor(), target);
		      
		
	}
	
	
	@Test
	public void _002killTest()
	{
		
		System.out.println("=================Actor Stop Test=================");

		final String testMsg = "test_mesg";
		final TestKit probe = new TestKit(system);

		final Props props = Props.create(MyActor.class);
		ActorRef target=null;
		target = system.actorOf(props);
		
		probe.watch(target);
		
		// Kill �޼����� actor�� ������Ű�� ActorKilledException�� ������.
		// Kill �޼����� actor�� �۾��� �Ͻ� ������Ű�� �ش� actor�� supervisor���� ActorKilledException�� ������. �̶� actor�� ���� �ڵ鸵(resuming, restarting, terminating)�� supervisor�� �����Ѵ�.
		target.tell(akka.actor.Kill.getInstance(), ActorRef.noSender());
		Terminated msg = probe.expectMsgClass(Terminated.class);
		assertEquals(msg.actor(), target);
		
		target = system.actorOf(props);
		probe.watch(target);
		// PoisonPill �޼����� �ش� �޼����� �Ϲ� �޼��� ���� mailbox �� ���� �� �޼����� ó���ɶ� actor�� �����ȴ�.
		// �ٽ� ���� PosionPill ���� �޼������� �� ó���ϰ� �����ȴٴ� ���̴�.
		target.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
		msg = probe.expectMsgClass(Terminated.class);
		assertEquals(msg.actor(), target);
	
		
		
	}
	
	
	
	
	

	
	@Test
	public void _003actorSelectionTest() {
		 
		final TestKit probe = new TestKit(system);

		final Props props = Props.create(CreatorActor.class);
		
		Props props2 = Props.create(CreatorActor.class,()->new CreatorActor() );
		
		
		ActorRef parent = system.actorOf(props,"parent");
		System.out.println(MessageFormat.format("actorRef = {0}", parent));
		
		for(int i = 0; i<10; i++)
		{
			parent.tell("child"+i, ActorRef.noSender());
		}
		
		/*
		 * Actor Path �� /usr/parent ��� actor�� �����Ѵ�.
		 * */
		ActorSelection actorSelection = system.actorSelection("/user/parent");
		System.out.println(MessageFormat.format("actorRef = {0}\n", actorSelection));
		
		
		/*
		 *  /user/parent�� �ڽ��߿� Actor Path�� "child"�� �����ϴ�  ��� actor�� �����Ѵ�. 
		 * */
		actorSelection = system.actorSelection("/user/parent/child*");
		System.out.println(MessageFormat.format("actorRef = {0}\n", actorSelection));
		
		System.out.println();
		System.out.println("-------childOfChild-------------");
		actorSelection.tell("childOfChild",  ActorRef.noSender());
		
		System.out.println();
		
		System.out.println("-------resolveOne-------------");
		/*
		 * ���õ� actor�� �߿� ������ �Ѱ��� actor�� �����Ѵ�.
		 * */
		Future<ActorRef> resolveOne = actorSelection.resolveOne(Timeout.apply(10, TimeUnit.SECONDS));
		try {
			ActorRef result = resolveOne.result(scala.concurrent.duration.Duration.create(10, TimeUnit.SECONDS),null);
			System.out.println(MessageFormat.format("actorRef = {0}\n", result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		/*
		 * ActorSelection ���� ���õ� actor���� ��ü�� �˰� ���� ���� �ִ�.
		 * �̶��� built-in �޼����� Identify �� �̿��ϸ� �ȴ�. ��� Actor�� Identify �޼����� �˰� �ְ� ���� �ش� �޼����� ������ �ڵ������� ActorIdentity�� �����Ѵ�.
		 * �̰��� �Ϲ����� �޼����� �����ϴ�. �߽Ű� ������ ���������� �ʴ´�.
		 * */
		Props temp = Props.create(ActorIdentityActor.class,()->new ActorIdentityActor() );
		ActorRef actorIdentityActor = system.actorOf(temp,"identitytest");
		
		
		actorIdentityActor.tell("Identify", ActorRef.noSender());
	
	}
	
	
	@Test
	public void _004askTest()
	{
		final TestKit probe = new TestKit(system);

		final Props props = Props.create(AnswerActor.class);
		
		ActorRef answer = system.actorOf(props,"answer");
		
		CompletionStage<Object> ask = PatternsCS.ask(answer, "zzzz", 1000);
		ask.toCompletableFuture().thenApply(v->{
			
			System.out.println(MessageFormat.format("answer = {0}\n", v));
			
			return v;
		});
		ask.exceptionally(v->{
			System.out.println(MessageFormat.format("Exception = {0}\n", v));
			return v;
		});
		
		
	}
	
	
	//@Test
	public void _005TimeoutTest()
	{
		
		final TestKit probe = new TestKit(system);

		final Props props = Props.create(ReceiveTimeoutActor.class);
		
		ActorRef answer = system.actorOf(props,"timeout");
	}
	
	
	//@Test
	public void _006TimerTest()
	{
		
		final TestKit probe = new TestKit(system);

		final Props props = Props.create(TimerActor.class);
		
		ActorRef answer = system.actorOf(props,"timer");
	}
	
	@Test
	public void _0061becomeUnbecomeTest()
	{
		
		System.out.println("=================Become ,  Unbecome �׽�Ʈ=================");
		
		
		final TestKit probe = new TestKit(system);

		final Props props = Props.create(BecomeUnbecomActor.class);
		
		ActorRef target = system.actorOf(props,"come");
		
		//target.tell("become ", ActorRef.noSender());
		
		target.tell("mesg", ActorRef.noSender());
		
	}
	
		

	@Test
	public void _007RouterTest()
	{
		
		
		
		final TestKit probe = new TestKit(system);

		final Props props = Props.create(Master.class);
		
		ActorRef router = system.actorOf(props,"router");
		
		router.tell(new Master.Work("test1"), ActorRef.noSender());
		router.tell(new Master.Work("test2"), ActorRef.noSender());
		router.tell(new Master.Work("test3"), ActorRef.noSender());
		router.tell(new Master.Work("test4"), ActorRef.noSender());
		router.tell(new Master.Work("test5"), ActorRef.noSender());
		router.tell(new Master.Work("test6"), ActorRef.noSender());
		router.tell(new Master.Work("test7"), ActorRef.noSender());
		
		router.tell(new Exception(""), ActorRef.noSender());
		router.tell(new Master.Work("test8"), ActorRef.noSender());
		router.tell(new Master.Work("test9"), ActorRef.noSender());
		router.tell(new Master.Work("test10"), ActorRef.noSender());
		router.tell(new Master.Work("test11"), ActorRef.noSender());
		router.tell(new Master.Work("test12"), ActorRef.noSender());
		router.tell(new Master.Work("test13"), ActorRef.noSender());
		router.tell(new Master.Work("test14"), ActorRef.noSender());
		router.tell(new Master.Work("test15"), ActorRef.noSender());
		router.tell(new Master.Work("test16"), ActorRef.noSender());
		router.tell(new Master.Work("test17"), ActorRef.noSender());
	}
	
	
	
	@Test
	public void _008PropsTest()
	{
		Props props = null; 
		
		// ���1. �Ķ���ͷ� �������� �ֱ�
		props = Props.create(ArgActor.class,"arg1");
		
		String arg = "arg1";
		
	
		// enclosing scope error
		// props = Props.create(ArgActor.class, ()-> new ArgActor(arg) );
		
		
		// ���2. ���ٽ��� �̿��Ͽ� �����ϱ�
		// �Ʒ� ����� ��õ���� �ʴ´�.
		final String fArg = "arg1";
		props = Props.create(ArgActor.class, ()-> {
			return new ArgActor(fArg);
			
		});
		
		
		// ���3. ���ٽ� ���� �����ϱ�
		// ���ٽ��� �������� �ʴ� java 7 ���Ͽ��� ����̴�.
		props = Props.create(ArgActor.class, new Creator<ArgActor>() {

			@Override
			public ArgActor create() throws Exception {
				// TODO Auto-generated method stub
				return new ArgActor(fArg);
			}
		});
		
		
		
		// ���3. �ش� Actor���� static �Լ��� �����, ������ private �� �����
		// �� ����� ��õ�Ѵ�.
		// ������ �ǵ��� �°� ������ �� �ִ�. ���ǵ� new Actor()�� ���� Actor�� ������ �� ���� �� �� �ִ�.
		String arg1 = "arg1";
		String arg2 = "arg2";
		props =ArgActor.props(arg1,arg2);
		
		
	}
	
	
	

	
	
	
	
	
	@Test
	public void _ZZZ()
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
