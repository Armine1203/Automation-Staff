package homework3.tests;

import homework3.HomePage;
import homework3.StaffRegisterPage;
import homework3.TestClass3;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.screenshots.ScreenshotExtension;

@ExtendWith(ScreenshotExtension.class)
public class StaffRegisterPageTest extends TestClass3 {
    HomePage homePage = new HomePage();
    StaffRegisterPage staffRegisterPage = new StaffRegisterPage();
    Logger logger = LoggerFactory.getLogger(StaffRegisterPage.class);

    @Test
    public void fillRegistrationForm() {
        homePage
                .hoverOnCandidateTab()
                .clickOnRegisterButton()
                .fillFirstName("Armine")
                .fillLastName("Lalayan")
                .fillBirthdayDropDown("2003", "March", "12");

        staffRegisterPage.fillEmail(false, "nbhjsdb-jsdj");
        staffRegisterPage.emailInput.clear();
        staffRegisterPage.fillEmail(true, "nbhjsdbjsdj@gmail.com");

        try {
            String password = System.getProperty("password");
            if (password == null || password.isEmpty()) {
                throw new IllegalArgumentException("Password not provided! Use: mvn test -Dtest.password=YourPassword123");
            }
            staffRegisterPage.fillPassword(password);
            staffRegisterPage.fillConfirmPassword(password);
        } catch (IllegalArgumentException exception) {
            logger.error("Password wasn't entered");
        }

        String previousStyle = staffRegisterPage.getRegisterButtonCurrentStyle();
        staffRegisterPage.selectPrivacyPolicyCheckbox();

        String currentStyle = staffRegisterPage.getRegisterButtonCurrentStyle();

        Assertions.assertNotEquals(previousStyle, currentStyle, "Register button style doesn't change");
    }

}
//I use this commands
//mvn test -Dbrowser=edge -Dtest=StaffRegisterPageTest -Dpassword=.......


