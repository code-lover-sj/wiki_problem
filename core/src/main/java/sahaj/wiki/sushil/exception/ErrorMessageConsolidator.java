package sahaj.wiki.sushil.exception;

import static sahaj.sushil.utils.Constants.NEW_LINE;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class facilitates to consolidate all the error messages together.
 */
public class ErrorMessageConsolidator {
    private ErrorMessageConsolidator() {

    }

    private static final Logger logger = LogManager.getLogger(ErrorMessageConsolidator.class);

    public static String getConsolidatedErrorMessage(final List<Exception> errors) {
        if (CollectionUtils.isEmpty(errors)) {
            return StringUtils.EMPTY;
        }

        final StringBuilder sb = new StringBuilder();

        for (final Exception e : errors) {
            sb.append(e.getMessage()).append(NEW_LINE);
        }

        final String errorMessage = sb.toString();
        logger.error("All the errors during execution - {}", errorMessage);

        return errorMessage;
    }
}
