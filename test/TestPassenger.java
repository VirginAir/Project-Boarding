
import org.junit.Assert;
import org.junit.Test;
import projectboarding.Cell;
import projectboarding.Passenger;


/**
 *
 * @author Matthew
 */
public class TestPassenger {
    
    @Test
    public void testPassenger() {
        Cell cell = new Cell(9, 5, Cell.CellType.AISLE);
        Passenger pas = new Passenger(cell, 0.8);
        
        Assert.assertTrue(pas.getTimePerRow() > 1 && pas.getTimePerRow() < 5);
        Assert.assertSame(pas.getSeat(), cell);
        Assert.assertTrue(pas.getBaggageTime() == 0 || (pas.getBaggageTime() > 3 && pas.getBaggageTime() < 20));
        Assert.assertSame(0, pas.getSeatInterferenceTime());
        
        pas.setCurrentCell(cell);
        Assert.assertSame(cell, pas.getCurrentCell());
        Assert.assertSame(false, pas.isHasTakenSeat());
        
        pas.setAisle(1);
        Assert.assertSame(1, pas.getAisle());
        
        pas.setHasTakenSeat(true);
        Assert.assertSame(true, pas.isHasTakenSeat());
        
        pas.setSeatInterferenceTime(20);
        Assert.assertSame(20, pas.getSeatInterferenceTime());
        
        int prevTPR = pas.getTimePerRow();
        pas.decreaseTimePerRow();
        Assert.assertSame(prevTPR - 1, pas.getTimePerRow());
        
        int prevBT = pas.getBaggageTime();
        pas.decreaseBaggageTime();
        Assert.assertSame(prevBT - 1, pas.getBaggageTime());
        
        pas.decreaseSeatInterferenceTime();
        Assert.assertSame(19, pas.getSeatInterferenceTime());
        
        pas.resetTimePerRow();
        Assert.assertSame(prevTPR, pas.getTimePerRow());
        Assert.assertSame(0, pas.getInterferingPassengers());
        
        pas.addInteferingPassengers();
        Assert.assertSame(1, pas.getInterferingPassengers());
    }
    
}
