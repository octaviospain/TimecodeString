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

package com.transgressoft.timecode.fps25;

import com.transgressoft.timecode.*;

/**
 * Factory class that creates {@link Fps25Timecode} objects.
 *
 * @author Octavio Calleya
 * @version 0.1
 */
public class Fps25TimecodeFactory extends TimecodeFactory {

	@Override
	public Timecode createTimeCode(int hours, int minutes, int seconds, int frames) {
		return Fps25Timecode.of(hours, minutes, seconds, frames);
	}

	@Override
	public Timecode createTimeCode(int frameCount) {
		return Fps25Timecode.of(frameCount);
	}
}
