package spring.testPetClinic.Utility;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import spring.testPetClinic.actionDriver.Action;
import spring.testPetClinic.base.BaseClass;

public class ListenerClass implements ITestListener {
	Action action = new Action();

	public void onTestStart(ITestResult result) {
		ExtentManager.test = ExtentManager.extent.createTest(result.getName());
	}

	public void onTestSuccess(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			ExtentManager.test.log(Status.PASS, "Pass Test case: " + result.getName());
		}
	}

	public void onTestFailure(ITestResult result) {
		ExtentManager.test.log(Status.FAIL,
                MarkupHelper.createLabel(result.getName() + " - Failed", ExtentColor.RED));
        String imgPath = action.screenShot(BaseClass.driver, result.getName());
        ExtentManager.test.fail("Screenshot attached",
                MediaEntityBuilder.createScreenCaptureFromPath(imgPath).build());
		
	}

	public void onTestSkipped(ITestResult result) {
		if (result.getStatus() == ITestResult.SKIP) {
			ExtentManager.test.log(Status.SKIP, "Skipped Test case is: " + result.getName());
		}
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
	}

}
