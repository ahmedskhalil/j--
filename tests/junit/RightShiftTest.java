// File 	: RightShiftTest.java
// Created 	: 03.03.2020
// Author 	: Ahmed Khalil

package junit;

import junit.framework.TestCase;
import pass.RightShift;

public class RightShiftTest extends TestCase {
	private RightShift rightshift;
	
	protected void setUp() throws Exception {
		super.setUp();
		rightshift = new RightShift();
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testRightShift() {
		this.assertEquals(rightshift.rightshift(0,42),0);
		this.assertEquals(rightshift.rightshift(42,1),21);
		this.assertEquals(rightshift.rightshift(128,4),32);
	}
}