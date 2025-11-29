package spring.testPetClinic.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import io.github.bonigarcia.wdm.WebDriverManager;
import spring.testPetClinic.Utility.ExtentManager;

public class BaseClass {
	
	public static Properties prop;
	public static WebDriver driver;
	
	

		
	@BeforeSuite
	public void ladenConfig() {
		ExtentManager.setExtent();
	
		try {
			prop = new Properties();
			System.out.print(System.getProperty("user.dir"));
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "\\Konfiguration\\konfig.properties");
			prop.load(ip);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public void launchApp() {
		
		
		String browsername= prop.getProperty("browser");
		if (browsername.contains("Edge")) {
			WebDriverManager.edgedriver().setup();
			driver=new EdgeDriver();
			// Set Browser to ThreadLocalMap
		} else if (browsername.contains("Chrome")) {
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(prop.getProperty("url"));
		
	}
	@AfterSuite
	public void afterSuite() {
		ExtentManager.endReport();
	}

	

}
	


