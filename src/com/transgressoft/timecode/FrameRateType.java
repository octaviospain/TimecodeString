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

import static com.transgressoft.timecode.TimecodeException.ErrorCase.INVALID_FRAME_RATE;

/**
 * Enumeration of frame rate types with operations to parse the frame
 * rate given a string, and check if his value is valid.
 *
 * @author Octavio Calleya
 * @version 1.0
 */
public enum FrameRateType {
	FPS24("24fps"),
	FPS25("25fps"),
	FPS30("30fps"),
	DF30("30DF");

	private String shortName;

	FrameRateType(String shortName) {
		this.shortName = shortName;
	}

	public static FrameRateType fromString(String string) throws TimecodeException {
		FrameRateType frameRateType = null;

		if ("24fps".equalsIgnoreCase(string))
			frameRateType = FPS24;
		else if ("25fps".equalsIgnoreCase(string))
			frameRateType = FPS25;
		else if ("30fps".equalsIgnoreCase(string))
			frameRateType = FPS30;
		else if ("30DF".endsWith(string))
			frameRateType = DF30;

		if (frameRateType == null)
			throw new TimecodeException(INVALID_FRAME_RATE);

		return frameRateType;
	}

	public boolean areValidValues(int hours, int minutes, int seconds, int frames) {
		boolean validHours = hours >= 0 && hours < 24;
		boolean validMinutes = minutes >= 0 && minutes < 60;
		boolean validSeconds = seconds >= 0 && seconds < 60;
		boolean validFrames = false;
		if (equals(FPS24))
			validFrames = frames >= 0 && frames < 24;
		else if (equals(FPS25))
			validFrames = frames >= 0 && frames < 25;
		else if (equals(FPS30) || equals(DF30))
			validFrames = frames >= 0 && frames < 30;
		return validHours && validMinutes && validSeconds && validFrames;
	}

	@Override
	public String toString() {
		return shortName;
	}
}
