package spring.testPetClinic.pageObjects;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import spring.testPetClinic.actionDriver.Action;
import spring.testPetClinic.base.BaseClass;

public class Owners extends BaseClass {
	Action action = new Action();

	@FindBy(xpath = "//table[@id='owners']//a")
	List<WebElement> ownersNameList;

	public Owners() {
		PageFactory.initElements(driver, this);
	}
	
	public List<String> bekommenOwnersName() {

		List<String> names = new ArrayList<>();
		for (WebElement element : ownersNameList) {
			names.add(element.getText());

		}
		return names;

	}
	
	public void klickenErstOwner() {
		action.klicken(ownersNameList.get(0), "ownersNameList");
		
	}
}
