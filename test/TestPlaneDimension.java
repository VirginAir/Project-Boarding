/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import projectboarding.Cell;
import projectboarding.PlaneDimension;

/**
 *
 * @author jxb11hhu
 */
public class TestPlaneDimension {
    
    public TestPlaneDimension() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testCreatePlaneDimension() {
        Cell[][] plane = new Cell[][]{};
       
        PlaneDimension planeDimension = new PlaneDimension(plane);
        Assert.assertArrayEquals(planeDimension.getAllSeats(), plane);
    }

}
