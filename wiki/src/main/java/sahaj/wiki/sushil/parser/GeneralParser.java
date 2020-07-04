package sahaj.wiki.sushil.parser;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sahaj.sushil.utils.Constants;

public class GeneralParser implements Parser {
    private static final Logger logger = LogManager.getLogger(GeneralParser.class);

    public String[] parse(final String input, String delimiter) {
        if (StringUtils.isBlank(input)) {
            logger.warn("Blank input was passed. Returning...");
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }

        if (null == delimiter) {
            delimiter = Constants.NEW_LINE;
        }

        return input.split(delimiter);
    }
}
