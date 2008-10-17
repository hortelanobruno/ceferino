package com.singularsys.jeptests;



import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.singularsys.jep.*;
import com.singularsys.jep.bigdecimal.BigDecComponents;

/**
 * Tests the print routines of the Jep class
 * @author singularsys
 */
public class JEPPrintTest {
	
	public JEPPrintTest() {
	}
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void checkOutput() throws Exception {
		checkSingleParser(new Jep());
	}
	
	@Test
	public void checkBigDec() throws Exception {
		checkSingleParser(new Jep(new BigDecComponents()));		
	}

	private void checkSingleParser(Jep jep) throws Exception {
		// this string requires all the brackets since the inner operators are
		// lower precedence than the outer ones
		String in_exp = "1.0^(1.0%(1.0/(1.0*(1.0+(1.0<=(1.0<(1.0==(1.0&&(1.0||1.0)))))))))";
		jep.parse(in_exp);
		String out_exp = jep.rootNodeToString();
		System.out.println(out_exp);
		Assert.assertEquals(out_exp, in_exp);
	}
}
