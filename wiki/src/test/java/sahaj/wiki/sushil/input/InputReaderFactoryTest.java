package sahaj.wiki.sushil.input; 

import static org.junit.Assert.*;
import static sahaj.wiki.sushil.input.InputType.*;

import org.junit.Test;

import sahaj.wiki.sushil.input.InputReaderFactory;
import sahaj.wiki.sushil.input.InputReder;
import sahaj.wiki.sushil.input.StringInputReader;

public class InputReaderFactoryTest {
	@Test
	public void testGetInputReader() {
		final InputReder stringInputReader = InputReaderFactory.getInputReader(STRING);
		
		assertNotNull(stringInputReader);
		assertTrue(stringInputReader instanceof StringInputReader);
	}
}
