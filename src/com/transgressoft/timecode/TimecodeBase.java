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

import java.util.*;

import static com.google.common.base.Preconditions.*;
import static com.transgressoft.timecode.TimecodeException.ErrorCase.*;

/**
 * Base class implementation of {@link Timecode}.
 *
 * @author Octavio Calleya
 * @version 1.0
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
        checkArgument(frameCount >= 0, FRAME_COUNT_LESS_0.getErrorMessage());
        String errorMessage = FRAME_COUNT_GREATER_LIMIT.getErrorMessage() + " " + getFrameCountLimit();
        checkArgument(frameCount < getFrameCountLimit(), errorMessage);
        this.frameCount = frameCount;
    }

    protected void countFrames(int hours, int minutes, int seconds, int frames) {
        frameCount += frames;
        frameCount += seconds * getFrameMax();
        frameCount += minutes * 60 * getFrameMax();
        frameCount += hours * 60 * 60 * getFrameMax();
    }

    protected void countUnits(int numberOfFrames) {
        int totalFrames = numberOfFrames;
        hours = totalFrames / (60 * 60 * getFrameMax());
        totalFrames -= hours * (60 * 60 * getFrameMax());
        minutes = totalFrames / (60 * getFrameMax());
        totalFrames -= minutes * (60 * getFrameMax());
        seconds = totalFrames / getFrameMax();
        frames = totalFrames % getFrameMax();
    }

    protected void addition(Timecode timecode) throws TimecodeException {
        frameCount += timecode.getFrameCount();
        countUnits(frameCount);

        if (frameCount >= getFrameCountLimit())
            throw new TimecodeException(RESULT_GREATER_LIMIT);
    }

    protected void subtraction(Timecode timecode) throws TimecodeException {
        frameCount -= timecode.getFrameCount();
        if (frameCount > 0)
            countUnits(frameCount);
        else {
            int oldHours = hours;
            countUnits(getFrameCountLimit() + frameCount);    // positive number of frames
            hours = oldHours - timecode.getHours();
        }

        if (Math.abs(frameCount) >= getFrameCountLimit())
            throw new TimecodeException(RESULT_LESSER_LIMIT);
    }

    protected abstract int getFrameMax();

    public abstract int getFrameCountLimit();

    @Override
    public int hashCode() {
        return Objects.hash(frames, seconds, minutes, hours, frameCount);
    }

    @Override
    public boolean equals(Object obj) {
        boolean result;
        if (obj instanceof TimecodeBase && ((TimecodeBase) obj).getFrames() == frames && ((TimecodeBase) obj)
                .getSeconds() == seconds && ((TimecodeBase) obj).getMinutes() == minutes && ((TimecodeBase) obj)
                .getHours() == hours && ((TimecodeBase) obj).getFrameCount() == frameCount) {
            result = true;
        }
        else {
            result = false;
        }
        return result;
    }

    @Override
    public int getFrames() {
        return frames;
    }

    @Override
    public int getSeconds() {
        return seconds;
    }

    @Override
    public int getMinutes() {
        return minutes;
    }

    @Override
    public int getHours() {
        return hours;
    }

    @Override
    public int getFrameCount() {
        return frameCount;
    }

    @Override
    public String toString() {
        return "Timecode[" + getStringRepresentation() + "/" + frameCount + "]";
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
}
