package sahaj.wiki.sushil.statement.match;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import sahaj.sushil.utils.Constants;
import sahaj.sushil.utils.SystemConfig;
import sahaj.wiki.sushil.exception.InvalidArgumentException;

@Ignore
public class QuestionCollisionResolverStrategyTest {
    private final SystemConfig sysConfig = new SystemConfig();
    StatementToAnswerAndQuestionMatcher matchedStmt;

    @Before
    public void setUp() throws Exception {
        final Set<Integer> questionIds = Stream.of(1, 2).collect(Collectors.toSet());
        matchedStmt = new StatementToAnswerAndQuestionMatcher(Constants.ZERO);
        matchedStmt.setQuestionId(2);
    }

    @After
    public void tearDown() throws Exception {

    }

    private QuestionCollisionResolverStrategy createTestSubject() {
        return null;
    }

    @Test
    public void testGetRandomStrategy() {
        final QuestionCollisionResolverStrategy strategy = QuestionCollisionResolverStrategy
                .valueOf(sysConfig.getQuestionCollisionResolverStrategy());

        assertSame(QuestionCollisionResolverStrategy.RANDOM, strategy);
    }

    @Test
    public void testGetWeightStrategy() {
        final QuestionCollisionResolverStrategy strategy = QuestionCollisionResolverStrategy
                .valueOf("WEIGHED_ON_NO_OF_WORDS");

        assertSame(QuestionCollisionResolverStrategy.WEIGHED_ON_NO_OF_WORDS, strategy);
    }

    @Test
    public void testGetCharStrategy() {
        final QuestionCollisionResolverStrategy strategy = QuestionCollisionResolverStrategy.valueOf("CHARACTER_MATCH");

        assertSame(QuestionCollisionResolverStrategy.CHARACTER_MATCH, strategy);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetStrategyWithInvalidString() {
        final QuestionCollisionResolverStrategy strategy = QuestionCollisionResolverStrategy.valueOf("");
    }

    @Test(expected = InvalidArgumentException.class)
    public void testRandomStrategyWithInvalidQuestionIds() {
        final QuestionCollisionResolverStrategy strategy = QuestionCollisionResolverStrategy
                .valueOf(sysConfig.getQuestionCollisionResolverStrategy());

        /*matchedStmt.setQuestionIds(null);
        strategy.resolveQuestionCollision(null, matchedStmt);*/
    }

    @Test
    public void testGetQuestionIdResolvedUsingRandom() {
        final QuestionCollisionResolverStrategy strategy = QuestionCollisionResolverStrategy
                .valueOf(sysConfig.getQuestionCollisionResolverStrategy());

        final int questionId = strategy.resolveQuestionCollision(null, matchedStmt);
        assertTrue(questionId > 0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetQuestionIdUsingWeightStrategy() {
        QuestionCollisionResolverStrategy.WEIGHED_ON_NO_OF_WORDS.resolveQuestionCollision(null, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetQuestionIdUsingCharStrategy() {
        QuestionCollisionResolverStrategy.CHARACTER_MATCH.resolveQuestionCollision(null, null);
    }
}