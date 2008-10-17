package com.singularsys.jeptests;

import com.singularsys.jep.standard.Complex;
import junit.framework.Assert;
import junit.framework.TestCase;

public class ComplexTest extends TestCase {

	public ComplexTest(String name) {
		super(name);
	}

	/**
	 * Tests the power method
	 */
	public void testPower() {
		Complex one = new Complex(1, 0);
		Complex negOne = new Complex(-1, 0);
		Complex i = new Complex(0, 1);
		Complex two = new Complex(2, 0);
		
		// power
		Assert.assertTrue((one.power(one)).equals(one,0));
		Assert.assertTrue((one.power(-1)).equals(one,0));
		Assert.assertTrue((one.power(negOne)).equals(one,0));
		Assert.assertTrue((negOne.power(two)).equals(one,0));
		Assert.assertTrue((i.power(two)).equals(negOne, 0));
		//Assert.assertTrue((negEight.power(1.0/3)).equals(negTwo,0));
	}
	
	/**
	 * Tests the mul method
	 */
	public void testMul() {
		Complex one = new Complex(1, 0);
		Complex negOne = new Complex(-1, 0);
		Complex i = new Complex(0, 1);
		
		// multiplication
		Assert.assertTrue((one.mul(one)).equals(one,0));
		Assert.assertTrue((one.mul(negOne)).equals(negOne,0));
		Assert.assertTrue((negOne.mul(one)).equals(negOne,0));
		Assert.assertTrue((i.mul(i)).equals(negOne,0));
	}
}
