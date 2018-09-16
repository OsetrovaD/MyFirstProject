package project.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class QuerySortUtil {

    public static final String BY_NAME_ASC = " ORDER BY name";
    public static final String BY_NAME_DESC = " ORDER BY name DESC";
    public static final String BY_YEAR_ASC = " ORDER BY issue_year";
    public static final String BY_YEAR_DESC = " ORDER BY issue_year DESC";
}
