package base_class;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class Base_class {

	protected WebDriver driver;

	@BeforeClass
	public void browser_setup() throws Exception {

		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@AfterClass
	public void tear_down() throws Exception {
		if (driver != null) {
			driver.quit();

		}

	}

}
