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
		 
		
		System.out.println("=================기본 액터 테스트 ActorRef, Tell, Receive=================");
		
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
		
		
		// target에게 "Hello" 메세지 보냄
		target.tell("hello~", ActorRef.noSender());
		
		// target actor 가 죽을 때 Terminated 메세지 받을 수 있다. 즉 actor가 죽을때를 감시하는 것이다.
		probe.watch(target);

		// target actor에게 kill 메세지를 보낸다. 해당 actor는 kill 메세지를 받는다면 즉시 사망한다. 동작하던 작업이 있다면 해당 작업이 다 끝나고 사망한다. 작업중간에 사망시키는 것은 아니다.
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
		
		// Kill 메세지는 actor를 중지시키고 ActorKilledException을 던진다.
		// Kill 메세지는 actor의 작업을 일시 중지시키고 해당 actor의 supervisor에게 ActorKilledException을 던진다. 이때 actor에 대한 핸들링(resuming, restarting, terminating)은 supervisor가 결정한다.
		target.tell(akka.actor.Kill.getInstance(), ActorRef.noSender());
		Terminated msg = probe.expectMsgClass(Terminated.class);
		assertEquals(msg.actor(), target);
		
		target = system.actorOf(props);
		probe.watch(target);
		// PoisonPill 메세지는 해당 메세지가 일반 메세지 같이 mailbox 에 들어가고 이 메세지가 처리될때 actor는 중지된다.
		// 다시 말해 PosionPill 이전 메세지까지 다 처리하고 중지된다는 것이다.
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
		 * Actor Path 가 /usr/parent 라는 actor를 선택한다.
		 * */
		ActorSelection actorSelection = system.actorSelection("/user/parent");
		System.out.println(MessageFormat.format("actorRef = {0}\n", actorSelection));
		
		
		/*
		 *  /user/parent의 자식중에 Actor Path가 "child"로 시작하는  모든 actor를 선택한다. 
		 * */
		actorSelection = system.actorSelection("/user/parent/child*");
		System.out.println(MessageFormat.format("actorRef = {0}\n", actorSelection));
		
		System.out.println();
		System.out.println("-------childOfChild-------------");
		actorSelection.tell("childOfChild",  ActorRef.noSender());
		
		System.out.println();
		
		System.out.println("-------resolveOne-------------");
		/*
		 * 선택된 actor들 중에 임의의 한개의 actor를 결정한다.
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
		 * ActorSelection 으로 선택된 actor들의 정체를 알고 싶을 때가 있다.
		 * 이때는 built-in 메세지인 Identify 를 이용하면 된다. 모든 Actor는 Identify 메세지를 알고 있고 또한 해당 메세지를 받으면 자동적으로 ActorIdentity로 응답한다.
		 * 이것은 일반적인 메세지와 동일하다. 발신과 응답을 보장하지는 않는다.
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
		
		System.out.println("=================Become ,  Unbecome 테스트=================");
		
		
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
		
		// 방법1. 파라미터로 생성인자 주기
		props = Props.create(ArgActor.class,"arg1");
		
		String arg = "arg1";
		
	
		// enclosing scope error
		// props = Props.create(ArgActor.class, ()-> new ArgActor(arg) );
		
		
		// 방법2. 람다식을 이용하여 생성하기
		// 아래 방법은 추천되지 않는다.
		final String fArg = "arg1";
		props = Props.create(ArgActor.class, ()-> {
			return new ArgActor(fArg);
			
		});
		
		
		// 방법3. 람다식 없이 생성하기
		// 람다식이 지원하지 않는 java 7 이하에서 방법이다.
		props = Props.create(ArgActor.class, new Creator<ArgActor>() {

			@Override
			public ArgActor create() throws Exception {
				// TODO Auto-generated method stub
				return new ArgActor(fArg);
			}
		});
		
		
		
		// 방법3. 해당 Actor내에 static 함수를 만들기, 생성자 private 로 만들기
		// 이 방법을 추천한다.
		// 개발자 의도에 맞게 제한할 수 있다. 임의도 new Actor()를 통해 Actor를 생성할 수 없게 할 수 있다.
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
