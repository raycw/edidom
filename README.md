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
  +-------------------------------------------------------------------+</pre>

## License ##
Apache License, Version 2
http://www.apache.org/licenses/LICENSE-2.0