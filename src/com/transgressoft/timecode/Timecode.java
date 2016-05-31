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
 * Interface for Timecodes.
 *
 * @see <a href="https://en.wikipedia.org/wiki/SMPTE_timecode">SMPTE Timecode</a>
 *
 * @author Octavio Calleya
 * @version 0.1
 */
public interface Timecode {

	/**
	 * Sums the given <tt>timecode</tt> value to the value of the actual {@link Timecode}
	 *
	 * @param timecode The timecode to sum
	 *
	 * @return The object itself once the operation has been performed on it
	 *
	 * @throws TimecodeException If the given <tt>Timecode</tt> object is not of
	 * 							 the same type of the actual object, or the result of the
	 * 							 operation resulted in a value greater than the admitted
	 */
	Timecode add(Timecode timecode) throws TimecodeException;

	/**
	 * Substracts the given <tt>timecode</tt> value to the value of the actual {@link Timecode}
	 *
	 * @param timecode The timecode to substract
	 *
	 * @return The object itself once the operation has been performed on it
	 *
	 * @throws TimecodeException If the given <tt>Timecode</tt> object is not of
	 * 							 the same type of the actual object, or the result of the
	 * 							 operation resulted in a value lesser than the admitted
	 */
	Timecode substract(Timecode timecode) throws TimecodeException;

	int getHours();

	int getMinutes();

	int getSeconds();

	int getFrames();

	int getFrameCount();

	/**
	 * Returns a <tt>String</tt> representation of the value of a timecode
	 * expressed as hours:minutes:seconds:frames
	 *
	 * @return The string representation of the value of a timecode
	 */
	String getStringRepresentation();
}
