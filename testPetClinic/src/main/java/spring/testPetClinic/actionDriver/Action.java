package spring.testPetClinic.actionDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import spring.testPetClinic.base.BaseClass;

public class Action extends BaseClass {

	public void scrollenNachSichtbarkeitdesElements(WebDriver driver, WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", ele);

	}

	public boolean klicken(WebElement locator, String locatorName) {
		boolean flag = false;
		try {
			locator.click();
			flag = true;
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (flag) {
				System.out.println("anklickbar \"" + locatorName + "\"");
			} else {
				System.out.println("Kann nicht anklicken \"" + locatorName + "\"");
			}
		}

	}

	public boolean typ(WebElement ele, String text) {
		boolean flag = false;
		try {
			flag = ele.isDisplayed();
			ele.clear();
			ele.sendKeys(text);
			// logger.info("Entered text :"+text);
			flag = true;
		} catch (Exception e) {
			System.out.println("Standort nicht gefunden");
			flag = false;
		} finally {
			if (flag) {
				System.out.println("Wert erfolgreich eingegeben");
			} else {
				System.out.println("Wert konnte nicht eingegeben werden");
			}

		}
		return flag;
	}

	public boolean elementFinden(WebDriver driver, WebElement ele) {
		boolean flag = false;
		try {
			ele.isDisplayed();
			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {
			if (flag) {
				System.out.println("Element erfolgreich gefunden bei");

			} else {
				System.out.println("Element konnte nicht gefunden werden bei");
			}
		}
		return flag;
	}

	public boolean wirdAngezeigt(WebDriver driver, WebElement ele) {
		boolean flag = false;
		flag = elementFinden(driver, ele);
		if (flag) {
			flag = ele.isDisplayed();
			if (flag) {
				System.out.println("Das Element wird angezeigt");
			} else {
				System.out.println("Das Element wird nicht angezeigt.");
			}
		} else {
			System.out.println("Nicht angezeigt");
		}
		return flag;
	}

	public Map<String, String> rufOwnerTabelleInformationen(WebElement tabelle, String r, String k, String v,
			boolean flag) {
		List<WebElement> rows = tabelle.findElements(By.tagName(r));
		Map<String, String> tablleData = new LinkedHashMap<>();

		for (WebElement row : rows) {
			String key = row.findElement(By.tagName(k)).getText();
			String value = row.findElement(By.tagName(v)).getText();
			if (flag) {
				tablleData.put(value, key);
			} else {
				tablleData.put(key, value);
			}
		}
		return tablleData;
	}

	public List<String> erhaltenPetsName(WebElement tabelle) {
		Map<String, String> tablleData = rufOwnerTabelleInformationen(tabelle, "dl", "dt", "dd", true);
		List<String> pets = new ArrayList<>();
		if (tablleData != null) {
			for (Map.Entry<String, String> entry : tablleData.entrySet()) {
				if ("Name".equals(entry.getValue())) {
					pets.add(entry.getKey());
				}
			}
			return pets;
		}
		return pets;
	}

	public List<Map<String, String>> erhaltenPetsVisitInfo(WebElement tabelle) {
		List<Map<String, String>> visitList = new ArrayList<>();

		// Get all rows inside tbody
		List<WebElement> rows = tabelle.findElements(By.tagName("tr"));

		for (WebElement row : rows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			if (cells.size() >= 2) {
				Map<String, String> visitData = new HashMap<>();
				visitData.put("date", cells.get(0).getText().trim());
				visitData.put("description", cells.get(1).getText().trim());
				visitList.add(visitData);
			}
		}
		return visitList;
	}

	public void explicitWait(WebDriver driver, WebElement element, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public boolean selectByValue(WebElement element, String value) {
		boolean flag = false;
		try {
			Select s = new Select(element);
			s.selectByValue(value);
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (flag) {
				System.out.println("Option selected by Value");
			} else {
				System.out.println("Option not selected by Value");
			}
		}
	}

	public String screenShot(WebDriver driver, String filename) {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "\\ScreenShots\\" + filename + "_" + dateName + ".png";

		try {
			FileUtils.copyFile(source, new File(destination));
		} catch (Exception e) {
			e.getMessage();
		}
		// This new path for jenkins
		String newImageString = "http://localhost:8080/job/Magneto_CICD/ws/Magneto-Store/ScreenShots/" + filename + "_"
				+ dateName + ".png";
		return destination;
	}

}
