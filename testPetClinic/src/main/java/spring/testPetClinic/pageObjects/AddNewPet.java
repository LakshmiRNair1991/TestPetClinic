package spring.testPetClinic.pageObjects;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import spring.testPetClinic.actionDriver.Action;
import spring.testPetClinic.base.BaseClass;

public class AddNewPet extends BaseClass {
	Action action = new Action();
	@FindBy(id = "name")
	WebElement name;

	@FindBy(id = "birthDate")
	WebElement birthDate;

	@FindBy(id = "type")
	WebElement petType;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement addPet;

	@FindBy(xpath = "//div[@class='form-group has-feedback']//input")
	List<WebElement> formElement;

	public AddNewPet() {
		PageFactory.initElements(driver, this);
	}

	public OwnerInformation erstellenPet(Map<String, String> data) {

		for (WebElement field : formElement) {
			String key = field.getAttribute("name");
			String value = data.get(key);
			if (key != "type") {
				if (value != null) {
					field.clear();
					field.sendKeys(value);
				}
			}
			else
			{
				action.selectByValue(field, value);
			}
		}
		action.klicken(addPet, "addPet");
		return new OwnerInformation();
	}
}
