package spring.testPetClinic.pageObjects;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import spring.testPetClinic.actionDriver.Action;
import spring.testPetClinic.base.BaseClass;

public class OwnerInformation extends BaseClass {
	Action action = new Action();

	@FindBy(xpath = "//h2[text()='Owner Information']/following::table[1]")
	WebElement ownerInformationTable;

	@FindBy(linkText = "Edit Owner")
	WebElement editOwner;

	@FindBy(linkText = "Add New Pet")
	WebElement addNewPet;

	@FindBy(xpath = "//h2[text()='Pets and Visits']/following::table[1]")
	WebElement petsAndVisitsTable;

	@FindBy(className = "table-condensed")
	WebElement visitDateDescriptionTable;

	@FindBy(linkText = "Add Visit")
	WebElement addVisit;

	@FindBy(xpath = "//*[contains(text(),'New Owner Created')]")
	WebElement erfolgMsg;

	@FindBy(xpath = "//*[contains(text(),'New Pet has been Added')]")
	WebElement erfolgMsgPet;

	@FindBy(xpath = "//*[contains(text(),'Your visit has been booked')]")
	WebElement erfolgMsgVisit;

	public OwnerInformation() {
		PageFactory.initElements(driver, this);
	}

	public String erfolgMsgWirdAngezeigt() {
		action.explicitWait(driver, erfolgMsg, 2);
		return erfolgMsg.getText();
	}

	public String erfolgMsgPetWirdAngezeigt() {
		action.explicitWait(driver, erfolgMsgPet, 2);
		return erfolgMsgPet.getText();
	}

	public String erfolgMsgPetVisitWirdAngezeigt() {
		action.explicitWait(driver, erfolgMsgVisit, 2);
		return erfolgMsgVisit.getText();
	}

	public String bekommenOwnerName() {
		Map<String, String> tablleData = action.rufOwnerTabelleInformationen(ownerInformationTable, "tr", "th", "td",
				false);
		return tablleData.get("Name");
	}

	public List<String> bekommenPetName() {
		return action.erhaltenPetsName(petsAndVisitsTable);
	}

	public List<Map<String, String>> bekommenPetVisitDate() {
		return action.erhaltenPetsVisitInfo(visitDateDescriptionTable);
	}

	public AddNewPet KlickeAufAddNewPet() {
		action.klicken(addNewPet, "addNewPet");
		return new AddNewPet();

	}

	public NewPetVisit KlickeAufAddVisit() {
		action.klicken(addVisit, "addVisit");
		return new NewPetVisit();

	}

	public String tauschDateFormat(String date) {

		String[] parts = date.split("-");
		if (parts.length == 3) {
			return parts[2] + "-" + parts[1] + "-" + parts[0]; // yyyy-MM-dd
		}
		return date;

	}

}
