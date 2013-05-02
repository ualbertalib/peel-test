peel-test
=========

Tests for peel.library.ualberta.ca

Background
----------
This package performs tests to validate and verify the peel-www project.  This package uses the Behaviour Driven Development (BDD) methodology by first writing 'stories' using the JBehave story syntax.  The stories are implemented as Selenese JUnit test cases and performed in the Firefox web browser by default.  

By default the package will test peeldev1.library.ualberta.ca.  To test another target set the baseUrl in test.properties or as an environment variable.  Browser used to test against can also be configured by setting browser (chrome, iexplorer, safari, and firefox).  If you are running tests against the sample included in peel-solr/test-files/indexing set sample to peel-solr.  See test.properties.sample for an example.

Requirements
------------
* Java 6+
* Apache Ant [http://ant.apache.org/]
* Apache Ivy [http://ant.apache.org/ivy/]
* Mozilla Firefox [http://www.mozilla.org/en-US/firefox/new/]
* Other Browsers
  * Internet Explorer (Windows only)
  * Safari
  * Chrome (or Chromium) (Windows and Linux)
* A network connection
 * to download dependencies
 * to access pages under test

To Run
------
    ant

See also
--------
* JBehave [http://jbehave.org/]
* Selenium [http://seleniumhq.org/]
