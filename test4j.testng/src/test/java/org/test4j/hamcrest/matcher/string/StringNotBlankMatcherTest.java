package org.test4j.hamcrest.matcher.string;

import java.util.Iterator;

import org.test4j.testng.Test4J;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = "assertion")
public class StringNotBlankMatcherTest extends Test4J {

	@Test(expectedExceptions = AssertionError.class, dataProvider = "dataForNotBlank")
	public void testNotBlank(String actual) {
		want.string(actual).notBlank();
	}

	@SuppressWarnings("rawtypes")
	@DataProvider
	public Iterator dataForNotBlank() {
		return new DataIterator() {
			{
				data((String) null);
				data("");
				data(" ");
				data("\n");
			}
		};
	}

	public void testNotBlank_Success() {
		want.string("tt").notBlank();
	}
}
