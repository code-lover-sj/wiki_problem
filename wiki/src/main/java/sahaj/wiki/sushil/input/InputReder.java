package sahaj.wiki.sushil.input;

import java.util.ArrayList;
import java.util.Map;

public interface InputReder {
	Map<InputElementType, ArrayList<String>> readInput(String source);
}
