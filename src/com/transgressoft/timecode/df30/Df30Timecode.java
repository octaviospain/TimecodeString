/**
 * Copyright 2016 Octavio Calleya
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

package com.transgressoft.timecode.df30;

import com.transgressoft.timecode.*;

/**
 * Class that represents a {@link Timecode} implementation of
 * a Drop-Frame 30 (29.97 fps) video timecode.
 *
 * @see <a href="https://en.wikipedia.org/wiki/SMPTE_timecode">SMPTE Timecode</a>
 *
 * @author Octavio Calleya
 * @version 0.1
 */
public class Df30Timecode extends TimecodeBase {

	/**
	 * Maximum amount of frames accepted, since the maximum value
	 * of the Timecode can be 23:59:59:28
	 */
	private static final int FRAME_COUNT_LIMIT = 2589407;

	protected Df30Timecode(int hours, int minutes, int seconds, int frames) {
		super(hours, minutes, seconds, frames);
		frameCount += frames;
		frameCount += seconds * 30;
		frameCount += minutes * 1800;
		frameCount += hours * 108000;
		frameDropCalculation();
	}

	protected Df30Timecode(int frameCount) {
		super(frameCount);
		reverseDropFrameCalculation(frameCount);
	}

	public static Df30Timecode of(int hours, int minutes, int seconds, int frames) {
		if(!FrameRateType.DF30.areValidValues(hours, minutes, seconds, frames))
			throw new IllegalArgumentException("Invalid timecode value");
		return new Df30Timecode(hours, minutes, seconds, frames);
	}

	public static Df30Timecode of(int frameCount) {
		if(frameCount < 0)
			throw new IllegalArgumentException("Frame count must be greater than zero");
		if(frameCount >= FRAME_COUNT_LIMIT)
			throw new IllegalArgumentException("Frame count is greater than " + FRAME_COUNT_LIMIT);
		return new Df30Timecode(frameCount);
	}

	@Override
	public Timecode add(Timecode timecode)  throws TimecodeException {
		if(!(timecode instanceof Df30Timecode))
			throw new TimecodeException("Sum operation is only valid between instances of the same Timecode class");

		frameCount += timecode.getFrameCount();
		reverseDropFrameCalculation(frameCount);

		if(frameCount >= FRAME_COUNT_LIMIT)
			throw new TimecodeException("Result is greater than limit");

		return this;
	}

	@Override
	public Timecode substract(Timecode timecode)  throws TimecodeException {
		if(!(timecode instanceof Df30Timecode))
			throw new TimecodeException("Substract operation is only valid between instances of the same Timecode class");

		frameCount -= timecode.getFrameCount();
		if(frameCount >= 0)
			reverseDropFrameCalculation(frameCount);
		else {
			int oldHours = hours;
			reverseDropFrameCalculation(FRAME_COUNT_LIMIT + frameCount);	// positive number of frames
			frameAdditionCalculation();
			hours = oldHours - timecode.getHours();
		}

		if(Math.abs(frameCount) >= FRAME_COUNT_LIMIT)
			throw new TimecodeException("Result value is lesser than limit");

		return this;
	}

	@Override
	public int getFrameCount() {
		return frameCount;
	}

	@Override
	public String getStringRepresentation() {
		String timecodeStringRepresentation = "";
		timecodeStringRepresentation += String.format("%02d", hours) + ":";
		timecodeStringRepresentation += String.format("%02d", minutes) + ":";
		timecodeStringRepresentation += String.format("%02d", seconds) + ";";
		timecodeStringRepresentation += String.format("%02d", frames);
		return timecodeStringRepresentation;
	}

	@Override
	public int getFrameCountLimit() {
		return FRAME_COUNT_LIMIT;
	}

	private void frameDropCalculation() {
		int totalMinutes = hours * 60 + minutes;
		frameCount = 108000 * hours + 1800 * minutes + 30 * seconds + frames;
		frameCount -= 2 * (totalMinutes - totalMinutes / 10);
	}

	private void reverseDropFrameCalculation(int numberOfFrames) {
		int totalFrames = numberOfFrames;

		int division = totalFrames / 17982;
		int modulus = totalFrames % 17982;
		totalFrames += 18 * division + 2 * ((modulus - 2) / 1798);

		frames = totalFrames % 30;
		seconds = (totalFrames / 30) % 60;
		minutes = (totalFrames / 1800) % 60;
		hours = (totalFrames / 108000) % 24;
	}

	private void frameAdditionCalculation() {
		if(minutes % 10 == 0 && seconds != 0 && (frames == 29 || frames == 30))
			carriage();
		else
			frames++;

		if(frames == 30)
			carriage();
	}

	private void carriage() {
		frames = 0;
		seconds++;
		if (seconds == 60) {
			seconds = 0;
			minutes++;
			if (minutes == 60) {
				minutes = 0;
				hours++;
			}
		}
	}
}
