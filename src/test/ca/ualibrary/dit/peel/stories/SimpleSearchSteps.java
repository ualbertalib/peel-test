package ca.ualibrary.dit.peel.stories;

import java.util.concurrent.TimeUnit;

import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
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

    @When("user enters <query> in the form")
    public void whenUserEntersqueryInTheForm(@Named("query") String query) {
	driver.findElement(By.xpath("(//input[@id='keywords'])[2]")).clear();
	driver.findElement(By.xpath("(//input[@id='keywords'])[2]")).sendKeys(
		query);
    }

    @When("user clicks 'search'")
    public void whenUserClickssearch() {
	driver.findElement(By.xpath("(//input[@id='submit'])[2]")).click();
    }

    @Then("title is 'Search Results'")
    public void thenTitleIsSearchResults() {
	driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	assertEquals("Search Results", driver.getTitle());
    }

    @Then("hits <hits>")
    public void thenHitsGreaterThanZero(@Named("hits") String hits) {
	String hitsFoundActual = "";
	String hitsFoundExpected =  hits + " hits found\\.";
	try {
	    hitsFoundActual = driver.findElement(By.className("hits-found"))
		    .getText();
	} catch (Error e) {
	    verificationErrors.append(e.toString());
	}
	assertTrue("'" + hitsFoundActual + "' should match '"
		+ hitsFoundExpected + "'",
		hitsFoundActual.matches(hitsFoundExpected));
    }
}
