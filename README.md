## Bugs and Things to add
- [ ] Add option to set the number of rows per block for the plane (SeatingMethod: numberOfRowsPerBlock getter/setter)
- [ ] BUG: Fix seating method to take into account planes which have a middle asle
- [ ] Checking to make sure a plane has at least one asle
- [ ] Checking that the asle goes from back to front with no breaks
- [ ] Option to re-edit your plane dimension if you made a mistake after creating it
- [ ] BUG: Plane dimension loads upside down
- [ ] BUG: Set plane dimension to 15*7 then put asles down 3 and 5 column and it creates the plane wrong

## Weekly Plan (to be completed for...)

#### Week 11
##### Ben
- [ ] Fix, tidy, comment
- [ ] Finish the custom method interface
- [ ] Add textures

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
* Added .dll and .dlib files for SeatingMethod to provide a more complex dynamic library usage for the project

#### (28/11, 11:17) - Ben
* Switched out for the wizard layout style, mentioned earlier. Custom methods aren't currently working but I'll get them done really soon.
* You can set up layouts of planes, and even save them for later use.
* You can now pick which simulation to view.
* Also changed some BoardingController stuff to work with this. It's a bit messy though.
* Results screen added (looks bad with the current time glitch).
* Few bugs here and there (don't run a simulation without defining a plane layout first!)
* For some reason, the Reverse Pyramid layout set up BREAKS on some layouts (see 'mini.pd' and 'bigplane.pd').
* Other than that, I just have to add in the Custom method, clean up the interface and code a little, and add textures. Easy stuff!
