// File 	: LogicalRightShiftTest.java
// Created 	: 06.03.2020
// Author 	: Ahmed Khalil

package junit;

import junit.framework.TestCase;
import pass.LogicalRightShift;

public class LogicalRightShiftTest extends TestCase {
	private LogicalRightShift logicalrightshift;
	
	protected void setUp() throws Exception {
		super.setUp();
		logicalrightshift = new LogicalRightShift();
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	public void testRightShift() {
		// Values checked in python :
		// def logical_rshift(value, n): return (value % 0x100000000) >> n
		this.assertEquals(logicalrightshift.logicalrightshift(-8,20),4095);
		this.assertEquals(logicalrightshift.logicalrightshift(-42,16),65535);
		this.assertEquals(logicalrightshift.logicalrightshift(-128,24),255);
	}
}