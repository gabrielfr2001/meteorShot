package ds;

import org.openqa.selenium.chrome.ChromeDriver;

public class Rank {
	public static void main(String[] args) {
		ChromeDriver driver = new ChromeDriver();

		driver.get("http://dontpad.com/roskowski/DAO");

		String innerHtml = "<head>...</head><body>...<div><textarea id=\"text\"></textarea></div>...</body>";
		driver.executeScript("document.innerHTML = " + innerHtml);
		driver.close();
	}
}
