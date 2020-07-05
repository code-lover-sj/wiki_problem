package sahaj.wiki.sushil.keyword.trie;

import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sahaj.wiki.sushil.exception.InvalidArgumentException;

public class Trie<T> {
    private static final Logger logger = LogManager.getLogger(Trie.class);

    private final TrieNode<T> root = new TrieNode<>(null);

    public TrieNode<T> addUnderRoot(final T newItem) {
        return add(root, newItem);
    }

    public TrieNode<T> add(TrieNode<T> node, final T newItem) {
        if (null == node) {
            node = root;
        }

        TrieNode<T> found = find(node, newItem);
        if (null == found) {
            logger.info("No child was already present under {} for {}. Adding...", node.getData(), newItem);

            found = new TrieNode<>(newItem);
            node.children.put(newItem, found);
        }

        logger.debug("The trie after adding {} -> {}", newItem, this);

        return found;
    }

    public TrieNode<T> add(TrieNode<T> node, final TrieNode<T> newNode) {
        if (null == node) {
            node = root;
        }

        final T newItem = newNode.getData();
        TrieNode<T> found = find(node, newItem);
        if (null == found) {
            logger.info("No child was already present under {} for {}. Adding...", node.getData(), newItem);

            node.children.put(newItem, newNode);
            found = newNode;
        }

        logger.debug("The trie after adding {} -> {}", newItem, this);

        return found;
    }

    public TrieNode<T> findUnderRoot(final T item) {
        final TrieNode<T> found = find(root, item);

        if (null == found) {
            logger.warn("Couldn't find {} in the trie.", item);
        }

        return found;
    }


    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Trie [");
        builder.append("root=").append(root);
        builder.append("]");
        return builder.toString();
    }

    public TrieNode<T> find(final TrieNode<T> node, final T item) {
        // TrieNode<T> found = null;
        if (null == node) {
            throw new InvalidArgumentException(
                    "Non null node is expected to be passed to search under it. If you wished to search under root, use the method findUnderRoot instead.");
        }

        if (MapUtils.isNotEmpty(node.children) && node.children.containsKey(item)) {
            logger.info("Found {} under {}.", item, null == node.getData() ? "root" : node.getData());
            return node.children.get(item);
        }

        return null;

        /*
         * for (final TrieNode<T> child : node.children.values()) { found = find(child, item, ++attempt); if (null !=
         * found) { return found; } }
         *
         * return found;
         */
    }
}
