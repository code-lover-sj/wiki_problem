package sahaj.wiki.sushil.input;

import java.util.Map;
import java.util.List;
import sahaj.wiki.sushil.constant.ElementType;

/**
 * It presents a contract to accept the input as a {@link String} and convert it into a well segregated {@link Map}. Key
 * of the map denotes the {@link ElementType}. Value will be the {@link List} of all the {@link String} elements of that
 * type.
 */
public interface InputReader {
    Map<ElementType, List<String>> readInput(String source);
}
