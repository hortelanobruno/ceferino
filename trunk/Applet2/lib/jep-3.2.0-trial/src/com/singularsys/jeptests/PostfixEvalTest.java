package com.singularsys.jeptests;

import com.singularsys.jep.Jep;
import com.singularsys.jep.configurableparser.StandardConfigurableParser;
import com.singularsys.jep.walkers.PostfixEvaluator;

public class PostfixEvalTest extends JepTest {

    public PostfixEvalTest(String name) {
	super(name);
    }

	@Override
	public void setUp() {
		this.jep = new Jep();
		jep.setComponent(new StandardConfigurableParser());
		jep.setComponent(new PostfixEvaluator());
		jep.setAllowAssignment(true);
		jep.setAllowUndeclared(true);
		jep.setImplicitMul(true);
	}

	@Override
	public void testSetAllowUndeclared() throws Exception {
	}

	
}
