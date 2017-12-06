package com.sarah.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by sarah on 11/12/2017.
 */
public class DateFormatter {

    /**
     * Converts string to localdate
     * @param dateString date that is a string
     * @return LocalDate
     */
    public LocalDate convertStringToLocalDate(String dateString) {
        char myChar = dateString.charAt(1);

        // Handle string dates like "5 December, 2017", they should be "05 December, 2017" to work
        if (!Character.isDigit(myChar)) {
            dateString = "0" + dateString;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy");
        formatter = formatter.withLocale(Locale.US);  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        return LocalDate.parse(dateString, formatter);
    }

    /**
     * Converts localdate to string
     * @param date date that is a localdate
     * @return String value
     */
    public String convertLocalDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy");
        return date.format(formatter);
    }
}
