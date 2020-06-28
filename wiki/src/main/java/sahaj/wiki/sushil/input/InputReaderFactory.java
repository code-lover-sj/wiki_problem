package sahaj.wiki.sushil.input;

import java.util.Arrays;
import java.util.Objects;

import sahaj.wiki.sushil.input.exception.UnsupportedInputTypeException;

public class InputReaderFactory {
    public static InputReder getInputReader(final InputType inputType) {
        Objects.requireNonNull(inputType,
                "Invalid input type. Supported types are " + Arrays.toString(InputType.values()));

        switch (inputType) {
            case STRING: {
                return new StringInputReader();
            }

            default: {
                throw new UnsupportedInputTypeException(
                        "This input type is not yet supported. Only STRING type is supported as of now");
            }
        }
    }
}
