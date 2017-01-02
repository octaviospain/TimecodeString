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

/**
 * Exception class for this {@link TimecodeString} library.
 *
 * @author Octavio Calleya
 * @version 1.0
 */
public class TimecodeException extends Exception {

	public TimecodeException() {
		super();
	}

	public TimecodeException(ErrorCase errorCase) {
		super(errorCase.getErrorMessage());
	}

	public TimecodeException(Throwable cause) {
		super(cause);
	}

	public TimecodeException(ErrorCase errorCase, Throwable cause) {
		super(errorCase.getErrorMessage(), cause);
	}

	public enum ErrorCase {
		INVALID_TIMECODE("Invalid timecode value"),
		INVALID_FRAME_RATE("Invalid frame rate format"),
		INVALID_INPUT_FORMAT("Invalid input format. Should be <frameCount> or hh:mm:ss:ff"),
		FRAME_COUNT_LESS_0("Frame count must be greater than zero"),
		FRAME_COUNT_GREATER_LIMIT("Frame count is greater than limit"),
		INVALID_ADDITION("Addition operation is only valid between instances of the same Timecode class"),
		INVALID_SUBTRACTION("Subtract operation is only valid between instances of the same Timecode class"),
		RESULT_GREATER_LIMIT("Result is greater than limit"),
		RESULT_LESSER_LIMIT("Result value is lesser than limit");

		private String errorMessage;

		ErrorCase(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public String getErrorMessage() {
			return errorMessage;
		}
	}
}
