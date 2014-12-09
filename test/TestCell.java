import org.junit.Assert;
import org.junit.Test;
import projectboarding.Cell;
import projectboarding.Cell.CellType;

/**
 *
 * @author Matthew
 */
public class TestCell {
    
    @Test
    public void testCell() {
        Cell cell = new Cell(9, 5, CellType.AISLE);
        
        Assert.assertSame(9, cell.getCellRow());
        Assert.assertSame(5, cell.getCellColumn());
        Assert.assertSame(CellType.AISLE, cell.getCellType());
        Assert.assertSame(false, cell.getHasPassenger());
        
        cell.setHasPassenger(true);
        Assert.assertSame(true, cell.getHasPassenger());
        
        cell.setCellType(CellType.SEAT);
        Assert.assertSame(CellType.SEAT, cell.getCellType());
    }
    
}
