package spring.testPetClinic.pageObjects;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import spring.testPetClinic.actionDriver.Action;
import spring.testPetClinic.base.BaseClass;

public class NewPetVisit extends BaseClass{
	Action action = new Action();
	
	@FindBy(xpath="//b[text()='Pet']/following::table[1]")
	WebElement petTable;
	
	@FindBy(id="description")
	WebElement description;
	
	@FindBy(id="date")
	WebElement date;
	
	@FindBy(xpath="//button[@type='submit']")
	WebElement addVisit;
	
	
	@FindBy(xpath="//span[text()='Your visit has been booked']")
	WebElement successMessage;
	
	@FindBy(xpath = "//div[@class='form-group has-feedback']//input")
	List<WebElement> formElement;
	
	public NewPetVisit() {
		PageFactory.initElements(driver, this);
	}
	
	public OwnerInformation erstellenPetVisit(Map<String, String> data) {

		for (WebElement field : formElement) {
			String key = field.getAttribute("name");
			String value = data.get(key);
			
				if (value != null) {
					field.clear();
					field.sendKeys(value);
				}
		}
		action.klicken(addVisit, "addVisit");
		return new OwnerInformation();
	}

}
