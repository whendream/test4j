package org.test4j.spec.txt;

import org.test4j.junit.JSpec;
import org.test4j.spec.annotations.Given;
import org.test4j.spec.annotations.Named;
import org.test4j.spec.annotations.StoryFile;
import org.test4j.spec.annotations.StorySource;
import org.test4j.spec.annotations.StoryType;
import org.test4j.spec.annotations.Then;

@StoryFile(type = StoryType.TXT, source = StorySource.ClassPath)
public class SkipStepDemo extends JSpec {

    StringBuilder buff = null;

    @Given
    public void inputInitString(final @Named("初始值") String init// <br>
    ) throws Exception {
        this.buff = new StringBuilder(init);
    }

    @Given
    public void appendThisString(final @Named("参数") String append// <br>
    ) throws Exception {
        this.buff.append(",").append(append);
    }

    @Then
    public void checkFinalText(final @Named("合并字符串") String finalString// <br>
    ) throws Exception {
        want.string(this.buff.toString()).isEqualTo(finalString);
    }
}
