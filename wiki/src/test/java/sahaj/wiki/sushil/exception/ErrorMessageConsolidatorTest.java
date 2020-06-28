package sahaj.wiki.sushil.exception;

import static org.junit.Assert.*;

import static sahaj.sushil.utils.Constants.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class ErrorMessageConsolidatorTest {
    @Test
    public void testWithEmptyErrorList() {
        final String emptyMsg = ErrorMessageConsolidator.getConsolidatedErrorMessage(Collections.emptyList());
        assertTrue(StringUtils.isBlank(emptyMsg));
    }

    @Test
    public void testWithNullErrorList() {
        final String emptyMsg = ErrorMessageConsolidator.getConsolidatedErrorMessage(Collections.emptyList());
        assertTrue(StringUtils.isBlank(emptyMsg));
    }

    @Test
    public void testWithActualErrorList() {
        final List<Exception> errors = new ArrayList<>(2);
        final String msg1 = "Unsupported input type.";
        errors.add(new InvalidArgumentException(msg1));

        final String msg2 = "No ending character is found.";
        errors.add(new InvalidArgumentException(msg2));


        final String consolidatedMsg = ErrorMessageConsolidator.getConsolidatedErrorMessage(errors);
        assertTrue(StringUtils.isNotBlank(consolidatedMsg));
        final String expected = msg1 + NEW_LINE + msg2 + NEW_LINE;

        assertEquals("Expected = " + expected + ", Actual = " + consolidatedMsg, expected, consolidatedMsg);
    }
}
