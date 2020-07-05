package sahaj.wiki.sushil.keyword.holder;

import static sahaj.sushil.utils.Constants.*;
import java.util.ArrayList;

import sahaj.wiki.sushil.keyword.trie.LeafNode;
import sahaj.wiki.sushil.keyword.trie.Trie;
import sahaj.wiki.sushil.keyword.trie.TrieNode;

public class AnswerTrieHolder {
    public Trie<String> buildTrieFromAnswers(final ArrayList<String> answers) {
        final Trie<String> trie = new Trie<>();


        int answerId = 0;

        for (final String answer : answers) {
            final String[] answerTerms = answer.split(String.valueOf(SPACE));

            TrieNode<String> added = null;
            for (final String term : answerTerms) {
                added = trie.add(added, term);

            }

            final LeafNode<String> leaf = new LeafNode<>(LEAF_TRIE_NODE, answerId);
            trie.add(added, leaf);
            answerId++;
        }


        return trie;
    }
}
