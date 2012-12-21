package ca.ualibrary.dit.peel.stories;

import java.util.concurrent.TimeUnit;

import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.thoughtworks.selenium.SeleneseTestBase;

public class SimpleSearchSteps extends SeleneseTestBase {

    private WebDriver driver;;
    private String baseUrl;

    @BeforeStory
    public void setUp() throws Exception {
	driver = new FirefoxDriver();
	baseUrl = "http://cocoon-server/";
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterStory
    public void tearDown() {
	driver.quit();
	String verificationErrorString = verificationErrors.toString();
	if (!"".equals(verificationErrorString)) {
	    fail(verificationErrorString);
	}
    }

    @Given("visitor is on the front page")
    public void givenVisitorIsOnTheFrontPage() {
	driver.get(baseUrl + "/index.html");
	assertEquals(
		"Peel's Prairie Provinces - Sources for Western Canada and Western Canadian History",
		driver.getTitle());
    }

    @When("user enters 'horse' in the form")
    public void whenUserEntershorseInTheForm() {
	driver.findElement(By.xpath("(//input[@id='keywords'])[2]")).clear();
	driver.findElement(By.xpath("(//input[@id='keywords'])[2]")).sendKeys(
		"horse");
    }

    @When("user clicks 'search'")
    public void whenUserClickssearch() {
	driver.findElement(By.xpath("(//input[@id='submit'])[2]")).click();
    }

    @Then("title is 'Search Results'")
    public void thenTitleIsSearchResults() {
	assertEquals("Search Results", driver.getTitle());
    }

    @Then("hits > 0")
    public void thenHitsGreaterThanZero() {
	// Warning: verifyTextPresent may require manual changes
	try {
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText()
		    .matches("^[\\s\\S]*2741 hits found\\.[\\s\\S]*$"));
	} catch (Error e) {
	    verificationErrors.append(e.toString());
	}
    }
}
