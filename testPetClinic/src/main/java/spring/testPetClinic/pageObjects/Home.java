package spring.testPetClinic.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import spring.testPetClinic.actionDriver.Action;
import spring.testPetClinic.base.BaseClass;

public class Home extends BaseClass{
	Action action=new Action();
	
	@FindBy(className="img-responsive")
	WebElement petImg;
	
	@FindBy(tagName="h2")
	WebElement Welcome;
	
	
	public Home() {
		PageFactory.initElements(driver, this);
	}
	
	public boolean pruefenHome() {
		return (action.wirdAngezeigt(driver, petImg)&&action.wirdAngezeigt(driver, Welcome));	
	}

}
