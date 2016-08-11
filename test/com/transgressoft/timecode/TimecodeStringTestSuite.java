package com.transgressoft.timecode;

import com.transgressoft.timecode.df30.*;
import com.transgressoft.timecode.fps25.*;
import org.junit.platform.runner.*;
import org.junit.runner.*;

/**
 * Test suite for the test classes
 *
 * @author Octavio Calleya
 * @version 0.1
 */
@RunWith(JUnitPlatform.class)
@SelectClasses({
		AcceptanceTest.class,
		TimecodeStringTests.class,
		Df30TimecodeTest.class,
		Fps25TimecodeTest.class
})
public class TimecodeStringTestSuite {

}