package sahaj.wiki.sushil.statement.match.trie;


import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sahaj.sushil.utils.Constants;
import sahaj.wiki.sushil.exception.InvalidArgumentException;
import sahaj.wiki.sushil.keyword.answer.builder.TrieBasedAnswerKeywordBuilder;
import sahaj.wiki.sushil.keyword.question.builder.HashMapBasedQuestionKeywordBuilder;
import sahaj.wiki.sushil.keyword.trie.Trie;
import sahaj.wiki.sushil.statement.match.StatementToAnswerAndQuestionMatcher;
import sahaj.wiki.sushil.utils.TestHelper;

public class TrieBasedStatementParserAndMatcherTest {
    private TrieBasedStatementParserAndMatcher testSubject;
    private Trie<String> answerKeywords;
    private Map<String, HashSet<Integer>> questionKeywords;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        answerKeywords = TestHelper.getAnswerTrie();
        questionKeywords = TestHelper.getQuestionKeywords();

        testSubject = createTestSubject();
    }

    private TrieBasedStatementParserAndMatcher createTestSubject() {
        return new TrieBasedStatementParserAndMatcher();
    }

    @Test
    public void testParseAndMatchStatementWithAnswerAndQuestionWithNullStmt() {
        expectedException.expect(InvalidArgumentException.class);
        expectedException.expectMessage("Invalid statement");
        testSubject.parseAndMatchStatementWithAnswerAndQuestion(null, 0, answerKeywords, questionKeywords);
    }

    @Test
    public void testParseAndMatchStatementWithAnswerAndQuestion() {
        final String stmt = "Pt Jawaharlal Neharu was the first Prime Minister of India.";
        final StatementToAnswerAndQuestionMatcher result = testSubject.parseAndMatchStatementWithAnswerAndQuestion(stmt,
                0, answerKeywords, questionKeywords);
        assertNotNull(result);
        assertEquals(Constants.ONE, result.getAnswerIds().size());
        assertTrue(result.getAnswerIds().contains(Constants.ZERO));

        assertEquals(Constants.ONE, result.getQuestionIdToCountMap().size());
        assertTrue(result.getQuestionIdToCountMap().containsKey(2));
    }

    @Test
    public void testParseAndMatchStatementWithAnswerAndQuestionWithRandomStmt() {
        final String stmt = "It is very random statement.";
        final StatementToAnswerAndQuestionMatcher result = testSubject.parseAndMatchStatementWithAnswerAndQuestion(stmt,
                0, answerKeywords, questionKeywords);

        assertNotNull(result);
        assertTrue(CollectionUtils.isEmpty(result.getAnswerIds()));
        assertTrue(MapUtils.isEmpty(result.getQuestionIdToCountMap()));
    }

    @Test
    public void testParseAndMatchStatementWithAnswerAndQuestionWithKeywordInQueOnly() {
        final String stmt = "Nation will be always greatful to Mahatama Gandhiji!";
        final StatementToAnswerAndQuestionMatcher result = testSubject.parseAndMatchStatementWithAnswerAndQuestion(stmt,
                0, answerKeywords, questionKeywords);

        assertNotNull(result);
        assertTrue(CollectionUtils.isEmpty(result.getAnswerIds()));

        assertNotNull(result.getQuestionIdToCountMap());
        assertEquals(1, result.getQuestionIdToCountMap().size());
    }

    @Test
    public void testParseAndMatchStatementWithAnswerAndQuestionWithAllKeywordsInAnsOnly() {
        final String stmt = "Cricket was invented by Britishers!!!";
        final StatementToAnswerAndQuestionMatcher result = testSubject.parseAndMatchStatementWithAnswerAndQuestion(stmt,
                0, answerKeywords, questionKeywords);

        assertNotNull(result);
        assertNotNull(result.getAnswerIds());
        assertTrue(result.getAnswerIds().contains(2));

        assertTrue(MapUtils.isEmpty(result.getQuestionIdToCountMap()));
    }

    @Test
    public void testParseAndMatchStatementWithAnswerAndQuestionWithFewKeywordsInAns() {
        final String stmt = "Pt Jawaharlal Neharu spent 9 years in Jail!";
        final StatementToAnswerAndQuestionMatcher result = testSubject.parseAndMatchStatementWithAnswerAndQuestion(stmt,
                0, answerKeywords, questionKeywords);

        assertNotNull(result);
        assertTrue(CollectionUtils.isEmpty(result.getAnswerIds()));
    }

    @Test
    public void testParseAndMatchStatementWithAnswerAndQuestionWithSameNoOfKeywordsInQuestion() {
        final String stmt = "Largest dam in the country is the most popular in the nation.";
        final StatementToAnswerAndQuestionMatcher result = testSubject.parseAndMatchStatementWithAnswerAndQuestion(stmt,
                0, answerKeywords, questionKeywords);

        assertNotNull(result);
        assertTrue(CollectionUtils.isEmpty(result.getAnswerIds()));

        final Set<Integer> questionIds = result.getQuestionIdToCountMap().keySet();
        // final Set<Integer> questionIds = result.getQuestionIds();
        assertNotNull(questionIds);
        assertTrue(2 == questionIds.size());
        assertTrue(questionIds.containsAll(Stream.of(Constants.ZERO, Constants.ONE).collect(Collectors.toSet())));
    }

    @Test
    public void testSelective() {
        final String stmt = "The plains zebra and the mountain zebra belong to the subgenus Hippotigris, but Grévy's zebra is the sole species of subgenus Dolichohippus.";

        final String q1 = "Which Zebras are endangered?";
        final String q2 = "Which are the three species of zebras?";
        final String q3 = "Which subgenus do the plains zebra and the mountain zebra belong to?";


        final Map<String, HashSet<Integer>> queKeys = new HashMapBasedQuestionKeywordBuilder().buildKeywords(Arrays.asList(q1, q2, q3));

        final String a1 = "subgenus Hippotigris";
        final Trie<String> ansKeys = new TrieBasedAnswerKeywordBuilder().buildKeywords(Arrays.asList(a1));

        final StatementToAnswerAndQuestionMatcher result = testSubject.parseAndMatchStatementWithAnswerAndQuestion(stmt,
                0, ansKeys, queKeys);
        assertTrue(result.getAnswerIds().contains(Constants.ZERO));
    }
}