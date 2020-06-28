package sahaj.wiki.sushil.input;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sahaj.wiki.sushil.input.constant.InputElementType;
import sahaj.wiki.sushil.input.constant.InputType;
import sahaj.wiki.sushil.input.factory.InputReaderFactory;
import sahaj.wiki.sushil.exception.InvalidArgumentException;
import sahaj.wiki.sushil.input.InputReader;

public class StringInputReaderTest {
    private final InputReader inputReader = InputReaderFactory.getInputReader(InputType.STRING);

    private String input;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        this.input = "This is the paragraph line1. This is line2. This is line3."
                + "This is Question 1."
                + "This is Question 2"
                + "This is Question 3"
                + "This is Question 4"
                + "This is Question 5"
                + "These are jumbled up answers 5; answer3; answer 2; answer1; 4";
    }

    @After
    public void tearDown() {
    }


    @Test
    public void testReadInputWithNullInput() {
        expectedException.expect(InvalidArgumentException.class);
        expectedException.expectMessage("Invalid argument was passed.");

        inputReader.readInput(null);
    }

    @Test
    public void testSuccessfulReadInput() {
        final Map<InputElementType, ArrayList<String>> readInput = inputReader.readInput(input);

        assertNotNull(readInput);
        assertTrue(MapUtils.isNotEmpty(readInput));
    }
}
