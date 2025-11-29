package spring.testPetClinic.testCases;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import spring.testPetClinic.Utility.Log;
import spring.testPetClinic.base.BaseClass;
import spring.testPetClinic.dataProvider.DataProviders;
import spring.testPetClinic.pageObjects.AddNewPet;
import spring.testPetClinic.pageObjects.OwnerInformation;

public class AddNewPetTest extends BaseClass {
	private OwnerInformation ownerInformation;
	private AddNewPet addNewPet;

	@BeforeMethod
	public void setup() {
		launchApp();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(dataProvider = "lastNameWithRandomPetProvider", dataProviderClass = DataProviders.class)
	public void anlegenEinesNeuenTiersZuEinemTierhalter(String lastName, Map<String, String> details) {
		Log.startTestCase("anlegenEinesNeuenTiersZuEinemTierhalter");
		FindOwnersTest findOwnersTest = new FindOwnersTest();
		findOwnersTest.sucheTierhalter(lastName);
		ownerInformation = new OwnerInformation();
		List<String> PetName = ownerInformation.bekommenPetName();
		if (PetName == null || (!PetName.contains(details.get("name")))) {
			addNewPet = ownerInformation.KlickeAufAddNewPet();
			ownerInformation = addNewPet.erstellenPet(details);
			if (ownerInformation != null) {
				Assert.assertTrue(ownerInformation.erfolgMsgPetWirdAngezeigt().contains("New Pet has been Added"),
						"Neu Pet ist nicht erstellt");
				List<String> PetNameLatest = ownerInformation.bekommenPetName();
				Assert.assertTrue(PetNameLatest.contains(details.get("name")), "Neu Pet ist nicht erstellt");
				Log.info("Neu Pet ist erstellt: " + details.get("name"));
			} else {
				Log.info("Einige Felder sind ungültig. ");
				Assert.fail("Einige Felder sind ungültig.");
			}
		} else {
			Log.info("der Tiername ist nicht für den Halter eindeutig sein)");
			Assert.fail("der Tiername ist nicht für den Halter eindeutig sein)");
		}
		Log.endTestCase("anlegenEinesNeuenTiersZuEinemTierhalter");

	}

}
