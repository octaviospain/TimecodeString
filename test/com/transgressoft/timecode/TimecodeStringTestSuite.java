package com.transgressoft.timecode;

import com.transgressoft.timecode.df30.*;
import com.transgressoft.timecode.fps24.*;
import com.transgressoft.timecode.fps25.*;
import org.junit.runner.*;
import org.junit.runners.*;
import org.junit.runners.Suite.*;

/**
 * Test suite for the test classes
 *
 * @author Octavio Calleya
 * @version 0.1
 */
@RunWith(Suite.class)
@SuiteClasses ({
			AcceptanceTest.class,
			TimecodeStringTests.class,
			Df30TimecodeTest.class,
			Fps25TimecodeTest.class,
			Fps24Timecode.class
			})
public class TimecodeStringTestSuite {

}
