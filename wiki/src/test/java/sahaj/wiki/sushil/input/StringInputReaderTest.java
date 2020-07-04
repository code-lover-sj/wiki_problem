package sahaj.wiki.sushil.input;

import static sahaj.wiki.sushil.input.constant.InputElementType.*;
import static sahaj.sushil.utils.Constants.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sahaj.wiki.sushil.input.constant.InputElementType;
import sahaj.wiki.sushil.input.constant.InputType;
import sahaj.wiki.sushil.input.exception.InvalidInputException;
import sahaj.wiki.sushil.input.factory.InputReaderFactory;
import sahaj.sushil.utils.SystemConfig;
import sahaj.wiki.sushil.exception.InvalidArgumentException;

public class StringInputReaderTest {
    private static final Logger logger = LogManager.getLogger(StringInputReaderTest.class);

    private final InputReader inputReader = InputReaderFactory.getInputReader(InputType.STRING);

    Map<InputElementType, ArrayList<String>> parsedInput;

    private final SystemConfig sysConfig = new SystemConfig(DEFAULT_SYS_CONFIG_PROPS_FILE);

    private String input;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        this.input = "This is the paragraph line1. This is line2. This is line3.\n"
                + "This is Question 1?\n" + "This is Question 2?\n" + "This is Question 3?\n" + "This is Question 4?\n"
                + "This is Question 5?\n"
                + "These are jumbled up answers 5; answer3; answer 2; answer1; 4";

        parsedInput = inputReader.readInput(input);
    }

    @After
    public void tearDown() {
        parsedInput.clear();
    }

    @Test
    public void testReadInputWithNullInput() {
        expectedException.expect(InvalidArgumentException.class);
        expectedException.expectMessage("Null or blank input");

        inputReader.readInput(null);
    }

    @Test
    public void testReadInvalidInput() {
        expectedException.expect(InvalidInputException.class);

        inputReader.readInput("This is invalid input.");
    }

    @Test
    public void testInvalidQuestionInput() {
        expectedException.expect(InvalidInputException.class);
        expectedException.expectMessage("don't look like a question. Question must end with ?.");

        final String modifiedInput = this.input.replaceAll("\\?", ".");
        logger.info("Replaced all ? with . New input - {}", modifiedInput);

        inputReader.readInput(modifiedInput);
    }

    @Test
    public void testSuccessfulReadInput() {
        assertNotNull(parsedInput);
        assertTrue(MapUtils.isNotEmpty(parsedInput));
    }

    @Test
    public void testParsedStatementsFromInput() {
        final ArrayList<String> stmts = parsedInput.get(STATEMENT);
        assertNotNull(stmts);
        assertTrue(3 == stmts.size());
    }

    @Test
    public void testParsedQuestions() {
        final ArrayList<String> questions = parsedInput.get(QUESTION);
        assertNotNull(questions);
        logger.info("Questions size = {}. Questions = {}", questions.size(), questions);

        assertTrue(Integer.parseInt(sysConfig.getNoOfQuestions()) == questions.size());
    }

    @Test
    public void testLesserAnswersThanQuestions() {
        final String lastAnsRemoved = input.replaceAll("; 4", "");

        expectedException.expect(InvalidInputException.class);
        expectedException.expectMessage("Number of answers must be same as number of questions.");

        inputReader.readInput(lastAnsRemoved);
    }

    @Test
    public void testParsedAnswers() {
        final ArrayList<String> answers = parsedInput.get(ANSWER);
        assertNotNull(answers);
        assertTrue(Integer.parseInt(sysConfig.getNoOfQuestions()) == answers.size());
    }
}
