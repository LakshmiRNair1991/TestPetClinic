package spring.testPetClinic.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import spring.testPetClinic.actionDriver.Action;
import spring.testPetClinic.base.BaseClass;

public class HeaderComponent extends BaseClass{
	Action action= new Action();
		
	@FindBy(xpath="//span[text()='Home']")
	WebElement Home;
	
	@FindBy(xpath="//span[text()='Find Owners']")
	WebElement findOwners;
	
	public HeaderComponent() {
		PageFactory.initElements(driver, this);
	}
	
	public Home clickHomes() {
		action.klicken(Home, "Home");
		return new Home();
		
	}
	
	public  FindOwners KlickeAufFindOwners() {
		action.klicken(findOwners, "findOwners");
		return new FindOwners();
		
	}

	

}
