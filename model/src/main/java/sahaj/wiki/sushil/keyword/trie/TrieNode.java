package sahaj.wiki.sushil.keyword.trie;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import sahaj.sushil.utils.Constants;

public class TrieNode<T> {
    private final T data;
    private final Set<Integer> ids;

    final Map<T, TrieNode<T>> children = new HashMap<>();

    public TrieNode(final T data) {
        this.data = data;
        ids = new HashSet<>();
    }

    public T getData() {
        return data;
    }

    public Map<T, TrieNode<T>> getChildren() {
        return Collections.unmodifiableMap(children);
    }

    public Set<Integer> getIds() {
        return Collections.unmodifiableSet(ids);
    }

    public void addIds(final Set<Integer> ids) {
        this.ids.addAll(ids);
    }

    public void addId(final int id) {
        this.ids.add(id);
    }

    public boolean isLeaf() {
        return ids.size() >= Constants.ONE;
    }


    @Override
    public int hashCode() {
        return Objects.hash(children, data, ids);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TrieNode)) {
            return false;
        }
        final TrieNode other = (TrieNode) obj;
        return Objects.equals(children, other.children) && Objects.equals(data, other.data)
                && Objects.equals(ids, other.ids);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("TrieNode [");
        if (data != null) {
            builder.append("data=").append(data).append(", ");
        }
        if (ids != null) {
            builder.append("ids=").append(ids).append(", ");
        }
        // Uncomment in case want to see the actual trie.
        /*
         * if (!children.isEmpty()) { builder.append("children=").append(children); }
         */
        builder.append("]");
        return builder.toString();
    }
}
