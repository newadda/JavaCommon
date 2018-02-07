package org.onecellboy.akka.test;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;

import org.apache.commons.math3.exception.util.ArgUtils;
import org.junit.Test;

import static io.vavr.Patterns.*;
import static io.vavr.API.*;

public class Vavr {

	@Test
	public void test() {
		ExecutionException executionException = new ExecutionException(null);
		
		if(executionException instanceof Exception)
		{
			System.out.println("zz");
		}
		
		/*
		String _try="test";
		
		Match(_try).of(
			    Case($Success($("test")), value ->{return null;} ),
			    Case($Success($("test")), x -> {return null;})
			);
		*/
	//	System.out.println(s);
		
		
	}

	

}
