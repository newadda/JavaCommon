package org.onecellboy.akka.persistence;

import java.io.Serializable;
import java.util.ArrayList;

import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.persistence.AbstractPersistentActor;
import akka.persistence.SnapshotOffer;

public class ExamplePersistentActor extends AbstractPersistentActor {

	public static class Cmd implements Serializable {
	    private static final long serialVersionUID = 1L;
	    private final String data;

	    public Cmd(String data) {
	        this.data = data;
	    }

	    public String getData() {
	        return data;
	    }
	}

	public static class Evt implements Serializable {
	    private static final long serialVersionUID = 1L;
	    private final String data;

	    public Evt(String data) {
	        this.data = data;
	    }

	    public String getData() {
	        return data;
	    }
	}

	public static class ExampleState implements Serializable {
	    private static final long serialVersionUID = 1L;
	    private final ArrayList<String> events;

	    public ExampleState() {
	        this(new ArrayList<>());
	    }

	    public ExampleState(ArrayList<String> events) {
	        this.events = events;
	    }

	    public ExampleState copy() {
	        return new ExampleState(new ArrayList<>(events));
	    }

	    public void update(Evt evt) {
	        events.add(evt.getData());
	    }

	    public int size() {
	        return events.size();
	    }

	    @Override
	    public String toString() {
	        return events.toString();
	    }
	}
	
	
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	  private ExampleState state = new ExampleState();
	    private int snapShotInterval = 5;

	    public int getNumEvents() {
	        return state.size();
	    }
	
	@Override
	public String persistenceId() {
		
		return getSelf().path().name();
	}

	@Override
	public Receive createReceiveRecover() {
		/* 스냅샷 복구후 저널복구가 들어간다.*/
		 return receiveBuilder()
		            .match(Evt.class, (e)->{
		            	/* 저널 복구*/
		            	log.info("[Recover] state update = {}",e.data);
		            	state.update(e);
		            } )
		            .match(SnapshotOffer.class, ss ->{
		            	/* 스냅샷 복구 */
		            	log.info("[Recover] SnapshotOffer update = {}",ss.snapshot());
		            	state = (ExampleState) ss.snapshot();
		            })
		            .build();
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
	            .match(Cmd.class, c -> {
	              final String data = c.getData();
	              final Evt evt = new Evt(data + "-" + getNumEvents());
	              
	              log.info("[Receive] Evt = {}",evt.data);
	              
	              persist(evt, (Evt e) -> {
	                  state.update(e);
	                  getContext().getSystem().eventStream().publish(e);
	                  if (lastSequenceNr() % snapShotInterval == 0 && lastSequenceNr() != 0)
	                      // IMPORTANT: create a copy of snapshot because ExampleState is mutable
	                	  /* 스냅샷 저장시 해당 시점의 저널들은 모두 사라진다. 스냅샷 저장으로 인해 이전 버전의 저널들은 필요없어지기 때문이다.*/
	                      saveSnapshot(state.copy());
	                  
	              });
	            })
	            .matchEquals("print", s -> System.out.println(state))
	            .matchEquals("delete", s -> {
	            	 log.info("[delete] ");
	            	 deleteMessages(lastSequenceNr());
	            	 deleteSnapshot(snapshotSequenceNr());
	            	 
	            })
	            .build();
	}

}
