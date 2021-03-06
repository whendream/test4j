package org.test4j.json.encoder.single.fixed;

import java.util.Locale;

import org.junit.Test;
import org.test4j.json.encoder.EncoderTest;
import org.test4j.json.encoder.single.fixed.LocaleEncoder;

public class LocaleEncoderTest extends EncoderTest {

	@Test
	public void testLocaleEncoder() {
		Locale locale = Locale.CHINESE;

		LocaleEncoder encoder = LocaleEncoder.instance;
		this.setUnmarkFeature(encoder);
		encoder.encode(locale, writer, references);

		String json = writer.toString();
		want.string(json).isEqualTo("'zh'");
	}
}
