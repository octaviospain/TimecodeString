package com.transgressoft.timecode;

import org.junit.Test;

/**
 * Unit tests for {@link TimecodeString} class. It also coverages code lines of
 * {@link TimecodeFactory} and its subclasses, {@link FrameRateType} and {@link TimecodeInputType}.
 *
 * @author Octavio Calleya
 * @version 0.1
 */
public class TimecodeStringTests {

	@Test(expected = TimecodeException.class)
	public void testInvalidFrameRateType() throws Exception {
		FrameRateType.fromString("GH45");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void test24FpsTimecodeStringCreation() throws Exception {
		TimecodeString.of(FrameRateType.FPS24.toString(), 12345);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void test30FpsTimecodeStringCreation() throws Exception {
		TimecodeString.of(FrameRateType.FPS30.toString(), 12345);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTimecodeStringWithNegativeHours() throws Exception {
		TimecodeString.of(FrameRateType.DF30.toString(), -12, 50, 30, 12);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTimecodeStringWithOutOfRangeHours() throws Exception {
		TimecodeString.of(FrameRateType.FPS25.toString(), 24, 50, 30, 12);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTimecodeStringWithNegativeMinutes() throws Exception {
		TimecodeString.of(FrameRateType.DF30.toString(), 12, -5, 30, 12);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTimecodeStringWithOutOfRangeMinutes() throws Exception {
		TimecodeString.of(FrameRateType.FPS25.toString(), 12, 60, 30, 12);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTimecodeStringWithNegativeSeconds() throws Exception {
		TimecodeString.of(FrameRateType.DF30.toString(), 12, 50, -5, 12);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTimecodeStringWithOutOfRangeSeconds() throws Exception {
		TimecodeString.of(FrameRateType.FPS25.toString(), 12, 50, 60, 12);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTimecodeStringWithNegativeFrames() throws Exception {
		TimecodeString.of(FrameRateType.DF30.toString(), 12, 50, 30, -5);
	}
}
