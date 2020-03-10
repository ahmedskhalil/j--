// File 	: LeftShiftTest.java
// Created 	: 06.03.2020
// Author 	: Ahmed Khalil

package junit;

import junit.framework.TestCase;
import pass.LeftShift;

public class LeftShiftTest extends TestCase {
	private LeftShift leftshift;
	
	protected void setUp() throws Exception {
		super.setUp();
		leftshift = new LeftShift();
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	public void testLeftShift() {
		this.assertEquals(leftshift.leftshift(0,42),0);
		this.assertEquals(leftshift.leftshift(42,1),84);
		this.assertEquals(leftshift.leftshift(128,4),2048); //2048
	}
}