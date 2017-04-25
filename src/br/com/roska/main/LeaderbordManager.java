package br.com.roska.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class LeaderbordManager {
	public static final String s = File.separator;
	public static final String PATH = "";
	public static final String FILE = "leaderboard.txt";
	public static final String DEFAULT_FORMAT = "NUMBER° - NOME, PONTOS pontos";
	private static final int MAX = 5;

	public void register(String nome, long pontos) {
		try {

			String strsDef[] = new String[MAX];
			String strs[] = read().split(";");

			if (strs.length > 0 && !strs[0].equals("")) {
				long keys[] = new long[MAX + 1];

				HashMap<Long, String> map = new HashMap<>();

				for (int i = 0; i < strs.length; i++) {

					String[] parts = strs[i].split("- ");
					String[] parts0 = parts[1].split(", ");
					parts0[1] = parts0[1].replace(" pontos", "");

					map.put(Long.parseLong(parts0[1]), parts0[0]);

					keys[i] = Long.parseLong(parts0[1]);

				}

				Arrays.sort(keys);

				keys[0] = pontos;
				map.put(pontos, nome);

				Arrays.sort(keys);

				strsDef = new String[5];

				for (int i = MAX; i > 0; i--) {
					try {
						if (map.get(keys[i]) != null)
							strsDef[MAX - i] = DEFAULT_FORMAT.replace("NUMBER", Integer.toString(MAX - i + 1))
									.replace("NOME", map.get(keys[i])).replace("PONTOS", Long.toString(keys[i]));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				strsDef = new String[1];
				strsDef[0] = DEFAULT_FORMAT.replace("NUMBER", Long.toString(1)).replace("NOME", nome).replace("PONTOS",
						Long.toString(pontos));
			}

			String str = "";
			for (int i = 0; i < strsDef.length; i++) {
				if (strsDef[i] != null) {
					if (!strsDef[i].contains("null")) {
						str += strsDef[i];
						if (i != strsDef.length - 1) {
							str += ";";
						}
					}
				}
			}

			BufferedWriter out = new BufferedWriter(new FileWriter(PATH + FILE));
			out.write(str);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String[] getRanking() {
		return read().split(";");
	}

	@SuppressWarnings("resource")
	private String read() {

		BufferedReader br = null;
		FileReader fr = null;
		String str = "";
		File f = new File(PATH + FILE);
		try {
			if (f.exists() && !f.isDirectory()) {

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
				}

			} else {
				f.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
}
