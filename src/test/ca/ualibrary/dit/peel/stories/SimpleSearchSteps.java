package ca.ualibrary.dit.peel.stories;

import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestBase;

public class SimpleSearchSteps extends SeleneseTestBase {
    
    private DefaultSelenium selenium;
    private SeleniumServer seleniumServer;

    @BeforeStory
    public void setUp() throws Exception {
	selenium = new DefaultSelenium("localhost", 4444, "*chrome",
		"http://cocoon-server/");
	seleniumServer = new SeleniumServer();
	seleniumServer.start();
	selenium.start();
    }
    
    @AfterStory
    public void tearDown() {
	selenium.stop();
	seleniumServer.stop();
    }

    @Given("visitor is on the front page")
    public void givenVisitorIsOnTheFrontPage() {
	selenium.open("/index.html");
    }

    @When("user enters 'horse' in the form")
    public void whenUserEntershorseInTheForm() {
	selenium.type("xpath=(//input[@id='keywords'])[2]", "horse");
    }

    @When("user clicks 'search'")
    public void whenUserClickssearch() {
	selenium.click("xpath=(//input[@id='submit'])[2]");
	selenium.waitForPageToLoad("30000");
    }

    @Then("title is 'Search Results'")
    public void thenTitleIsSearchResults() {
	verifyEquals(selenium.getTitle(), "Search Results");
    }

    @Then("hits > 0")
    public void thenHitsGreaterThanZero() {
	verifyTrue(selenium
		.isTextPresent("2741 hits found."));
    }
}
