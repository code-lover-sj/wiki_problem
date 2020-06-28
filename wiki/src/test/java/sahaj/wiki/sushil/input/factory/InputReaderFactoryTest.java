package sahaj.wiki.sushil.input.factory;

import static org.junit.Assert.*;
import static sahaj.wiki.sushil.input.constant.InputType.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sahaj.wiki.sushil.input.InputReader;
import sahaj.wiki.sushil.input.StringInputReader;
import sahaj.wiki.sushil.input.exception.UnsupportedInputTypeException;
import sahaj.wiki.sushil.input.factory.InputReaderFactory;

public class InputReaderFactoryTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testExceptionInGetInputReaderForNullInputType() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Invalid input type.");

        InputReaderFactory.getInputReader(null);
    }

    @Test
    public void testExceptionIsThrownForFileInputType() {
        expectedException.expect(UnsupportedInputTypeException.class);
        expectedException.expectMessage("This input type is not yet supported.");

        InputReaderFactory.getInputReader(FILE);
    }

    @Test
    public void testExceptionIsThrownForURLInputType() {
        expectedException.expect(UnsupportedInputTypeException.class);
        expectedException.expectMessage("This input type is not yet supported.");

        InputReaderFactory.getInputReader(URL);
    }

    @Test
    public void testGetStringInputReader() {
        final InputReader stringInputReader = InputReaderFactory.getInputReader(STRING);

        assertNotNull(stringInputReader);
        assertTrue(stringInputReader instanceof StringInputReader);
    }
}
