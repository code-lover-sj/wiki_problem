package sahaj.wiki.sushil.input.validator;

import static org.junit.Assert.*;
import static sahaj.sushil.utils.Constants.*;

import java.util.ArrayList;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

public class InputValidatorImplTest {
    private final InputValidatorImpl validator = new InputValidatorImpl();

    @Test
    public void testWithEmptyErrorListAndNullInput() {
        final boolean valid = validator.validate(null, null);

        assertFalse(valid);
    }

    @Test
    public void testWithEmptyErrorListAndBlankInput() {
        final boolean valid = validator.validate("", null);

        assertFalse(valid);
    }

    @Test
    public void testWithErrorListAndBlankInput() {
        final ArrayList<Exception> errors = new ArrayList<>(ONE);
        final boolean valid = validator.validate("", errors);

        assertFalse(valid);
        assertTrue(CollectionUtils.isNotEmpty(errors));
        assertTrue(ONE == errors.size());
    }

    @Test
    public void testWithErrorListAndValidInput() {
        final ArrayList<Exception> errors = new ArrayList<>(ONE);
        final boolean valid = validator.validate("Valid Input", errors);

        assertTrue(valid);
        assertTrue(CollectionUtils.isEmpty(errors));
    }
}
