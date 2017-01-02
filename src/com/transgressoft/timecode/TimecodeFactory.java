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

import com.transgressoft.timecode.df30.*;
import com.transgressoft.timecode.fps24.*;
import com.transgressoft.timecode.fps25.*;
import com.transgressoft.timecode.fps30.*;

/**
 * Abstract factory class of creation of {@link Timecode} objects.
 *
 * @author Octavio Calleya
 * @version 1.0
 */
public abstract class TimecodeFactory {

	private static Fps25TimecodeFactory fps25TimecodeFactory = new Fps25TimecodeFactory();
	private static Df30TimecodeFactory df30TimecodeFactory = new Df30TimecodeFactory();
	private static Fps24TimecodeFactory fps24TimecodeFactory = new Fps24TimecodeFactory();
	private static Fps30TimecodeFactory fps30TimecodeFactory = new Fps30TimecodeFactory();

	public static Timecode createTimeCode(FrameRateType type, int hours, int minutes, int seconds, int frames) {
		TimecodeFactory timecodeFactory = chooseFactory(type);
		return timecodeFactory.createTimeCode(hours, minutes, seconds, frames);
	}

	private static TimecodeFactory chooseFactory(FrameRateType type) {
		TimecodeFactory timecodeFactory = null;
		switch (type) {
			case FPS24:
				timecodeFactory = fps24TimecodeFactory;
				break;
			case FPS25:
				timecodeFactory = fps25TimecodeFactory;
				break;
			case FPS30:
				timecodeFactory = fps30TimecodeFactory;
				break;
			case DF30:
				timecodeFactory = df30TimecodeFactory;
				break;
		}
		return timecodeFactory;
	}

	public abstract Timecode createTimeCode(int hours, int minutes, int seconds, int frames);

	public static Timecode createTimeCode(FrameRateType type, int numberOfFrames) {
		TimecodeFactory timecodeFactory = chooseFactory(type);
		return timecodeFactory.createTimeCode(numberOfFrames);
	}

	public abstract Timecode createTimeCode(int frameCount);
}
