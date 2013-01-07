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
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.Select;

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
    }

    @Given("visitor is on the front page")
    public void givenVisitorIsOnTheFrontPage() {
	driver.get(baseUrl + "/index.html");
	assertEquals(
		"Peel's Prairie Provinces - Sources for Western Canada and Western Canadian History",
		driver.getTitle());
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

    @When("user enters <id> <value> in the form")
    public void whenUserEntersIdValueInTheForm(@Named("id") String id,
	    @Named("value") String value) {
	enterInElement(By.id(id), value);
    }

    @When("user enters <name> <value> in the form")
    public void whenUserEntersNameValueInTheForm(@Named("name") String name,
	    @Named("value") String value) {
	enterInElement(By.name(name), value);
    }

    @When("user selects <name> <value> in the form")
    public void whenUserSelectsNameValueInTheForm(@Named("name") String name,
	    @Named("value") String value) {
	new Select(driver.findElement(By.name(name)))
.selectByValue(value);
    }

    @When("user enters <keywords> in the form")
    public void whenUserEntersKeywordsInTheForm(
	    @Named("keywords") String keywords) {
	enterInElement(By.id("keywords2"), keywords);
    }

    private void enterInElement(By element, String value) {
	driver.findElement(element).clear();
	driver.findElement(element).sendKeys(value);
    }

    @When("user clicks 'go'")
    public void whenUserClicksGo() {
	driver.findElement(By.id("submit2")).click();
    }

    @When("user selects <sort>")
    public void whenUserSelectsSort(@Named("sort") String sort) {
	// TODO why do I have to do this?
	driver.findElement(By.id("status-any")).click();
	driver.findElement(By.id(sort)).click();
    }

    @When("user clicks 'submit' at the bottom of form")
    public void whenUserClicksSubmitAtTheBottomOfForm() {
	driver.findElement(By.cssSelector("p.buttons > input.submit")).click();
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
	String hitsFoundExpected =  hits + " hits found\\.";
	String hitsFoundActual = driver.findElement(
		By.className("hits-found"))
		    .getText();
	assertTrue("'" + hitsFoundActual + "' should match '"
		+ hitsFoundExpected + "'",
		hitsFoundActual.matches(hitsFoundExpected));
    }

    @Then("first result is <peelbib>")
    public void thenFirstResultIsPeelbib(@Named("peelbib") String peelbib) {
	String firstResultExpected = peelbib;
	String firstResultActual = driver.findElement(
		    new ByChained(By.xpath("//li[@class='result'][@value=1]"),
			    By.xpath("//dt/a")))
		    .getText();
	assertTrue("'" + firstResultActual + "' should match '"
		+ firstResultExpected + "'",
		firstResultActual.matches(firstResultExpected));
    }
}
