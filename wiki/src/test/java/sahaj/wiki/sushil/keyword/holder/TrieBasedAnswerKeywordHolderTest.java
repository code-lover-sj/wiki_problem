package sahaj.wiki.sushil.keyword.holder;

import static sahaj.sushil.utils.Constants.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sahaj.wiki.sushil.keyword.trie.LeafNode;
import sahaj.wiki.sushil.keyword.trie.Trie;
import sahaj.wiki.sushil.keyword.trie.TrieNode;

public class TrieBasedAnswerKeywordHolderTest {
    private static final String answer3 = "2";

    private TrieBasedAnswerKeywordHolder testSubject;
    private Trie<String> trie;

    @Before
    public void setUp() throws Exception {
        testSubject = createTestSubject();

        final String answer1 = "This is answer 3";
        final String answer2 = "answer 1";


        final ArrayList<String> answerList = (ArrayList<String>) Stream.of(answer1, answer2, answer3)
                .collect(Collectors.toList());

        trie = testSubject.buildTrieFromAnswers(answerList);
    }

    @After
    public void tearDown() throws Exception {
        testSubject = null;
        trie = null;
    }

    private TrieBasedAnswerKeywordHolder createTestSubject() {
        return new TrieBasedAnswerKeywordHolder();
    }

    @Test
    public void testBuildTrieFromAnswers() throws Exception {
        assertNotNull(trie);
        assertNotNull(trie.findUnderRoot(answer3));
    }

    @Test
    public void testLeafNode() throws Exception {
        final TrieNode<String> finalAnswerTerm = trie.findUnderRoot(answer3.toLowerCase());
        final TrieNode<String> leafNode = trie.find(finalAnswerTerm, LEAF_TRIE_NODE.toLowerCase());
        assertNotNull(leafNode);
        assertTrue(leafNode instanceof LeafNode);
        assertTrue(2 == ((LeafNode<String>) leafNode).getId());
    }

    @Test
    public void testMultipleTermsOfAnswers() {
        String searchTerm = "answer";
        TrieNode<String> searched = trie.findUnderRoot(searchTerm);


        assertNotNull(searched);

        searchTerm = "1";

        searched = trie.find(searched, searchTerm);

        assertNotNull(searched);

        searched = trie.find(searched, LEAF_TRIE_NODE.toLowerCase());
        assertNotNull(searched);
        assertTrue(1 == ((LeafNode<String>) searched).getId());
    }
}