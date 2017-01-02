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

package com.transgressoft.timecode.fps24;

import com.transgressoft.timecode.*;

import static com.transgressoft.timecode.TimecodeException.ErrorCase.*;

/**
 * Class that represents a {@link Timecode} implementation of
 * a 24 fps video timecode.
 *
 * @author Octavio Calleya
 * @version 0.1
 * @see <a href="https://en.wikipedia.org/wiki/SMPTE_timecode">SMPTE Timecode</a>
 */
public class Fps24Timecode extends TimecodeBase {

    /**
     * Maximum amount of frames accepted, since the maximum value
     * of the Timecode can be 23:59:59:23:
     *
     * <p>24 * 60 * 60 * 24 = 2 073 600</p>
     */
    private static final int FRAME_COUNT_LIMIT = 2073600;
    private static final int FRAME_MAX = 24;

    private Fps24Timecode(int hours, int minutes, int seconds, int frames) {
        super(hours, minutes, seconds, frames);
        countFrames(hours, minutes, seconds, frames);
    }

    private Fps24Timecode(int frameCount) {
        super(frameCount);
        countUnits(frameCount);
    }

    public static Fps24Timecode of(int hours, int minutes, int seconds, int frames) {
        if (! FrameRateType.FPS24.areValidValues(hours, minutes, seconds, frames))
            throw new IllegalArgumentException(INVALID_TIMECODE.getErrorMessage());
        return new Fps24Timecode(hours, minutes, seconds, frames);
    }

    public static Fps24Timecode of(int frameCount) {
        if (frameCount < 0)
            throw new IllegalArgumentException(FRAME_COUNT_LESS_0.getErrorMessage());
        if (frameCount >= FRAME_COUNT_LIMIT)
            throw new IllegalArgumentException(FRAME_COUNT_GREATER_LIMIT.getErrorMessage() + " " + FRAME_COUNT_LIMIT);
        return new Fps24Timecode(frameCount);
    }

    @Override
    public Timecode add(Timecode timecode) throws TimecodeException {
        if (! (timecode instanceof Fps24Timecode))
            throw new TimecodeException(INVALID_ADDITION);
        addition(timecode);
        return this;
    }

    @Override
    public Timecode subtract(Timecode timecode) throws TimecodeException {
        if (! (timecode instanceof Fps24Timecode))
            throw new TimecodeException(INVALID_SUBTRACTION);
        subtraction(timecode);
        return this;
    }

    @Override
    public int getFrameCountLimit() {
        return FRAME_COUNT_LIMIT;
    }

    @Override
    protected int getFrameMax() {
        return FRAME_MAX;
    }
}
