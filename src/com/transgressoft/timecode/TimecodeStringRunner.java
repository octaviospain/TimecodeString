/*
 * Copyright 2016, 2017 Octavio Calleya
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.transgressoft.timecode;

import org.docopt.*;

import java.io.*;
import java.util.*;

/**
 * Sample class to run the TimecodeString library by command line.
 * Uses {@link Docopt} library to parse command line arguments.
 *
 * @author Octavio Calleya
 * @version 1.0
 * @see <a href="http://docopt.org">docopt.org</a>
 * @see <a href="https://github.com/docopt/docopt.java">docopt for Java</a>
 */
public class TimecodeStringRunner {

	/***************************
	 * Command line usage
	 ***************************/

	private static final String DOC = "Timecode String.\n\n" +
			"Usage:\n" +
			"  TimecodeString <frame_rate> <value>\n" +
			"  TimecodeString <input_file> [-o <output_file>]\n\n" +
			"Options:\n" +
			"  -o = <output_file> specify output file [default: output.txt]\n";

	private static String frameRate;
	private static String value;
	private static String inputFilePath;
	private static String outputFilePath;
	private static List<TimecodeString> timecodes;

	public static void main(String... args) {
		Map<String, Object> options = new Docopt(DOC).withVersion("TimecodeString 0.1").parse(args);
		frameRate = (String) options.get("<frame_rate>");
		value = (String) options.get("<value>");
		inputFilePath = (String) options.get("<input_file>");
		outputFilePath = (String) options.get("-o");
		if (outputFilePath == null)
			outputFilePath = "output.txt";

		try {
			if (frameRate != null && value != null) {
				if (! outputFilePath.equals("output.txt"))
					System.err.println(DOC);
				else
					System.out.println(TimecodeString.of(frameRate, value).getOppositeRepresentation());
			}
			else if (inputFilePath != null) {
				readTimecodesFromFile();
				writeTimecodesToFile();
			}
		}
		catch (Exception exception) {
			System.err.println("Error: " + exception.getMessage());
		}
	}

	private static void readTimecodesFromFile() throws Exception {
		File inputFile = new File(inputFilePath);
		if (! inputFile.exists())
			throw new IllegalArgumentException("Input file doesn't exist");
		if (inputFile.isDirectory())
			throw new IllegalArgumentException("Input file can't be a directory");

		timecodes = new ArrayList<>();
		Scanner scanner = new Scanner(inputFile);
		String frameRate;
		String value;

		while (scanner.hasNextLine()) {
			StringTokenizer stk = new StringTokenizer(scanner.nextLine(), " ");
			if (stk.countTokens() != 2) {
				scanner.close();
				throw new IllegalArgumentException("Invalid line arguments. Should be <frame_rate> <value>");
			}

			frameRate = stk.nextToken();
			value = stk.nextToken();
			timecodes.add(TimecodeString.of(frameRate, value));
		}
		scanner.close();
	}

	private static void writeTimecodesToFile() throws Exception {
		File outputFile = new File(outputFilePath);

		PrintWriter printWriter = new PrintWriter(new FileOutputStream(outputFile));
		for (TimecodeString timecodeString : timecodes)
			printWriter.println(timecodeString.getOppositeRepresentation());
		printWriter.close();
		System.out.println("Timecode string conversion successfully dumped to " + outputFile.getAbsolutePath());
	}
}
