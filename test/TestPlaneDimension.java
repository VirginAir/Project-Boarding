import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;
import projectboarding.Cell;
import projectboarding.PlaneDimension;

/**
 *
 * @author jxb11hhu
 */
public class TestPlaneDimension {
    
    private final PlaneDimension planeDimension;
    private final Cell[][] planeSeatingLayout;
    
    private final Cell[] normalRow;
    private final Cell[] priorityRow;
    
    public TestPlaneDimension() {
        this.normalRow = new Cell[]{new Cell(0,0,Cell.CellType.SEAT),
            new Cell(0,1,Cell.CellType.SEAT), 
            new Cell(0,2,Cell.CellType.AISLE),
            new Cell(0,3,Cell.CellType.SEAT),
            new Cell(0,4,Cell.CellType.SEAT), 
            new Cell(0,5,Cell.CellType.SEAT), 
            new Cell(0,6,Cell.CellType.SEAT),
            new Cell(0,7,Cell.CellType.AISLE),
            new Cell(0,8,Cell.CellType.SEAT),
            new Cell(0,9,Cell.CellType.SEAT)};
        this.priorityRow = new Cell[]{new Cell(1,0,Cell.CellType.NONE),
            new Cell(1,1,Cell.CellType.PRIORITY_SEAT), 
            new Cell(1,2,Cell.CellType.AISLE),
            new Cell(1,3,Cell.CellType.PRIORITY_SEAT),
            new Cell(1,4,Cell.CellType.PRIORITY_SEAT), 
            new Cell(1,5,Cell.CellType.PRIORITY_SEAT), 
            new Cell(1,6,Cell.CellType.PRIORITY_SEAT),
            new Cell(1,7,Cell.CellType.AISLE),
            new Cell(1,8,Cell.CellType.PRIORITY_SEAT),
            new Cell(1,9,Cell.CellType.NONE)};
        
        this.planeSeatingLayout = new Cell[][]{this.normalRow, this.priorityRow};
        
        // Create the plane
        this.planeDimension = new PlaneDimension(this.planeSeatingLayout);
    }
    
    @Test
    public void testEasyMethods() {
        Assert.assertArrayEquals(this.planeDimension.getAllSeats(), this.planeSeatingLayout);
        Assert.assertSame(1, this.planeDimension.getNumberOfPriorityRows());
        Assert.assertSame(1, this.planeDimension.getNumberOfNormalRows());
        Assert.assertSame(2, this.planeDimension.totalNumberOfRows());
        Assert.assertSame(0, this.planeDimension.getNormalRowNumbers().get(0));
        Assert.assertSame(10, this.planeDimension.getNumberOfColumns());
        Assert.assertSame(2, this.planeDimension.getNumberOfAisles());
        Assert.assertSame(6, this.planeDimension.getNumberOfPrioritySeats());
        Assert.assertSame(8, this.planeDimension.getNumberOfNormalSeats());
        Assert.assertSame(14, this.planeDimension.totalNumberOfSeats());
    }
    
    @Test
    public void testGetPrioritySeats() {
        Cell[][] priSeats = this.planeDimension.getPrioritySeats();
        
        Assert.assertSame(priSeats[0].length, 6);
    }
    
    @Test
    public void testGetNormalSeats() {
        Cell[][] norSeats = this.planeDimension.getNormalSeats();
        
        Assert.assertSame(norSeats[0].length, 8);
    }
    
    @Test
    public void testGetAileColumnNumbers() {
        ArrayList<Integer> col = this.planeDimension.getAisleColumnNumbers();
        
        Assert.assertSame(2, col.get(0));
        Assert.assertSame(7, col.get(1));
    }
    
    @Test
    public void testResetHasPassenger() {
        this.planeDimension.resetHasPassengers();
        
        Cell[][] seats = this.planeDimension.getAllSeats();
        
        for (Cell[] s: seats){
            for (Cell c: s) {
                Assert.assertSame(false, c.getHasPassenger());
            }
        }
    }

}
