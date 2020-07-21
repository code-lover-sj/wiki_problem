package sahaj.wiki.sushil.keyword;

import java.util.List;

public interface KeywordsBuilder<DATA_STRUCTURE> {
    DATA_STRUCTURE buildKeywords(final List<String> strings);

    // In case of inverted index, we may need to pass the structure to this method.
    DATA_STRUCTURE buildKeywords(final List<String> strings, final DATA_STRUCTURE dataStructure);
}
