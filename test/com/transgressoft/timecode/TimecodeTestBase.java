package com.transgressoft.timecode;

import com.transgressoft.timecode.df30.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Octavio Calleya
 */
public class TimecodeTestBase {

	/**
	 * Asserts that the {@code actualTimecode} has the resulting hours, minutes, seconds, frames, and frame count
	 *
	 * @param operationLabel     The assertion message to be displayed with the {@code assertAll} method
	 * @param expectedHours      The expected hours
	 * @param expectedMinutes    The expected minutes
	 * @param expectedSeconds    The expected seconds
	 * @param expectedFrames     The expected frames
	 * @param expectedFrameCount The expected frame count
	 * @param actualTimecode     The actual {@link Timecode} to test
	 */
	protected void assertTimecodeOperationWithUnits(String operationLabel, int expectedHours, int expectedMinutes,
			int expectedSeconds, int expectedFrames, int expectedFrameCount, Timecode actualTimecode) {

		boolean isDropFrame = actualTimecode instanceof Df30Timecode;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(String.format("%02d", expectedHours) + ":");
		stringBuilder.append(String.format("%02d", expectedMinutes) + ":");
		stringBuilder.append(String.format("%02d", expectedSeconds) + (isDropFrame ? ";" : ":"));
		stringBuilder.append(String.format("%02d", expectedFrames));
		String expectedToString = "Timecode[" + stringBuilder.toString() + "/" + expectedFrameCount + "]";

		assertAll(operationLabel,
				  () -> assertEquals(expectedFrames, actualTimecode.getFrames()),
				  () -> assertEquals(expectedSeconds, actualTimecode.getSeconds()),
				  () -> assertEquals(expectedMinutes, actualTimecode.getMinutes()),
				  () -> assertEquals(expectedHours, actualTimecode.getHours()),
				  () -> assertEquals(expectedFrameCount, actualTimecode.getFrameCount()),
				  () -> assertEquals(stringBuilder.toString(), actualTimecode.getStringRepresentation()),
				  () -> assertEquals(expectedToString, actualTimecode.toString()));
	}

	/**
	 * Asserts that the {@code actualTimecode} is equals to the {@code expectedTimecode}
	 *
	 * @param operationLabel   The assertion message to be displayed with the {@code assertAll} method
	 * @param expectedTimecode The expected {@link Timecode}
	 * @param actualTimecode   The actual {@link Timecode} to test
	 */
	protected void assertTimecodeOperation(String operationLabel, Timecode expectedTimecode, Timecode actualTimecode) {
		assertAll(operationLabel,
				  () -> assertEquals(expectedTimecode.getFrames(), actualTimecode.getFrames()),
				  () -> assertEquals(expectedTimecode.getSeconds(), actualTimecode.getSeconds()),
				  () -> assertEquals(expectedTimecode.getMinutes(), actualTimecode.getMinutes()),
				  () -> assertEquals(expectedTimecode.getHours(), actualTimecode.getHours()),
				  () -> assertEquals(expectedTimecode.getFrameCount(), actualTimecode.getFrameCount()),
				  () -> assertEquals(expectedTimecode, actualTimecode));
	}
}