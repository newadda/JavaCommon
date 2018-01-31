package org.oncellboy.globalization;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;


/**
 * √‚√≥ : ±Ó∏‘¿Ω..
 * */
public class CharsetControl extends Control {

	static final String FOTMAT = "properties";
	
	String charsetName="UTF-8";
	
	
	public CharsetControl() {}
	
	public CharsetControl(String charsetName)
	{
		this.charsetName= charsetName;
	}
	

	@Override
    public List<String> getFormats(String baseName) {

		 return Collections.singletonList(FOTMAT);
       

    }
	                  
	
	@Override
	public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
			throws IllegalAccessException, InstantiationException, IOException {
		
		  String bundleName = toBundleName(baseName, locale);
		  String resourceName = toResourceName(bundleName, format);
		  URL resourceURL = loader.getResource(resourceName);
		  if (resourceURL == null) {

			  resourceName = toResourceName(baseName, format);
			  resourceURL = loader.getResource(resourceName);
			  if(resourceURL==null) {return null;}
		  }
		  
		  InputStream stream = getResourceInputStream(resourceURL, reload);
		  
		  try
	        {
				InputStreamReader input = new InputStreamReader(stream, Charset.forName(this.charsetName));
				
	            PropertyResourceBundle result = new PropertyResourceBundle(input);
	            return result;
	        }
	        finally
	        {
	            stream.close();
	        }
		  
	}
	
	 private InputStream getResourceInputStream(final URL resourceURL,
             boolean reload)
			throws IOException {
	
		 
		if (!reload)
			return resourceURL.openStream();

		try {
			// This permission has already been checked by
			// ClassLoader.getResource(String), which will return null
			// in case the code has not enough privileges.
			return AccessController.doPrivileged(new PrivilegedExceptionAction<InputStream>() {
				public InputStream run() throws IOException {
					URLConnection connection = resourceURL.openConnection();
					connection.setUseCaches(false);
					return connection.getInputStream();
				}
			});
		} catch (PrivilegedActionException x) {
			throw (IOException) x.getCause();
		}
	}

}
