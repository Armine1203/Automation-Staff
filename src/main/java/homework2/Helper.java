package homework2;

import homework.BasePage;
import org.openqa.selenium.WebElement;

public class Helper extends BasePage {
    public static int extractNumberFromElement(WebElement element) {
        return Integer.parseInt(element.getText().replaceAll("[^0-9]", ""));
    }

}
