package com.transgressoft.timecode;

import javafx.util.*;
import org.jooq.lambda.*;
import org.junit.jupiter.api.*;
import org.junit.platform.runner.*;
import org.junit.runner.*;

import java.util.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Acceptance tests for the given input and output cases with the problem.
 *
 * @author Octavio Calleya
 * @version 1.0
 */
@RunWith(JUnitPlatform.class)
public class AcceptanceTest {

	static Map<Pair<String, String>, String> fps25WithUnitsCases;
	static Map<Pair<String, String>, String> fps25WithFrameCountCases;
	static Map<Pair<String, String>, String> df30WithUnitCases;
	static Map<Pair<String, String>, String> df30WithFrameCountCases;

	@BeforeAll
	static void initTestInputs() {
		fps25WithUnitsCases = new HashMap<>();
		fps25WithFrameCountCases = new HashMap<>();
		df30WithUnitCases = new HashMap<>();
		df30WithFrameCountCases = new HashMap<>();
		addTestInputs();
	}

	static void addTestInputs() {
		addTestCase(fps25WithUnitsCases, "25fps", "00:00:00:01", "1");
		addTestCase(fps25WithUnitsCases, "25fps", "00:00:01:00", "25");
		addTestCase(fps25WithUnitsCases, "25fps", "00:00:10:00", "250");
		addTestCase(fps25WithUnitsCases, "25fps", "00:01:00:00", "1500");
		addTestCase(fps25WithUnitsCases, "25fps", "00:10:00:00", "15000");
		addTestCase(fps25WithUnitsCases, "25fps", "01:00:00:00", "90000");
		addTestCase(fps25WithUnitsCases, "25fps", "00:00:00:05", "5");
		addTestCase(fps25WithUnitsCases, "25fps", "00:00:13:14", "339");
		addTestCase(fps25WithUnitsCases, "25fps", "00:00:51:21", "1296");
		addTestCase(fps25WithUnitsCases, "25fps", "00:08:14:01", "12351");
		addTestCase(fps25WithUnitsCases, "25fps", "00:37:48:21", "56721");
		addTestCase(fps25WithUnitsCases, "25fps", "01:05:57:04", "98929");
		addTestCase(fps25WithUnitsCases, "25fps", "15:31:53:23", "1397848");
		addTestCase(fps25WithUnitsCases, "25fps", "22:42:55:18", "2044393");

		addTestCase(fps25WithFrameCountCases, "25fps", "1", "00:00:00:01");
		addTestCase(fps25WithFrameCountCases, "25fps", "25", "00:00:01:00");
		addTestCase(fps25WithFrameCountCases, "25fps", "250", "00:00:10:00");
		addTestCase(fps25WithFrameCountCases, "25fps", "1500", "00:01:00:00");
		addTestCase(fps25WithFrameCountCases, "25fps", "15000", "00:10:00:00");
		addTestCase(fps25WithFrameCountCases, "25fps", "90000", "01:00:00:00");
		addTestCase(fps25WithFrameCountCases, "25fps", "8", "00:00:00:08");
		addTestCase(fps25WithFrameCountCases, "25fps", "92", "00:00:03:17");
		addTestCase(fps25WithFrameCountCases, "25fps", "252", "00:00:10:02");
		addTestCase(fps25WithFrameCountCases, "25fps", "3481", "00:02:19:06");
		addTestCase(fps25WithFrameCountCases, "25fps", "59129", "00:39:25:04");
		addTestCase(fps25WithFrameCountCases, "25fps", "173161", "01:55:26:11");
		addTestCase(fps25WithFrameCountCases, "25fps", "1904771", "21:09:50:21");

		addTestCase(df30WithUnitCases, "30DF", "00:00:00;01", "1");
		addTestCase(df30WithUnitCases, "30DF", "00:00:01;00", "30");
		addTestCase(df30WithUnitCases, "30DF", "00:00:10;00", "300");
		addTestCase(df30WithUnitCases, "30DF", "00:01:00;02", "1800");
		addTestCase(df30WithUnitCases, "30DF", "00:10:00;00", "17982");
		addTestCase(df30WithUnitCases, "30DF", "01:00:00;00", "107892");
		addTestCase(df30WithUnitCases, "30DF", "00:00:00;09", "9");
		addTestCase(df30WithUnitCases, "30DF", "00:00:02;05", "65");
		addTestCase(df30WithUnitCases, "30DF", "00:00:45;21", "1371");
		addTestCase(df30WithUnitCases, "30DF", "00:01:27;04", "2612");
		addTestCase(df30WithUnitCases, "30DF", "00:14:18;26", "25740");
		addTestCase(df30WithUnitCases, "30DF", "01:11:34;08", "128700");
		addTestCase(df30WithUnitCases, "30DF", "18:18:51;16", "1975968");
		addTestCase(df30WithUnitCases, "30DF", "21:41:20;29", "2340087");

		addTestCase(df30WithFrameCountCases, "30DF", "1", "00:00:00;01");
		addTestCase(df30WithFrameCountCases, "30DF", "30", "00:00:01;00");
		addTestCase(df30WithFrameCountCases, "30DF", "300", "00:00:10;00");
		addTestCase(df30WithFrameCountCases, "30DF", "1800", "00:01:00;02");
		addTestCase(df30WithFrameCountCases, "30DF", "17982", "00:10:00;00");
		addTestCase(df30WithFrameCountCases, "30DF", "107892", "01:00:00;00");
		addTestCase(df30WithFrameCountCases, "30DF", "7", "00:00:00;07");
		addTestCase(df30WithFrameCountCases, "30DF", "99", "00:00:03;09");
		addTestCase(df30WithFrameCountCases, "30DF", "571", "00:00:19;01");
		addTestCase(df30WithFrameCountCases, "30DF", "2854", "00:01:35;06");
		addTestCase(df30WithFrameCountCases, "30DF", "279884", "02:35:38;24");
		addTestCase(df30WithFrameCountCases, "30DF", "1958418", "18:09:06;00");
	}

	static void addTestCase(Map<Pair<String, String>, String> casesMap, String frameRate, String argument, String output) {
		Pair<String, String> input = new Pair<>(frameRate, argument);
		casesMap.put(input, output);
	}

	/**
	 * Asserts the given input against the expected output
	 *
	 * @param input          A {@link Pair} containing the frame rate type and the {@code argument} string
	 * @param expectedOutput The expected Timecode {@code string} representation
	 *
	 * @throws TimecodeException
	 */
	void assertInputOutput(Pair<String, String> input, String expectedOutput) throws TimecodeException {
		String frameRate = input.getKey();
		String argument = input.getValue();
		TimecodeString timecodeString = TimecodeString.of(frameRate, argument);

		assertEquals(expectedOutput, timecodeString.getOppositeRepresentation());
	}

	@Nested
	@DisplayName ("25FPS Timecode")
	class Fps25TimecodeAcceptanceTests {

		@TestFactory
		@DisplayName("Given frame count")
		Stream<DynamicTest> fps25ValuesTestFactory() {
			Iterator<Pair<String, String>> fps25CasesIterator = fps25WithUnitsCases.keySet().iterator();

			return DynamicTest.stream(
					fps25CasesIterator,
					input -> "Input: " + fps25WithUnitsCases.get(input) + " Expected output: " + input.getValue(),
					Unchecked.consumer(this::assertFps25UnitCase));
		}

		void assertFps25UnitCase(Pair<String, String> input) throws Exception {
			String expectedOutput = fps25WithUnitsCases.get(input);
			assertInputOutput(input, expectedOutput);
		}

		@TestFactory
		@DisplayName("Given hh:mm:ss:ff")
		Stream<DynamicTest> fps25WithFrameCountTestFactory() {
			Iterator<Pair<String, String>> fps25CasesIterator = fps25WithFrameCountCases.keySet().iterator();

			return DynamicTest.stream(
					fps25CasesIterator,
					input -> "Input: " + fps25WithFrameCountCases.get(input) + " Expected output: " + input.getValue(),
					Unchecked.consumer(this::assertFps25FrameCountCase));
		}

		void assertFps25FrameCountCase(Pair<String, String> input) throws Exception {
			String expectedOutput = fps25WithFrameCountCases.get(input);
			assertInputOutput(input, expectedOutput);
		}
	}

	@Nested
	@DisplayName ("DF30 Timecode")
	class Df30TimecodeAcceptanceTests {

		@TestFactory
		@DisplayName("Given frame count")
		Stream<DynamicTest> df30ValuesTestFactory() {
			Iterator<Pair<String, String>> fps25CasesIterator = df30WithUnitCases.keySet().iterator();

			return DynamicTest.stream(
					fps25CasesIterator,
					input -> "Input: " + df30WithUnitCases.get(input) + " Expected output: " + input.getValue(),
					Unchecked.consumer(this::assertDf30UnitCase));
		}

		void assertDf30UnitCase(Pair<String, String> input) throws Exception {
			String expectedOutput = df30WithUnitCases.get(input);
			assertInputOutput(input, expectedOutput);
		}

		@TestFactory
		@DisplayName("Given hh:mm:ss:ff")
		Stream<DynamicTest> df30WithFrameCountTestFactory() {
			Iterator<Pair<String, String>> fps25CasesIterator = df30WithFrameCountCases.keySet().iterator();

			return DynamicTest.stream(
					fps25CasesIterator,
					input -> "Input: " + df30WithFrameCountCases.get(input) + " Expected output: " + input.getValue(),
					Unchecked.consumer(this::assertDf30FrameCountCase));
		}

		void assertDf30FrameCountCase(Pair<String, String> input) throws Exception {
			String expectedOutput = df30WithFrameCountCases.get(input);
			assertInputOutput(input, expectedOutput);
		}
	}
}
