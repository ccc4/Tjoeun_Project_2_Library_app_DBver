package db.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class DB_Info {
	private File file;
	private Properties propertie;
	
	public DB_Info(String fileName) {
		file = new File(fileName);
		
		propertie = new Properties();
		
		try {
			this.propertie.load(new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8")));
		} catch (Exception e) {
			propertie = null;
		}
	}
	public String readProperties(String key) {
		if(propertie == null) return "";
		
		return propertie.getProperty(key);
	}
}
