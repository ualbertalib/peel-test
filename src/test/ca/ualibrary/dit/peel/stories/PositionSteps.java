package ca.ualibrary.dit.peel.stories;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class PositionSteps extends PeelSteps {

	@Given("visitor is on a peelbib results page for $term")
	public void givenVisitorIsOnAPeelbibResultsPageForTerm(
			@Named("term") String term) {
		givenVisitorIsOnResultsPageForTerm("peelbib", term);
	}

	private void givenVisitorIsOnResultsPageForTerm(String index, String term) {
		driver.get(baseUrl
				+ "search/?search=raw&pageNumber=1&field=body&rawQuery=" + term
				+ "&index=" + index);
    wait.until(ExpectedConditions.titleIs("Search Results"));
		assertEquals("Search Results", driver.getTitle());
	}

	@Given("visitor is on a newspaper results page for $term")
	public void givenVisitorIsOnANewspaperResultsPageForTerm(
			@Named("term") String term) {
		givenVisitorIsOnResultsPageForTerm("newspapers", term);
	}

	@When("user clicks through to item")
	public void whenUserClicksThroughToItem() {
	  
	  if( hasContent ) {
	    WebElement firstResult = driver.findElement(By
	        .xpath("//li[@class='result'][@value=1]"));
	    WebElement page = firstResult.findElement(By
	        .xpath("dl/dd[@class='results']/a"));
	    String title = firstResult.findElement(By.xpath("dl/dt/a")).getText()
	        + ", p. " + page.getText();
	    page.click();
	    wait.until(ExpectedConditions.titleIs(title));
	    
	    assertEquals(title, driver.getTitle());
	  }
	}
	
	@When("user clicks through to a newspaper item")
	public void whenUserClicksThroughToANewspaperItem() {
	  if( hasContent ) {
	    WebElement firstResult = driver.findElement(By
	        .xpath("//li[@class='result'][@value=1]"));
	    WebElement page = firstResult.findElement(By
	        .xpath("dl/dd[@class='image']/a/img"));
	    String title = firstResult.findElement(By.xpath("dl/dt")).getText();
	    title = title.replaceAll("p\\.", "Page ");
	    // replace last ',' with ', Item'
	    int lastComma = title.lastIndexOf(",");
	    title = title.substring(0, lastComma + 1) + " Item"
	        + title.substring(lastComma + 1, title.length());
	    page.click();
	    wait.until(ExpectedConditions.titleIs(title));
	    assertEquals(title, driver.getTitle());
	  }
	}

	@Then("results contain hits on page")
	public void thenResultsContainHitsOnPage() {
		assertTrue(isElementPresent(By
				.xpath("//li[@class='result']/dl/dd[@class='results']/a")));

		String prefix = "^[0-9]+ hits on [0-9]+ pages:.*";
		if( isSample ) { 
		  assertTrue("should specify x hits on y pages",
		      driver.findElement(By.cssSelector("dd.results")).getText()
		      .matches(prefix));
		} else {
		  String text = driver.findElement(By.cssSelector("dd.results")).getText();
		  boolean matches = text.matches(prefix) || text.matches("Full text unavailable");
		  assertTrue("should specify unavailable or x hits on y pages", matches);
		}
	}

	@Then("something is highlighted")
	public void thenSomethingIsHighlighted() {
	  if ( hasContent ) {
	    assertTrue(isElementPresent(By.cssSelector("img.highlight_on")));
	  }
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
