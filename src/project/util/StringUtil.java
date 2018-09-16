package project.util;

import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class StringUtil {

    private static final String EMPTY = "";
    private static final String ZERO = "0";

    public static boolean isEmpty(String value) {
        return Objects.isNull(value) || EMPTY.equals(value.trim());
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public boolean areNumbersAsStringValid(String[] numbers) {
        boolean result = true;

        for (String number : numbers) {
            if (isNumberAsStringWrong(number)) {
                result = false;
            }
        }

        return result;
    }

    public boolean isNumberAsStringWrong(String number) {
        return Integer.valueOf(number) <= 0;
    }
}
