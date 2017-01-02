# TimecodeString
[![Build Status](https://travis-ci.org/octaviospain/TimecodeString.svg?branch=master)](https://travis-ci.org/octaviospain/TimecodeString)
[![codecov](https://codecov.io/gh/octaviospain/TimecodeString/branch/master/graph/badge.svg)](https://codecov.io/gh/octaviospain/TimecodeString)
[![license](https://img.shields.io/badge/license-apache%202-brightgreen.svg)](https://github.com/octaviospain/TimecodeString/blob/master/LICENSE.txt)

Library that converts between frame counts and a [timecode](https://en.wikipedia.org/wiki/SMPTE_timecode) string representation.
The library supports multiple frame rates including 25fps and DF30 (29.97fps).

# Usage
You can use the TimecodeString library by two ways:

1. Instantiating the `TimecodeString.java` class in your java project

```java
String frameRate = "25fps";
String value = "01:20:59:24"
TimecodeString timecode = TimecodeString.of(frameRate, value);
String oppositeRepresentation = timecode.getOppositeRepresentation(); // = 121499

String frameRate = "25fps";
String value = "121499"
TimecodeString timecode = TimecodeString.of(frameRate, value);
String oppositeRepresentation = timecode.getOppositeRepresentation(); // = "01:20:59:24"
```

2. Using it as a command line program with the packaged `.jar`
passing arguments to it (thanks to [docopt](https://github.com/docopt/docopt.java)) with the following usage:

```
Usage:
    java -jar TimecodeString.jar <frame_rate> <value>
    java -jar TimecodeString.jar <input_file> [-o <output_file>]

Options:
   -o = <output_file> specify output file [default: output.txt]
```

### TO DO

* Implement 30Fps and other Timecodes
