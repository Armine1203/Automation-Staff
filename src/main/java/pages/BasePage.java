package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.How;

import javax.swing.*;

public class BasePage {
    protected WebDriver driver = DriverGenerator.getDriver();
    protected Actions action = new Actions(driver);
}
