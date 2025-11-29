package spring.testPetClinic.testCases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import spring.testPetClinic.Utility.Log;
import spring.testPetClinic.base.BaseClass;
import spring.testPetClinic.pageObjects.Home;

public class HomePageTest extends BaseClass {
	private Home home;

	@BeforeMethod
	public void setup() {
		launchApp();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	
	  @Test public void pruefenHomePage() { Log.startTestCase("pruefenHomePage");
	  Log.info("Pruefen das Haustierbild und Welcome angezeigt."); home = new
	  Home(); boolean result = home.pruefenHome(); Assert.assertTrue(result);
	  Log.info("Das Haustierbild und Welcome werden angezeigt.");
	  Log.endTestCase("pruefenHomePage");
	  
	  }
	 

}
