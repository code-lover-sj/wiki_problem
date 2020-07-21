package sahaj.wiki.sushil.keyword.question.builder;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sahaj.sushil.utils.Constants;
import sahaj.wiki.sushil.keyword.exception.InvalidQuestionException;
import sahaj.wiki.sushil.keyword.question.builder.HashMapBasedQuestionKeywordBuilder;

public class HashMapBasedQuestionKeywordBuilderTest {
    private HashMapBasedQuestionKeywordBuilder testSubject;
    private List<String> questions;

    @Before
    public void setUp() throws Exception {
        testSubject = createTestSubject();
        final String q1 = "What is the name of first Prime Minister of India?";
        final String q2 = "Who is known as the father of nation?";
        final String q3 = "Which bird is the national bird?";
        final String q4 = "This is the random question with all common words - why where how when what which and or of are is were was as a with";
        final String q5 = "True or False? National game of India is Cricket.";

        questions = new ArrayList<>(3);

        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);
        questions.add(q5);
    }

    @After
    public void tearDown() throws Exception {
        testSubject = null;
        questions.clear();
        questions = null;
    }

    private HashMapBasedQuestionKeywordBuilder createTestSubject() {
        return new HashMapBasedQuestionKeywordBuilder();
    }


    @Test
    public void testGetQuestionKeywordsForNullQuestions() throws Exception {
        final Map<String, HashSet<Integer>> result = testSubject.buildKeywords(null);

        assertTrue(MapUtils.isEmpty(result));
    }

    @Test
    public void testGetQuestionKeywordsForNullQuestion() throws Exception {
        questions.add(null);

        final String expectedException = "Should have thrown InvalidQuestionException";
        try {
            testSubject.buildKeywords(questions);
            fail(expectedException);
        } catch (final InvalidQuestionException iqe) {

        } catch (final Exception e) {
            fail(expectedException);
        }
    }


    @Test
    public void testGetQuestionKeywordsForWordWithPuctuation() throws Exception {
        final Map<String, HashSet<Integer>> result = testSubject.buildKeywords(questions);

        assertNotNull(result);
        assertFalse(result.containsKey("cricket."));
    }

    @Test
    public void testGetQuestionKeywordsForWordWithoutPuctuation() throws Exception {
        final Map<String, HashSet<Integer>> result = testSubject.buildKeywords(questions);

        assertNotNull(result);
        assertTrue(result.containsKey("cricket"));
    }

    @Test
    public void testGetQuestionKeywordsForValidWord() throws Exception {
        final Map<String, HashSet<Integer>> result = testSubject.buildKeywords(questions);

        assertTrue(result.containsKey("name"));
    }

    @Test
    public void testKeywordsDonotConatinCommonWordCapitalized() {
        final Map<String, HashSet<Integer>> result = testSubject.buildKeywords(questions);

        assertNotNull(result);
        assertFalse(result.containsKey("What"));
    }

    @Test
    public void testKeywordsDonotConatinCommonWordLowerCase() {
        final Map<String, HashSet<Integer>> result = testSubject.buildKeywords(questions);

        assertNotNull(result);
        assertFalse(result.containsKey("what"));
    }

    @Test
    public void testKeywordsDonotConatinAnyCommonWordLowerCase() {
        final Map<String, HashSet<Integer>> result = testSubject.buildKeywords(questions);

        assertNotNull(result);
        Constants.COMMON_WORDS.forEach(word -> assertFalse(result.containsKey(word)));
    }

    @Test
    public void testKeywordsDonotConatinAnyCommonWordCapitalized() {
        final Map<String, HashSet<Integer>> result = testSubject.buildKeywords(questions);

        assertNotNull(result);
        Constants.COMMON_WORDS.forEach(word -> assertFalse(result.containsKey(StringUtils.capitalize(word))));
    }

    @Test
    public void testKeywordsDoNotContainValidWordCapitalized() {
        final Map<String, HashSet<Integer>> result = testSubject.buildKeywords(questions);

        assertFalse(result.containsKey("Prime"));
    }

    @Test
    public void testKeywordsContainsCorrectQueNo() {
        final Map<String, HashSet<Integer>> result = testSubject.buildKeywords(questions);

        final HashSet<Integer> primeWord = result.get("prime");
        assertNotNull(primeWord);
        assertTrue(1 == primeWord.size());
        assertTrue(primeWord.contains(0));
    }

    @Test
    public void testKeywordsContainsCorrectQueNos() {
        final Map<String, HashSet<Integer>> result = testSubject.buildKeywords(questions);

        final HashSet<Integer> primeWord = result.get("national");
        assertNotNull(primeWord);
        assertTrue(2 == primeWord.size());
        assertTrue(primeWord.contains(4) && primeWord.contains(2));
    }
}