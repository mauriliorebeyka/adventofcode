package adventofcode.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import adventofcode.challenge.InputReader;
import adventofcode.challenge.InputReaderFileFactory;
import adventofcode.challenge.InputReaderHttpClientFactory;
import adventofcode.challenge.InputUtilsResponseHandler;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InputReaderTest {

	@MockBean
	private InputReaderHttpClientFactory inputReaderHttpClientFactory;

	@MockBean
	private InputReaderFileFactory inputReaderFileFactory;

	@Mock
	private CloseableHttpClient mockCloseableHttpClient;

	@Mock
	private BufferedReader mockBufferedReader;

	@Mock
	private PrintWriter mockPrintWriter;

	@Autowired
	private InputReader inputReader;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.openMocks(this);

		when(inputReaderHttpClientFactory.getHttpClient()).thenReturn(mockCloseableHttpClient);
		when(inputReaderFileFactory.getBufferedReader(any(File.class))).thenReturn(mockBufferedReader);
		when(inputReaderFileFactory.getPrintWriter(any(File.class))).thenReturn(mockPrintWriter);
		when(mockBufferedReader.lines()).thenReturn(new ArrayList<String>().stream());

	}

	@Test
	public void testReadFromWeb() throws Exception {
		when(mockCloseableHttpClient.execute(any(HttpGet.class), any(InputUtilsResponseHandler.class)))
				.thenReturn("Line1\nLine2");
		List<String> input = Arrays.asList("Line1", "Line2");
		assertEquals(input, inputReader.readMultipleLines(2020, 1));
		verify(mockPrintWriter,times(2)).println(anyString());
	}

	@Test
	public void testReadFromFile() throws Exception {
		when(mockBufferedReader.lines()).thenReturn(Arrays.stream(new String[] { "File1", "File2" }));
		List<String> input = Arrays.asList("File1", "File2");
		assertEquals(input, inputReader.readMultipleLines(2020, 1));
		verifyNoInteractions(mockCloseableHttpClient);
	}
}
