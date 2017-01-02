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

package com.transgressoft.timecode.fps24;

import com.transgressoft.timecode.*;

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

    private Fps24Timecode(int hours, int minutes, int seconds, int frames) {
        super(hours, minutes, seconds, frames);
        frameCount += frames;
        frameCount += seconds * 24;
        frameCount += minutes * 60 * 24;
        frameCount += hours * 60 * 60 * 24;
    }

    private Fps24Timecode(int frameCount) {
        super(frameCount);
        countUnits(frameCount);
    }

    private void countUnits(int numberOfFrames) {
        int totalFrames = numberOfFrames;
        hours = totalFrames / 86400;
        totalFrames -= hours * 86400;
        minutes = totalFrames / 1440;
        totalFrames -= minutes * 1440;
        seconds = totalFrames / 24;
        frames = totalFrames % 24;
    }

    public static Fps24Timecode of(int hours, int minutes, int seconds, int frames) {
        if (! FrameRateType.FPS24.areValidValues(hours, minutes, seconds, frames))
            throw new IllegalArgumentException("Invalid timecode value");
        return new Fps24Timecode(hours, minutes, seconds, frames);
    }

    public static Fps24Timecode of(int frameCount) {
        if (frameCount < 0)
            throw new IllegalArgumentException("Frame count must be greater than zero");
        if (frameCount >= FRAME_COUNT_LIMIT)
            throw new IllegalArgumentException("Frame count is greater than " + FRAME_COUNT_LIMIT);
        return new Fps24Timecode(frameCount);
    }

    @Override
    public int getFrameCountLimit() {
        return FRAME_COUNT_LIMIT;
    }

    @Override
    public Timecode add(Timecode timecode) throws TimecodeException {
        if (! (timecode instanceof Fps24Timecode))
            throw new TimecodeException("Addition operation is only valid between instances of the same Timecode class");

        frameCount += timecode.getFrameCount();
        countUnits(frameCount);

        if (frameCount >= FRAME_COUNT_LIMIT)
            throw new TimecodeException("Result is greater than limit");

        return this;
    }

    @Override
    public Timecode subtract(Timecode timecode) throws TimecodeException {
        if (! (timecode instanceof Fps24Timecode))
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
}