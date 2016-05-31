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
 * Exception class for this {@link TimecodeString} library.
 *
 * @author Octavio Calleya
 * @version 0.1
 */
public class TimecodeException extends Exception {

	public TimecodeException() {
		super();
	}

	public TimecodeException(String message) {
		super(message);
	}

	public TimecodeException(Throwable cause) {
		super(cause);
	}

	public TimecodeException(String message, Throwable cause) {
		super(message, cause);
	}
}
