package com.fortify.demo.steps.libraries;

import static java.util.Objects.isNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {

    /**
     * Tests a value from the {@code source} by the given regular expression
     * <p>
     * regex example: {@code Verification Code: (\d+)}
     *
     * @param source a text where the information for test should be present
     * @param regex  a regex pattern
     * @return true, if source text conforms regex.
     */
    public static Boolean test(final String source, final String regex) {
        if (isNull(source)) {
            return false;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        return matcher.find();
    }

}
