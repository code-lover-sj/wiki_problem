package sahaj.sushil.utils;

public class StringUtils {
    public static String chopPunctuation(String term) {
        final char lastChar = term.charAt(term.length() - 1);
        if (!Character.isAlphabetic(lastChar) && !Character.isDigit(lastChar)) {
            term = org.apache.commons.lang3.StringUtils.chop(term);
        }

        return term;
    }

    public static String toLowerCaseWithChoppedPunctuation(String term) {
        term = term.toLowerCase();
        return chopPunctuation(term);
    }

    public static void main(final String[] args) {
        toLowerCaseWithChoppedPunctuation("1");
    }
}
