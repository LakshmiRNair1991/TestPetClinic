package spring.testPetClinic.testCases;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import spring.testPetClinic.Utility.Log;
import spring.testPetClinic.base.BaseClass;
import spring.testPetClinic.dataProvider.DataProviders;
import spring.testPetClinic.pageObjects.AddOwner;
import spring.testPetClinic.pageObjects.FindOwners;
import spring.testPetClinic.pageObjects.HeaderComponent;
import spring.testPetClinic.pageObjects.OwnerInformation;

public class AddOwnerTest extends BaseClass {
	private HeaderComponent headerComponent;
	private FindOwners findOwners;
	private AddOwner addOwner;
	private OwnerInformation ownerInformation;

	@BeforeMethod
	public void setup() {
		launchApp();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(dataProvider = "ownerDetailsProvider", dataProviderClass = DataProviders.class)
	public void anlegenEinesTierhalters(Map<String, String> details) {
		Log.startTestCase("anlegenEinesTierhalters");
		headerComponent = new HeaderComponent();
		findOwners = new FindOwners();
		addOwner = new AddOwner();
		Log.info("Klicken Auf FindOwners");
		headerComponent.KlickeAufFindOwners();
		findOwners.pruefenFindOwners();
		Log.info("Die Seite „FindOwners“ wurde geladen.");
		Log.info("Klicken Auf AddOwners");
		addOwner = findOwners.KlickeAufAddOwner();
		addOwner.pruefenErstellenOwners();
		Log.info("Die Seite „AddOwners“ wurde geladen.");
		ownerInformation = addOwner.erstellenOwner(details);
		if (ownerInformation != null) {
			Assert.assertTrue(ownerInformation.erfolgMsgWirdAngezeigt().contains("New Owner Created"),
					"Neu Owner ist nicht erstellt");
			String ownerName = ownerInformation.bekommenOwnerName();
			Assert.assertTrue(ownerName.contains(details.get("firstName")), "Owner Name ist nicht erstellt");
			Log.info("Owner erstellt: " + ownerName);
		} else {
			Log.info("Einige Felder sind leer oder die Telefonnummer ist ungültig. ");
			Assert.fail("Einige Felder sind leer oder die Telefonnummer ist ungültig.");
		}
		Log.info("Erstellte einen Tierhalter.");
		Log.endTestCase("anlegenEinesTierhalters");
	}

}
