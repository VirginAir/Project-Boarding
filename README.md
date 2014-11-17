## Weekly Plan (to be completed for...)
#### Week 8
##### Ben
- [ ] Add textures
- [ ] Add smoother animation


#### Week 7
###### Matthew
- [X] Look into using .dll's for the seating method maths
- [ ] Look at multithreading the boarding controllers

###### Ben
- [X] Add basic user control functions


## Changelog
#### (18/10, 17:00) - Matthew
* The plane dimension class has had a complete change. You can now create any size plane required, including any position of the seats and number of columns per row. The piority seats can also be spread inbetween the normal seats as nessessary.
* It is initialized from a Cell[][] array containing each of the rows of the plane and in each row theCell[] array contains all of the seats (cells), including aisles or non-seats.
* This array can EASILY be created programmatically and siginificantly eases the process of working out the seating order.

#### (19/10, 00:16) - Matt
* Finished logic for passengers to handle how they enter plane
* Once on plane, logic written to determine getting to the seat. This handles people putting up bags, in the way in the aisle, and people in the way of seats too.

#### (20/10, 17:36) - Ben
* Finally pushed basic visualisation
* Green squares represent passenger placement, Red are empty seats
* Need to make it so the sizing is dynamic and adjusts based on seating dimensions (currently it just adds a new row which can go off screen...)
* Also added libraries for Mac support but have yet to test these.

#### (22/10, 13:30) - Matthew
* Completed the following seating methods: back-to-front, block-boarding, outside-in, random and rotating-zone.

#### (05/11, 11:00) - Matthew
* Completed all defualt seating methods and added custom seating method support

#### (17/11, 01:54) - Ben
* Basic interaction present
* Simulation can be reset
* IMPORTANT: Need some way in BoardingController to be able to elegantly Start, Stop and Reset the simulation. I attempted to put in some quick methods to do it but since I don't know much about the class, they don't work 100%. Hit 'Run' about 10 times and you'll see the simulation slows right down. I don't know why. Seems like the times between passengers being let on, increase, or something. It's not graphical, because the 'results' are output as the graphics end, so the graphics are keeping up.

#### (17/11, 13:00) - Matthew
* Added .dll and .dlib files for PlaneDimension to provide simple dynamic library usage for the project

## Previous Weekly Plans

#### Week 6
###### Matthew
- [x] Complete the programmed seating methods.

###### Matt
- [x] Wait for Ben to finish visualisation to work out bugs in timings.

##### Ben
- [X] Add dynamic sizing for screen elements
- [X] Fix up dirty code

#### Week 5
- [x] **Working example of the project**

###### Matthew
- [x] Change the plane dementions class to allow any plane with any dimentions to be inputted into the system.

###### Matt
- [X] Complete the passenger class with simple maths to work out the timings

###### Ben
- [X] Finish the simple visulisation of the simulation
