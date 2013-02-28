package ca.ualibrary.dit.peel.stories;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class FacetSteps extends SearchSteps {

	String facetText = "";
	String facetHits = "";
	protected static long WAIT_TIME = 10;

	@Given("visitor is on a newspapers results page for 'alberta'")
	public void givenVisitorIsOnANewspapersResultsPageForAlberta() {
		driver.get(baseUrl
				+ "search/?search=raw&pageNumber=1&field=body&rawQuery=alberta&index=newspapers");
		driver.manage().timeouts().pageLoadTimeout(WAIT_TIME, TimeUnit.SECONDS);
		assertEquals("Search Results", driver.getTitle());
	}

	@Given("visitor is on a peelbib results page for 'canada'")
	public void givenVisitorIsOnAPeelbibResultsPageForCanada() {
		driver.get(baseUrl
				+ "search/?search=raw&pageNumber=1&field=body&rawQuery=canada&index=peelbib");
		driver.manage().timeouts().pageLoadTimeout(WAIT_TIME, TimeUnit.SECONDS);
		assertEquals("Search Results", driver.getTitle());
	}

	@When("user clicks on $display-sort")
	@Alias("user clicks on <display-sort>")
	public void whenUserClicksOnSort(@Named("display-sort") String sort) {
		List<WebElement> facets = driver.findElements(By
				.xpath("//*[@id=\"subCol\"]/div[1]/ul/li/a"));
		if (!facets.get(0).isDisplayed())
			driver.findElement(By.id("sort-options")).click();
		driver.findElement(By.linkText(sort)).click();
	}

	@When("user selects publication $position")
	public void whenUserSelectsPublication(
			@Named("position") String position) {
		selectFacet(position, "//*[@id=\"subCol\"]/div[4]/ul/li/a",
				"h4#newspapers-block");
	}

	@When("user selects newspaper $position language")
	public void whenUserSelectsNewspaperLangauge(
			@Named("position") String position) {
		selectFacet(position, "//*[@id=\"subCol\"]/div[3]/ul/li/a",
				"h4#languages-block");
	}

	@When("user selects peelbib $position language")
	public void whenUserSelectsPeelbibLangauge(
			@Named("position") String position) {
		selectFacet(position, "//*[@id=\"subCol\"]/div[4]/ul/li/a",
				"h4#languages-block");
	}

	@When("user selects $range range of publication")
	public void whenUserSelectsRangeOfPublication(@Named("range") String range) {
		selectFacet(range, "//*[@id=\"subCol\"]/div[2]/ul/li/a",
				"h4#years-in-publication");
	}

	@When("user selects fulltext available")
	public void whenUserSelectsFulltextAvailable() {
		selectFacet("first", "//*[@id=\"subCol\"]/div[5]/ul/li/a",
				"h4#digstatus-block");
	}

	@When("user selects peelbib $position subject")
	public void whenUserSelectsPeelbibSubject(
			@Named("position") String position) {
		List<WebElement> facets = driver.findElements(By.xpath("//*[@id=\"subCol\"]/div[6]/table/tbody/tr"));
		if (!facets.get(0).isDisplayed()) {
			driver.findElement(By.cssSelector("h4#found-in")).click();
		}
		WebElement facet = null;
		if ("first".equals(position))
			facet = facets.get(1);  // the 0 index element is the sort options
		else if ("last".equals(position))
			facet = facets.get(facets.size() - 1);
		else {
			Random rand = new Random();
			// random number in range
			if (1 < facets.size())
				facet = facets.get(rand.nextInt(facets.size() - 1));
			else
				facet = facets.get(1);
		}
		facetText = facet.findElement(By.xpath("th")).getText();
		facetHits = facet.findElement(By.xpath("td")).getText();
		facet.findElement(By.xpath("th/a")).click();
	}

	private void selectFacet(String position, String xpath,
			String cssSelectFacet) {
		List<WebElement> facets = driver.findElements(By.xpath(xpath));
		if (!facets.get(0).isDisplayed()) {
			driver.findElement(By.cssSelector(cssSelectFacet))
					.click();
		}
		WebElement facet = null;
		if ("first".equals(position))
			facet = facets.get(0);
		else if ("last".equals(position))
			facet = facets.get(facets.size() - 1);
		else {
			Random rand = new Random();
			// random number in range
			if (1 < facets.size())
				facet = facets.get(rand.nextInt(facets.size() - 1));
			else
				facet = facets.get(0);
		}
		facetText = facet.getText();

		String suffix = facetText.endsWith("hits") ? " hits" : " hit";
		facetHits = facetText.substring(facetText.indexOf(": ") + 2,
				facetText.indexOf(suffix));
		facetText = facetText.substring(0, facetText.indexOf(":"));

		facet.click();
	}

	@Then("$facet match breadcrumbs year")
	public void thenFacetMatchBreadcrumbsYear() {
		if (isSample) {
			String breadcrumbsFoundExpected = "^[\\s\\S]*Query:.*"
					+ Pattern.quote(facetText.replaceAll("\\p{Pd}", "TO"))
					+ ".*[\\s\\S]*$";

			String breadcrumbsFoundActual = driver.findElement(
					By.className("breadcrumbs")).getText();
			assertTrue("'" + breadcrumbsFoundActual + "' should match regex '"
					+ breadcrumbsFoundExpected + "'",
					breadcrumbsFoundActual.matches(breadcrumbsFoundExpected));
		}
	}

	@Then("$facet match breadcrumbs")
	public void thenFacetMatchBreadcrumbs() {
		if (isSample) {
			String breadcrumbsFoundExpected = "^[\\s\\S]*Query:.*"
					+ Pattern.quote(facetText)
					+ ".*[\\s\\S]*$";

			String breadcrumbsFoundActual = driver.findElement(
					By.className("breadcrumbs")).getText();
			assertTrue("'" + breadcrumbsFoundActual + "' should match regex '"
					+ breadcrumbsFoundExpected + "'",
					breadcrumbsFoundActual.matches(breadcrumbsFoundExpected));
		}
	}

	@Then("results have newspaper years of publication in $range range")
	public void thenResultsHaveNewspaperPublicationInRange()
			throws ParseException {
		SimpleDateFormat formatFacet = new SimpleDateFormat("yyyy");
		String[] dates = facetText.split(" \\p{Pd} ");
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

	@Then("results have peelbib years of publication in $range range")
	public void thenResultsHavePeelbibYearsOfPublicationInRange()
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		String[] dates = facetText.split(" \\p{Pd} ");
		Date lowerDate = format.parse(dates[0]);
		Date upperDate = format.parse(dates[1]);

		WebElement firstResult = driver.findElement(By
				.xpath("//li[@class='result'][@value=1]"));
		String pubyear1 = firstResult
				.findElement(By.xpath("dl/dd[@class='info']")).getText().trim();
		int sample = pubyear1.length() < 6 ? pubyear1.length() : 6;
		// ignore this it's not the publication year
		if (!pubyear1.matches(".*\\[[0-9]{4}\\]")) {
			Date date = format.parse(pubyear1.substring(
					pubyear1.length() - sample).replaceAll("[^0-9]*", ""));

			assertTrue("should be in range", lowerDate.compareTo(date) <= 0);
			assertTrue("should be in range", upperDate.compareTo(date) >= 0);
		}
	}

	@Then("results have $position language")
	public void thenResultsHaveLanguage() {
		String resultLanguageActual = driver.findElement(
				By.xpath("//li[@class='result'][@value=1]/dl/dd[@class='by']"))
				.getText();
		resultLanguageActual = resultLanguageActual
				.substring(resultLanguageActual.indexOf("Language: ")
						+ "Language: ".length());
		assertTrue("'" + resultLanguageActual + "' should match '" + facetText
				+ "'", resultLanguageActual.matches(facetText));
	}

	@Then("results have $position publication")
	public void thenResultsHavePublication() {
		String resultPublicationActual = driver.findElement(
				By.xpath("//li[@class='result'][@value=1]/dl/dt/cite/em"))
				.getText();
		assertTrue("'" + resultPublicationActual + "' should match '"
				+ facetText + "'", resultPublicationActual.matches(facetText));
	}

	@Then("results have fulltext available")
	public void thenResultsHaveFulltextAvailable() {
		List<WebElement> results = driver.findElements(By
				.xpath("//li[@class='result']/dl/dd[3]/span/a"));
		for (WebElement result : results) {
			String resultFulltextActual = result.getText();
			assertTrue("'" + resultFulltextActual + "' should match '"
					+ facetText + "'", resultFulltextActual.matches(facetText));
		}
	}

	@Then("$facet match hits")
	public void thenFacetMatchHits() {
		thenHitsEquals(facetHits);
	}

	@Then("<display-sort> is fixed")
	@Alias("$display-sort is fixed")
	public void thensortIsFixed(@Named("display-sort") String sort) {
		assertEquals(sort, driver.findElement(By.cssSelector("strong"))
				.getText());
	}
}
