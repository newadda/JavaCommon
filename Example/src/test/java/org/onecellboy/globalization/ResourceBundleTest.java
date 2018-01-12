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
		// properties 파일을 UTF-8로 저장했고 난 자바가 UTF-8을 기본으로 사용하게 한다.
		
		System.setProperty("file.encoding", "UTF-8");
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
		
		System.out.println("==한국어==");
		
		// new Locale(언어, 지역);
		Locale aLocale = new Locale("ko","KR");
		
		// {리소스 경로..}/{리소스 디폴트 이름}
		String propertyPath = "language/lang";
		//혹은 패키지 경로 형식으로 경로 지정
		//String propertyPath = "language.lang";
		
		
		ResourceBundle bundle=null;
		
		/*Locale */
		//Locale aLocale = new Locale.Builder().setLanguage("ko").setRegion("KR").build();
		
		/*방법 1 자바 charset 에 의존*/
		//ResourceBundle bundle = ResourceBundle.getBundle("language/lang",aLocale);
     	
		/*방법 2 UTF-8 charset 명시적 지정(파일이 UTF-8 로 저장시)*/
		/*
		InputStream stream = getClass().getClassLoader().getResourceAsStream(propertyPath+"_ko_KR.properties");
		InputStreamReader input = new InputStreamReader(stream, Charset.forName("UTF-8"));
		bundle=new PropertyResourceBundle(input);
     	*/
	
		

		System.out.println("========= 기본 ResourceBundle.getBundle() 이용 =========");
		System.out.println("==한국어 ko_KR==");
		aLocale = new Locale("ko","KR");
		bundle = ResourceBundle.getBundle(propertyPath,aLocale);
		System.out.println("resourece one = "+bundle.getString("one"));
	
		
		System.out.println("==일본어 ja_JP==");
		aLocale = new Locale("ja","JP");
		bundle = ResourceBundle.getBundle(propertyPath,aLocale);
		System.out.println("resourece one = "+bundle.getString("one"));

		
		System.out.println("==중국내 영어 en_CN==");
		aLocale = new Locale("en","CN");
		bundle = ResourceBundle.getBundle(propertyPath,aLocale);
		System.out.println("resourece one = "+bundle.getString("one"));
	

		// 리소스 번들 캐쉬 초기화
		ResourceBundle.clearCache();
		
		
		System.out.println("========= 인코딩 된 property 파일을 읽을 수 있게 Custom Control을 사용한 ResourceBundle.getBundle() =========");
		System.out.println("==한국어 ko_KR==");
		aLocale = new Locale("ko","KR");
		bundle = ResourceBundle.getBundle(propertyPath,aLocale,new CharsetControl());
		System.out.println("resourece one = "+bundle.getString("one"));
	
		
		System.out.println("==일본어 ja_JP==");
		aLocale = new Locale("ja","JP");
		bundle = ResourceBundle.getBundle(propertyPath,aLocale,new CharsetControl());
		System.out.println("resourece one = "+bundle.getString("one"));
	
		
		System.out.println("==중국내 영어 en_CN==");
		aLocale = new Locale("en","CN");
		bundle = ResourceBundle.getBundle( propertyPath,aLocale,new CharsetControl());
		System.out.println("resourece one = "+bundle.getString("one"));

	
		aLocale = new Locale("ko","KR");
		bundle = ResourceBundle.getBundle( "language/lang",aLocale,new XMLResourceBundleControl());
		System.out.println("resourece one = "+bundle.getString("one"));

		
	
	   
	
		
	}
	

}
