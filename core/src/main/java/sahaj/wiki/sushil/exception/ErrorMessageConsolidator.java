package sahaj.wiki.sushil.exception;

import static sahaj.sushil.utils.Constants.NEW_LINE;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class ErrorMessageConsolidator {
    public static String getConsolidatedErrorMessage(final List<Exception> errors) {
        if (CollectionUtils.isEmpty(errors)) {
            return StringUtils.EMPTY;
        }

        final StringBuilder sb = new StringBuilder();

        for (final Exception e : errors) {
            sb.append(e.getMessage()).append(NEW_LINE);
        }

        return sb.toString();
    }
}
