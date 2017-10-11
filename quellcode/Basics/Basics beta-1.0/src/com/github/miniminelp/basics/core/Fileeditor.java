package com.github.miniminelp.basics.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
@author Nicolas Schmidt
@copy (c) Nicolas Schmidt 2017
*/

public class Fileeditor {
	//save
	public static void saveObject(Object object, String saveFile) throws IOException{
		FileOutputStream fos = new FileOutputStream(saveFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.close();
	}
	
	public static void write(String s, File f) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter(f.getPath(), "UTF-8");
	    writer.print(s);
	    writer.close();
	}
	
	//load
	public static String loadString(InputStream source) throws Exception{
		Scanner s = new Scanner(source);
		String ret = "";
		while(s.hasNextLine()){
			ret+=s.nextLine()+"\n";
		}
		s.close();
		return ret;	
	}
	
	
	public static Object loadObject(String saveFile) throws IOException, ClassNotFoundException, Exception{
		FileInputStream fis = new FileInputStream(saveFile);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object out = (Object) ois.readObject();
        ois.close();
        return out;
	}
}