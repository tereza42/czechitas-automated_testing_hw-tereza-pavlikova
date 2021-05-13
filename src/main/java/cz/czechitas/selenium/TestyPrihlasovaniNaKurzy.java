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

    @BeforeEach
    public void setUp() {
//      System.setProperty("webdriver.gecko.driver", System.getProperty("user.home") + "/Java-Training/Selenium/geckodriver");
        System.setProperty("webdriver.gecko.driver", "C:\\Java-Training\\Selenium\\geckodriver.exe");
        browser = new FirefoxDriver();
        browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @Test
    public void appLogIn() {
        browser.navigate().to("https://cz-test-jedna.herokuapp.com/");
        WebElement logInButton = browser.findElement(By.xpath("//div/a[@href='https://cz-test-jedna.herokuapp.com/prihlaseni']"));
        logInButton.click();

        logIn();

        Assertions.assertEquals(browser.getCurrentUrl(), "https://cz-test-jedna.herokuapp.com/zaci");
    }

    @Test
    public void courseApplication() {

        //choose the child's first name and surname
        String childForename = "";
        String childSurname = "";
        String xPath = "//td[contains(text(), '" + childForename + " " + childSurname + "')]";

        browser.navigate().to("https://cz-test-jedna.herokuapp.com/");
        WebElement webCoursesButton = browser.findElement(By.xpath("//div/a[@href='https://cz-test-jedna.herokuapp.com/11-trimesicni-kurzy-webu']"));
        webCoursesButton.click();
        WebElement moreInfoHTML = browser.findElement(By.xpath("//div/a[@href='https://cz-test-jedna.herokuapp.com/zaci/pridat/41-html-1']"));
        moreInfoHTML.click();

        logIn();

        htmlCourseApplication(childForename, childSurname);

        browser.navigate().to("https://cz-test-jedna.herokuapp.com/zaci/");
        WebElement childIsSignedUp = browser.findElement(By.xpath(xPath));
    }

    @Test
    public void courseApplication2() {

        //choose the child's first name and surname
        String childForename2 = "";
        String childSurname2 = "";
        String xPath2 = "//td[contains(text(), '" + childForename2 + " " + childSurname2 + "')]";

        browser.navigate().to("https://cz-test-jedna.herokuapp.com/");
        WebElement logInButton = browser.findElement(By.xpath("//div/a[@href='https://cz-test-jedna.herokuapp.com/prihlaseni']"));
        logInButton.click();

        logIn();

        WebElement newApplication = browser.findElement(By.xpath("/html/body/div/div/div/div/div/div[1]/a"));
        newApplication.click();
        WebElement moreInfoHTML = browser.findElement(By.xpath("//div/a[@href='https://cz-test-jedna.herokuapp.com/11-trimesicni-kurzy-webu']"));
        moreInfoHTML.click();
        WebElement htmlApply = browser.findElement(By.xpath("//div/a[@href='https://cz-test-jedna.herokuapp.com/zaci/pridat/41-html-1']"));
        htmlApply.click();

        htmlCourseApplication(childForename2, childSurname2);

        browser.navigate().to("https://cz-test-jedna.herokuapp.com/zaci/");
        WebElement childIsSignedUp2 = browser.findElement(By.xpath(xPath2));

    }

    @Test
    public void applicationOverview() {

        //choose the order of the application you want to display from the list on the website
        int applicationNr = ;
        String xPathOverview = "//table/tbody/tr[" + applicationNr + "]/td[5]/div/a[1]";

        browser.navigate().to("https://cz-test-jedna.herokuapp.com/");
        WebElement logInButton = browser.findElement(By.xpath("//div/a[@href='https://cz-test-jedna.herokuapp.com/prihlaseni']"));
        logInButton.click();

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
