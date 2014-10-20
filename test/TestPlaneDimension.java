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
import projectboarding.Cell;
import projectboarding.PlaneDimension;

/**
 *
 * @author jxb11hhu
 */
public class TestPlaneDimension {
    
    private final PlaneDimension planeDimension;
    private Cell[][] planeSeatingLayout;
    
    public TestPlaneDimension() {
        // Create the plane
        this.planeDimension = new PlaneDimension(this.planeSeatingLayout);
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
    
    @Test
    public void testCreatePlaneDimension() {
        Assert.assertArrayEquals(this.planeDimension.getAllSeats(), this.planeSeatingLayout);
    }

}
