package ca.ualibrary.dit.peel.stories;

import java.util.concurrent.TimeUnit;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;


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
		driver.manage().timeouts().pageLoadTimeout(WAIT_TIME, TimeUnit.SECONDS);
		assertEquals("Search Results", driver.getTitle());
	}

	@Given("visitor is on a newspaper results page for $term")
	public void givenVisitorIsOnANewspaperResultsPageForTerm(
			@Named("term") String term) {
		givenVisitorIsOnResultsPageForTerm("newspapers", term);
	}

	@When("user clicks through to item")
	public void whenUserClicksThroughToItem() {
		WebElement firstResult = driver.findElement(By
				.xpath("//li[@class='result'][@value=1]"));
		WebElement page = firstResult.findElement(By
				.xpath("dl/dd[@class='results']/a"));
		String title = firstResult.findElement(By.xpath("dl/dt/a")).getText()
				+ ", p. " + page.getText();
		page.click();
		driver.manage().timeouts().pageLoadTimeout(WAIT_TIME, TimeUnit.SECONDS);

		assertEquals(title, driver.getTitle());
	}
	
	@When("user clicks through to a newspaper item")
	public void whenUserClicksThroughToANewspaperItem() {
		WebElement firstResult = driver.findElement(By
				.xpath("//li[@class='result'][@value=1]"));
		WebElement page = firstResult.findElement(By
				.xpath("dl/dd[@class='image']/a"));
		String title = firstResult.findElement(By.xpath("dl/dt")).getText();
		title = title.replaceAll("p.", "Page ");
		// replace last ',' with ', Item'
		int lastComma = title.lastIndexOf(",");
		title = title.substring(0, lastComma + 1) + " Item"
				+ title.substring(lastComma + 1, title.length());
		page.click();
		driver.manage().timeouts().pageLoadTimeout(WAIT_TIME, TimeUnit.SECONDS);

		assertEquals(title, driver.getTitle());
	}

	@Then("results contain hits on page")
	public void thenResultsContainHitsOnPage() {
		assertTrue(isElementPresent(By
				.xpath("//li[@class='result']/dl/dd[@class='results']/a")));

		String prefix = "^[0-9]+ hits on [0-9]+ pages:.*";
		assertTrue("should specify x hits on y pages",
				driver.findElement(By.cssSelector("dd.results")).getText()
						.matches(prefix));
	}

	@Then("something is highlighted")
	public void thenSomethingIsHighlighted() {
		assertTrue(isElementPresent(By.cssSelector("img.highlight_on")));
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
