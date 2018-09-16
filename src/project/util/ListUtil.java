package project.util;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ListUtil {

    public static <T> boolean areValuesNull(List<T> list) {
        boolean result = false;
        for (T t : list) {
            if (t == null) {
                result = true;
            }
        }

        return result;
    }
}
