package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppSettings {
	private static AppSettings instance = null;
	private Properties props = null;
	
	private AppSettings() {
		System.out.println("Properties initialization...");
		InputStream fis;
		try {
//			fis = new FileInputStream("input/config.properties");
			InputStream is = this.getClass().getResourceAsStream("/config.properties");
			props = new Properties();
			props.load(is);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Properties getProps() {
		if(props == null) {
			new AppSettings();
		}
		return props;
	}
	

	public static synchronized AppSettings getInstance() {
		if(instance == null) {
			instance = new AppSettings();
		}
		return instance;
	}
	
	
	public String getProperty(String key) {
		String value = null;
		if(!props.containsKey(key)){
			System.out.println("KEY :"+key+" NOT FOUND!");
		} else {
			value = (String) props.get(key);
		}
		return value;
	}
	
	public static void main(String[] args) {
		for (int i = 1; i < 11; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					System.out.println(Thread.currentThread());
					String val = AppSettings.getInstance().getProperty("db.login");
					System.out.println(val);
					
				}
			}, "TH #"+i).start();
		}
	}
	
}
