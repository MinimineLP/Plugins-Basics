package test;

import java.io.IOException;

public class startbat {
	public static void main(String[] args) {
		try {
			Runtime.getRuntime().exec("cmd /c start \"\" C:/Users/Nicolas/Desktop/pluginsprogramm/start.bat");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
