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

import static com.transgressoft.timecode.TimecodeException.ErrorCase.INVALID_INPUT_FORMAT;

/**
 * Enumeration of different type of input formats of the value of a timecode.
 *
 * @author Octavio Calleya
 * @version 1.0
 */
public enum TimecodeInputType {

	/**
	 * Timecode value as hours:minutes:seconds:frames
	 */
	UNITS_INPUT_TYPE,

	/**
	 * Timecode value as total number of frames
	 */
	FRAME_COUNT_INPUT_TYPE;

	/**
	 * Parses the given <tt>String</tt> to a <tt>TimecodeInputType</tt> that
	 * matches the string format of the timecode value
	 *
	 * @param string The value of a timecode with uncertain format
	 *
	 * @return The TimecodeInputType enum
	 *
	 * @throws TimecodeException if the given <tt>String</tt> has not a valid format
	 */
	public static TimecodeInputType fromString(String string) throws TimecodeException {
		TimecodeInputType timecodeInputType = null;

		if (string.matches("\\d{2}:\\d{2}:\\d{2}[;:]\\d{2}"))
			timecodeInputType = UNITS_INPUT_TYPE;
		else if (string.matches("\\d+"))
			timecodeInputType = FRAME_COUNT_INPUT_TYPE;

		if (timecodeInputType == null)
			throw new TimecodeException(INVALID_INPUT_FORMAT);

		return timecodeInputType;
	}
}
