package org.onecellboy.globalization;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.TimeZone;

import org.junit.Test;
import org.logicalcobwebs.cglib.core.Local;
import org.oncellboy.globalization.CharsetControl;
import org.oncellboy.globalization.XMLResourceBundle;
import org.oncellboy.globalization.XMLResourceBundleControl;;

public class ResourceBundleTest {
	
	@Test
	public void test() throws IOException
	{
		// properties ������ UTF-8�� �����߰� �� �ڹٰ� UTF-8�� �⺻���� ����ϰ� �Ѵ�.
		
		System.setProperty("file.encoding", "UTF-8");
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
		
		System.out.println("==�ѱ���==");
		
		// new Locale(���, ����);
		Locale aLocale = new Locale("ko","KR");
		
		// {���ҽ� ���..}/{���ҽ� ����Ʈ �̸�}
		String propertyPath = "language/lang";
		//Ȥ�� ��Ű�� ��� �������� ��� ����
		//String propertyPath = "language.lang";
		
		
		ResourceBundle bundle=null;
		
		/*Locale */
		//Locale aLocale = new Locale.Builder().setLanguage("ko").setRegion("KR").build();
		
		/*��� 1 �ڹ� charset �� ����*/
		//ResourceBundle bundle = ResourceBundle.getBundle("language/lang",aLocale);
     	
		/*��� 2 UTF-8 charset ����� ����(������ UTF-8 �� �����)*/
		/*
		InputStream stream = getClass().getClassLoader().getResourceAsStream(propertyPath+"_ko_KR.properties");
		InputStreamReader input = new InputStreamReader(stream, Charset.forName("UTF-8"));
		bundle=new PropertyResourceBundle(input);
     	*/
	
		

		System.out.println("========= �⺻ ResourceBundle.getBundle() �̿� =========");
		System.out.println("==�ѱ��� ko_KR==");
		aLocale = new Locale("ko","KR");
		bundle = ResourceBundle.getBundle(propertyPath,aLocale);
		System.out.println("resourece one = "+bundle.getString("one"));
	
		
		System.out.println("==�Ϻ��� ja_JP==");
		aLocale = new Locale("ja","JP");
		bundle = ResourceBundle.getBundle(propertyPath,aLocale);
		System.out.println("resourece one = "+bundle.getString("one"));

		
		System.out.println("==�߱��� ���� en_CN==");
		aLocale = new Locale("en","CN");
		bundle = ResourceBundle.getBundle(propertyPath,aLocale);
		System.out.println("resourece one = "+bundle.getString("one"));
	

		// ���ҽ� ���� ĳ�� �ʱ�ȭ
		ResourceBundle.clearCache();
		
		
		System.out.println("========= ���ڵ� �� property ������ ���� �� �ְ� Custom Control�� ����� ResourceBundle.getBundle() =========");
		System.out.println("==�ѱ��� ko_KR==");
		aLocale = new Locale("ko","KR");
		bundle = ResourceBundle.getBundle(propertyPath,aLocale,new CharsetControl());
		System.out.println("resourece one = "+bundle.getString("one"));
	
		
		System.out.println("==�Ϻ��� ja_JP==");
		aLocale = new Locale("ja","JP");
		bundle = ResourceBundle.getBundle(propertyPath,aLocale,new CharsetControl());
		System.out.println("resourece one = "+bundle.getString("one"));
	
		
		System.out.println("==�߱��� ���� en_CN==");
		aLocale = new Locale("en","CN");
		bundle = ResourceBundle.getBundle( propertyPath,aLocale,new CharsetControl());
		System.out.println("resourece one = "+bundle.getString("one"));

	
		aLocale = new Locale("ko","KR");
		bundle = ResourceBundle.getBundle( "language/lang",aLocale,new XMLResourceBundleControl());
		System.out.println("resourece one = "+bundle.getString("one"));

		
	
	   
	
		
	}
	

}
