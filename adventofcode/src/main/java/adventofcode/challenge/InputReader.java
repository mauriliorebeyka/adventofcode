package adventofcode.challenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import adventofcode.exceptions.InputParseException;

@Service
public class InputReader {

	private int year;

	private int day;

	@Autowired
	private InputReaderHttpClientFactory inputReaderHttpClientFactory;
	
	@Autowired
	private InputReaderFileFactory inputReaderFileFactory;
	
	private static final Logger LOG = LogManager.getLogger(InputReader.class);

	public List<String> readMultipleLines(int year, int day) throws InputParseException {
		this.year = year;
		this.day = day;
		LOG.debug("Reading input...");
		List<String> lines = readFromFile();
		if (lines.isEmpty()) {
			lines = readFromWeb();
			if (!lines.isEmpty()) {
				writeToFile(lines);
			}
		}
		LOG.info("Successfully read {} input lines", lines.size());
		return lines;
	}

	private void writeToFile(List<String> lines) {
		File file = getFile();
		try (PrintWriter writer = inputReaderFileFactory.getPrintWriter(file)) {
			lines.stream().forEach(writer::println);
			LOG.debug("Successfully wrote {} lines into file {}", lines.size(), file.getPath());
		} catch (IOException e) {
			LOG.error("Failed to write to input file", e);
		}
	}

	private List<String> readFromFile() {
		File file = getFile();
		List<String> lines = new ArrayList<>();
		try (BufferedReader reader = inputReaderFileFactory.getBufferedReader(file)) {
			lines = reader.lines().collect(Collectors.toList());
			LOG.debug("Successfully read {} lines from file {}", lines.size(), file.getPath());
		} catch (IOException e) {
			LOG.debug("Error reading from file");
		}
		return lines;
	}

	private List<String> readFromWeb() throws InputParseException {
		String endpoint = "https://adventofcode.com/" + year + "/day/" + day + "/input";
		LOG.debug("Reading input from web: {}",endpoint);
		try (CloseableHttpClient httpclient = inputReaderHttpClientFactory.getHttpClient()) {
			InputUtilsResponseHandler handler = new InputUtilsResponseHandler();
			HttpGet get = new HttpGet(endpoint);
			String response = httpclient.execute(get, handler);
			LOG.debug("Input successfully read");
			return Arrays.asList(response.split("\n"));
		} catch (IOException e) {
			LOG.error("Error reading from web: {}", e.getMessage().replace("\n", ""));
			throw new InputParseException("Error reading from web", e);
		}
	}

	private File getFile() {
		return new File(System.getProperty("java.io.tmpdir") + "/year" + year + "day" + day + ".txt");
	}
}