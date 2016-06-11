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

package com.transgressoft.timecode;

/**
 * Base class implementation of {@link Timecode}.
 *
 * @author Octavio Calleya
 * @version 0.1
 */
public abstract class TimecodeBase implements Timecode {

	protected int hours = 0;
	protected int minutes = 0;
	protected int seconds = 0;
	protected int frames = 0;
	protected int frameCount = 0;

	/**
	 * Creates a new instance of a {@link Timecode} by the
	 * given hours, minutes, seconds and frames
	 *
	 * @param hours
	 * @param minutes
	 * @param seconds
	 * @param frames
	 */
	protected TimecodeBase(int hours, int minutes, int seconds, int frames) {
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
		this.frames = frames;
	}

	/**
	 * Creates a new instance of a {@link Timecode} by the
	 * given frame count
	 *
	 * @param frameCount
	 */
	protected TimecodeBase(int frameCount) {
		this.frameCount = frameCount;
	}

	public abstract int getFrameCountLimit();

	@Override
	public int getHours() {
		return hours;
	}

	@Override
	public int getMinutes() {
		return minutes;
	}

	@Override
	public int getSeconds() {
		return seconds;
	}

	@Override
	public int getFrames() {
		return frames;
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
		timecodeStringRepresentation += String.format("%02d", seconds) + ":";
		timecodeStringRepresentation += String.format("%02d", frames);
		return timecodeStringRepresentation;
	}

	@Override
	public String toString() {
		return "Timecode[" + getStringRepresentation() + "/" + frameCount + "]";
	}

	@Override
	public int hashCode() {
		int hash = 23;
		hash = 71 * hash + frames;
		hash = 71 * hash + seconds;
		hash = 71 * hash + minutes;
		hash = 71 * hash + hours;
		hash = 71 * hash + frameCount;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result;
		if(obj instanceof TimecodeBase &&
				((TimecodeBase) obj).getFrames() == frames &&
				((TimecodeBase) obj).getSeconds() == seconds &&
				((TimecodeBase) obj).getMinutes() == minutes &&
				((TimecodeBase) obj).getHours() == hours &&
				((TimecodeBase) obj).getFrameCount() == frameCount)
			result = true;
		else
			result = false;
		return result;
	}
}