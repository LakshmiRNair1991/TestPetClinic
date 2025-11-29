package spring.testPetClinic.pageObjects;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import spring.testPetClinic.actionDriver.Action;
import spring.testPetClinic.base.BaseClass;

public class AddOwner extends BaseClass {
	Action action = new Action();

	@FindBy(xpath = "//div[@class='form-group has-feedback']//input")
	List<WebElement> formElement;

	@FindBy(id = "firstName")
	WebElement firstName;

	@FindBy(id = "lastName")
	WebElement lastName;

	@FindBy(id = "address")
	WebElement Address;

	@FindBy(id = "city")
	WebElement City;

	@FindBy(id = "telephone")
	WebElement Telephone;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement addOwner;

	@FindBy(xpath = "//*[text()='must not be blank']")
	WebElement leerMsg;

	@FindBy(xpath = "//*[contains(text(),'Telephone must be a 10-digit number')]")
	WebElement telefonMsg;

	public AddOwner() {
		PageFactory.initElements(driver, this);
	}

	public boolean pruefenErstellenOwners() {
		for (WebElement e : formElement) {
			if (!action.wirdAngezeigt(driver, e)) {
				return false;
			}
		}
		if (!action.wirdAngezeigt(driver, addOwner)) {
			return false;
		}
		return true;

	}

	public OwnerInformation erstellenOwner(Map<String, String> data) {

		for (WebElement field : formElement) {
			String key = field.getAttribute("name");
			String value = data.get(key);
			if (value != null) {
				field.clear();
				field.sendKeys(value);
			}
		}

		action.klicken(addOwner, "addOwner");
		return new OwnerInformation();

	}

}
