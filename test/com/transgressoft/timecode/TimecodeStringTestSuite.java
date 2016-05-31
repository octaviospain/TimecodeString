package com.transgressoft.timecode;

import org.junit.runner.*;
import org.junit.runners.*;
import com.transgressoft.timecode.df30.*;
import com.transgressoft.timecode.fps25.*;

/**
 * Test suite for the test classes
 *
 * @author Octavio Calleya
 * @version 0.1
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
		AcceptanceTest.class,
		TimecodeStringTests.class,
		Df30TimecodeTest.class,
		Fps25TimecodeTest.class
})
public class TimecodeStringTestSuite {

}