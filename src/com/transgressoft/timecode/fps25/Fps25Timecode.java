/**
 * Copyright 2016 Octavio Calleya
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.transgressoft.timecode.fps25;

import com.transgressoft.timecode.*;

/**
 * Class that represents a {@link Timecode} implementation of
 * a 25 fps video timecode.
 *
 * @author Octavio Calleya
 * @version 0.1
 * @see <a href="https://en.wikipedia.org/wiki/SMPTE_timecode">SMPTE Timecode</a>
 */
public class Fps25Timecode extends TimecodeBase {

	/**
	 * Maximum amount of frames accepted, since the maximum value
	 * of the Timecode can be 23:59:59:24:
	 *
	 * <p>24 * 60 * 60 * 25 = 2 160 000</p>
	 */
	private static final int FRAME_COUNT_LIMIT = 2160000;

	protected Fps25Timecode(int hours, int minutes, int seconds, int frames) {
		super(hours, minutes, seconds, frames);
		frameCount += frames;
		frameCount += seconds * 25;
		frameCount += minutes * 60 * 25;
		frameCount += hours * 60 * 60 * 25;
	}

	protected Fps25Timecode(int frameCount) {
		super(frameCount);
		countUnits(frameCount);
	}

	private void countUnits(int numberOfFrames) {
		int totalFrames = numberOfFrames;
		hours = totalFrames / 90000;
		totalFrames -= hours * 90000;
		minutes = totalFrames / 1500;
		totalFrames -= minutes * 1500;
		seconds = totalFrames / 25;
		frames = totalFrames % 25;
	}

	public static Fps25Timecode of(int hours, int minutes, int seconds, int frames) {
		if (! FrameRateType.FPS25.areValidValues(hours, minutes, seconds, frames))
			throw new IllegalArgumentException("Invalid timecode value");
		return new Fps25Timecode(hours, minutes, seconds, frames);
	}

	public static Fps25Timecode of(int frameCount) {
		if (frameCount < 0)
			throw new IllegalArgumentException("Frame count must be greater than zero");
		if (frameCount >= FRAME_COUNT_LIMIT)
			throw new IllegalArgumentException("Frame count is greater than " + FRAME_COUNT_LIMIT);
		return new Fps25Timecode(frameCount);
	}

	@Override
	public Timecode add(Timecode timecode) throws TimecodeException {
		if (! (timecode instanceof Fps25Timecode))
			throw new TimecodeException("Addition operation is only valid between instances of the same Timecode class");

		frameCount += timecode.getFrameCount();
		countUnits(frameCount);

		if (frameCount >= FRAME_COUNT_LIMIT)
			throw new TimecodeException("Result is greater than limit");

		return this;
	}

	@Override
	public Timecode subtract(Timecode timecode) throws TimecodeException {
		if (! (timecode instanceof Fps25Timecode))
			throw new TimecodeException("Subtract operation is only valid between instances of the same Timecode class");

		frameCount -= timecode.getFrameCount();
		if (frameCount > 0)
			countUnits(frameCount);
		else {
			int oldHours = hours;
			countUnits(FRAME_COUNT_LIMIT + frameCount);    // positive number of frames
			hours = oldHours - timecode.getHours();
		}

		if (Math.abs(frameCount) >= FRAME_COUNT_LIMIT)
			throw new TimecodeException("Result value is lesser than " + "limit");

		return this;
	}

	@Override
	public int getFrameCount() {
		return frameCount;
	}

	@Override
	public int getFrameCountLimit() {
		return FRAME_COUNT_LIMIT;
	}
}
