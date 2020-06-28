package sahaj.wiki.sushil.input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import sahaj.sushil.utils.Builder;

public class StringInputReader implements InputReder {
    private final InputType INPUT_TYPE = InputType.STRING;

    @Override
    public Map<InputElementType, ArrayList<String>> readInput(final String source) {
        // TODO Auto-generated method stub
        return Collections.emptyMap();
    }

    public static class StringInputReaderBuilder implements Builder<StringInputReader> {
        @Override
        public StringInputReader build() {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
