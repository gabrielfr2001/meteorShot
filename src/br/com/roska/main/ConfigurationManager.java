package br.com.roska.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import br.com.roska.util.Logger;

public class ConfigurationManager {
	public static final String s = File.separator;
	public static final String PATH = "";
	public static final String FILE = "configuration.txt";

	@SuppressWarnings("resource")
	private String read() {

		BufferedReader br = null;
		FileReader fr = null;
		String str = "";
		try {

			fr = new FileReader(PATH + FILE);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(PATH + FILE));

			while ((sCurrentLine = br.readLine()) != null) {
				str += sCurrentLine;
			}
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
				Painter.logger.log(e);
			}

		} catch (IOException e) {
			e.printStackTrace();
			Painter.logger.log(e);
		}
		return str;
	}

	public void reload() {
		try {
			File f = new File(PATH + FILE);
			if (f.exists() && !f.isDirectory()) {
				String re[] = read().split(";");
				for (int i = 0; i < re.length; i++) {
					String aa[] = re[i].split("=");
					String a = aa[0];
					String s = aa[1];
					switch (a) {
					case "play_sound":
						Painter.playsounds = b(s);
						break;
					case "log":
						Logger.log = b(s);
						break;
					default:
						break;
					}
				}
			}
		} catch (Exception e) {
			Painter.logger.log(e);
		}
	}

	private boolean b(String b) {
		return Boolean.parseBoolean(b);
	}

	@SuppressWarnings("unused")
	private int i(String b) {
		return Integer.parseInt(b);
	}

	@SuppressWarnings("unused")
	private double d(String b) {
		return Double.parseDouble(b);
	}
}
