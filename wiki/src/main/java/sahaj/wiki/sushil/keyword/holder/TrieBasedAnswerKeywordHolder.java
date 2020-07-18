package sahaj.wiki.sushil.keyword.holder;

import static sahaj.sushil.utils.Constants.*;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sahaj.wiki.sushil.keyword.trie.LeafNode;
import sahaj.wiki.sushil.keyword.trie.Trie;
import sahaj.wiki.sushil.keyword.trie.TrieNode;

public class TrieBasedAnswerKeywordHolder {
    private static final Logger logger = LogManager.getLogger(TrieBasedAnswerKeywordHolder.class);

    public Trie<String> buildTrieFromAnswers(final List<String> answers) {
        final Trie<String> trie = new Trie<>();


        int answerId = 0;

        for (final String answer : answers) {
            final String[] answerTerms = answer.split(SPACE);

            TrieNode<String> added = null;
            for (final String term : answerTerms) {
                added = trie.add(added, term.toLowerCase());
            }

            final LeafNode<String> leaf = new LeafNode<>(LEAF_TRIE_NODE.toLowerCase(), answerId);
            trie.add(added, leaf);
            answerId++;
        }


        return trie;
    }
}
