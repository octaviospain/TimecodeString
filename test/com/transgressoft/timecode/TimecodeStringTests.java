package com.transgressoft.timecode;

import com.transgressoft.timecode.fps25.*;
import org.junit.jupiter.api.*;

import static com.transgressoft.timecode.TimecodeException.ErrorCase.*;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link TimecodeString} class. It also coverages code lines of
 * {@link TimecodeFactory} and its subclasses, {@link FrameRateType},
 * {@link TimecodeInputType} and {@link TimecodeBase}.
 *
 * @author Octavio Calleya
 * @version 1.0
 */
public class TimecodeStringTests {

	@Test
	@DisplayName ("24FPS Timecode valid values")
	void testValidValues24FpsFrameRateType() {
		assertTrue(FrameRateType.FPS24.areValidValues(0, 0, 0, 0));
	}

	@Test
	@DisplayName ("Hashcode tests")
	void testTwoTimecodesEqualHashCode() {
		TimecodeBase timecodeBaseOne = Fps25Timecode.of(50);
		TimecodeBase timecodeBaseTwo = Fps25Timecode.of(50);
		assertTrue(timecodeBaseOne.hashCode() == timecodeBaseTwo.hashCode());
	}

	@Test
	@DisplayName ("Timecodes not equals")
	void testTwoTimecodesNotEqual() {
		TimecodeBase timecodeBaseOne = Fps25Timecode.of(50);
		TimecodeBase timecodeBaseTwo = Fps25Timecode.of(25);
		assertTrue(! timecodeBaseOne.equals(timecodeBaseTwo));
	}

	@Test
	@DisplayName ("TimecodeException constructor tests")
	void testTimecodeExceptionConstructors() {
		TimecodeException timecodeException = new TimecodeException();
		assertTrue(timecodeException.getMessage() == null);

		timecodeException = new TimecodeException(new Throwable());
		assertTrue(timecodeException.getCause() != null);

		timecodeException = new TimecodeException(INVALID_TIMECODE, new Throwable());
		assertTrue(timecodeException.getMessage().equals(INVALID_TIMECODE.getErrorMessage()));
		assertTrue(timecodeException.getCause() != null);
	}

	@Test
	@DisplayName ("Invalid frame rate type throws exception")
	void testInvalidFrameRateType() {
		TimecodeException exception = expectThrows(TimecodeException.class, () -> FrameRateType.fromString("GH45"));
		assertEquals("Invalid frame rate format", exception.getMessage());
	}

	@Test
	@DisplayName ("DF30 Timecode with negative hours throws exception")
	void testTimecodeStringWithNegativeHours() {
		IllegalArgumentException exception = expectThrows(IllegalArgumentException.class,
											  () -> TimecodeString.of(FrameRateType.DF30.toString(), - 12, 50, 30, 12));
		assertEquals("Invalid timecode value", exception.getMessage());
	}

	@Test
	@DisplayName ("25FPS Timecode with out of range hours throws exception")
	void testTimecodeStringWithOutOfRangeHours() {
		IllegalArgumentException exception = expectThrows(IllegalArgumentException.class,
											  () -> TimecodeString.of(FrameRateType.FPS25.toString(), 24, 50, 30, 12));
		assertEquals("Invalid timecode value", exception.getMessage());
	}

	@Test
	@DisplayName ("DF30 Timecode with negative minutes throws exception")
	void testTimecodeStringWithNegativeMinutes() {
		IllegalArgumentException exception = expectThrows(IllegalArgumentException.class,
											  () -> TimecodeString.of(FrameRateType.DF30.toString(), 12, - 5, 30, 12));
		assertEquals("Invalid timecode value", exception.getMessage());
	}

	@Test
	@DisplayName ("25FPS Timecode with out of range minutes throws exception")
	void testTimecodeStringWithOutOfRangeMinutes() {
		IllegalArgumentException exception = expectThrows(IllegalArgumentException.class,
											  () -> TimecodeString.of(FrameRateType.FPS25.toString(), 12, 60, 30, 12));
		assertEquals("Invalid timecode value", exception.getMessage());
	}

	@Test
	@DisplayName ("DF30 Timecode with negative seconds throws exception")
	void testTimecodeStringWithNegativeSeconds() {
		IllegalArgumentException exception = expectThrows(IllegalArgumentException.class,
											  () -> TimecodeString.of(FrameRateType.DF30.toString(), 12, 50, - 5, 12));
		assertEquals("Invalid timecode value", exception.getMessage());
	}

	@Test
	@DisplayName ("25FPS Timecode with out of range seconds throws exception")
	void testTimecodeStringWithOutOfRangeSeconds() {
		IllegalArgumentException exception = expectThrows(IllegalArgumentException.class,
											  () -> TimecodeString.of(FrameRateType.FPS25.toString(), 12, 50, 60, 12));
		assertEquals("Invalid timecode value", exception.getMessage());
	}

	@Test
	@DisplayName ("DF30 Timecode with negative frames throws exception")
	void testTimecodeStringWithNegativeFrames() throws Exception {
		IllegalArgumentException exception = expectThrows(IllegalArgumentException.class,
											  () -> TimecodeString.of(FrameRateType.DF30.toString(), 12, 50, 30, - 5));
		assertEquals("Invalid timecode value", exception.getMessage());
	}
}
