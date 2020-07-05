package sahaj.wiki.sushil.keyword.trie;

import java.util.Objects;

public class LeafNode<T> extends TrieNode<T> {
    private final int id;

    public LeafNode(final T data, final int id) {
        super(data);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(id);
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof LeafNode)) {
            return false;
        }
        final LeafNode<?> other = (LeafNode<?>) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("LeafNode [id=").append(id).append(", ");
        if (children != null) {
            builder.append("children=").append(children);
        }
        builder.append("]");
        return builder.toString();
    }
}
