# TimecodeString
[![Build Status](https://travis-ci.org/octaviospain/TimecodeString.svg?branch=master)](https://travis-ci.org/octaviospain/TimecodeString)
[![codecov](https://codecov.io/gh/octaviospain/TimecodeString/branch/master/graph/badge.svg)](https://codecov.io/gh/octaviospain/TimecodeString)
[![license](https://img.shields.io/badge/license-apache%202-brightgreen.svg)](https://github.com/octaviospain/TimecodeString/blob/master/LICENSE.txt)

Library that converts between frame counts and a [timecode](https://en.wikipedia.org/wiki/SMPTE_timecode) string representation.
The library supports multiple frame rates including such as 24fps, 25fps, 30fps and DF30 (29.97fps). It also provides functions to
perform addition and subtraction operation between timecodes.

## Usage
You can use the TimecodeString library by two ways:

1. Instantiating the `TimecodeString.java` class in your java project

```java
String frameRate = "25fps";
String value = "01:20:59:24"
TimecodeString timecode = TimecodeString.of(frameRate, value);
String frameCount = timecode.getFrameCountString();             // = 121499
String fullTimecodeString = timecode.getFullTimecodeString();   // = 01:20:59:24

String frameRate = "25fps";
String value = "121499"
TimecodeString timecode = TimecodeString.of(frameRate, value);
String fullTimecodeString = timecode.getFullTimecodeString();   // = "01:20:59:24"
String frameCount = timecode.getFrameCountString();             // = 121499
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

Example with frame rate argument:
```
java -jar TimecodeString.jar 25fps 252
```
would print
```
00:00:00:05
```

Example With an input file formatted with the following format:

```
25fps 00:00:00:05
25fps 252
30DF 00:00:00;09
30DF 571
...
```

Example
```
java -jar TimecodeString.jar input.txt
```
would print
```
Timecode string conversion successfully dumped to /Users/octavio/Desktop/output.txt
```
and the output.txt file:
```
5
00:00:10:02
9
00:00:19;01
30
00:00:01:00
```

## To do

* Implement other Timecodes