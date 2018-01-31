package org.oncellboy.globalization;

import java.util.Enumeration;

import java.util.ResourceBundle;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;



/**
 * 형식
 * <?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
<properties>
<comment>labels francais</comment>
<entry key="maCle1">la valeur de la cle 1</entry>
<entry key="maCle2">la valeur de la cle 2</entry>
<entry key="maCle3">la valeur de la cle 3</entry>
</properties>
 * 
 * 출처 : https://gist.github.com/asicfr/1b76ea60029264d7be15d019a866e1a4#file-xmlresourcebundle-java
 * */
public class XMLResourceBundle extends ResourceBundle {

	private Properties props;

	public XMLResourceBundle(InputStream stream) throws IOException {
		props = new Properties();
		props.loadFromXML(stream);
	}

	@Override
	protected Object handleGetObject(String key) {
		return props.getProperty(key);
	}

	@Override
	public Enumeration<String> getKeys() {
		Set<String> handleKeys = props.stringPropertyNames();
		return Collections.enumeration(handleKeys);
	}

}
