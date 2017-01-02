package com.transgressoft.timecode.fps24;

import com.transgressoft.timecode.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Fps24Timecode} class.
 *
 * @author Octavio Calleya
 * @version 0.1
 */
public class Fps24TimecodeTest extends TimecodeTestBase {

    @Test
    @DisplayName ("Frame count is correct")
    void testFps24TimecodeFrameCount() {
        int frameCount = 234567;
        Timecode timecode = TimecodeFactory.createTimeCode(FrameRateType.FPS24, frameCount);
        assertEquals(frameCount, timecode.getFrameCount());
    }

    @Test
    @DisplayName ("Hours, minutes, seconds, and frames are correct")
    void testFps24TimecodeHoursMinutesSecondsFrames() {
        int hours = 12;
        int minutes = 5;
        int seconds = 16;
        int frames = 23;
        Timecode timecode = TimecodeFactory.createTimeCode(FrameRateType.FPS24, hours, minutes, seconds, frames);


        assertTimecodeOperationWithUnits("12:05:16:23", hours, minutes, seconds, frames, timecode.getFrameCount(), timecode);
    }

    @Test
    @DisplayName ("Frame count limit is correct")
    void testFps24TimecodeFrameCountLimit() {
        Fps24Timecode timecode = (Fps24Timecode) TimecodeFactory.createTimeCode(FrameRateType.FPS24, 23, 59, 59, 23);
        assertEquals(timecode.getFrameCountLimit() - 1, timecode.getFrameCount());
    }

    @Test
    @DisplayName ("Out of range units throws exception")
    void testFps24TimecodeWithOutOfRangeUnits() {
        IllegalArgumentException exception = expectThrows(IllegalArgumentException.class,
                                                          () -> TimecodeString.of(FrameRateType.FPS24.toString(), 12, 50, 30, 25));

        assertEquals("Invalid timecode value", exception.getMessage());
    }

    @Test
    @DisplayName ("Invalid input throws exception")
    void testFps24TimecodeWithInvalidInput() {
        TimecodeException exception = expectThrows(TimecodeException.class,
                                                   () -> TimecodeString.of(FrameRateType.FPS24.toString(), "12:ab:23;44"));

        assertEquals("Invalid input format. Should be <frameCount> or hh:mm:ss:ff", exception.getMessage());
    }

    @Test
    @DisplayName ("Frame count out of limit throws exception")
    void testFps24TimecodeFrameContOutOfLimit() {
        IllegalArgumentException exception = expectThrows(IllegalArgumentException.class,
                                                          () -> TimecodeFactory.createTimeCode(FrameRateType.FPS24, 2073600));

        assertEquals("Frame count is greater than limit 2073600", exception.getMessage());
    }

    @Test
    @DisplayName ("Negative frame count throws exception")
    void testFps24TimecodeWithNegativeFrameCount() {
        IllegalArgumentException exception = expectThrows(IllegalArgumentException.class,
                                                          () -> TimecodeString.of(FrameRateType.FPS24.toString(), -15));

        assertEquals("Frame count must be greater than zero", exception.getMessage());
    }

    @Nested
    @DisplayName ("Addition tests")
    class AdditionTests {

        @Test
        @DisplayName ("Addition between different Timecodes throws exception")
        void testFps24TimecodeAdditionInvalid() {
            Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 15, 6, 0, 12);
            Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.DF30, 2, 5, 1, 0);

            TimecodeException exception = expectThrows(TimecodeException.class, () -> actualTimecode.add(timecodeToAdd));

            String exceptionMessage = "Addition operation is only valid between instances of the same Timecode class";
            assertEquals(exceptionMessage, exception.getMessage());
        }

        @Test
        @DisplayName ("Addition overflow throws exception")
        void testFps24TimecodeAdditionOverflow() {
            Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 15, 6, 0, 12);
            Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 10, 5, 2, 23);

            TimecodeException exception = expectThrows(TimecodeException.class, () -> actualTimecode.add(timecodeToAdd));
            assertEquals("Result is greater than limit", exception.getMessage());
        }

        @Test
        @DisplayName ("Addition of zero")
        void testFps24TimecodeAdditionOfZero() throws Exception {
            Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 15, 6, 0, 12);
            Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 0, 0, 0, 0);
            Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 15, 6, 0, 12);

            actualTimecode.add(timecodeToAdd);

            assertTimecodeOperation("15:06:00:12 + 00:00:00:00", expectedTimecode, actualTimecode);
        }

        @Test
        @DisplayName ("Addition with carriage")
        void testFps24TimecodeAdditionWithCarriage() throws Exception {
            Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 22, 59, 59, 23);
            Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 0, 0, 0, 1);
            Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 23, 0, 0, 0);

            actualTimecode.add(timecodeToAdd);

            assertTimecodeOperation("22:59:59:23 + 00:00:00:01", expectedTimecode, actualTimecode);
        }

        @Test
        @DisplayName ("Commutative addition")
        void testFps24TimecodeAdditionCommutative() throws Exception{
            Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 10, 5, 3, 14);
            Timecode timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 11, 6, 4, 10);
            Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 21, 11, 8, 0);

            actualTimecode.add(timecodeToAdd);

            assertTimecodeOperation("10:05:03:14 + 11:06:04:10", expectedTimecode, actualTimecode);

            actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 11, 6, 4, 10);
            timecodeToAdd = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 10, 5, 3, 14);

            actualTimecode.add(timecodeToAdd);

            assertTimecodeOperation("11:06:04:10 + 10:05:03:14", expectedTimecode, actualTimecode);
        }
    }

    @Nested
    @DisplayName ("Subtraction tests")
    class SubtractionTests {

        @Test
        @DisplayName ("Subtraction under zero")
        void testFps24TimecodeSubtractionUnderZero() throws Exception {
            Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 5, 5, 15, 20);
            Timecode timecodeToSubtract = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 6, 1, 5, 23);

            int expectedHours = -1;
            int expectedMinutes = 4;
            int expectedSeconds = 9;
            int expectedFrames = 21;
            int expectedFrameCount = -80403;

            actualTimecode.subtract(timecodeToSubtract);
            assertTimecodeOperationWithUnits("05:05:15:20 - 06:01:05:23", expectedHours, expectedMinutes,
                                             expectedSeconds, expectedFrames, expectedFrameCount, actualTimecode);
        }

        @Test
        @DisplayName ("Subtraction underflow throws exception")
        void testFps24TimecodeSubtractionUnderflow() {
            Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 5, 5, 15, 20);
            Timecode timecodeToSubtract = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 23, 59, 59, 23);

            TimecodeException exception = expectThrows(TimecodeException.class,
                                                       () -> actualTimecode.subtract(timecodeToSubtract).subtract(timecodeToSubtract));

            assertEquals("Result value is lesser than limit", exception.getMessage());
        }

        @Test
        @DisplayName ("Subtraction result zero")
        void testFps24TimecodeSubtractionResultZero() throws Exception {
            Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 5, 5, 15, 20);
            Timecode timecodeToSubtract = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 5, 5, 15, 20);
            Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 0, 0, 0, 0);

            actualTimecode.subtract(timecodeToSubtract);

            assertTimecodeOperation("05:05:15:20 - 05:05:15:20", expectedTimecode, actualTimecode);
        }

        @Test
        @DisplayName ("Subtraction between different Timecodes throws exception")
        void testFps24TimecodeSubtractionInvalid() {
            Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 10, 5, 15, 14);
            Timecode timecodeToSubtract = TimecodeFactory.createTimeCode(FrameRateType.DF30, 0, 0, 0, 0);

            TimecodeException exception = expectThrows(TimecodeException.class,
                                                       () -> actualTimecode.subtract(timecodeToSubtract));

            String exceptionMessage = "Subtract operation is only valid between instances of the same Timecode class";
            assertEquals(exceptionMessage, exception.getMessage());
        }

        @Test
        @DisplayName ("Subtraction of zero")
        void testFps24TimecodeSubtractionOfZero() throws Exception {
            Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 10, 5, 15, 14);
            Timecode timecodeToSubtract = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 0, 0, 0, 0);
            Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 10, 5, 15, 14);

            actualTimecode.subtract(timecodeToSubtract);

            assertTimecodeOperation("10:05:15:14 - 10:05:15:14", expectedTimecode, actualTimecode);
        }

        @Test
        @DisplayName ("Normal subtraction")
        void testFps24TimecodeSubtraction() throws Exception {
            Timecode actualTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 10, 5, 15, 14);
            Timecode timecodeToSubtract = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 5, 3, 4, 10);
            Timecode expectedTimecode = TimecodeFactory.createTimeCode(FrameRateType.FPS24, 5, 2, 11, 4);

            actualTimecode.subtract(timecodeToSubtract);

            assertTimecodeOperation("10:05:15:14 - 05:03:04:10", expectedTimecode, actualTimecode);
        }
    }
} 