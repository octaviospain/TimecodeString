package com.transgressoft.timecode.fps25;

import org.junit.*;
import com.transgressoft.timecode.*;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link Fps25Timecode} class.
 *
 * @author Octavio Calleya
 * @version 0.1
 */
public class Fps25TimecodeTest {

	@Test
	public void testFps25TimecodeSubstractionUnderZero() throws Exception {
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 5, 5, 15, 20);
		Timecode timecodeToSubstract = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 6, 1, 5, 23);

		int expectedHours = -1;
		int expectedMinutes = 4;
		int expectedSeconds = 9;
		int expectedFrames = 22;
		int expectedFrameCount = -83753;
		String expectedStringRepresentation = "";
		expectedStringRepresentation += String.format("%02d", expectedHours) + ":";
		expectedStringRepresentation += String.format("%02d", expectedMinutes) + ":";
		expectedStringRepresentation += String.format("%02d", expectedSeconds) + ":";
		expectedStringRepresentation += String.format("%02d", expectedFrames);
		String expectedToString = "Timecode[" + expectedStringRepresentation + "/" + expectedFrameCount + "]";

		actualTimecode.substract(timecodeToSubstract);

		assertEquals(expectedFrames, actualTimecode.getFrames());
		assertEquals(expectedSeconds, actualTimecode.getSeconds());
		assertEquals(expectedMinutes, actualTimecode.getMinutes());
		assertEquals(expectedHours, actualTimecode.getHours());
		assertEquals(expectedFrameCount, actualTimecode.getFrameCount());
		assertEquals(expectedStringRepresentation, actualTimecode.getStringRepresentation());
		assertEquals(expectedToString, actualTimecode.toString());
	}

	@Test(expected = TimecodeException.class)
	public void testFps25TimecodeSubstractionUnderflow() throws Exception {
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 5, 5, 15, 20);
		Timecode timecodeToSubstract = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 23, 59, 59, 24);

		actualTimecode.substract(timecodeToSubstract).substract(timecodeToSubstract);
	}

	@Test
	public void testFps25TimecodeSubstractionResultZero() throws Exception {
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 5, 5, 15, 20);
		Timecode timecodeToSubstract = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 5, 5, 15, 20);
		Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 0, 0, 0, 0);

		actualTimecode.substract(timecodeToSubstract);

		assertEquals(expectedTimecode.getFrames(), actualTimecode.getFrames());
		assertEquals(expectedTimecode.getSeconds(), actualTimecode.getSeconds());
		assertEquals(expectedTimecode.getMinutes(), actualTimecode.getMinutes());
		assertEquals(expectedTimecode.getHours(), actualTimecode.getHours());
		assertEquals(expectedTimecode.getFrameCount(), actualTimecode.getFrameCount());
		assertEquals(expectedTimecode, actualTimecode);
	}

	@Test(expected = TimecodeException.class)
	public void testFps25TimecodeSubstractionInvalid() throws Exception {
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 10, 5, 15, 14);
		Timecode timecodeToSubstract = TimecodeFactory.createTimeCode(FrameRateType.DF30, 0, 0, 0, 0);

		actualTimecode.substract(timecodeToSubstract);
	}

	@Test
	public void testFps25TimecodeSubstractionOfZero() throws Exception {
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 10, 5, 15, 14);
		Timecode timecodeToSubstract = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 0, 0, 0, 0);
		Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 10, 5, 15, 14);

		actualTimecode.substract(timecodeToSubstract);

		assertEquals(expectedTimecode.getFrames(), actualTimecode.getFrames());
		assertEquals(expectedTimecode.getSeconds(), actualTimecode.getSeconds());
		assertEquals(expectedTimecode.getMinutes(), actualTimecode.getMinutes());
		assertEquals(expectedTimecode.getHours(), actualTimecode.getHours());
		assertEquals(expectedTimecode.getFrameCount(), actualTimecode.getFrameCount());
		assertEquals(expectedTimecode, actualTimecode);
	}

	@Test
	public void testFps25TimecodeSubstraction() throws Exception {
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 10, 5, 15, 14);
		Timecode timecodeToSubstract = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 5, 3, 4, 10);
		Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 5, 2, 11, 4);

		actualTimecode.substract(timecodeToSubstract);

		assertEquals(expectedTimecode.getFrames(), actualTimecode.getFrames());
		assertEquals(expectedTimecode.getSeconds(), actualTimecode.getSeconds());
		assertEquals(expectedTimecode.getMinutes(), actualTimecode.getMinutes());
		assertEquals(expectedTimecode.getHours(), actualTimecode.getHours());
		assertEquals(expectedTimecode.getFrameCount(), actualTimecode.getFrameCount());
		assertEquals(expectedTimecode, actualTimecode);
	}

	@Test(expected = TimecodeException.class)
	public void testFps25TimecodeAdditionInvalid() throws Exception {

		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 15, 6, 0, 12);
		Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.DF30, 2, 5, 1, 0);

		actualTimecode.add(timecodeToAdd);
	}

	@Test(expected = TimecodeException.class)
	public void testFps25TimecodeAdditionOverflow() throws Exception {
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 15, 6, 0, 12);
		Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 10, 5, 2, 24);

		actualTimecode.add(timecodeToAdd);
	}

	@Test
	public void testFps25TimecodeAdditionOfZero() throws Exception {
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 15, 6, 0, 12);
		Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 0, 0, 0, 0);
		Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 15, 6, 0, 12);

		actualTimecode.add(timecodeToAdd);

		assertEquals(expectedTimecode.getFrames(), actualTimecode.getFrames());
		assertEquals(expectedTimecode.getSeconds(), actualTimecode.getSeconds());
		assertEquals(expectedTimecode.getMinutes(), actualTimecode.getMinutes());
		assertEquals(expectedTimecode.getHours(), actualTimecode.getHours());
		assertEquals(expectedTimecode.getFrameCount(), actualTimecode.getFrameCount());
		assertEquals(expectedTimecode, actualTimecode);
	}

	@Test
	public void testFps25TimecodeAdditionWithCarriage() throws Exception {
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 22, 59, 59, 24);
		Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 0, 0, 0, 1);
		Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 23, 0, 0, 0);

		actualTimecode.add(timecodeToAdd);

		assertEquals(expectedTimecode.getFrames(), actualTimecode.getFrames());
		assertEquals(expectedTimecode.getSeconds(), actualTimecode.getSeconds());
		assertEquals(expectedTimecode.getMinutes(), actualTimecode.getMinutes());
		assertEquals(expectedTimecode.getHours(), actualTimecode.getHours());
		assertEquals(expectedTimecode.getFrameCount(), actualTimecode.getFrameCount());
		assertEquals(expectedTimecode, actualTimecode);
	}

	@Test
	public void testFps25TimecodeAdditionCommutative() throws Exception{
		Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 10, 5, 3, 14);
		Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 11, 6, 4, 10);
		Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 21, 11, 7, 24);

		actualTimecode.add(timecodeToAdd);

		assertEquals(expectedTimecode.getFrames(), actualTimecode.getFrames());
		assertEquals(expectedTimecode.getSeconds(), actualTimecode.getSeconds());
		assertEquals(expectedTimecode.getMinutes(), actualTimecode.getMinutes());
		assertEquals(expectedTimecode.getHours(), actualTimecode.getHours());
		assertEquals(expectedTimecode.getFrameCount(), actualTimecode.getFrameCount());
		assertEquals(expectedTimecode, actualTimecode);

		actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 11, 6, 4, 10);
		timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 10, 5, 3, 14);

		actualTimecode.add(timecodeToAdd);

		assertEquals(expectedTimecode.getFrames(), actualTimecode.getFrames());
		assertEquals(expectedTimecode.getSeconds(), actualTimecode.getSeconds());
		assertEquals(expectedTimecode.getMinutes(), actualTimecode.getMinutes());
		assertEquals(expectedTimecode.getHours(), actualTimecode.getHours());
		assertEquals(expectedTimecode.getFrameCount(), actualTimecode.getFrameCount());
		assertEquals(expectedTimecode, actualTimecode);
	}

	@Test
	public void testFps25TimecodeFrameCount() {
		int frameCount = 34567;
		Timecode timecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, frameCount);
		assertEquals(frameCount, timecode.getFrameCount());
	}

	@Test
	public void testFps25TimecodeHoursMinutesSecondsFrames() {
		int hours = 12;
		int minutes = 5;
		int seconds = 16;
		int frames = 24;
		Timecode timecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, hours, minutes, seconds, frames);
		assertEquals(hours, timecode.getHours());
		assertEquals(minutes, timecode.getMinutes());
		assertEquals(seconds, timecode.getSeconds());
		assertEquals(frames, timecode.getFrames());
	}

	@Test
	public void testFps25TimecodeFrameCountLimit() {
		Fps25Timecode timecode = (Fps25Timecode) TimecodeFactory.createTimeCode(FrameRateType.FPS25, 23, 59, 59, 24);
		assertEquals(timecode.getFrameCountLimit() - 1, timecode.getFrameCount());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFps25TimecodeWithOutOfRangeUnits() throws Exception {
		TimecodeString.of(FrameRateType.FPS25.toString(), 12, 50, 30, 25);
	}

	@Test(expected = TimecodeException.class)
	public void testFps25TimecodeWithInvalidInput() throws Exception {
		TimecodeString.of(FrameRateType.FPS25.toString(), "12:ab:23;44");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFps25TimecodeFrameContOutOfLimit() {
		TimecodeFactory.createTimeCode(FrameRateType.FPS25, 2160000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFps25TimecodeWithNegativeFrameCount() throws Exception {
		TimecodeString.of(FrameRateType.FPS25.toString(), -15);
	}
}
