package com.transgressoft.timecode;

import javafx.util.*;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Acceptance tests for the given input and output cases with the problem.
 *
 * @author Octavio Calleya
 * @version 0.1
 */
public class AcceptanceTest {

	static Map<Pair<String, String>, String> fps25WithUnitsCases;
	static Map<Pair<String, String>, String> fps25WithFrameCountCases;
	static Map<Pair<String, String>, String> df30WithUnitCases;
	static Map<Pair<String, String>, String> df30WithFrameCountCases;

	static Map<Pair<String, String>, String> fps25WithUnitsAuditionCases;
	static Map<Pair<String, String>, String> fps25WithFrameCountAuditionCases;
	static Map<Pair<String, String>, String> df30WithUnitAuditionCases;
	static Map<Pair<String, String>, String> df30WithFrameCountAuditionCases;

	@BeforeClass
	public static void initTestInputs() {
		fps25WithUnitsCases = new HashMap<>();
		fps25WithFrameCountCases = new HashMap<>();
		df30WithUnitCases = new HashMap<>();
		df30WithFrameCountCases = new HashMap<>();
		addTestInputs();
		fps25WithUnitsAuditionCases = new HashMap<>();
		fps25WithFrameCountAuditionCases = new HashMap<>();
		df30WithUnitAuditionCases = new HashMap<>();
		df30WithFrameCountAuditionCases = new HashMap<>();
		addAuditionTestInputs();
	}

	private static void addTestInputs() {
		addTestCase(fps25WithUnitsCases, "25fps", "00:00:00:01", "1");
		addTestCase(fps25WithUnitsCases, "25fps", "00:00:01:00", "25");
		addTestCase(fps25WithUnitsCases, "25fps", "00:00:10:00", "250");
		addTestCase(fps25WithUnitsCases, "25fps", "00:01:00:00", "1500");
		addTestCase(fps25WithUnitsCases, "25fps", "00:10:00:00", "15000");
		addTestCase(fps25WithUnitsCases, "25fps", "01:00:00:00", "90000");

		addTestCase(fps25WithFrameCountCases, "25fps", "1", "00:00:00:01");
		addTestCase(fps25WithFrameCountCases, "25fps", "25", "00:00:01:00");
		addTestCase(fps25WithFrameCountCases, "25fps", "250", "00:00:10:00");
		addTestCase(fps25WithFrameCountCases, "25fps", "1500", "00:01:00:00");
		addTestCase(fps25WithFrameCountCases, "25fps", "15000", "00:10:00:00");
		addTestCase(fps25WithFrameCountCases, "25fps", "90000", "01:00:00:00");

		addTestCase(df30WithUnitCases, "30DF", "00:00:00;01", "1");
		addTestCase(df30WithUnitCases, "30DF", "00:00:01;00", "30");
		addTestCase(df30WithUnitCases, "30DF", "00:00:10;00", "300");
		addTestCase(df30WithUnitCases, "30DF", "00:01:00;02", "1800");
		addTestCase(df30WithUnitCases, "30DF", "00:10:00;00", "17982");
		addTestCase(df30WithUnitCases, "30DF", "01:00:00;00", "107892");

		addTestCase(df30WithFrameCountCases, "30DF", "1", "00:00:00;01");
		addTestCase(df30WithFrameCountCases, "30DF", "30", "00:00:01;00");
		addTestCase(df30WithFrameCountCases, "30DF", "300", "00:00:10;00");
		addTestCase(df30WithFrameCountCases, "30DF", "1800", "00:01:00;02");
		addTestCase(df30WithFrameCountCases, "30DF", "17982", "00:10:00;00");
		addTestCase(df30WithFrameCountCases, "30DF", "107892", "01:00:00;00");
	}

	private static void addAuditionTestInputs() {
		addTestCase(fps25WithUnitsAuditionCases, "25fps", "00:00:00:05", "28");
		addTestCase(fps25WithUnitsAuditionCases, "25fps", "00:00:13:14", "339");
		addTestCase(fps25WithUnitsAuditionCases, "25fps", "00:00:51:21", "1296");
		addTestCase(fps25WithUnitsAuditionCases, "25fps", "00:08:14:01", "12350");
		addTestCase(fps25WithUnitsAuditionCases, "25fps", "00:37:48:21", "56721");
		addTestCase(fps25WithUnitsAuditionCases, "25fps", "01:05:57:04", "98929");
		addTestCase(fps25WithUnitsAuditionCases, "25fps", "15:31:53:23", "1397848");
		addTestCase(fps25WithUnitsAuditionCases, "25fps", "22:42:55:18", "2044376");

		addTestCase(fps25WithFrameCountAuditionCases, "25fps", "8", "00:00:00:08");
		addTestCase(fps25WithFrameCountAuditionCases, "25fps", "92", "00:00:03:17");
		addTestCase(fps25WithFrameCountAuditionCases, "25fps", "252", "00:00:10:02");
		addTestCase(fps25WithFrameCountAuditionCases, "25fps", "3481", "00:02:19:06");
		addTestCase(fps25WithFrameCountAuditionCases, "25fps", "59129", "00:39:25:04");
		addTestCase(fps25WithFrameCountAuditionCases, "25fps", "173161", "01:55:26:11");
		addTestCase(fps25WithFrameCountAuditionCases, "25fps", "1904771", "21:09:50:21");

		addTestCase(df30WithUnitAuditionCases, "30DF", "00:00:00;09", "9");
		addTestCase(df30WithUnitAuditionCases, "30DF", "00:00:02;05", "65");
		addTestCase(df30WithUnitAuditionCases, "30DF", "00:00:45;21", "1371");
		addTestCase(df30WithUnitAuditionCases, "30DF", "00:01:27;04", "2612");
		addTestCase(df30WithUnitAuditionCases, "30DF", "00:14:18;26", "25740");
		addTestCase(df30WithUnitAuditionCases, "30DF", "01:11:34;08", "128700");
		addTestCase(df30WithUnitAuditionCases, "30DF", "18:18:51;16", "1975968");
		addTestCase(df30WithUnitAuditionCases, "30DF", "21:41:20;29", "2340087");

		addTestCase(df30WithFrameCountAuditionCases, "30DF", "7", "00:00:00;07");
		addTestCase(df30WithFrameCountAuditionCases, "30DF", "99", "00:00:03;09");
		addTestCase(df30WithFrameCountAuditionCases, "30DF", "571", "00:00:19;01");
		addTestCase(df30WithFrameCountAuditionCases, "30DF", "2854", "00:01:35;06");
		addTestCase(df30WithFrameCountAuditionCases, "30DF", "279884", "02:35:38;24");
		addTestCase(df30WithFrameCountAuditionCases, "30DF", "1958418", "18:09:06;00");
	}

	private static void addTestCase(Map<Pair<String, String>, String> casesMap, String frameRate, String argument, String output) {
		Pair<String, String> input = new Pair(frameRate, argument);
		casesMap.put(input, output);
	}

	@Test
	public void fps25WithUnitsTestCases() throws Exception {
		TimecodeString timecodeString;
		String fps25FrameRate;
		String unitsValueInput;
		String expectedOutput;
		for(Pair<String, String> input: fps25WithUnitsCases.keySet()) {
			fps25FrameRate = input.getKey();
			unitsValueInput = input.getValue();
			expectedOutput = fps25WithUnitsCases.get(input);

			timecodeString = TimecodeString.of(fps25FrameRate, unitsValueInput);

			assertEquals(expectedOutput, timecodeString.getOppositeRepresentation());
		}
	}

	@Test
	public void fps25WithFrameCountTestCases() throws Exception {
		TimecodeString timecodeString;
		String fps25FrameCountInput;
		String frameCountValeInput;
		String expectedOutput;
		for(Pair<String, String> input: fps25WithFrameCountCases.keySet()) {
			fps25FrameCountInput = input.getKey();
			frameCountValeInput = input.getValue();
			expectedOutput = fps25WithFrameCountCases.get(input);

			timecodeString = TimecodeString.of(fps25FrameCountInput, frameCountValeInput);

			assertEquals(expectedOutput, timecodeString.getOppositeRepresentation());
		}
	}

	@Test
	public void df30WithUnitsTestCases() throws Exception {
		TimecodeString timecodeString;
		String df30FrameRate;
		String unitsValueInput;
		String expectedOutput;
		for(Pair<String, String> input: df30WithUnitCases.keySet()) {
			df30FrameRate = input.getKey();
			unitsValueInput = input.getValue();
			expectedOutput = df30WithUnitCases.get(input);

			timecodeString = TimecodeString.of(df30FrameRate, unitsValueInput);

			assertEquals(expectedOutput, timecodeString.getOppositeRepresentation());
		}
	}

	@Test
	public void df30WithFrameCountCases() throws Exception {
		TimecodeString timecodeString;
		String df30FrameRate;
		String frameCountValueInput;
		String expectedOutput;
		for(Pair<String, String> input: df30WithFrameCountCases.keySet()) {
			df30FrameRate = input.getKey();
			frameCountValueInput = input.getValue();
			expectedOutput = df30WithFrameCountCases.get(input);

			timecodeString = TimecodeString.of(df30FrameRate, frameCountValueInput);

			assertEquals(expectedOutput, timecodeString.getOppositeRepresentation());
		}
	}

	@Test
	public void fps25WithUnitAuditionCases() throws Exception {
		TimecodeString timecodeString;
		String fps25FrameRate;
		String unitsValueInput;
		String expectedOutput;
		for(Pair<String, String> input: fps25WithUnitsCases.keySet()) {
			fps25FrameRate = input.getKey();
			unitsValueInput = input.getValue();
			expectedOutput = fps25WithUnitsCases.get(input);

			timecodeString = TimecodeString.of(fps25FrameRate, unitsValueInput);

			assertEquals(expectedOutput, timecodeString.getOppositeRepresentation());
		}
	}

	@Test
	public void fps25WithFrameCountAuditionCases() throws Exception {
		TimecodeString timecodeString;
		String fps25FrameRate;
		String frameCountValueInput;
		String expectedOutput;
		for(Pair<String, String> input: fps25WithFrameCountAuditionCases.keySet()) {
			fps25FrameRate = input.getKey();
			frameCountValueInput = input.getValue();
			expectedOutput = fps25WithFrameCountAuditionCases.get(input);

			timecodeString = TimecodeString.of(fps25FrameRate, frameCountValueInput);

			assertEquals(expectedOutput, timecodeString.getOppositeRepresentation());
		}
	}

	@Test
	public void df30WithUnitAuditionCases() throws Exception {
		TimecodeString timecodeString;
		String df30FrameRate;
		String unitsValueInput;
		String expectedOutput;
		for(Pair<String, String> input: df30WithUnitAuditionCases.keySet()) {
			df30FrameRate = input.getKey();
			unitsValueInput = input.getValue();
			expectedOutput = df30WithUnitAuditionCases.get(input);

			timecodeString = TimecodeString.of(df30FrameRate, unitsValueInput);

			assertEquals(expectedOutput, timecodeString.getOppositeRepresentation());
		}
	}

	@Test
	public void df30WithFrameCountAuditionCases() throws Exception {
		TimecodeString timecodeString;
		String df30FrameRate;
		String frameCountValueInput;
		String expectedOutput;
		for(Pair<String, String> input: df30WithFrameCountAuditionCases.keySet()) {
			df30FrameRate = input.getKey();
			frameCountValueInput = input.getValue();
			expectedOutput = df30WithFrameCountAuditionCases.get(input);

			timecodeString = TimecodeString.of(df30FrameRate, frameCountValueInput);

			assertEquals(expectedOutput, timecodeString.getOppositeRepresentation());
		}
	}
}
