package br.com.roska.util;

public class Criptographer {

	private static final String REGEX = "%";

	public Criptographer(String seed) {
	}

	public String encripto(String str) {
		try {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < str.length(); i++) {
				if (i != str.length() - 1) {
					sb.append(criptoChar(str.charAt(i)) + REGEX);
				} else {
					sb.append(criptoChar(str.charAt(i)));
				}
			}
			return sb.toString();
		} catch (Exception e) {
			return "";
		}
	}

	private String criptoChar(int a) {
		try {
			StringBuilder sb = new StringBuilder();
			for (int i = 256;; i /= 2) {
				if (i == 0) {
					break;
				}
				boolean e = a >= i;
				a = a >= i ? a - i : a;
				sb.append(e ? "1" : "0");
			}
			String str = sb.toString();
			int i = 0;
			while (true && i < str.length()) {
				if (str.charAt(i) == '1') {
					return str.substring(i, str.length());
				}
				i++;
			}
			return "0";
		} catch (Exception e) {
			return "";
		}
	}

	private char decriptoChar(String charAt) {
		try {
			return (char) Integer.parseInt(decode(charAt));
		} catch (Exception e) {
			return ' ';
		}
	}

	private String decode(String charAt) {
		try {
			return Integer.toString(Integer.parseInt(charAt, 2));
		} catch (Exception e) {
			return "";
		}
	}

	public String decripto(String str) {
		try {
			String[] strings = str.split(REGEX);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < strings.length; i++) {
				sb.append(decriptoChar(strings[i]));
			}
			return sb.toString();
		} catch (Exception e) {
			return "";
		}
	}

}
