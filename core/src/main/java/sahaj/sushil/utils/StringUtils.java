package sahaj.sushil.utils;

public class StringUtils {
    private StringUtils() {
    }

    public static String chopPunctuation(String term) {
        char lastChar = term.charAt(term.length() - 1);
        while (!Character.isAlphabetic(lastChar) && !Character.isDigit(lastChar)) {
            term = org.apache.commons.lang3.StringUtils.chop(term);

            if (org.apache.commons.lang3.StringUtils.isBlank(term)) {
                return term;
            }

            lastChar = term.charAt(term.length() - 1);
        }

        return term;
    }

    public static String toLowerCaseWithChoppedPunctuation(String term) {
        if (org.apache.commons.lang3.StringUtils.isBlank(term)) {
            return term;
        }

        term = term.toLowerCase().trim().intern();
        return chopPunctuation(term);
    }
}
