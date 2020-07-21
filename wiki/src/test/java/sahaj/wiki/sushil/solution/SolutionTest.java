package sahaj.wiki.sushil.solution;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sahaj.wiki.sushil.utils.TestHelper;

public class SolutionTest {
    private Solution testSubject;

    @Before
    public void setUp() throws Exception {
        testSubject = createTestSubject();
    }

    @After
    public void tearDown() throws Exception {
        testSubject = null;
    }

    private Solution createTestSubject() {
        return new Solution();
    }

    @Test
    public void testSolve() throws Exception {
        final List<String> result = testSubject.solve(TestHelper.getProblemStatementInput());
        assertEquals(TestHelper.getExpectedOutputForProblemStatement(), result);
    }
}