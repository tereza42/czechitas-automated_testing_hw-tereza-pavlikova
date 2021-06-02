package cz.czechitas.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestyPrihlasovaniNaKurzy {

    WebDriver browser;
    String homePage = "https://cz-test-jedna.herokuapp.com/";
    String applicationsListPage = "https://cz-test-jedna.herokuapp.com/zaci";


    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "C:\\Java-Training\\Selenium\\geckodriver.exe");
        browser = new FirefoxDriver();
        browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @Test
    public void userShouldBeAbleToLogIn() {
        browser.navigate().to(homePage);
        clickOnLoginButton();
        logIn();

        Assertions.assertEquals(browser.getCurrentUrl(), applicationsListPage);
    }

    @Test
    public void loggedInUserShouldBeAbleToCreateApplication() {

        String childForename = "Annie";
        String childSurname = "Czechita" + System.currentTimeMillis();
        String xPath = "//td[contains(text(), '" + childForename + " " + childSurname + "')]";

        browser.navigate().to(homePage);
        List <WebElement> coursesMoreInfoButtons = browser.findElements(By.xpath("//a[contains(text(), 'Více informací')]"));
        WebElement htmlMoreInfo = coursesMoreInfoButtons.get(1);
        htmlMoreInfo.click();
        WebElement moreInfoHTML = browser.findElement(By.xpath("/html/body/div/div//a"));
        moreInfoHTML.click();

        logIn();
        htmlCourseApplication(childForename, childSurname);

        browser.navigate().to(applicationsListPage);
        WebElement childIsSignedUp = browser.findElement(By.xpath(xPath));
        Assertions.assertNotNull(childIsSignedUp);
    }

    @Test
    public void userShouldBeAbleToSelectCourseThenLogIn() {

        String childForename2 = "Susie";
        String childSurname2 = "Czechita" + System.currentTimeMillis();
        String xPath2 = "//td[contains(text(), '" + childForename2 + " " + childSurname2 + "')]";

        browser.navigate().to(homePage);
        clickOnLoginButton();

        logIn();

        WebElement newApplication = browser.findElement(By.xpath("//*[text()='Vytvořit novou přihlášku']"));
        newApplication.click();
        List <WebElement> coursesMoreInfoButtons = browser.findElements(By.xpath("//a[contains(text(), 'Více informací')]"));
        WebElement htmlMoreInfo = coursesMoreInfoButtons.get(1);
        htmlMoreInfo.click();
        WebElement moreInfoHTML = browser.findElement(By.xpath("/html/body/div/div//a"));
        moreInfoHTML.click();

        htmlCourseApplication(childForename2, childSurname2);

        browser.navigate().to(applicationsListPage);
        WebElement childIsSignedUp2 = browser.findElement(By.xpath(xPath2));
        Assertions.assertNotNull(childIsSignedUp2);
    }

    @Test
    public void userShouldBeAbleToViewExistingApplication() {

        int applicationNrFromOverview = 4;
        String xPathOverview = "//table/tbody/tr[" + applicationNrFromOverview + "]/td[5]/div/a[1]";

        browser.navigate().to(homePage);
        clickOnLoginButton();

        logIn();

        WebElement appDetail = browser.findElement(By.xpath(xPathOverview));
        appDetail.click();

        List<WebElement> participantDetails = browser.findElements(By.xpath("//tbody/tr/td[contains(text(), 'Detaily žáka')]"));
        Assertions.assertTrue(participantDetails.size() > 0);
    }

    @AfterEach
    public void tearDown() {
        browser.close();
    }
    public void clickOnLoginButton() {
        WebElement logInButton = browser.findElement(By.xpath("//*[text()='Přihlásit                ']"));
        logInButton.click();
    }

    public void logIn() {
        WebElement logInEmail = browser.findElement(By.xpath("//div/input[@id='email']"));
        logInEmail.sendKeys("nemamparu@email.cz");
        WebElement logInPW = browser.findElement(By.xpath("//div/input[@id='password']"));
        logInPW.sendKeys("Jednadvezofkajde3");
        WebElement logInSubmit = browser.findElement(By.xpath("//button[@type='submit']"));
        logInSubmit.click();
    }

    public void htmlCourseApplication(String forename, String surname) {

        WebElement chooseCourseTerm = browser.findElement(By.xpath("//div[contains(text(), 'Vyberte termín...')]"));
        chooseCourseTerm.click();
        WebElement chosenTerm = browser.findElement(By.xpath("//li/a/span[@class='text']"));
        chosenTerm.click();
        WebElement childForeAppl = browser.findElement(By.id("forename"));
        childForeAppl.sendKeys(forename);
        WebElement childSurAppl = browser.findElement(By.id("surname"));
        childSurAppl.sendKeys(surname);
        WebElement doB = browser.findElement(By.id("birthday"));
        doB.sendKeys("01.01.2012");
        WebElement paymentTransfer = browser.findElement(By.id("payment_transfer"));
        paymentTransfer.sendKeys(Keys.SPACE);
        WebElement termsConditions = browser.findElement(By.id("terms_conditions"));
        termsConditions.sendKeys(Keys.SPACE);
        WebElement applicationSubmit = browser.findElement(By.xpath("//input[@value='Vytvořit přihlášku']"));
        applicationSubmit.click();
    }
}
