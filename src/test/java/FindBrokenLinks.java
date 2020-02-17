import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FindBrokenLinks {

	@Test
	public void checkLinks() throws InterruptedException {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.softwaretestingmaterial.com");

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		List<WebElement> links = driver.findElements(By.tagName("a"));
		int totalLinks = links.size();
		for (int i = 0; i < totalLinks; i++) {
			WebElement element = links.get(i);
			// By using "href" attribute, we could get the url of the requried link
			String url = element.getAttribute("href");
			// System.out.println(url);
			// calling verifyLink() method here. Passing the parameter as url which we
			// collected in the above link
			// See the detailed functionality of the verifyLink(url) method below
			// Thread.sleep(2000L);
			verifyLink(url);

		}

	}

	// The below function verifyLink(String urlLink) verifies any broken links and
	// return the server status.
	public static void verifyLink(String urlLink) {
		try {
			// Use URL Class - Create object of the URL Class and pass the urlLink as
			// parameter
			URL link = new URL(urlLink);
			// Create a connection using URL object (i.e., link)
			HttpURLConnection httpConn = (HttpURLConnection) link.openConnection();
			// Set the timeout for 2 seconds
			httpConn.setConnectTimeout(3000);
			// connect using connect method
			httpConn.connect();
			// use getResponseCode() to get the response code.
			if (httpConn.getResponseCode() == 200) {
				System.out.println(urlLink + " - " + httpConn.getResponseMessage());
				System.out.println("EverythingOK");
			}
			if (httpConn.getResponseCode() == 404) {
				System.out.println(urlLink + " - " + httpConn.getResponseMessage());
			}

		}
		// getResponseCode method returns = IOException - if an error occurred
		// connecting to the server.
		catch (Exception e) {
			e.printStackTrace();

		}

	}

}
