package org.onecellboy.db.hibernate;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.MarkerManager;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.ext.EventData;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

public class SLF4JTest {

	
	private static final   XLogger xLogger = XLoggerFactory.getXLogger(SLF4JTest.class);
	
	  //
	
	@Test
	public void testFlowTracing() {
		
		xLogger.entry("1","2");
		
		try {
			throw new Exception();
		}catch (Exception e) {
			xLogger.catching( e );
		}
		
		
		xLogger.exit();
		
	}
	
	
	@Test
	public void testMarker() {
		 XLogger xLogger = XLoggerFactory.getXLogger(SLF4JTest.class);
		 Marker sqlMarker = MarkerFactory.getMarker("SQL");
		 Marker updateMarker = MarkerFactory.getMarker("SQL_UPDATE");
		 Marker updateMarker3 = MarkerFactory.getMarker("SQL_UPDATE2");
		 
		 updateMarker.add(sqlMarker);
		 updateMarker3.add(updateMarker);
		
		 xLogger.debug(sqlMarker, "sqlMarker log");
		 
		 xLogger.debug(updateMarker, "updateMarker log");
		 
		 xLogger.debug(updateMarker3, "updateMarker log");
	
		 
		
	}
	
	
	@Test
	public void testEventLogger() {
	
		
		
		String toAccount = "Ã¶¼ö";
		String fromAccount = "¿µÈñ";
		int amount = 5000;
		
	   
		
		
		EventData eventData = new EventData();
		eventData.setEventDateTime(new Date());
		eventData.setEventType("transfer");
	    String confirm = UUID.randomUUID().toString();
	    eventData.setEventId(confirm);
	    eventData.put("toAccount", toAccount);
	    eventData.put("fromAccount", fromAccount);
	    eventData.put("amount", amount);
	    eventData.setMessage(" message .....");

		org.slf4j.ext.EventLogger.logEvent(eventData);
	}
	
	
	
	@Test
	public void testMDC() {
		 MDC.put("userid", "guest");
		 MDC.put("userid2", "guest");
		 func1();
		 
		 func2();
		 
		 MDC.clear();
		 
		 func1();
		
	}
	
	
	private void func1()
	{
		 XLogger xLogger = XLoggerFactory.getXLogger(SLF4JTest.class);
		 
		 xLogger.debug("func1 log");
		 
		 
		
	}
	

	private void func2()
	{
		 XLogger xLogger = XLoggerFactory.getXLogger(SLF4JTest.class);
		 
		 xLogger.error("func2 log");
		
	}
	
	
}
