package br.com.roska.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {

	public static String DEFAULT_PATH = "";
	public static String DEFAULT_NAME = "LOG.txt";
	private StackTraceElement[] stack;

	public void log(String str) {
		String txt = load();
		try (PrintWriter out = new PrintWriter(DEFAULT_PATH + DEFAULT_NAME)) {
			out.println(txt);
			out.println(str);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	private String load() {
		String totalText = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(DEFAULT_PATH + DEFAULT_NAME));
			String sCurrentLine = "";
			while ((sCurrentLine = br.readLine()) != null) {
				totalText += sCurrentLine + "\n";
			}
			totalText += "\n";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return totalText;
	}

	public void log(Exception e) {
		stack = e.getStackTrace();
		String txt = load();
		try (PrintWriter out = new PrintWriter(DEFAULT_PATH + DEFAULT_NAME)) {
			out.println(txt);
			out.println("++" + e.toString());
			for (int i = 0; i < stack.length; i++) {
				out.println(stack[i].toString());
			}

		} catch (FileNotFoundException err) {
			err.printStackTrace();
		}
	}
}
