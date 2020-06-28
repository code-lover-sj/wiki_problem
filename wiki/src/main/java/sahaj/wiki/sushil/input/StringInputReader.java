package sahaj.wiki.sushil.input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import sahaj.sushil.utils.Builder;
import sahaj.wiki.sushil.input.constant.InputElementType;

public class StringInputReader extends AbstractInputReader {
    @Override
    public Map<InputElementType, ArrayList<String>> _readInput(final String source) {
        return Collections.emptyMap();
    }

    public static class StringInputReaderBuilder implements Builder<StringInputReader> {
        public StringInputReader build() {
            // TODO Auto-generated method stub
            return null;
        }

    }
}
