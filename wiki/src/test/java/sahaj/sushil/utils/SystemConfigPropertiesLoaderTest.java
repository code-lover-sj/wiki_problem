package sahaj.sushil.utils;


import static org.junit.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sahaj.wiki.sushil.exception.InvalidArgumentException;

public class SystemConfigPropertiesLoaderTest {
    private static final String NO_SUCH_PROPERTY = "No such property";

    private static final String FIVE = "5";

    private static final String NO_OF_QUESTIONS = "no_of_questions";

    private final Logger logger = LogManager.getLogger(SystemConfigPropertiesLoaderTest.class.toString());

    private SystemConfigPropertiesLoader testSubject;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        testSubject = createTestSubject();
    }

    @After
    public void tearDown() throws Exception {
        testSubject = null;
    }

    private SystemConfigPropertiesLoader createTestSubject() {
        return new SystemConfigPropertiesLoader(Constants.DEFAULT_SYS_CONFIG_PROPS_FILE);
    }

    @Test
    public void testGetPropertyWithNullFile() throws Exception {
        testSubject = new SystemConfigPropertiesLoader(null);

        expectedException.expect(InvalidArgumentException.class);
        expectedException.expectMessage(NO_SUCH_PROPERTY);
        testSubject.getProperty("temp");
    }

    @Test
    public void testGetPropertyWithNonExistingFile() throws Exception {
        testSubject = new SystemConfigPropertiesLoader("fake.txt");

        expectedException.expect(InvalidArgumentException.class);
        expectedException.expectMessage(NO_SUCH_PROPERTY);
        testSubject.getProperty("temp");
    }

    @Test
    public void testGetPropertyWithoutPassingFile() throws Exception {
        logger.info("Testing get property with auto loading system config properties file. Not passing file name.");
        testSubject = new SystemConfigPropertiesLoader(null);

        final String result = testSubject.getProperty(NO_OF_QUESTIONS);
        assertEquals(FIVE, result);
    }

    @Test
    public void testGetInvalidProperty() throws Exception {
        final String key = "no_property";

        expectedException.expect(InvalidArgumentException.class);
        expectedException.expectMessage(NO_SUCH_PROPERTY);

        testSubject.getProperty(key);
    }

    @Test
    public void testGetValidProperty() throws Exception {
        final String key = NO_OF_QUESTIONS;
        String result;

        result = testSubject.getProperty(key);

        assertEquals(FIVE, result);

        result = testSubject.getProperty("answer_delimiter");
        assertEquals(";", result);
    }

    @Test
    public void testGetSecondProperty() throws Exception {
        final String key = NO_OF_QUESTIONS;
        String result;

        result = testSubject.getProperty(key);

        assertEquals(FIVE, result);

        result = testSubject.getProperty("answer_delimiter");
        assertEquals(";", result);
    }
}