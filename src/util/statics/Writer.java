package util.statics;

import java.io.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Writer {
	String filename;
	String extension;

	BufferedWriter writer;

	public Writer(String filename){
		this(filename,false);
	}
	public Writer(String filename, boolean append){
		// create the path
		try {
			new File(filename).getParentFile().mkdirs();
		} catch (Exception e){
			System.out.println(e.toString());
		}
		// create the actual log
		try {
			writer = new BufferedWriter(new FileWriter(filename,append));
		} catch (Exception e){
			System.out.println(e.toString());
		}
		this.filename=filename;
		this.extension="";
		String[] allExtensions=filename.split(".");
		if(allExtensions.length>0){
			this.extension=allExtensions[allExtensions.length-1];
		}
	}

	public void write(String text){
		try{
			writer.append(text+"\n");
			writer.flush();
			//writer.close();
		} catch (Exception e){
			System.out.println(e.toString());
		}
	}

	public void writeError(Object callingObject, String text){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String timestamp = dateFormat.format(date);
		String className = "";
		if(callingObject!=null){ className = callingObject.getClass().getName(); }
		String printoutText = timestamp+" "+className+" "+text;
		this.write(printoutText);
		System.out.println(printoutText);
	}
	
	public void close(){
		try{
			writer.close();
		} catch (Exception e){
			System.out.println(e.toString());
		}
	}
}
