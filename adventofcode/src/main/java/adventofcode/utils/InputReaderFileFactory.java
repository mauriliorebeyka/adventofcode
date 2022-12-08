package adventofcode.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.stereotype.Component;

@Component
public class InputReaderFileFactory {

	public PrintWriter getPrintWriter(File file) throws IOException {
		return new PrintWriter(new BufferedWriter(new FileWriter(file)));
	}
	
	public BufferedReader getBufferedReader(File file) throws FileNotFoundException {
		return new BufferedReader(new FileReader(file));
	}
}
