package org.jtester.spec.txt;

import org.jtester.junit.JSpec;
import org.jtester.spec.annotations.StoryFile;
import org.jtester.spec.annotations.StorySource;
import org.jtester.spec.annotations.StoryType;
import org.test4j.spec.annotations.Given;
import org.test4j.spec.annotations.Named;
import org.test4j.spec.annotations.Then;
import org.test4j.spec.annotations.When;

@StoryFile(type = StoryType.TXT, source = StorySource.ClassPath, encoding = "GBK")
public class JSpecDemo extends JSpec {
    private String greeting    = "";

    private String guest       = "";

    private String housemaster = "";

    private String date        = "";

    @Given
    public void giveGreeting(@Named("guest") String guest,// <br>
                             @Named("greeting") String greeting,// <br>
                             @Named("housemaster") String housemaster,// <br>
                             @Named("date") String date// <br>
    ) {
        this.greeting = greeting;
        this.guest = guest;
        this.housemaster = housemaster;
        this.date = date;
    }

    String wholeword = "";

    @When
    public void doGreeting() {
        this.wholeword = String.format("%s %s %s on %s.", this.housemaster, this.greeting, this.guest, this.date);
    }

    @Then
    public void checkGreeting(@Named("完整句子") String expected) {
        want.string(this.wholeword).isEqualTo(expected);
    }
}
