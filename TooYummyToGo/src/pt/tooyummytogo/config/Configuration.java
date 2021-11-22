package pt.tooyummytogo.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Configuration {

	private static Configuration INSTANCE = new Configuration();
	private Properties p = new Properties();

	public static Configuration getInstance() {
		return INSTANCE;
	}

	private Configuration() {
		try {
			p.load(new FileInputStream ("defaults.properties"));
		} catch (IOException e) {
			//do nothing
		}
	}
	
	public String getProperty(String property) {
		return p.getProperty(property);
	}
	
	
	
}
