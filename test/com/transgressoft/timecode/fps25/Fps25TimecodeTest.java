package com.transgressoft.timecode.fps25;

import com.transgressoft.timecode.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Fps25Timecode} class.
 *
 * @author Octavio Calleya
 * @version 0.1
 */
public class Fps25TimecodeTest extends TimecodeTestBase {

	@Test
	@DisplayName ("Frame count is correct")
	void testFps25TimecodeFrameCount() {
		int frameCount = 34567;
		Timecode timecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, frameCount);
		assertEquals(frameCount, timecode.getFrameCount());
	}

	@Test
	@DisplayName ("Hours, minutes, seconds, and frames are correct")
	void testFps25TimecodeHoursMinutesSecondsFrames() {
		int hours = 12;
		int minutes = 5;
		int seconds = 16;
		int frames = 24;
		Timecode timecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, hours, minutes, seconds, frames);


		assertTimecodeOperationWithUnits("12:05:16:24", hours, minutes, seconds, frames, timecode.getFrameCount(), timecode);
	}

	@Test
	@DisplayName ("Frame count limit is correct")
	void testFps25TimecodeFrameCountLimit() {
		Fps25Timecode timecode = (Fps25Timecode) TimecodeFactory.createTimeCode(FrameRateType.FPS25, 23, 59, 59, 24);
		assertEquals(timecode.getFrameCountLimit() - 1, timecode.getFrameCount());
	}

	@Test
	@DisplayName ("Out of range units throws exception")
	void testFps25TimecodeWithOutOfRangeUnits() {
		IllegalArgumentException exception = expectThrows(IllegalArgumentException.class,
											  () -> TimecodeString.of(FrameRateType.FPS25.toString(), 12, 50, 30, 25));

		assertEquals("Invalid timecode value", exception.getMessage());
	}

	@Test
	@DisplayName ("Invalid input throws exception")
	void testFps25TimecodeWithInvalidInput() {
		TimecodeException exception = expectThrows(TimecodeException.class,
											   () -> TimecodeString.of(FrameRateType.FPS25.toString(), "12:ab:23;44"));

		assertEquals("Invalid input format. Should be <frameCount> or hh:mm:ss:ff", exception.getMessage());
	}

	@Test
	@DisplayName ("Frame count out of limit throws exception")
	void testFps25TimecodeFrameContOutOfLimit() {
		IllegalArgumentException exception = expectThrows(IllegalArgumentException.class,
												  () -> TimecodeFactory.createTimeCode(FrameRateType.FPS25, 2160000));

		assertEquals("Frame count is greater than limit 2160000", exception.getMessage());
	}

	@Test
	@DisplayName ("Negative frame count throws exception")
	void testFps25TimecodeWithNegativeFrameCount() {
		IllegalArgumentException exception = expectThrows(IllegalArgumentException.class,
														  () -> TimecodeString.of(FrameRateType.FPS25.toString(), -15));

		assertEquals("Frame count must be greater than zero", exception.getMessage());
	}

	@Nested
	@DisplayName ("Addition tests")
	class AdditionTests {

		@Test
		@DisplayName ("Addition between different Timecodes throws exception")
		void testFps25TimecodeAdditionInvalid() {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 15, 6, 0, 12);
			Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.DF30, 2, 5, 1, 0);

			TimecodeException exception = expectThrows(TimecodeException.class, () -> actualTimecode.add(timecodeToAdd));

			String exceptionMessage = "Addition operation is only valid between instances of the same Timecode class";
			assertEquals(exceptionMessage, exception.getMessage());
		}

		@Test
		@DisplayName ("Addition overflow throws exception")
		void testFps25TimecodeAdditionOverflow() {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 15, 6, 0, 12);
			Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 10, 5, 2, 24);

			TimecodeException exception = expectThrows(TimecodeException.class, () -> actualTimecode.add(timecodeToAdd));
			assertEquals("Result is greater than limit", exception.getMessage());
		}

		@Test
		@DisplayName ("Addition of zero")
		void testFps25TimecodeAdditionOfZero() throws Exception {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 15, 6, 0, 12);
			Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 0, 0, 0, 0);
			Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 15, 6, 0, 12);

			actualTimecode.add(timecodeToAdd);

			assertTimecodeOperation("15:06:00:12 + 00:00:00:00", expectedTimecode, actualTimecode);
		}

		@Test
		@DisplayName ("Addition with carriage")
		void testFps25TimecodeAdditionWithCarriage() throws Exception {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 22, 59, 59, 24);
			Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 0, 0, 0, 1);
			Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 23, 0, 0, 0);

			actualTimecode.add(timecodeToAdd);

			assertTimecodeOperation("22:59:59:24 + 00:00:00:01", expectedTimecode, actualTimecode);
		}

		@Test
		@DisplayName ("Commutative addition")
		void testFps25TimecodeAdditionCommutative() throws Exception{
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 10, 5, 3, 14);
			Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 11, 6, 4, 10);
			Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 21, 11, 7, 24);

			actualTimecode.add(timecodeToAdd);

			assertTimecodeOperation("10:05:03:14 + 11:06:04:10", expectedTimecode, actualTimecode);

			actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 11, 6, 4, 10);
			timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 10, 5, 3, 14);

			actualTimecode.add(timecodeToAdd);

			assertTimecodeOperation("11:06:04:10 + 10:05:03:14", expectedTimecode, actualTimecode);
		}
	}

	@Nested
	@DisplayName ("Subtraction tests")
	class SubtractionTests {

		@Test
		@DisplayName ("Subtraction under zero")
		void testFps25TimecodeSubtractionUnderZero() throws Exception {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 5, 5, 15, 20);
			Timecode timecodeToSubtract = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 6, 1, 5, 23);

			int expectedHours = -1;
			int expectedMinutes = 4;
			int expectedSeconds = 9;
			int expectedFrames = 22;
			int expectedFrameCount = -83753;

			actualTimecode.subtract(timecodeToSubtract);
			assertTimecodeOperationWithUnits("05:05:15:20 - 06:01:05:23", expectedHours, expectedMinutes,
											 expectedSeconds, expectedFrames, expectedFrameCount, actualTimecode);
		}

		@Test
		@DisplayName ("Subtraction underflow throws exception")
		void testFps25TimecodeSubtractionUnderflow() {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 5, 5, 15, 20);
			Timecode timecodeToSubtract = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 23, 59, 59, 24);

			TimecodeException exception = expectThrows(TimecodeException.class,
													   () -> actualTimecode.subtract(timecodeToSubtract).subtract(timecodeToSubtract));

			assertEquals("Result value is lesser than limit", exception.getMessage());
		}

		@Test
		@DisplayName ("Subtraction result zero")
		void testFps25TimecodeSubtractionResultZero() throws Exception {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 5, 5, 15, 20);
			Timecode timecodeToSubtract = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 5, 5, 15, 20);
			Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 0, 0, 0, 0);

			actualTimecode.subtract(timecodeToSubtract);

			assertTimecodeOperation("05:05:15:20 - 05:05:15:20", expectedTimecode, actualTimecode);
		}

		@Test
		@DisplayName ("Subtraction between different Timecodes throws exception")
		void testFps25TimecodeSubtractionInvalid() {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 10, 5, 15, 14);
			Timecode timecodeToSubtract = TimecodeFactory.createTimeCode(FrameRateType.DF30, 0, 0, 0, 0);

			TimecodeException exception = expectThrows(TimecodeException.class,
													   () -> actualTimecode.subtract(timecodeToSubtract));

			String exceptionMessage = "Subtract operation is only valid between instances of the same Timecode class";
			assertEquals(exceptionMessage, exception.getMessage());
		}

		@Test
		@DisplayName ("Subtraction of zero")
		void testFps25TimecodeSubtractionOfZero() throws Exception {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 10, 5, 15, 14);
			Timecode timecodeToSubtract = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 0, 0, 0, 0);
			Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 10, 5, 15, 14);

			actualTimecode.subtract(timecodeToSubtract);

			assertTimecodeOperation("10:05:15:14 - 10:05:15:14", expectedTimecode, actualTimecode);
		}

		@Test
		@DisplayName ("Normal subtraction")
		void testFps25TimecodeSubtraction() throws Exception {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 10, 5, 15, 14);
			Timecode timecodeToSubtract = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 5, 3, 4, 10);
			Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 5, 2, 11, 4);

			actualTimecode.subtract(timecodeToSubtract);

			assertTimecodeOperation("10:05:15:14 - 05:03:04:10", expectedTimecode, actualTimecode);
		}
	}
}
