package com.sarah.utility;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Created by sarah on 11/30/2017.
 */
public class Validator {

    public boolean isStringNumeric(String stringNumber) {
        if (stringNumber.matches("^-?\\d+$")) {
            return true;
        } else {
            return false;
        }
    }

    public String cleanInput(String input) {

        return StringEscapeUtils.escapeJava(input);
    }
}
