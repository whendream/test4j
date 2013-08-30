package org.jtester.datafilling.annotations;

import org.jtester.datafilling.utils.FillDataTestConstants;
import org.test4j.datafilling.annotations.FillString;

public class StringValuePojo  {


	@FillString(length = FillDataTestConstants.STR_ANNOTATION_TWENTY_LENGTH)
	/** A String attribute with length 20 */
	private String twentyLengthString;

	@FillString(value = FillDataTestConstants.STR_ANNOTATION_PRECISE_VALUE)
	private String preciseValueString;

	/**
	 * @return the twentyLengthString
	 */
	public String getTwentyLengthString() {
		return twentyLengthString;
	}

	/**
	 * @param twentyLengthString
	 *            the twentyLengthString to set
	 */
	public void setTwentyLengthString(String twentyLengthString) {
		this.twentyLengthString = twentyLengthString;
	}

	/**
	 * @return the preciseValueString
	 */
	public String getPreciseValueString() {
		return preciseValueString;
	}

	/**
	 * @param preciseValueString
	 *            the preciseValueString to set
	 */
	public void setPreciseValueString(String preciseValueString) {
		this.preciseValueString = preciseValueString;
	}
}
