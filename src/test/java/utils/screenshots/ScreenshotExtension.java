package utils.screenshots;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import homework.DriverGenerator;

import java.io.ByteArrayInputStream;

public class ScreenshotExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isPresent()) {
            takeScreenshot("FAILED", context.getDisplayName());
        }
    }

    private void takeScreenshot(String status, String testName) {
        try {
            byte[] screenshotBytes = ((TakesScreenshot) DriverGenerator.getInstance()).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(status + " Screenshot - " + testName,
                    new ByteArrayInputStream(screenshotBytes));
        } catch (Exception e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
        }
    }
}