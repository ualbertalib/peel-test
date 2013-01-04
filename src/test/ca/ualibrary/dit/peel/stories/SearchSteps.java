package ca.ualibrary.dit.peel.stories;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

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

public class SearchSteps extends SeleneseTestBase {

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
    public void whenUserEntersQueryInTheForm(@Named("query") String query) {
	driver.findElement(By.xpath("(//input[@id='keywords'])[2]")).clear();
	driver.findElement(By.xpath("(//input[@id='keywords'])[2]")).sendKeys(
		query);
    }

    @When("user clicks 'search'")
    public void whenUserClicksSearch() {
	driver.findElement(By.xpath("(//input[@id='submit'])[2]")).click();
    }

    @Then("title is 'Search Results'")
    public void thenTitleIsSearchResults() {
	driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	assertEquals("Search Results", driver.getTitle());
    }

    @Then("breadcrumbs contain <query>")
    public void thenBreadcrumbsContainQuery(@Named("query") String query) {
	String breadcrumbsFoundActual = "";
	String breadcrumbsFoundExpected = "^[\\s\\S]*Query: "
		+ Pattern.quote(query)
		+ "[\\s\\S]*$";
	try {
	    breadcrumbsFoundActual = driver.findElement(
		    By.className("breadcrumbs"))
		    .getText();
	} catch (Error e) {
	    verificationErrors.append(e.toString());
	}
	assertTrue("'" + breadcrumbsFoundActual + "' should match regex '"
		+ breadcrumbsFoundExpected + "'",
		breadcrumbsFoundActual.matches(breadcrumbsFoundExpected));
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

    @Given("visitor is on the 'find books' page")
    public void givenVisitorIsOnTheFindBooksPage() {
	driver.get(baseUrl + "/index.html");
	assertEquals(
		"Peel's Prairie Provinces - Sources for Western Canada and Western Canadian History",
		driver.getTitle());
	driver.findElement(By.cssSelector("a.tab4")).click();
	assertEquals("Advanced Search", driver.getTitle());
    }

    @When("user enters <keywords> in the form")
    public void whenUserEntersKeywordsInTheForm(
	    @Named("keywords") String keywords) {
	driver.findElement(By.id("keywords2")).clear();
	driver.findElement(By.id("keywords2")).sendKeys(keywords);
    }

    @When("user clicks 'go'")
    public void whenUserClicksGo() {
	driver.findElement(By.id("submit2")).click();
    }

    @When("user selects <sort>")
    public void whenUserSelectssort(@Named("sort") String sort) {
	driver.findElement(By.id("sort-score")).click();
    }

    @When("user clicks 'submit' at the bottom of form")
    public void whenUserClickssubmitAtTheBottomOfForm() {
	driver.findElement(By.cssSelector("p.buttons > input.submit")).click();
    }
}
