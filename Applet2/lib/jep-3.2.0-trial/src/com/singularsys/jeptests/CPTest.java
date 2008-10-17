package com.singularsys.jeptests;

import com.singularsys.jep.Jep;
import com.singularsys.jep.Operator;
import com.singularsys.jep.OperatorTable;
import com.singularsys.jep.ParseException;
import com.singularsys.jep.configurableparser.ConfigurableParser;
import com.singularsys.jep.configurableparser.StandardConfigurableParser;
import com.singularsys.jep.parser.Node;

/**
 * Tests for the configurable parser.
 */
public class CPTest extends JepTest {

	public CPTest(String name) {
		super(name);
	}

	@Override
	public void setUp() {
		this.jep = new Jep();
		jep.setComponent(new StandardConfigurableParser());
	}

	/**
	 * Tests the addOperator method
	 * 
	 * @throws ParseException
	 */
	public void testAddOp() throws ParseException {
		OperatorTable ot = jep.getOperatorTable();
		Operator preinc  = new Operator("preinc",  "++", null, Operator.PREFIX + Operator.UNARY);
		Operator postinc = new Operator("postinc", "++", null, Operator.SUFFIX + Operator.UNARY);
		Operator predec  = new Operator("predec",  "--", null, Operator.PREFIX + Operator.UNARY);
		Operator postdec = new Operator("postdec", "--", null, Operator.SUFFIX + Operator.UNARY);
		Operator bitcomp = new Operator("bitcomp", "~",  null, Operator.PREFIX + Operator.UNARY);
		Operator lshift  = new Operator("<<",  null, Operator.BINARY + Operator.LEFT);
		Operator rshift  = new Operator(">>",  null, Operator.BINARY + Operator.LEFT);
		Operator rshift0 = new Operator(">>>", null, Operator.BINARY + Operator.LEFT);
		Operator bitand  = new Operator("&", null, Operator.BINARY + Operator.LEFT);
		Operator bitor   = new Operator("|", null, Operator.BINARY + Operator.LEFT);
		Operator bitxor  = new Operator("^", null, Operator.BINARY + Operator.LEFT);

		ot.addOperator(preinc,  ot.getUMinus());
		ot.addOperator(postinc, preinc);
		ot.addOperator(predec,  preinc);
		ot.addOperator(postdec, preinc);
		ot.addOperator(bitcomp, ot.getNot());
		ot.insertOperator(lshift, ot.getLT());
		ot.addOperator(rshift,  lshift);
		ot.addOperator(rshift0, lshift);
		ot.appendOperator(bitand, ot.getEQ());
		ot.removeOperator(ot.getPower());
		ot.appendOperator(bitxor, bitand);
		ot.appendOperator(bitor, bitxor);
		
		jep.reinitializeComponents();
		
		Node n = jep.parse("x=1");
		n = jep.parse("++x");
		nodeTest(n, preinc);
		n = jep.parse("x++");
		nodeTest(n, postinc);
		n = jep.parse("--x");
		nodeTest(n, predec);
		n = jep.parse("x--");
		nodeTest(n, postdec);
		n = jep.parse("-x");
		nodeTest(n, ot.getUMinus());
	}
	
	public void testCPStrings() throws Exception
	{
	    valueTest("'A\\bB\\fC\\nD\\rE\\tF\'","A\bB\fC\nD\rE\tF");
	    valueTest("'A\\u0021B\\u00a3C'","A!B\u00a3C");
	    valueTest("'A\u0021B\\u00a3C'","A!B\u00a3C");
	    valueTest("'\7'","\u0007");
	    valueTest("'\07'","\u0007");
	    valueTest("'\007'","\u0007");
	    valueTest("'\7A'","\u0007A");
	    valueTest("'\07A'","\u0007A");
	    valueTest("'\007A'","\u0007A");
	    
	    valueTest("A='1'","1");
	    valueTest("B='2'","2");
	    valueTest("(A=='1')&&(B=='2')",myTrue);
	}

	public void testEmptyEqn2() throws Exception
	{
	 String s = "1;;2;\n;3;\n";
	 printTestHeader("Testing reading multiple with empty equations: "+s);
	 jep.initMultiParse(s);
	 ConfigurableParser cp = (ConfigurableParser) jep.getParser();
	 Node n1 = cp.continueParse();
	 Object o1 = jep.evaluate(n1);
	 this.myAssertEquals("1", 1.0,o1);

	 Node n2 = cp.continueParse();
	 Object o2 = jep.evaluate(n2);
	 this.myAssertEquals("2", 2.0,o2);

	 Node n3 = cp.continueParse();
	 Object o3 = jep.evaluate(n3);
	 this.myAssertEquals("3", 3.0,o3);

	 Node n4 = jep.continueParsing();
	 assertNull("Next parse should give a null result",n4);
	}


	public void testBug61() throws Exception
	{
	    valueTest("a=2",2.0);
	    valueTest("b=3",3.0);
	    valueTest("c=4",4.0);
	    valueTest("d=5",5.0);
	    valueTest("(a+b+c)/d",(2.+3.+4.)/5.);
	}

	public void testMultiDimArrayAccess() throws Exception
	{
	    printTestHeader("Testing multidim array access");
	    valueTestString("x=[[1,2],[3,4]]","[[1.0, 2.0], [3.0, 4.0]]");
	    valueTest("x[2][1]",3.0);
	    valueTest("x[2][1]=5",5.0);
	    valueTestString("x","[[1.0, 2.0], [5.0, 4.0]]");

	    valueTestString("x=[[1,2,3],[4,5,6],[7,8,9]]","[[1.0, 2.0, 3.0], [4.0, 5.0, 6.0], [7.0, 8.0, 9.0]]");
	    valueTest("x[2][3]=-1",-1.0);
	    valueTestString("x","[[1.0, 2.0, 3.0], [4.0, 5.0, -1.0], [7.0, 8.0, 9.0]]");

	    valueTestString("x=[[[1,2],[3,4]],[[5,6],[7,8]]]",
		    "[[[1.0, 2.0], [3.0, 4.0]], [[5.0, 6.0], [7.0, 8.0]]]");
	    valueTest("x[2][1][2]",6.0);
	    valueTest("x[2][1][2]=-1",-1.0);
	    valueTestString("x",
	    	"[[[1.0, 2.0], [3.0, 4.0]], [[5.0, -1.0], [7.0, 8.0]]]");


	}

}
