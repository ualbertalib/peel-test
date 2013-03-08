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
import org.openqa.selenium.support.pagefactory.ByChained;

public class FacetSteps extends SearchSteps {

	String facetText = "";
	String facetHits = "";
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

	@Given("visitor is on the browse page")
	public void givenVisitorIsOnTheBrowsePage() {
		driver.get(baseUrl + "browse/");
		driver.manage().timeouts().pageLoadTimeout(WAIT_TIME, TimeUnit.SECONDS);
		assertEquals("Browse Peel Bibliography", driver.getTitle());
	}

	@Given("visitor is on the <category> page")
	public void givenVisitorIsOnTheCategoryPage(
			@Named("category") String category) {
		driver.get(baseUrl + "browse/" + category + "/");
		driver.manage().timeouts().pageLoadTimeout(WAIT_TIME, TimeUnit.SECONDS);
	}

	@When("user enters $prefix in the $categoryType searchbox")
	@Alias("user enters $prefix in the <categoryType> searchbox")
	public void whenUserEntersPrefixInTheSearchbox(
			@Named("prefix") String prefix,
			@Named("categoryType") String categoryType) {
		driver.findElement(By.id(categoryType)).clear();
		WebElement element = driver.findElement(By.id(categoryType));
		element.sendKeys(prefix);
		element.findElement(new ByChained(By.xpath(".."), By.name("submit")))
				.click();
	}

	@When("user selects location on map")
	public void whenUserSelectsEdmontonAlberta1Hit() {
		driver.findElement(By.id("map-tool")).click();
		driver.findElement(By.id("mtgt_unnamed_0")).click();
		WebElement location = driver.findElement(By.linkText("Search"));
		WebElement parent = location.findElement(By.xpath(".."));
		facetText = parent.getText();
		facetHits = facetText.replaceAll("[^0-9]", "");
		facetText = facetText.substring(0, facetText.indexOf("(")).trim();
		location.click();
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

	@When("user clicks <position> entry")
	public void whenUserClicksPositionEntry(@Named("position") String position) {
		List<WebElement> facets = driver
				.findElements(By
				.xpath("//*[@id=\"mainCol\"]/div/ul/li/a"));
		WebElement facet = selectPositionInCategory(position, facets);
		facetText = facet.findElement(By.xpath("..")).getText();
		facet.click();
		facetHits = facetText.replaceAll("(^.*\\()*( hit(s)*\\)$)*", "");
		facetText = facetText.substring(0, facetText.indexOf("(")).trim();
	}

	@When("user clicks <position> entry of <category>")
	public void whenUserClicksEntryOfCategory(
			@Named("position") String position,
			@Named("category") String category) {
		int catIndex = 0;
		if ("author".equals(category))
			catIndex = 1;
		else if ("title".equals(category))
			catIndex = 2;
		else if ("subject".equals(category))
			catIndex = 3;
		List<WebElement> facets = driver.findElements(By
				.xpath("//*[@id=\"mainCol\"]/div[" + catIndex + "]/ul/li/a"));
		WebElement facet = selectPositionInCategory(position, facets);
		facetText = facet.findElement(By.xpath("..")).getText();
		facet.click();
		facetHits = facetText.replaceAll("(^.*\\()*( hit(s)*\\)$)*", "");
		facetText = facetText.substring(0, facetText.indexOf("(")).trim();

	}

	private void selectFacet(String position, String xpath,
			String cssSelectFacet) {
		List<WebElement> facets = driver.findElements(By.xpath(xpath));
		if (!facets.get(0).isDisplayed()) {
			driver.findElement(By.cssSelector(cssSelectFacet))
					.click();
		}
		WebElement facet = selectPositionInCategory(position, facets);

		facetText = facet.getText();
		facet.click();

		String suffix = facetText.endsWith("hits") ? " hits" : " hit";
		facetHits = facetText.substring(facetText.indexOf(": ") + 2,
				facetText.indexOf(suffix));
		facetText = facetText.substring(0, facetText.indexOf(":"));
	}

	private WebElement selectPositionInCategory(String position,
			List<WebElement> facets) {
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
		return facet;
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
		SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
		String[] dates = facetText.split(" \\p{Pd} ");
		Date lowerDate = formatYear.parse(dates[0]);

		SimpleDateFormat formatFull = new SimpleDateFormat("MMMMM dd, yyyy");
		Date upperDate = formatFull.parse("December 31, " + dates[1]);

		WebElement firstResult = driver.findElement(By
				.xpath("//li[@class='result'][@value=1]"));
		String[] split = firstResult.findElement(By.xpath("dl/dt")).getText()
				.split(", ");
		Date date = formatFull.parse(split[1] + ", " + split[2]);

		assertTrue("should be greater than " + lowerDate,
				lowerDate.compareTo(date) <= 0);
		assertTrue("should be less than " + upperDate,
				upperDate.compareTo(date) >= 0);
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

			assertTrue("should be greater than " + lowerDate,
					lowerDate.compareTo(date) <= 0);
			assertTrue("should be greater than " + upperDate,
					upperDate.compareTo(date) >= 0);
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
				+ facetText + "'",
				resultPublicationActual.matches(Pattern.quote(facetText)));
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

	@Then("entries match $prefix")
	public void thenEntriesMatchPrefix(@Named("prefix")String prefix) {
		List<WebElement> facets = driver.findElements(By
				.xpath("//*[@id=\"mainCol\"]/div/ul/li/a"));
		assertTrue("first entry should match prefix", 
				facets.get(0).getText().startsWith(prefix));
		assertTrue("last entry should match prefix",
				facets.get(facets.size() - 1)
				.getText().startsWith(prefix));
		Random rand = new Random();
		if (1 < facets.size())
			assertTrue("random entry should match prefix",
					facets.get(rand.nextInt(facets.size() - 1)).getText()
							.startsWith(prefix));
	}

	@Then("<display-sort> is fixed")
	@Alias("$display-sort is fixed")
	public void thenSortIsFixed(@Named("display-sort") String sort) {
		assertEquals(sort, driver.findElement(By.cssSelector("strong"))
				.getText());
	}
}
