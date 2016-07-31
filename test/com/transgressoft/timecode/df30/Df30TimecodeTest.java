package com.transgressoft.timecode.df30;

import org.junit.*;
import com.transgressoft.timecode.*;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link Df30Timecode} class.
 *
 * @author Octavio Calleya
 * @version 0.1
 */
public class Df30TimecodeTest {

	@Test
	public void testDf30TimecodeSubstractionUnderZeroWithDropFrameAdditionAndMinutesCarriage() throws Exception {
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 7, 50, 0, 17);
		Timecode timecodeToSubstract = TimecodeFactory.createTimeCode(FrameRateType.DF30, 11, 50, 0, 17);

		int expectedHours = -4;
		int expectedMinutes = 0;
		int expectedSeconds = 0;
		int expectedFrames = 0;
		int expectedFrameCount = -431568;
		String expectedStringRepresentation = "";
		expectedStringRepresentation += String.format("%02d", expectedHours) + ":";
		expectedStringRepresentation += String.format("%02d", expectedMinutes) + ":";
		expectedStringRepresentation += String.format("%02d", expectedSeconds) + ";";
		expectedStringRepresentation += String.format("%02d", expectedFrames);
		String expectedToString = "Timecode[" + expectedStringRepresentation + "/" + expectedFrameCount + "]";

		actualTimecode.subtract(timecodeToSubstract);

		assertEquals(expectedFrames, actualTimecode.getFrames());
		assertEquals(expectedSeconds, actualTimecode.getSeconds());
		assertEquals(expectedMinutes, actualTimecode.getMinutes());
		assertEquals(expectedHours, actualTimecode.getHours());
		assertEquals(expectedFrameCount, actualTimecode.getFrameCount());
		assertEquals(expectedStringRepresentation, actualTimecode.getStringRepresentation());
		assertEquals(expectedToString, actualTimecode.toString());
	}

	@Test
	public void testDf30TimecodeSubstractionUnderZeroWithDropFrameAdditionAndSecondsCarriage() throws Exception {
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 7, 12, 6, 11);
		Timecode timecodeToSubstract = TimecodeFactory.createTimeCode(FrameRateType.DF30, 11, 11, 5, 15);

		int expectedHours = -4;
		int expectedMinutes = 1;
		int expectedSeconds = 0;
		int expectedFrames = 26;
		int expectedFrameCount = -429744;
		String expectedStringRepresentation = "";
		expectedStringRepresentation += String.format("%02d", expectedHours) + ":";
		expectedStringRepresentation += String.format("%02d", expectedMinutes) + ":";
		expectedStringRepresentation += String.format("%02d", expectedSeconds) + ";";
		expectedStringRepresentation += String.format("%02d", expectedFrames);
		String expectedToString = "Timecode[" + expectedStringRepresentation + "/" + expectedFrameCount + "]";

		actualTimecode.subtract(timecodeToSubstract);

		assertEquals(expectedFrames, actualTimecode.getFrames());
		assertEquals(expectedSeconds, actualTimecode.getSeconds());
		assertEquals(expectedMinutes, actualTimecode.getMinutes());
		assertEquals(expectedHours, actualTimecode.getHours());
		assertEquals(expectedFrameCount, actualTimecode.getFrameCount());
		assertEquals(expectedStringRepresentation, actualTimecode.getStringRepresentation());
		assertEquals(expectedToString, actualTimecode.toString());
	}

	@Test
	public void testDf30TimecodeSubstractionUnderZeroWithDropFrameAdditionAndFrameCarriage() throws Exception {
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 7, 12, 0, 11);
		Timecode timecodeToSubstract = TimecodeFactory.createTimeCode(FrameRateType.DF30, 11, 11, 5, 9);

		int expectedHours = -4;
		int expectedMinutes = 0;
		int expectedSeconds = 55;
		int expectedFrames = 0;
		int expectedFrameCount = -429918;
		String expectedStringRepresentation = "";
		expectedStringRepresentation += String.format("%02d", expectedHours) + ":";
		expectedStringRepresentation += String.format("%02d", expectedMinutes) + ":";
		expectedStringRepresentation += String.format("%02d", expectedSeconds) + ";";
		expectedStringRepresentation += String.format("%02d", expectedFrames);
		String expectedToString = "Timecode[" + expectedStringRepresentation + "/" + expectedFrameCount + "]";

		actualTimecode.subtract(timecodeToSubstract);

		assertEquals(expectedFrames, actualTimecode.getFrames());
		assertEquals(expectedSeconds, actualTimecode.getSeconds());
		assertEquals(expectedMinutes, actualTimecode.getMinutes());
		assertEquals(expectedHours, actualTimecode.getHours());
		assertEquals(expectedFrameCount, actualTimecode.getFrameCount());
		assertEquals(expectedStringRepresentation, actualTimecode.getStringRepresentation());
		assertEquals(expectedToString, actualTimecode.toString());
	}

	@Test
	public void testDf30TimecodeSubstractionUnderZero() throws Exception {
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 7, 12, 0, 11);
		Timecode timecodeToSubstract = TimecodeFactory.createTimeCode(FrameRateType.DF30, 11, 11, 5, 15);

		int expectedHours = -4;
		int expectedMinutes = 0;
		int expectedSeconds = 54;
		int expectedFrames = 24;
		int expectedFrameCount = -429924;
		String expectedStringRepresentation = "";
		expectedStringRepresentation += String.format("%02d", expectedHours) + ":";
		expectedStringRepresentation += String.format("%02d", expectedMinutes) + ":";
		expectedStringRepresentation += String.format("%02d", expectedSeconds) + ";";
		expectedStringRepresentation += String.format("%02d", expectedFrames);
		String expectedToString = "Timecode[" + expectedStringRepresentation + "/" + expectedFrameCount + "]";

		actualTimecode.subtract(timecodeToSubstract);

		assertEquals(expectedFrames, actualTimecode.getFrames());
		assertEquals(expectedSeconds, actualTimecode.getSeconds());
		assertEquals(expectedMinutes, actualTimecode.getMinutes());
		assertEquals(expectedHours, actualTimecode.getHours());
		assertEquals(expectedFrameCount, actualTimecode.getFrameCount());
		assertEquals(expectedStringRepresentation, actualTimecode.getStringRepresentation());
		assertEquals(expectedToString, actualTimecode.toString());
	}

	@Test(expected = TimecodeException.class)
	public void testDf30TimecodeSubstractionUnderflow() throws Exception {
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 5, 5, 15, 20);
		Timecode timecodeToSubstract = TimecodeFactory.createTimeCode(FrameRateType.DF30, 23, 59, 59, 24);

		actualTimecode.subtract(timecodeToSubstract).subtract(timecodeToSubstract);
	}

	@Test
	public void testDf30TimecodeSubstractionResultZero() throws Exception {
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 5, 5, 15, 20);
		Timecode timecodeToSubstract = TimecodeFactory.createTimeCode(FrameRateType.DF30, 5, 5, 15, 20);
		Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 0, 0, 0, 0);

		actualTimecode.subtract(timecodeToSubstract);

		assertEquals(expectedTimecode.getFrames(), actualTimecode.getFrames());
		assertEquals(expectedTimecode.getSeconds(), actualTimecode.getSeconds());
		assertEquals(expectedTimecode.getMinutes(), actualTimecode.getMinutes());
		assertEquals(expectedTimecode.getHours(), actualTimecode.getHours());
		assertEquals(expectedTimecode.getFrameCount(), actualTimecode.getFrameCount());
		assertEquals(expectedTimecode, actualTimecode);
	}

	@Test(expected = TimecodeException.class)
	public void testDf30TimecodeSubstractionInvalid() throws Exception {
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 10, 5, 15, 14);
		Timecode timecodeToSubstract = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 0, 0, 0, 0);

		actualTimecode.subtract(timecodeToSubstract);
	}

	@Test
	public void testDf30TimecodeSubstractionOfZero() throws Exception {
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 10, 5, 15, 14);
		Timecode timecodeToSubstract = TimecodeFactory.createTimeCode(FrameRateType.DF30, 0, 0, 0, 0);
		Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 10, 5, 15, 14);

		actualTimecode.subtract(timecodeToSubstract);

		assertEquals(expectedTimecode.getFrames(), actualTimecode.getFrames());
		assertEquals(expectedTimecode.getSeconds(), actualTimecode.getSeconds());
		assertEquals(expectedTimecode.getMinutes(), actualTimecode.getMinutes());
		assertEquals(expectedTimecode.getHours(), actualTimecode.getHours());
		assertEquals(expectedTimecode.getFrameCount(), actualTimecode.getFrameCount());
		assertEquals(expectedTimecode, actualTimecode);
	}

	@Test
	public void testDf30TimecodeSubstraction() throws Exception {
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 10, 5, 15, 14);
		Timecode timecodeToSubstract = TimecodeFactory.createTimeCode(FrameRateType.DF30, 2, 14, 21, 10);
		Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 7, 50, 54, 2);

		actualTimecode.subtract(timecodeToSubstract);

		assertEquals(expectedTimecode.getFrames(), actualTimecode.getFrames());
		assertEquals(expectedTimecode.getSeconds(), actualTimecode.getSeconds());
		assertEquals(expectedTimecode.getMinutes(), actualTimecode.getMinutes());
		assertEquals(expectedTimecode.getHours(), actualTimecode.getHours());
		assertEquals(expectedTimecode.getFrameCount(), actualTimecode.getFrameCount());
		assertEquals(expectedTimecode, actualTimecode);
	}

	@Test(expected = TimecodeException.class)
	public void testDf30TimecodeAdditionInvalid() throws Exception {

		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 15, 6, 0, 12);
		Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 2, 5, 1, 0);

		actualTimecode.add(timecodeToAdd);
	}

	@Test(expected = TimecodeException.class)
	public void testDf30TimecodeAdditionOverflow() throws Exception {
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 15, 6, 0, 12);
		Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.DF30, 10, 5, 2, 24);

		actualTimecode.add(timecodeToAdd);
	}

	@Test
	public void testDf30TimecodeAdditionOfZero() throws Exception {
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 15, 6, 0, 12);
		Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.DF30, 0, 0, 0, 0);
		Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 15, 6, 0, 12);

		actualTimecode.add(timecodeToAdd);

		assertEquals(expectedTimecode.getFrames(), actualTimecode.getFrames());
		assertEquals(expectedTimecode.getSeconds(), actualTimecode.getSeconds());
		assertEquals(expectedTimecode.getMinutes(), actualTimecode.getMinutes());
		assertEquals(expectedTimecode.getHours(), actualTimecode.getHours());
		assertEquals(expectedTimecode.getFrameCount(), actualTimecode.getFrameCount());
		assertEquals(expectedTimecode, actualTimecode);
	}

	@Test
	public void testDf30TimecodeAdditionWithCarriage() throws Exception {
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 22, 59, 59, 24);
		Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.DF30, 0, 0, 0, 10);
		Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 23, 0, 0, 4);

		actualTimecode.add(timecodeToAdd);

		assertEquals(expectedTimecode.getFrames(), actualTimecode.getFrames());
		assertEquals(expectedTimecode.getSeconds(), actualTimecode.getSeconds());
		assertEquals(expectedTimecode.getMinutes(), actualTimecode.getMinutes());
		assertEquals(expectedTimecode.getHours(), actualTimecode.getHours());
		assertEquals(expectedTimecode.getFrameCount(), actualTimecode.getFrameCount());
		assertEquals(expectedTimecode, actualTimecode);
	}

	@Test
	public void testDf30TimecodeAdditionCommutative() throws Exception{
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 10, 5, 3, 14);
		Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.DF30, 11, 6, 4, 10);
		Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 21, 11, 7, 22);

		actualTimecode.add(timecodeToAdd);

		assertEquals(expectedTimecode.getFrames(), actualTimecode.getFrames());
		assertEquals(expectedTimecode.getSeconds(), actualTimecode.getSeconds());
		assertEquals(expectedTimecode.getMinutes(), actualTimecode.getMinutes());
		assertEquals(expectedTimecode.getHours(), actualTimecode.getHours());
		assertEquals(expectedTimecode.getFrameCount(), actualTimecode.getFrameCount());
		assertEquals(expectedTimecode, actualTimecode);

		actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 11, 6, 4, 10);
		timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.DF30, 10, 5, 3, 14);

		actualTimecode.add(timecodeToAdd);

		assertEquals(expectedTimecode.getFrames(), actualTimecode.getFrames());
		assertEquals(expectedTimecode.getSeconds(), actualTimecode.getSeconds());
		assertEquals(expectedTimecode.getMinutes(), actualTimecode.getMinutes());
		assertEquals(expectedTimecode.getHours(), actualTimecode.getHours());
		assertEquals(expectedTimecode.getFrameCount(), actualTimecode.getFrameCount());
		assertEquals(expectedTimecode, actualTimecode);
	}
	
	@Test
	public void testDf30TimecodeFrameCount() {
		int frameCount = 34567;
		Timecode timecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, frameCount);
		assertEquals(frameCount, timecode.getFrameCount());
	}

	@Test
	public void testDf30TimecodeHoursMinutesSecondsFrames() {
		int hours = 12;
		int minutes = 5;
		int seconds = 16;
		int frames = 24;
		Timecode timecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, hours, minutes, seconds, frames);
		assertEquals(hours, timecode.getHours());
		assertEquals(minutes, timecode.getMinutes());
		assertEquals(seconds, timecode.getSeconds());
		assertEquals(frames, timecode.getFrames());
	}

	@Test
	public void testDf30TimecodeFrameCountLimit() {
		Df30Timecode timecode = (Df30Timecode) TimecodeFactory.createTimeCode(FrameRateType.DF30, 23, 59, 59, 28);
		assertEquals(timecode.getFrameCountLimit() - 1, timecode.getFrameCount());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDf30TimecodeWithOutOfRangeUnits() throws Exception {
		TimecodeString.of(FrameRateType.DF30.toString(), 12, 50, 30, 30);
	}

	@Test(expected = TimecodeException.class)
	public void testDf30TimecodeWithInvalidInput() throws Exception {
		TimecodeString.of(FrameRateType.DF30.toString(), "12:ab:23;44");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDf30TimecodeFrameCountOutOfLimit() {
		TimecodeFactory.createTimeCode(FrameRateType.DF30, 2589407);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDf30TimecodeWithNegativeFrameCount() throws Exception {
		TimecodeString.of(FrameRateType.DF30.toString(), -15);
	}
}
