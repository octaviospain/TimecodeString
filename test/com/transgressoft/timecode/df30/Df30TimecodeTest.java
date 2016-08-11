package com.transgressoft.timecode.df30;

import com.transgressoft.timecode.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Df30Timecode} class.
 *
 * @author Octavio Calleya
 * @version 0.1
 */
public class Df30TimecodeTest extends TimecodeTestBase {

	@Test
	@DisplayName ("Frame count is correct")
	void testDf30TimecodeFrameCount() {
		int frameCount = 34567;
		Timecode timecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, frameCount);
		assertEquals(frameCount, timecode.getFrameCount());
	}

	@Test
	@DisplayName ("Hours, minutes, seconds, and frames are correct")
	void testDf30TimecodeHoursMinutesSecondsFrames() {
		int hours = 12;
		int minutes = 5;
		int seconds = 16;
		int frames = 24;
		Timecode timecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, hours, minutes, seconds, frames);

		assertTimecodeOperationWithUnits("12:05:16:24", hours, minutes, seconds, frames, timecode.getFrameCount(), timecode);
	}

	@Test
	@DisplayName ("Frame count limit is correct")
	void testDf30TimecodeFrameCountLimit() {
		Df30Timecode timecode = (Df30Timecode) TimecodeFactory.createTimeCode(FrameRateType.DF30, 23, 59, 59, 28);
		assertEquals(timecode.getFrameCountLimit() - 1, timecode.getFrameCount());
	}

	@Test
	@DisplayName ("Out of range units throws exception")
	void testDf30TimecodeWithOutOfRangeUnits() {
		IllegalArgumentException exception = expectThrows(IllegalArgumentException.class, () -> TimecodeString
				.of(FrameRateType.DF30.toString(), 12, 50, 30, 30));

		assertEquals("Invalid timecode value", exception.getMessage());
	}

	@Test
	@DisplayName ("Invalid input throws exception")
	void testDf30TimecodeWithInvalidInput() throws Exception {
		TimecodeException exception = expectThrows(TimecodeException.class, () -> TimecodeString
				.of(FrameRateType.DF30.toString(), "12:ab:23;44"));

		assertEquals("Invalid input format. Should be <frameCount> or hh:mm:ss:ff", exception.getMessage());
	}

	@Test
	@DisplayName ("Frame count out of limit throws exception")
	void testDf30TimecodeFrameCountOutOfLimit() {
		IllegalArgumentException exception = expectThrows(IllegalArgumentException.class, () -> TimecodeFactory
				.createTimeCode(FrameRateType.DF30, 2589407));

		assertEquals("Frame count is greater than 2589407", exception.getMessage());
	}

	@Test
	@DisplayName ("Negative frame count throws exception")
	void testDf30TimecodeWithNegativeFrameCount() throws Exception {
		IllegalArgumentException exception = expectThrows(IllegalArgumentException.class,
													  () -> TimecodeString.of(FrameRateType.DF30.toString(), - 15));

		assertEquals("Frame count must be greater than zero", exception.getMessage());
	}

	@Nested
	@DisplayName ("Addition tests")
	class AdditionTests {

		@Test
		@DisplayName ("Addition between different Timecodes throws exception")
		void testDf30TimecodeAdditionInvalid() {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 15, 6, 0, 12);
			Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 2, 5, 1, 0);

			TimecodeException exception = expectThrows(TimecodeException.class,
													   () -> actualTimecode.add(timecodeToAdd));

			String exceptionMessage = "Addition operation is only valid between instances of the same Timecode class";
			assertEquals(exceptionMessage, exception.getMessage());
		}

		@Test
		@DisplayName ("Addition overflow throws exception")
		void testDf30TimecodeAdditionOverflow() {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 15, 6, 0, 12);
			Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.DF30, 10, 5, 2, 24);

			TimecodeException exception = expectThrows(TimecodeException.class,
													   () -> actualTimecode.add(timecodeToAdd));
			assertEquals("Result is greater than limit", exception.getMessage());
		}

		@Test
		@DisplayName ("Addition of zero")
		void testDf30TimecodeAdditionOfZero() throws Exception {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 15, 6, 0, 12);
			Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.DF30, 0, 0, 0, 0);
			Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 15, 6, 0, 12);

			actualTimecode.add(timecodeToAdd);

			assertTimecodeOperation("15:06:00;12 + 00:00:00;00", expectedTimecode, actualTimecode);
		}

		@Test
		@DisplayName ("Addition with carriage")
		void testDf30TimecodeAdditionWithCarriage() throws Exception {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 22, 59, 59, 24);
			Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.DF30, 0, 0, 0, 10);
			Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 23, 0, 0, 4);

			actualTimecode.add(timecodeToAdd);

			assertTimecodeOperation("22:59:59;24 + 00:00:00;10", expectedTimecode, actualTimecode);
		}

		@Test
		@DisplayName ("Commutative addition")
		void testDf30TimecodeAdditionCommutative() throws Exception {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 10, 5, 3, 14);
			Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.DF30, 11, 6, 4, 10);
			Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 21, 11, 7, 22);

			actualTimecode.add(timecodeToAdd);

			assertTimecodeOperation("10:05:03;14 + 11:06:04;10", expectedTimecode, actualTimecode);

			Timecode actualTimecode2 = TimecodeFactory.createTimeCode(FrameRateType.DF30, 11, 6, 4, 10);
			Timecode timecodeToAdd2 = TimecodeFactory.createTimeCode(FrameRateType.DF30, 10, 5, 3, 14);

			actualTimecode2.add(timecodeToAdd2);

			assertTimecodeOperation("11:06:04;10 + 10:05:03;14", expectedTimecode, actualTimecode);
		}
	}

	@Nested
	@DisplayName ("Subtraction tests")
	class SubtractionTests {

		@Test
		@DisplayName ("Under zero + drop frame addition + minutes carriage")
		void testDf30TimecodeSubtractionUnderZeroWithDropFrameAdditionAndMinutesCarriage() throws Exception {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 7, 50, 0, 17);
			Timecode timecodeToSubtract = TimecodeFactory.createTimeCode(FrameRateType.DF30, 11, 50, 0, 17);

			int expectedHours = - 4;
			int expectedMinutes = 0;
			int expectedSeconds = 0;
			int expectedFrames = 0;
			int expectedFrameCount = - 431568;

			actualTimecode.subtract(timecodeToSubtract);

			assertTimecodeOperationWithUnits("07:50:00;17 - 11:50:00;17", expectedHours, expectedMinutes,
											 expectedSeconds, expectedFrames, expectedFrameCount, actualTimecode);
		}

		@Test
		@DisplayName ("Under zero + drop frame addition + seconds carriage")
		void testDf30TimecodeSubtractionUnderZeroWithDropFrameAdditionAndSecondsCarriage() throws Exception {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 7, 12, 6, 11);
			Timecode timecodeToSubtract = TimecodeFactory.createTimeCode(FrameRateType.DF30, 11, 11, 5, 15);

			int expectedHours = - 4;
			int expectedMinutes = 1;
			int expectedSeconds = 0;
			int expectedFrames = 26;
			int expectedFrameCount = - 429744;

			actualTimecode.subtract(timecodeToSubtract);

			assertTimecodeOperationWithUnits("07:12:60;11 - 11:11:05;15", expectedHours, expectedMinutes,
											 expectedSeconds, expectedFrames, expectedFrameCount, actualTimecode);
		}

		@Test
		@DisplayName ("Under zero + drop frame addition + frame carriage")
		void testDf30TimecodeSubtractionUnderZeroWithDropFrameAdditionAndFrameCarriage() throws Exception {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 7, 12, 0, 11);
			Timecode timecodeToSubtract = TimecodeFactory.createTimeCode(FrameRateType.DF30, 11, 11, 5, 9);

			int expectedHours = - 4;
			int expectedMinutes = 0;
			int expectedSeconds = 55;
			int expectedFrames = 0;
			int expectedFrameCount = - 429918;

			actualTimecode.subtract(timecodeToSubtract);

			assertTimecodeOperationWithUnits("07:12:00;11 - 11:11:05;09", expectedHours, expectedMinutes,
											 expectedSeconds, expectedFrames, expectedFrameCount, actualTimecode);
		}

		@Test
		@DisplayName ("Subtraction under zero")
		void testDf30TimecodeSubtractionUnderZero() throws Exception {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 7, 12, 0, 11);
			Timecode timecodeToSubtract = TimecodeFactory.createTimeCode(FrameRateType.DF30, 11, 11, 5, 15);

			int expectedHours = - 4;
			int expectedMinutes = 0;
			int expectedSeconds = 54;
			int expectedFrames = 24;
			int expectedFrameCount = - 429924;


			actualTimecode.subtract(timecodeToSubtract);

			assertTimecodeOperationWithUnits("10:05:15;14 - 02:14:21;10", expectedHours, expectedMinutes,
											 expectedSeconds, expectedFrames, expectedFrameCount, actualTimecode);

		}

		@Test
		@DisplayName ("Subtraction underflow throws exception")
		void testDf30TimecodeSubtractionUnderflow() {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 5, 5, 15, 20);
			Timecode timecodeToSubtract = TimecodeFactory.createTimeCode(FrameRateType.DF30, 23, 59, 59, 24);

			TimecodeException exception = expectThrows(TimecodeException.class,
													   () -> actualTimecode.subtract(timecodeToSubtract)
																		   .subtract(timecodeToSubtract));

			assertEquals("Result value is lesser than limit", exception.getMessage());
		}

		@Test
		@DisplayName ("Subtraction result zero")
		void testDf30TimecodeSubtractionResultZero() throws Exception {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 5, 5, 15, 20);
			Timecode timecodeToSubtract = TimecodeFactory.createTimeCode(FrameRateType.DF30, 5, 5, 15, 20);
			Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 0, 0, 0, 0);

			actualTimecode.subtract(timecodeToSubtract);

			assertTimecodeOperation("05:05:15;20 - 05:05:15;20", expectedTimecode, actualTimecode);
		}

		@Test
		@DisplayName ("Subtraction between different Timecodes throws exception")
		void testDf30TimecodeSubtractionInvalid() {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 10, 5, 15, 14);
			Timecode timecodeToSubtract = TimecodeFactory.createTimeCode(FrameRateType.FPS25, 0, 0, 0, 0);

			TimecodeException exception = expectThrows(TimecodeException.class,
													   () -> actualTimecode.subtract(timecodeToSubtract));

			String exceptionMessage = "Subtract operation is only valid between instances of the same Timecode class";
			assertEquals(exceptionMessage, exception.getMessage());
		}

		@Test
		@DisplayName ("Subtraction of zero")
		void testDf30TimecodeSubtractionOfZero() throws Exception {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 10, 5, 15, 14);
			Timecode timecodeToSubtract = TimecodeFactory.createTimeCode(FrameRateType.DF30, 0, 0, 0, 0);
			Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 10, 5, 15, 14);

			actualTimecode.subtract(timecodeToSubtract);

			assertTimecodeOperation("10:05:15;14 - 00:00:00;00", expectedTimecode, actualTimecode);
		}

		@Test
		@DisplayName ("Normal subtraction")
		void testDf30TimecodeSubtraction() throws Exception {
			Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 10, 5, 15, 14);
			Timecode timecodeToSubtract = TimecodeFactory.createTimeCode(FrameRateType.DF30, 2, 14, 21, 10);
			Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.DF30, 7, 50, 54, 2);

			actualTimecode.subtract(timecodeToSubtract);

			assertTimecodeOperation("10:05:15;14 - 02:14:21;10", expectedTimecode, actualTimecode);
		}
	}
}
