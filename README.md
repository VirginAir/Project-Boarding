## Weekly Plan (to be completed for...)
#### Week 6
###### Matthew
- [ ] Complete the programmed seating methods.

#### Week 5
- [ ] **Working example of the project**

###### Matthew
- [x] Change the plane dementions class to allow any plane with any dimentions to be inputted into the system.

###### Matt
- [X] Complete the passenger class with simple maths to work out the timings
- [ ] Wait for Ben to finish visualisation to work out bugs in timings.

###### Ben
- [ ] Finish the simple visulisation of the simulation

## Changelog
#### (18/08, 17:00) - Matthew
* The plane dimension class has had a complete change. You can now create any size plane required, including any position of the seats and number of columns per row. The piority seats can also be spread inbetween the normal seats as nessessary.
* It is initialized from a Cell[][] array containing each of the rows of the plane and in each row theCell[] array contains all of the seats (cells), including aisles or non-seats.
* This array can EASILY be created programmatically and siginificantly eases the process of working out the seating order.

#### (19/10, 00:16) - Matt
*Finished logic for passengers to hendle how they enter plane
*Once on plane, logic written to determine getting to the seat. This handles people putting up bags, in the way in the aisle, and people in the way of seats too.
