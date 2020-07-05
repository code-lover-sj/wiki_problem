package sahaj.wiki.sushil.keyword.trie;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

public class TrieNode<T> {
    private final T data;
    final Map<T, TrieNode<T>> children = new HashMap<>();

    public TrieNode(final T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (MapUtils.isEmpty(children) ? 0 : children.hashCode());
        result = prime * result + (data == null ? 0 : data.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TrieNode<?> other = (TrieNode<?>) obj;
        if (MapUtils.isEmpty(children)) {
            if (MapUtils.isNotEmpty(children)) {
                return false;
            }
        } else if (!children.equals(other.children)) {
            return false;
        }
        if (data == null) {
            if (other.data != null) {
                return false;
            }
        } else if (!data.equals(other.data)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TrieNode [data=" + data + ", children=" + children + "]";
    }
}
