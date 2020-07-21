package sahaj.wiki.sushil.input.factory;

import java.util.Arrays;
import java.util.Objects;

import sahaj.wiki.sushil.input.InputReader;
import sahaj.wiki.sushil.input.StringInputReader;
import sahaj.wiki.sushil.input.constant.InputType;
import sahaj.wiki.sushil.input.exception.UnsupportedInputTypeException;

public class InputReaderFactory {
    private InputReaderFactory() {

    }

    public static InputReader getInputReader(final InputType inputType) {
        Objects.requireNonNull(inputType,
                "Invalid input type. Supported types are " + Arrays.toString(InputType.values()));

        switch (inputType) {
            case STRING: {
                return new StringInputReader();
            }

            // Planning to support File and URL input types in future.
            default: {
                throw new UnsupportedInputTypeException("Sorry!!! Only STRING type is supported as of now.");
            }
        }
    }
}
