/* @author rich
 * Created on 26-Feb-2004
 */

package com.singularsys.jepexamples.diagnostics;
import java.util.Random;

import com.singularsys.jep.Jep;
import com.singularsys.jep.JepComponent;
import com.singularsys.jep.NodeFactory;
import com.singularsys.jep.PrintVisitor;
import com.singularsys.jep.Variable;
import com.singularsys.jep.VariableFactory;
import com.singularsys.jep.VariableTable;
import com.singularsys.jep.configurableparser.StandardConfigurableParser;
import com.singularsys.jep.parser.Node;
import com.singularsys.jep.reals.RealEvaluator;
import com.singularsys.jep.standard.DoubleNumberFactory;
import com.singularsys.jep.standard.StandardFunctionTable;
import com.singularsys.jep.standard.StandardOperatorTable;

/**
 * Compares the speed of evaluation between standard evaluator and real 
 * evaluator and occasionally java.
 * <p>
 * If you have some nice complicated examples, I'd love to
 * hear about them to see if we can tune things up. - rich
 */
public class TokSpeed {
	static Jep j;
	static Jep tj;
	static int num_itts = 100000; // for normal use
//	static int num_itts = 1000;	  // for use with profiler
	static long seed; // seed for random number generator
	static int num_vals = 1000; // number of random numbers selected
	static int nDeriv = 50;
	
	/**
	 * Main method, executes all speed tests.
	 * @param args
	 */
	public static void main(String args[])	{
		if(args.length == 1)
			num_itts = Integer.parseInt(args[0]);
		System.out.println("Performing "+num_itts+" iterations.");
		initJep();
		doAll("5",new String[]{});
		doAll("x",new String[]{"x"});
		doAll("1+x",new String[]{"x"});
		doAll("x^2",new String[]{"x"});
		doAll("x*x",new String[]{"x"});
		doAll("5*x",new String[]{"x"});
		doAll("cos(x)",new String[]{"x"});
		doCos();
		doAll("1+x+x^2",new String[]{"x"});
		doAll("1+x+x^2+x^3",new String[]{"x"});
		doAll("1+x+x^2+x^3+x^4",new String[]{"x"});
		doAll("1+x+x^2+x^3+x^4+x^5",new String[]{"x"});
		doAll("1+x(1+x(1+x(1+x(1+x))))",new String[]{"x"});
		doHorner();
		doAll("if(x>0.5,1,0)",new String[]{"x"});
		doAll("1*2*3+4*5*6+7*8*9",new String[]{});
		doAll("x1*x2*x3+x4*x5*x6+x7*x8*x9",new String[]{"x1","x2","x3","x4","x5","x6","x7","x8","x9"});
		doAll("cos(x)^2+sin(x)^2",new String[]{"x"});

		StringBuffer sb = new StringBuffer();
		for(int i=1;i<=nDeriv ;++i)
		{
			if(i%2==0)
				sb.append("-");
			else if(i>1)
				sb.append("+");
			sb.append("x^"+i+"/"+i);
		}
		String expression = sb.toString();
		doAll(expression,new String[]{"x"});
		doLn();
	}
	

	static void initJep()
	{
		Runtime rt = Runtime.getRuntime();
		long max = rt.maxMemory();
		long free = rt.freeMemory();
		long tot = rt.totalMemory();
		System.out.println("Tot "+tot+" free "+free+" max "+max+" tot-free "+(tot-free));

		j = new Jep();
		max = rt.maxMemory();
		free = rt.freeMemory();
		tot = rt.totalMemory();
		System.out.println("Tot "+tot+" free "+free+" max "+max+" tot-free "+(tot-free));
		

		tj = new Jep(new JepComponent[]{
				new DoubleNumberFactory(),
				new VariableFactory(),
				new NodeFactory(),
				new StandardFunctionTable(),
				new VariableTable(),
				new StandardOperatorTable(),
				new StandardConfigurableParser(),
				new RealEvaluator(),
				new PrintVisitor()
		});
		max = rt.maxMemory();
		free = rt.freeMemory();
		tot = rt.totalMemory();
		System.out.println("Tot "+tot+" free "+free+" max "+max+" tot-free "+(tot-free));
	}

	/**
	 * Run speed comparison between jep and rpe.
	 * 
	 * @param eqn
	 *            The equation to test
	 * @param varNames
	 *            an array of variable names which will be set to random values.
	 */
	public static void doAll(String eqn, String varNames[]) {
		System.out.println("\nTesting speed for \"" + eqn + "\"");
		seed = System.currentTimeMillis();
		try {
			j.parse(eqn);
		} catch (Exception e) {
		}
		
		Variable vars[] = new Variable[varNames.length];
		Variable tvars[] = new Variable[varNames.length];
		Double varVals[][] = new Double[varNames.length][num_vals];
		Random generator = new Random(seed);

		// for each element in vars
		for (int i = 0; i < vars.length; ++i) {
			// add variables to each parser
			vars[i] = j.addVariable(varNames[i]);
			tvars[i] = tj.getVariable(varNames[i]);
			if(tvars[i]==null)
				tvars[i] = tj.addVariable(varNames[i]);
			
			// create variable values
			for (int j = 0; j < num_vals; ++j)
				varVals[i][j] = new Double(generator.nextDouble());
		}


		long jTime = doEval(j, eqn, vars, varVals);
		System.out.println("Using Jep:\t"+jTime);
		long tjTime = doEval(tj, eqn, tvars, varVals);
		System.out.println("Using TokJep:\t"+tjTime);
		if (tjTime != 0) {
			Double ratio = ((double) jTime) / tjTime;
			System.out.printf("Speed up:\t%.1f\n", new Object[] { ratio });
		} else
			System.out.println("Speed up:\t" + jTime + "/0");
		// System.out.println("<tr><td>"+eqn+"</td><td>"+tj+"</td><td>"+tr+"</td></tr>");
	}
	
	static long doEval(Jep jep, String eqn2, Variable vars[], Double vals[][])
	{
		long tdiff = 0;
		try {
			Node node = jep.parse(eqn2);
			
			// get current time
			long t1 = System.currentTimeMillis();
			// perform iterations
			for (int i = 0; i < num_itts; ++i) {
				// set each variable value
				for (int j = 0; j < vars.length; ++j)
					vars[j].setValue(vals[j][i % num_vals]);
				jep.evaluate(node);
			}
			// get current time
			long t2 = System.currentTimeMillis();
			// calc time elapsed
			tdiff = t2 - t1;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		return tdiff;
	}

	
	static void doCos()
	{
		Double[] varVals = new Double[num_vals];
		Random generator = new Random(seed);
		for(int j=0;j<num_vals;++j) {
		    varVals[j] = new Double(generator.nextDouble());
		}
		
		long t1 = System.currentTimeMillis();
		double x; 
		for(int i=0;i<num_itts;++i)
		{
			x = varVals[i%num_vals].doubleValue();
			@SuppressWarnings("unused")
			double c = Math.cos(x);
			//double s = Math.sin(x);
			//y = c*c+s*s;
		}
		long t2 = System.currentTimeMillis();
		System.out.println("Using Java:\t"+(t2-t1));
	}

	static void doHorner()
	{
		Double[] varVals = new Double[num_vals];
		Random generator = new Random(seed);
		for(int j=0;j<num_vals;++j)
			varVals[j] = new Double(generator.nextDouble());
		
		long t1 = System.currentTimeMillis();
		double x; 
		@SuppressWarnings("unused")
		double y;
		for(int i=0;i<num_itts;++i)
		{
			x = varVals[i%num_vals].doubleValue();
			y = 1+x*(1+x*(1+x*(1+x*(1+x))));
		}
		long t2 = System.currentTimeMillis();
		System.out.println("Using Java:\t"+(t2-t1));
	}

	static final double powN(double r,short n){
		switch(n){
			case 0: r = 1.0; break;
			case 1: break;
			case 2: r *= r; break;
			case 3: r *= r*r; break;
			case 4: r *= r*r*r; break;
			case 5: r *= r*r*r*r; break;
			case 6: r *= r*r*r*r*r; break;
			case 7: r *= r*r*r*r*r*r; break;
			case 8: r *= r*r*r*r*r*r*r; break;
			default:
			   {
				   short bitMask = n;
				   double evenPower = r;
				   double result;
				   if ( (bitMask & 1) != 0 )
				      result = r;
				   else
				      result = 1;
				   bitMask >>>= 1;
				   while ( bitMask != 0 ) {
				      evenPower *= evenPower;
				      if ( (bitMask & 1) != 0 )
				         result *= evenPower;
				      bitMask >>>= 1;
				   } // end while
				r = result;
			   }
		}
		return r;
	} 

	static void doLn()
	{
		Double[] varVals = new Double[num_vals];
		Random generator = new Random(seed);
		for(int j=0;j<num_vals;++j) {
		    varVals[j] = new Double(generator.nextDouble());
		}
		
		long t1 = System.currentTimeMillis();
		double x; 
		for(int i=0;i<num_itts;++i)
		{
			x = varVals[i%num_vals].doubleValue();
			double res = 0;
			for(int j=1;j<nDeriv;++j)
			{
				double val = powN(x,(short) j)/j;
				if(j%2==0) {
				    res -= val;
				} else {
				    res += val;
				}
			}
		}
		long t2 = System.currentTimeMillis();
		System.out.println("Using Java:\t"+(t2-t1));
	}
}
