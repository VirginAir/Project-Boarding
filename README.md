Project-Boarding
================
Initial Plan:
-Base language: Java
-Hardcoded methods, e.g. Start from front/back/middle, random, outside-in by column, block boarding, reverse pyramid, rotating zone, http://www.bbc.co.uk/news/science-environment-14717695
-Basic top-down visual simulation
-User set and random passenger counts and boarding times. 
Extras: blockages at certain points on column (moving for lost people), blocking rows, groups of people, wrong seats, timer for next, passengers don’t turn up/late
-multi-threaded passengers, multi-thread boarding tests
-comparison between methods in a table
-User-defined methods (using some external format, XML or something)
-Plugins: DLLs (C++ usage)
-Extra Languages: C++

-Settings:
--# of passengers
--Plane seating dimensions
--Range of random values
--Time between passenger entry
--How many entrances and placement of entrances
--Selector for pre-defined methods
--Editor for user defined boarding methods

Week 2-3:
Basic visualisation, random boardings, randomised AI of passenger, initially hardcoded, initial mockup graphics, timeplan for following weeks
-boarding plan representation as array(in code)
-passenger allocation let in at time intervals, multi-threaded (random time for now)
----for each passenger (class), specified seat number, time to find, found seat
-graphical view




