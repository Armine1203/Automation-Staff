package homework.Helpers;

import homework.BasePage;
import org.openqa.selenium.WebElement;

public class IntegerParseHelper extends BasePage {
    public static int extractNumberFromElement(WebElement element) {
        return Integer.parseInt(element.getText().replaceAll("[^0-9]", ""));
    }


}
