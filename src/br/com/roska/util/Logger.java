package br.com.roska.util;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	public static String DEFAULT_PATH = "logs";
	public static String DEFAULT_NAME = File.separator + "N.txt";

	private StackTraceElement[] stack;
	private PrintWriter out;
	public static boolean log = false;

	public Logger() {
		File f = new File(DEFAULT_PATH);
		try {
			if (!f.exists()) {
				f.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void log(Exception e) {
		if (log) {
			File f = new File(DEFAULT_PATH);

			try {
				if (!f.exists()) {
					f.mkdir();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			stack = e.getStackTrace();

			try {
				PrintWriter out = new PrintWriter(
						DEFAULT_PATH + DEFAULT_NAME.replace("N", Long.toString(System.currentTimeMillis())));

				this.out = out;

				printCabecalho();

				println("localized message - " + e.getLocalizedMessage());
				println("message - " + e.getMessage());
				println("cause - " + e.getCause());
				println(e.getClass().getSimpleName());
				println(e);

				printEnd();

				out.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private void printEnd() {
		println("\r\n########-JAVA ROSCA LOGGER-########");
	}

	private void printCabecalho() {

		println("########-JAVA ROSCA LOGGER-########");
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
		Date date = new Date();
		println(dateFormat.format(date));
		println("Contate o suporte caso você esteja vendo esta mensagem - gabrielfroskowski2001@gmail.com");
		println("\r\n");

	}

	private void println(String message) {
		this.out.print(message + "\r\n");

	}

	private void println(Exception e) {
		for (int i = 0; i < stack.length; i++) {
			this.out.print(stack[i].toString() + "\r\n");
		}
	}
}
