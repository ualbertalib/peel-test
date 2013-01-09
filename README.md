peel-test
=========

Tests for peel.library.ualberta.ca

Background
----------
This package performs tests to validate and verify the peel-www project.  This package uses the Behaviour Driven Development (BDD) methodology by first writing 'stories' using the JBehave story syntax.  The stories are implemented as Selenese JUnit test cases and performed in the Firefox web browser.  

By default the package will test peel.library.ualberta.ca.  To test another target set the baseUrl in test.properties.  See test.properties.sample for an example.

Requirements
------------
* Java 6+
* Apache Ant [http://ant.apache.org/]
* Apache Ivy [http://ant.apache.org/ivy/]
* Mozilla Firefox [http://www.mozilla.org/en-US/firefox/new/]
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
