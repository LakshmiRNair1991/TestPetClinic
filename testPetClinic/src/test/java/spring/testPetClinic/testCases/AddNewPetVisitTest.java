
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
import spring.testPetClinic.pageObjects.FindOwners;
import spring.testPetClinic.pageObjects.HeaderComponent;
import spring.testPetClinic.pageObjects.NewPetVisit;
import spring.testPetClinic.pageObjects.OwnerInformation;

public class AddNewPetVisitTest extends BaseClass {

	private OwnerInformation ownerInformation;
	private NewPetVisit newPetVisit;

	@BeforeMethod
	public void setup() {
		launchApp();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(dataProvider = "lastNameWithRandomPetVisitProvider", dataProviderClass = DataProviders.class)
	public void hinzufügenEinesNeuenBesuchsFürEinTier(String lastName, Map<String, String> details) {
		Log.startTestCase("hinzufügenEinesNeuenBesuchsFürEinTier");
		FindOwnersTest findOwnersTest = new FindOwnersTest();
		findOwnersTest.sucheTierhalter(lastName);

		ownerInformation = new OwnerInformation();
		List<String> PetName = ownerInformation.bekommenPetName();

		if (PetName != null) {
			newPetVisit = ownerInformation.KlickeAufAddVisit();
			ownerInformation = newPetVisit.erstellenPetVisit(details);

			if (ownerInformation != null) {
				Assert.assertTrue(
						ownerInformation.erfolgMsgPetVisitWirdAngezeigt().contains("Your visit has been booked"),
						"Neu PetVisit ist nicht erstellt");

				List<Map<String, String>> PetNameVisitLatest = ownerInformation.bekommenPetVisitDate();

				String expectedIsoDate = ownerInformation.tauschDateFormat(details.get("date"));
				Assert.assertNotNull(expectedIsoDate, "Ungültiges Datum im DataProvider: " + details.get("date"));

				boolean visitExists = PetNameVisitLatest.stream()
						.anyMatch(row -> expectedIsoDate.equals(row.get("date")) && details
								.getOrDefault("description", "").equalsIgnoreCase(row.getOrDefault("description", "")));

				Assert.assertTrue(visitExists, "Visit nicht gefunden");

				Log.info("Neu PetVisit ist erstellt: " + details.get("name"));
			} else {
				Log.info("Einige Felder sind ungültig. ");
				Assert.fail("Einige Felder sind ungültig.");
			}
		} else {
			Log.info("der Halter hat kein Tier");
			Assert.fail("der Halter hat kein Tier");
		}

		Log.endTestCase("hinzufügenEinesNeuenBesuchsFürEinTier");
	}
}
