package sahaj.wiki.sushil.input;

import java.util.ArrayList;
import java.util.Map;

import sahaj.wiki.sushil.constant.ElementType;

public interface InputReader {
	Map<ElementType, ArrayList<String>> readInput(String source);
}
