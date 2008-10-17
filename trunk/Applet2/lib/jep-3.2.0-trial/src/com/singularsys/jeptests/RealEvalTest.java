package com.singularsys.jeptests;

import junit.framework.Assert;

import com.singularsys.jep.reals.RealEvaluator;

public class RealEvalTest extends CPTest {

	public RealEvalTest(String name) {
		super(name);
	}

	@Override
	public void setUp() {
		super.setUp();
		jep.setComponent(new RealEvaluator());
		this.myFalse = new Double(0.0);
		this.myTrue = new Double(1.0);
	}

	@Override
	protected void valueTest(String expr, Object expected) throws Exception {
		if(expected instanceof Integer)
			super.valueTest(expr, ((Integer)expected).doubleValue());
		else
			super.valueTest(expr, expected);
	}

	public void testEvaluate() throws Exception {
		// test a very basic expression
		Object result = jep.evaluate(jep.parse("2.1345"));
		Assert.assertTrue(result instanceof Double);
		Assert.assertTrue(result.equals(2.1345));
	}

	@Override
	public void testComplex() throws Exception {
	}

	@Override
	public void testChangeVariable() {
	}

	@Override
	public void testListAccess() throws Exception {
	}

	@Override
	public void testMultiDimArray() throws Exception {
	}
	
	@Override
	public void testMultiDimArrayAccess() throws Exception {
	}

	@Override
	public void testStrings() throws Exception {
	}

	@Override
	public void testCPStrings() throws Exception {
	}

}
