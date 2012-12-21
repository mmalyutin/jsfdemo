package org.coder36.webdemo.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;

/**
 * Web Demo Controller
 * @author Mark Middleton
 */
@ManagedBean
@SessionScoped
public class WebDemoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<DataContainer> data = new ArrayList<DataContainer>();
	private String systemProperty;
	private String environmentVariable;
	private String jndi;

	public List<String> systemPropertyAutoComplete( String query ) {
		List<String> l = new ArrayList<String>();
		Properties p = System.getProperties();
		for( String s: p.stringPropertyNames() ) {
			if( s.toUpperCase().startsWith( query.toUpperCase() ) ) {
				l.add( s );
			}
		}
				
		return l;
	}	
	
	// doesnt work!
	public List<String> jndiAutoComplete( String query ) {
		List<String> l = new ArrayList<String>();
		try {			
			InitialContext ctx = new InitialContext();
			NamingEnumeration<NameClassPair> list = ctx.list( query );
			while (list.hasMore()) {
				String s = list.next().getName();
				if( s.toUpperCase().startsWith( query.toUpperCase() ) ) {
					l.add( s );
				}				
			}			
		}
		catch( Exception e ) {
		}
		return l;
	}
	
	public List<String> envinronmentVariableAutoComplete( String query ) {
		List<String> l = new ArrayList<String>();
		Map<String,String> p = System.getenv();
		for( String s: p.keySet() ) {
			if( s.toUpperCase().startsWith( query.toUpperCase() ) ) {
				l.add( s );
			}
		}
		return l;
	}
	
	public void reset() {
		data = new ArrayList<DataContainer>();
	}
	
	public void findEnvironmentVariable() {
		DataContainer c = new DataContainer( environmentVariable, environmentVariable.isEmpty() ? null : System.getenv( environmentVariable )  );
		data.add(c);
	}	
	
	public void findSystemProperty() {
		DataContainer c = new DataContainer( systemProperty, systemProperty.isEmpty() ? null : System.getProperty( systemProperty ) );
		data.add(c);
	}
	
	public void findJndi() {
		try {
			InitialContext ctx = new InitialContext();
			Object o = ctx.lookup( jndi );
			DataContainer c = new DataContainer( jndi, o == null ? null : o.toString() );
			data.add(c);
		}
		catch( Exception e ) {
			DataContainer c = new DataContainer( jndi, null );
			data.add(c);
		}
	}

	public List<DataContainer> getData() {
		return data;
	}

	public String getSystemProperty() {
		return systemProperty;
	}

	public void setSystemProperty(String systemProperty) {
		this.systemProperty = systemProperty;
	}
	
	public String getEnvironmentVariable() {
		return environmentVariable;
	}

	public void setEnvironmentVariable(String envProperty) {
		this.environmentVariable = envProperty;
	}	
	
	public String getJndi() {
		return jndi;
	}

	public void setJndi(String jndi) {
		this.jndi = jndi;
	}	

	public class DataContainer implements Serializable {

		private static final long serialVersionUID = 1L;
		private Date timestamp = new Date();
		private String propName;
		private String propValue;		
		
		public DataContainer( String propName, String propValue ) {
			this.propName = propName;
			this.propValue = propValue;
		}
		
		public String getPropName() {
			return propName;
		}

		public String getPropValue() {
			return propValue;
		}

		public Date getTimestamp() {
			return timestamp;
		}

	}

}