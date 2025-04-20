package homework.Helpers;

import homework.BasePage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CurrentURL_CheckHelper extends BasePage {
    private static String previousURL;

    public void compareCurrentURL_withPrevious() throws TimeoutException {
        try {
            previousURL = driver.getCurrentUrl();
            System.out.println("previousURL " + previousURL);
            wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(previousURL)));
            System.out.println("current url " + driver.getCurrentUrl());
        } catch (TimeoutException timeoutException) {
            System.out.println("TimeoutException");
        }
    }
}
