package org.jtester.datafilling.annotations;

import org.jtester.datafilling.strategies.WrongTypeStrategy;
import org.test4j.datafilling.annotations.FillWith;
public class StringWithWrongStrategyTypePojo {

	@FillWith(value = WrongTypeStrategy.class)
	private String postCodeDestinedToFail;

	/**
	 * @return the postCodeDestinedToFail
	 */
	public String getPostCodeDestinedToFail() {
		return postCodeDestinedToFail;
	}

	/**
	 * @param postCodeDestinedToFail
	 *            the postCodeDestinedToFail to set
	 */
	public void setPostCodeDestinedToFail(String postCodeDestinedToFail) {
		this.postCodeDestinedToFail = postCodeDestinedToFail;
	}
}
