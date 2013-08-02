# Introduction #
Inspired by JDOM, **edidom** is both Java-centric and Java-optimized. It behaves like Java, it uses Java collections, it is completely natural API for current Java developers, and it provides a low-cost entry point for using EDI. 

## How to build ##
You need to install [MAVEN](http://maven.apache.org/), edidom is using maven to manage project build. If you using eclipse, I highly recommend you to install [m2e](http://www.eclipse.org/m2e/).  
  
	mvn package

## Data Structure ##
<pre>
  +-------------------------------------------------------------------+
  |  InterchangeEnvelope (1)                                          |
  |-------------------------------------------------------------------|
  |                                                                   |
  |  +------------------------------------------------------------+   |
  |  |  GroupEnvelope (0..*)                                      |   |
  |  |------------------------------------------------------------|   |
  |  |                                                            |   |
  |  |  +-----------------------------------------------------+   |   |
  |  |  |  Transaction (1..*)                                 |   |   |
  |  |  |-----------------------------------------------------|   |   |
  |  |  | +-------------------------------------------------+ |   |   |
  |  |  | | Segment (1..*)                                  | |   |   |
  |  |  | +-------------------------------------------------+ |   |   |
  |  |  | ...                                                 |   |   |
  |  |  | ...                                                 |   |   |
  |  |  | ..                                                  |   |   |
  |  |  | ..                                                  |   |   |
  |  |  | .                                                   |   |   |
  |  |  | +-------------------------------------------------+ |   |   |
  |  |  | | Segment                                         | |   |   |
  |  |  | +-------------------------------------------------+ |   |   |
  |  |  +-----------------------------------------------------+   |   |
  |  +------------------------------------------------------------+   |
  +-------------------------------------------------------------------+
</pre>

## Getting Started ##
### Read EDI ###
Create a <code>Builder</code> first. For X12, use <code>com.github.edidom.input.X12Builder</code>, for EDIFACT, use <code>com.github.edidom.input.EdifactBuilder</code>

    File ediFile = new File(path);
    Builder builder = new EdifactBuilder();
    Document doc = builder.buildDocument(ediFile);
### Modify Content ###
Get all <code>Segment</code> "N9" and change the value of field number 2

    List<Segment> segments = doc.getSegments("N9);
    for (Segment seg : segments) {
      seg.getField(2).setValue("AA")
    }
### Write EDI ###
Create cooresponding outputter and output as a string

    X12Outputter outputter = new X12Outputter();
    String ediOutput = outputter.outputString(doc);
    
That is!! Good luck!!

## License ##
Apache License, Version 2

http://www.apache.org/licenses/LICENSE-2.0
