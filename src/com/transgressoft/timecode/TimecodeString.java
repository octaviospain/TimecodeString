/*
 * Copyright 2016, 2017 Octavio Calleya
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.transgressoft.timecode;

import java.util.*;

/**
 * Class that converts between frame counts and a timecode string representation.
 *
 * @author Octavio Calleya
 * @version 0.1
 */
public class TimecodeString {

	private static int hours;
	private static int minutes;
	private static int seconds;
	private static int frames;
	private static int frameCount;

	private Timecode timecode;
	private TimecodeInputType inputType;

	private TimecodeString(Timecode timecode, TimecodeInputType inputType) {
		this.timecode = timecode;
		this.inputType = inputType;
	}

	/**
	 * Constructs a {@link TimecodeString} object given the frame rate and the value,
	 * both represented as <tt>String</tt> objects
	 *
	 * @param frameRateString The frame rate
	 * @param value           The value of the timecode
	 *
	 * @return a <tt>TimecodeString</tt> object
	 *
	 * @throws TimecodeException if <tt>frameRateString</tt> or <tt>value</tt> are not well formatted or unsupported
	 */
	public static TimecodeString of(String frameRateString, String value) throws TimecodeException {
		TimecodeInputType timecodeInputType = TimecodeInputType.fromString(value);

		TimecodeString timecodeString = null;

		if (timecodeInputType.equals(TimecodeInputType.FRAME_COUNT_INPUT_TYPE)) {
			frameCount = Integer.valueOf(value);
			timecodeString = of(frameRateString, frameCount);
		}
		else if (timecodeInputType.equals(TimecodeInputType.UNITS_INPUT_TYPE)) {
			parseInputByUnits(value);
			timecodeString = of(frameRateString, hours, minutes, seconds, frames);
		}
		clearStaticValues();
		return timecodeString;
	}

	/**
	 * Constructs a {@link TimecodeString} object given a frame rate expressed as <tt>String</tt>
	 * and the number of frames
	 *
	 * @param frameRateString The frame rate
	 * @param frameCount      The number of frames
	 *
	 * @return a <tt>TimecodeString</tt> object
	 */
	public static TimecodeString of(String frameRateString, int frameCount) throws TimecodeException {
		FrameRateType frameRateType = FrameRateType.fromString(frameRateString);
		Timecode givenTimecode = TimecodeFactory.createTimeCode(frameRateType, frameCount);
		return new TimecodeString(givenTimecode, TimecodeInputType.FRAME_COUNT_INPUT_TYPE);
	}

	private static void parseInputByUnits(String input) {
		StringTokenizer stringTokenizer = new StringTokenizer(input, ":;");
		hours = Integer.valueOf(stringTokenizer.nextToken());
		minutes = Integer.valueOf(stringTokenizer.nextToken());
		seconds = Integer.valueOf(stringTokenizer.nextToken());
		frames = Integer.valueOf(stringTokenizer.nextToken());
	}

	/**
	 * Constructs a {@link TimecodeString} object given a frame rate expressed as <tt>String</tt>,
	 * the number of days, hours, minutes, seconds, and frames;
	 *
	 * @param frameRateString The frame rate
	 * @param hh              The number of hours
	 * @param mm              The number of minutes
	 * @param ss              The number of seconds
	 * @param ff              The number of frames
	 *
	 * @return a <tt>TimecodeString</tt> object
	 */
	public static TimecodeString of(String frameRateString, int hh, int mm, int ss, int ff) throws TimecodeException {
		FrameRateType frameRateType = FrameRateType.fromString(frameRateString);
		Timecode givenTimecode = TimecodeFactory.createTimeCode(frameRateType, hh, mm, ss, ff);
		return new TimecodeString(givenTimecode, TimecodeInputType.UNITS_INPUT_TYPE);
	}

	private static void clearStaticValues() {
		hours = 0;
		minutes = 0;
		seconds = 0;
		frames = 0;
		frameCount = 0;
	}

	public String getOppositeRepresentation() {
		String timeCodeRepresentation = null;
		if (inputType.equals(TimecodeInputType.UNITS_INPUT_TYPE))
			timeCodeRepresentation = Long.toString(timecode.getFrameCount());
		else if (inputType.equals(TimecodeInputType.FRAME_COUNT_INPUT_TYPE))
			timeCodeRepresentation = timecode.getStringRepresentation();
		return timeCodeRepresentation;
	}
}
