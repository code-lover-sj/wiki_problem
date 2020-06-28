package sahaj.wiki.sushil.input;

import java.util.ArrayList;
import java.util.Map;

import sahaj.wiki.sushil.input.constant.InputElementType;

public interface InputReader {
	Map<InputElementType, ArrayList<String>> readInput(String source);
}
