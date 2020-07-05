package sahaj.wiki.sushil.keyword.trie;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sahaj.wiki.sushil.exception.InvalidArgumentException;

public class TrieTest {
    private static final Logger logger = LogManager.getLogger(TrieTest.class);

    private static final String TEST1 = "Test1";

    private Trie<String> trie;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Before
    public void setUp() throws Exception {
        trie = createTestSubject();
    }

    @After
    public void tearDown() throws Exception {
        trie = null;
    }

    private Trie<String> createTestSubject() {
        return new Trie<>();
    }

    @Test
    public void testInvalidSearch() throws Exception {
        final TrieNode<String> searchedNode = trie.findUnderRoot(TEST1);
        assertNull(searchedNode);
    }

    @Test
    public void testAddOneTerm() throws Exception {
        final String newItem = TEST1;

        final TrieNode<String> added = trie.add(null, newItem);

        final TrieNode<String> searchedNode = trie.findUnderRoot(TEST1);
        assertNotNull(searchedNode);
        assertEquals(added, searchedNode);
    }

    @Test
    public void testAddChild() throws Exception {
        final String newItem = TEST1;

        final TrieNode<String> added = trie.add(null, newItem);
        final String test1Child = "Test1-Child";
        final TrieNode<String> child = trie.add(added, test1Child);

        logger.info(trie.toString());

        TrieNode<String> searchedNode = trie.findUnderRoot(TEST1);

        searchedNode = trie.find(searchedNode, test1Child);
        assertNotNull(searchedNode);
        assertEquals(child, searchedNode);
    }

    @Test
    public void testAddExistingChild() throws Exception {
        final String newItem = TEST1;

        final TrieNode<String> added = trie.add(null, newItem);
        final String test1Child = "Test1-Child";
        final TrieNode<String> child = trie.add(added, test1Child);

        logger.info(trie.toString());

        final TrieNode<String> child2 = trie.add(added, test1Child);
        assertEquals(child, child2);
        assertTrue(child == child2);


        /*
         * TrieNode<String> searchedNode = trie.findUnderRoot(TEST1);
         *
         * searchedNode = trie.find(searchedNode, test1Child); assertNotNull(searchedNode); assertEquals(child,
         * searchedNode);
         */
    }

    @Test
    public void testAddMultipleChildren() throws Exception {
        final String newItem = TEST1;

        final TrieNode<String> added = trie.add(null, newItem);
        final String test1Child = "Test1-Child";
        final TrieNode<String> child = trie.add(added, test1Child);

        final String test2 = "Test2";
        final TrieNode<String> added2 = trie.addUnderRoot(test2);

        final String test2Child = "Test2-Child";
        final TrieNode<String> child2 = trie.add(added2, test2Child);

        logger.info(trie.toString());

        final TrieNode<String> searchedNode = trie.find(added2, test2Child);
        assertEquals(child2, searchedNode);
    }

    @Test
    public void testAddNewNodeUnderRoot() {
        final TrieNode<String> node = new TrieNode<>(TEST1);
        final TrieNode<String> added = trie.add(null, node);

        final TrieNode<String> searchedNode = trie.findUnderRoot(TEST1);
        assertNotNull(searchedNode);
        assertEquals(added, searchedNode);
    }

    @Test
    public void testAddChildNode() throws Exception {
        final String newItem = TEST1;

        final TrieNode<String> added = trie.add(null, newItem);
        final String test1Child = "Test1-Child";
        final TrieNode<String> child = new TrieNode<String>(test1Child);
        final TrieNode<String> addedChild = trie.add(added, child);

        logger.info(trie.toString());

        final TrieNode<String> searchedNode = trie.find(added, test1Child);
        assertNotNull(searchedNode);
        assertEquals(addedChild, searchedNode);
    }

    @Test
    public void testAddExistingChildNode() throws Exception {
        final String newItem = TEST1;

        final TrieNode<String> added = trie.add(null, newItem);
        final String test1Child = "Test1-Child";
        final TrieNode<String> child = new TrieNode<String>(test1Child);
        final TrieNode<String> addedChild = trie.add(added, child);

        final TrieNode<String> readd = trie.add(added, addedChild);

        assertEquals(readd, addedChild);
    }

    @Test
    public void testFindUnderNodeUsingNull() {
        expectedException.expect(InvalidArgumentException.class);
        expectedException.expectMessage("Non null node is expected to be passed to search under it");
        trie.find(null, TEST1);
    }
}