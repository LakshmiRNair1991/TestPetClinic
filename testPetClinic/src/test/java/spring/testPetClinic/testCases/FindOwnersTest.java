package spring.testPetClinic.testCases;

import java.util.List;
import java.util.Optional;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import spring.testPetClinic.Utility.Log;
import spring.testPetClinic.base.BaseClass;
import spring.testPetClinic.dataProvider.DataProviders;
import spring.testPetClinic.pageObjects.FindOwners;
import spring.testPetClinic.pageObjects.HeaderComponent;
import spring.testPetClinic.pageObjects.OwnerInformation;
import spring.testPetClinic.pageObjects.Owners;

public class FindOwnersTest extends BaseClass {
	private HeaderComponent headerComponent;
	private FindOwners findOwners;
	private OwnerInformation ownerInformation;
	private Owners owners;

	@BeforeClass
	public void setup() {
		launchApp();
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

	public void sucheTierhalter(String lastName) {
		headerComponent = new HeaderComponent();
		findOwners = new FindOwners();
		Log.info("Klicken Auf FindOwners");
		headerComponent.KlickeAufFindOwners();
		findOwners.pruefenFindOwners();
		Log.info("Die Seite „FindOwners“ wurde geladen.");
		findOwners.typLastName(lastName);
		Assert.fail();

		Optional<Object> pageObjectOpt = findOwners.KlickeAufFindOwner();
		Assert.assertTrue(pageObjectOpt.isPresent(), "No valid page context found!");

		Object pageObject = pageObjectOpt.get();

		if (pageObject instanceof OwnerInformation) {
			ownerInformation = (OwnerInformation) pageObject;
			String ownerName = ownerInformation.bekommenOwnerName();
			Assert.assertTrue(ownerName.contains(lastName), "Owner Name ist inkorrekt");
			Log.info("Owner Gefunden: " + ownerName);
		} else if (pageObject instanceof Owners) {
			owners = (Owners) pageObject;
			List<String> ownerNames = owners.bekommenOwnersName();
			for (String element : ownerNames) {
				Assert.assertTrue(element.contains(lastName), "Owner Name ist inkorrekt");
			}
			Log.info("Alle Owner Geladen: ");
			owners.klickenErstOwner();
		}

		else {
			Log.info("Owner nicht Gefunden:, blieb auf der FindOwners-Seite. ");
			Assert.fail("Owner nicht Gefunden:, blieb auf der FindOwners-Seite.");
		}
	}

	@Test(dataProvider = "lastname", dataProviderClass = DataProviders.class)
	public void sucheNachEinemTierhalter(String lastName) {
		Log.startTestCase("sucheNachEinemTierhalter");
		FindOwnersTest findOwnersTest = new FindOwnersTest();
		findOwnersTest.sucheTierhalter(lastName);
		Log.endTestCase("sucheNachEinemTierhalter");

	}

}
