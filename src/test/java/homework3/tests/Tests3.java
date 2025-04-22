package homework3.tests;

import homework3.HomePage;
import homework3.StaffRegisterPage;
import homework3.TestClass3;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Tests3 extends TestClass3 {
    HomePage homePage = new HomePage();
    StaffRegisterPage staffRegisterPage = new StaffRegisterPage();
    Logger logger = LoggerFactory.getLogger(StaffRegisterPage.class);

    @Test
    public void test() throws InterruptedException {
        homePage
                .hoverOnCandidateTab()
                .clickOnRegisterButton()
                .fillFirstName("Armine")
                .fillLastName("Lalayan")

                .fillBirthdayDropDown("2003", "March", "12");


        staffRegisterPage.fillEmail(false,"nbhjsdb-jsdj");
        staffRegisterPage.emailInput.clear();
        staffRegisterPage.fillEmail(true,"nbhjsdbjsdj@gmail.com");

        try {
            FileReader reader = new FileReader("src/test/resources/testdata.properties");
            Properties properties = new Properties();
            properties.load(reader);
            String password = properties.getProperty("test.password");
            staffRegisterPage.fillPassword(password);
            staffRegisterPage.fillConfirmPassword(password);
        }catch (IOException exception){
            logger.error("File isn't found");
        }

        String previousStyle = staffRegisterPage.getRegisterButtonCurrentStyle();
        staffRegisterPage.selectPrivacyPolicyCheckbox();

        String currentStyle = staffRegisterPage.getRegisterButtonCurrentStyle();

        Assertions.assertNotEquals(previousStyle,currentStyle,"Register button style doesn't change");
    }

}