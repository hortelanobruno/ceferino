/*
Created 15 Jan 2007 - Richard Morris
*/
package com.singularsys.jepexamples.diagnostics;

import com.singularsys.jep.Jep;
import com.singularsys.jep.ParseException;
import com.singularsys.jep.parser.Node;

public class ParseSpeed {
	String[] equations;
	/**
	 * Builds a set of equations
	 * @param neqn number of equations
	 * @param nvar number of variables
	 */
	void buildEquations(int nEqns,int nVars) {
		equations = new String[nEqns];
		for(int i=0;i<nEqns;++i)
			equations[i] = buildEquation(i,nVars);
	}
	
	String buildEquation(int eqNo,int nVars) {
		StringBuffer sb = new StringBuffer();
		appendVar(sb,eqNo,0);
		for(int i=1;i<nVars;++i) {
			sb.append('+');
			appendVar(sb,eqNo,i);
		}
		return sb.toString();
	}
	
	void appendVar(StringBuffer sb,int eqNo,int varNo) {
		sb.append('x');
		sb.append(eqNo);
		sb.append('_');
		sb.append(varNo);
		
	}
	public void go(int nEqns,int nVars) {
		System.out.println("Parsing "+nEqns+" equations with "+nVars+" variables");
		long t1 = System.currentTimeMillis(); 
		buildEquations(nEqns,nVars);
		Node[] nodes = new Node[equations.length];
		long t2 = System.currentTimeMillis();
		System.out.println("Build equations\t"+(t2-t1));
		
		Jep jep = new Jep();
		long t3 = System.currentTimeMillis(); 
		System.out.println("Initialise Jep\t"+(t3-t2));
		
		for(int i=0;i<nEqns;++i)
			try {
				nodes[i] = jep.parse(equations[i]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		long t4 = System.currentTimeMillis(); 
		System.out.println("Parse\t"+(t4-t3));
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ParseSpeed ps = new ParseSpeed();
		ps.go(Integer.valueOf(args[0]),Integer.valueOf(args[1]));
	}

}
