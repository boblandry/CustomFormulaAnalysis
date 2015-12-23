package com.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class OutputXML {

	public static void output(String xml){
		Date date = new Date();
		String t = String.valueOf(date.getTime());
		File file = new File(t+".xml");
		FileOutputStream out = null;
		try {
			if(!file.exists()){
			
				file.createNewFile();
			}
			out = new FileOutputStream(file);
			StringBuffer sb = new StringBuffer();
			sb.append(xml);
			out.write(sb.toString().getBytes("UTF-8"));
			out.close();	
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(file.getAbsolutePath());
	}
	
}
