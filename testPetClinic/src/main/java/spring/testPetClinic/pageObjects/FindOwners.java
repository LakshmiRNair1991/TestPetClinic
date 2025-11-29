package spring.testPetClinic.pageObjects;

import java.util.Optional;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import spring.testPetClinic.actionDriver.Action;
import spring.testPetClinic.base.BaseClass;

public class FindOwners extends BaseClass {
	Action action = new Action();
	HeaderComponent headerComponent;
	FindOwners findOwners;
	OwnerInformation ownerInformation;
	Owners owners;

	@FindBy(id = "lastName")
	WebElement lastName;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement findOwner;

	@FindBy(linkText = "Add Owner")
	WebElement addOwner;

	@FindBy(xpath = "//p[text()='has not been found']")
	WebElement ownerNichtFinden;

	public FindOwners() {
		PageFactory.initElements(driver, this);
	}

	public boolean pruefenFindOwners() {
		return (action.wirdAngezeigt(driver, lastName) && action.wirdAngezeigt(driver, findOwner)
				&& action.wirdAngezeigt(driver, addOwner));
	}

	public void typLastName(String lName) {
		action.klicken(lastName, lName);
		action.typ(lastName,lName );
	}

	public Optional<Object> KlickeAufFindOwner() {
		action.klicken(findOwner, "findOwner");
		String url = driver.getCurrentUrl();
		if (action.wirdAngezeigt(driver, ownerNichtFinden)) {
			return null ;
		} 
		else if(url.matches(".*/owners/\\d+$")){
			return Optional.of(new OwnerInformation());
		}
		else if(url.matches(".*/owners\\?lastName=[A-Za-z]+"))
				{
			return Optional.of(new Owners());
		}
		else {
            throw new IllegalArgumentException("Invalid context: " + url);
        }

	}

	public AddOwner KlickeAufAddOwner() {
		action.klicken(addOwner, "addOwner");
		return new AddOwner();
	}
	
	

}
