package sahaj.wiki.sushil.statement.match.trie;


import static org.junit.Assert.*;
import static sahaj.wiki.sushil.utils.TestHelper.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.map.HashedMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sahaj.sushil.utils.Constants;
import sahaj.wiki.sushil.constant.ElementType;
import sahaj.wiki.sushil.exception.InvalidArgumentException;
import sahaj.wiki.sushil.keyword.trie.Trie;
import sahaj.wiki.sushil.statement.match.trie.StatementProcessor.StatementProcessorBuilder;

public class StatementProcessorTest {
    private StatementProcessor testSubject;
    private StatementProcessorBuilder builder;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        testSubject = createTestSubject();
    }

    @After
    public void tearDown() throws Exception {
        builder = null;
    }

    private StatementProcessor createTestSubject() {
        builder = new StatementProcessorBuilder();

        /*
         * final Map<ElementType, List<String>> input = new HashedMap<>(2); final String para =
         * "This is a valid paragraph. Programming is an art. An skilled artist can only craft a beautiful art." +
         * " To create an art, you must be dedicated towards it. " +
         * "Josh Bloch, Martin Fowler and Robert Martin are known craftsmen in Java programming. " +
         * "Gangs of four takes the programming to next level. Each developer who wants to craft the code must read their books."
         * ;
         *
         * final String q1 = "Who can craft a beautiful art?"; final String q2 = "What is required to create an art?";
         * final String q3 = "Who are known craftsmen in Java programming?"; final String q4 =
         * "What does the Gangs of four do?"; final String q5 = "What must a developer do to craft the code?";
         *
         * final List<String> questionsList = Stream.of(q1, q2, q3, q4, q5).collect(Collectors.toList());
         *
         * final KeywordsBuilder<Map<String, HashSet<Integer>>> questionKeywordBuilder = new
         * HashMapBasedQuestionKeywordBuilder(); final Map<String, HashSet<Integer>> questionKeywords =
         * questionKeywordBuilder.buildKeywords(questionsList);
         *
         * final String answer = "programming to next level; read their books; be dedicated; An skilled artist; " +
         * "Josh Bloch, Martin Fowler and Robert Martin"; final KeywordsBuilder<Trie<String>> answerKeywordsBuilder =
         * new TrieBasedAnswerKeywordBuilder(); final List<String> answerAsList = Arrays.asList(answer); final
         * Trie<String> answerKeywords = answerKeywordsBuilder.buildKeywords(answerAsList);
         *
         * input.put(ElementType.STATEMENT, Arrays.asList(para)); input.put(ElementType.QUESTION, questionsList);
         * input.put(ElementType.ANSWER, answerAsList);
         */

        final Map<ElementType, List<String>> input = getParsedInput();
        final Trie<String> answerKeywords = buildAnswerKeywords();
        final Map<String, HashSet<Integer>> questionKeywords = buildQuestionKeywords();

        builder.input(input).answerKeywords(answerKeywords).questionKeywords(questionKeywords);

        return builder.build();
    }

    @Test
    public void testConstructionWithoutSettingRawInput() throws Exception {
        builder.input(null);

        expectedException.expect(InvalidArgumentException.class);
        expectedException.expectMessage("Invalid input (null or empty) was passed.");

        builder.build();
    }

    @Test
    public void testConstructionWithoutSettingAnswerTrie() throws Exception {
        builder.answerKeywords(null);

        expectedException.expect(InvalidArgumentException.class);
        expectedException.expectMessage("Invalid answer keywords trie (null or empty) was passed.");

        builder.build();
    }

    @Test
    public void testConstructionWithoutSettingQuestionKeywords() throws Exception {
        builder.questionKeywords(null);

        expectedException.expect(InvalidArgumentException.class);
        expectedException.expectMessage("Invalid question keywords (null or empty) were passed.");

        builder.build();
    }

    @Test
    public void testConstruction() throws Exception {
        assertNotNull(testSubject);
    }

    @Test
    public void testProcessWithInvalidStatements() {
        final Map<ElementType, List<String>> input = new HashedMap<>(2);
        input.put(ElementType.STATEMENT, Arrays.asList(""));


        expectedException.expect(InvalidArgumentException.class);
        expectedException.expectMessage("Invalid statements were passed as input");

        testSubject = builder.input(input).build();
    }

    @Test
    public void testProcessForFirstCorrectAnswer() {
        final List<String> answers = testSubject.processAndGetCorrectAnswerIds();

        assertNotNull(answers);
        assertEquals(ANSWER1, answers.get(Constants.ZERO));
    }

    @Test
    public void testProcessForSecondCorrectAnswer() {
        final List<String> answers = testSubject.processAndGetCorrectAnswerIds();

        assertNotNull(answers);
        assertEquals(ANSWER2, answers.get(Constants.ONE));
    }

    @Test
    public void testProcessForAllCorrectAnswer() {
        final List<String> answers = testSubject.processAndGetCorrectAnswerIds();

        assertNotNull(answers);
        assertEquals(expectedAnswerList, answers);
    }
}