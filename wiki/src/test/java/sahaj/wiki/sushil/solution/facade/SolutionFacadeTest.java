package sahaj.wiki.sushil.solution.facade;


import static org.junit.Assert.assertEquals;
import static sahaj.wiki.sushil.utils.TestHelper.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class SolutionFacadeTest {
    private SolutionFacade testSubject;

    @Before
    public void setUp() throws Exception {
        testSubject = createTestSubject();
    }

    @After
    public void tearDown() throws Exception {

    }

    private SolutionFacade createTestSubject() {
        return new SolutionFacade();
    }

    @Test
    public void testGetCorrectSequnetialAnswersFromProblemStatement() {
        final List<String> result = testSubject.getCorrectSequnetialAnswers(getProblemStatementInput());
        assertEquals(getExpectedOutputForProblemStatement(), result);
    }

    @Test
    public void testWithCustomPara() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PARA).append(Q1).append(Q2).append(Q3).append(Q4).append(Q5);
        stringBuilder.append(ANSWER);

        final List<String> result = testSubject.getCorrectSequnetialAnswers(stringBuilder.toString());
        assertEquals(expectedAnswerList, result);
    }
}