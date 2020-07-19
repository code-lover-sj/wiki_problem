package sahaj.wiki.sushil.keyword.answer.holder;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sahaj.sushil.utils.Constants;
import sahaj.wiki.sushil.keyword.trie.Trie;
import sahaj.wiki.sushil.keyword.trie.TrieNode;

public class TrieBasedAnswerKeywordBuilderTest {
    private static final String answer3 = "2";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private TrieBasedAnswerKeywordBuilder testSubject;
    private Trie<String> trie;

    @Before
    public void setUp() throws Exception {
        testSubject = createTestSubject();

        final String answer1 = "This is answer 3";
        final String answer2 = "answer 1";


        final ArrayList<String> answerList = (ArrayList<String>) Stream.of(answer1, answer2, answer3)
                .collect(Collectors.toList());

        trie = testSubject.buildKeywords(answerList);
    }

    @After
    public void tearDown() throws Exception {
        testSubject = null;
        trie = null;
    }

    private TrieBasedAnswerKeywordBuilder createTestSubject() {
        return new TrieBasedAnswerKeywordBuilder();
    }

    @Test
    public void testLeafNode() throws Exception {
        final TrieNode<String> leafNode = trie.findUnderRoot(answer3.toLowerCase());
        assertNotNull(leafNode);
        assertTrue(leafNode.getIds().contains(2));
    }

    @Test
    public void testMultipleTermsOfAnswers() {
        String searchTerm = "answer";
        TrieNode<String> searched = trie.findUnderRoot(searchTerm);


        assertNotNull(searched);

        searchTerm = "1";

        searched = trie.find(searched, searchTerm);

        assertNotNull(searched);

        assertNotNull(searched);
        assertTrue(1 == searched.getIds().size());
    }

    @Test
    public void testBuildKeywords() throws Exception {
        assertNotNull(trie);
        assertNotNull(trie.findUnderRoot(answer3));
    }

    @Test
    public void testSameLeaves() {
        final String s1 = "Coffee";
        final String s2 = "Coffee";


        final Trie<String> trie2 = testSubject.buildKeywords(Arrays.asList(s1, s2));
        assertNotNull(trie2);

        final TrieNode<String> node = trie2.findUnderRoot(s1.toLowerCase());
        assertNotNull(node);

        assertTrue(node.getIds().contains(Constants.ZERO));
        assertTrue(node.getIds().contains(Constants.ONE));
    }
}