package ca.ualibrary.dit.peel.stories;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class FacetSteps extends SearchSteps {

	String facetText = "";
	protected static long WAIT_TIME = 10;

	@Given("visitor is on a newspapers results page for 'alberta'")
	public void givenVisitorIsOnANewspapersResultsPageForAlberta() {
		driver.get(baseUrl
				+ "search/?search=raw&pageNumber=1&field=body&rawQuery=alberta&index=newspapers");
		driver.manage().timeouts().pageLoadTimeout(WAIT_TIME, TimeUnit.SECONDS);
		assertEquals("Search Results", driver.getTitle());
	}

	@When("user clicks on <display-sort>")
	public void whenUserClicksOnSort(@Named("display-sort") String sort) {
		List<WebElement> facets = driver.findElements(By
				.xpath("//*[@id=\"subCol\"]/div[1]/ul/li/a"));
		if (!facets.get(0).isDisplayed())
			driver.findElement(By.id("sort-options")).click();
		driver.findElement(By.linkText(sort)).click();
	}

	@When("user selects $range range of publication")
	public void whenUserSelectsRangeOfPublication(@Named("range") String range) {
		List<WebElement> facets = driver.findElements(By
				.xpath("//*[@id=\"subCol\"]/div[2]/ul/li/a"));
		if (!facets.get(0).isDisplayed()) {
			driver.findElement(By.cssSelector("h4#years-in-publication"))
					.click();
		}
		WebElement facet = null;
		if( "first".equals( range ) )
			facet = facets.get(0);
		else if ( "last".equals( range) )
			facet = facets.get(facets.size() - 1);
		else {
			Random rand = new Random();
			// random number in range that is not first or last
			if (2 < facets.size())
				facet = facets.get(rand.nextInt(facets.size() - 1) + 1);
		}
		facetText = facet.getText();
		facet.click();
	}

	@Then("$facet match breadcrumbs")
	public void thenRandomRangeMatchBreadcrumbs() {
		if (isSample) {
			String breadcrumbsFoundExpected = "^[\\s\\S]*Query:.*"
					+ Pattern.quote(
							facetText.substring(0,facetText.indexOf(":"))
							.replaceAll("\\p{Pd}","TO")
					)+ ".*[\\s\\S]*$";

			String breadcrumbsFoundActual = driver.findElement(
					By.className("breadcrumbs")).getText();
			assertTrue("'" + breadcrumbsFoundActual + "' should match regex '"
					+ breadcrumbsFoundExpected + "'",
					breadcrumbsFoundActual.matches(breadcrumbsFoundExpected));
		}
	}

	@Then("results have publication in $range range")
	public void thenResultsHavePublicationInRange() throws ParseException {
		SimpleDateFormat formatFacet = new SimpleDateFormat("yyyy");
		String[] dates = facetText.substring(0, facetText.indexOf(":")).split(
				" \\p{Pd} ");
		Date lowerDate = formatFacet.parse(dates[0]);
		Date upperDate = formatFacet.parse(dates[1]);

		SimpleDateFormat formatTitle = new SimpleDateFormat("MMMMM dd, yyyy");
		WebElement firstResult = driver.findElement(By
				.xpath("//li[@class='result'][@value=1]"));
		String[] split = firstResult.findElement(By.xpath("dl/dt")).getText()
				.split(", ");
		Date date = formatTitle.parse(split[1] + ", " + split[2]);

		assertTrue("should be in range", lowerDate.compareTo(date) <= 0);
		assertTrue("should be in range", upperDate.compareTo(date) >= 0);
	}

	@Then("$facet match hits")
	public void thenFacetMatchHits() {
		String hits = facetText.substring(facetText.indexOf(": ") + 2,
				facetText.indexOf(" hits"));
		thenHitsEquals( hits );
	}

	@Then("<display-sort> is fixed")
	public void thensortIsFixed(@Named("display-sort") String sort) {
		assertEquals(sort, driver.findElement(By.cssSelector("strong"))
				.getText());
	}
}
