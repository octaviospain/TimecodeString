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

package com.transgressoft.timecode.fps30;

import com.transgressoft.timecode.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.transgressoft.timecode.TimecodeException.ErrorCase.*;

/**
 * Class that represents a {@link Timecode} implementation of
 * a 30 fps video timecode.
 *
 * @author Octavio Calleya
 * @version 1.0
 * @see <a href="https://en.wikipedia.org/wiki/SMPTE_timecode">SMPTE Timecode</a>
 */
public class Fps30Timecode extends TimecodeBase {

    /**
     * Maximum amount of frames accepted, since the maximum value
     * of the Timecode can be 23:59:59:29:
     *
     * <p>24 * 60 * 60 * 30 = 2 592 000</p>
     */
    private static final int FRAME_COUNT_LIMIT = 2592000;
    private static final int FRAME_MAX = 30;

    private Fps30Timecode(int hours, int minutes, int seconds, int frames) {
        super(hours, minutes, seconds, frames);
        countFrames(hours, minutes, seconds, frames);
    }

    private Fps30Timecode(int frameCount) {
        super(frameCount);
        countUnits(frameCount);
    }

    public static Fps30Timecode of(int hours, int minutes, int seconds, int frames) {
        boolean validValues = FrameRateType.FPS30.areValidValues(hours, minutes, seconds, frames);
        checkArgument(validValues, INVALID_TIMECODE.getErrorMessage(), hours, minutes, seconds, frames);
        return new Fps30Timecode(hours, minutes, seconds, frames);
    }

    public static Fps30Timecode of(int frameCount) {
        return new Fps30Timecode(frameCount);
    }

    @Override
    public Timecode add(Timecode timecode) throws TimecodeException {
        if (! (timecode instanceof Fps30Timecode))
            throw new TimecodeException(INVALID_ADDITION);
        addition(timecode);
        return this;
    }

    @Override
    public Timecode subtract(Timecode timecode) throws TimecodeException {
        if (! (timecode instanceof Fps30Timecode))
            throw new TimecodeException(INVALID_SUBTRACTION);
        subtraction(timecode);
        return this;
    }

    @Override
    protected int getFrameMax() {
        return FRAME_MAX;
    }

    @Override
    public int getFrameCountLimit() {
        return FRAME_COUNT_LIMIT;
    }
}
