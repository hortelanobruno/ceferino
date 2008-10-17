package com.singularsys.jepexamples.diagnostics;

import com.singularsys.jep.ComponentSet;
import com.singularsys.jep.Evaluator;
import com.singularsys.jep.FunctionTable;
import com.singularsys.jep.Jep;
import com.singularsys.jep.NodeFactory;
import com.singularsys.jep.NumberFactory;
import com.singularsys.jep.OperatorTable;
import com.singularsys.jep.Parser;
import com.singularsys.jep.PrintVisitor;
import com.singularsys.jep.VariableFactory;
import com.singularsys.jep.VariableTable;
import com.singularsys.jep.configurableparser.StandardConfigurableParser;
import com.singularsys.jep.misc.LightWeightComponentSet;
import com.singularsys.jep.misc.NullParser;
import com.singularsys.jep.parser.StandardParser;
import com.singularsys.jep.standard.DoubleNumberFactory;
import com.singularsys.jep.standard.StandardEvaluator;
import com.singularsys.jep.standard.StandardFunctionTable;
import com.singularsys.jep.standard.StandardOperatorTable;
import com.singularsys.jep.standard.StandardVariableTable;

/**
 * Used to examine the memory used to initialise a Jep instance.
 * Typical values are
 * <ul>
 * <li>With a StandardParser: 56661
 * <li>With a ConfigurableParser: 14033
 * <li>With a NullParser: 5265
 * </ul>
 */
public class ExamineSizes {
    static final int N=100;
    enum ParserType { STANDARD, CONFIG, NULL };
    static ParserType pt = ParserType.CONFIG;
    ExamineSizes() {
	    printMem("Init "+N+" instances ");

	    NumberFactory nuf[]=new NumberFactory[N];
	    VariableFactory vf[]=new VariableFactory[N];
	    NodeFactory nf[] = new NodeFactory[N];
	    FunctionTable[] ft = new FunctionTable[N];
	    VariableTable vt[] = new VariableTable[N];
	    OperatorTable ot[] = new OperatorTable[N];
	    Parser p[] = new Parser[N];
	    Evaluator e[] = new Evaluator[N];
	    PrintVisitor pv[] = new PrintVisitor[N];
	    Jep j[] = new Jep[N];
	    Jep j2[] = new Jep[N];
	    ComponentSet cs = new ComponentSet();

	    long initMem = printMem("Init arrays");

	    for(int i=0;i<N;++i)
		nuf[i] = new DoubleNumberFactory();
	    printMem("DoubleNumberFactory");
	    
	    for(int i=0;i<N;++i)
		vf[i] = new VariableFactory();
	    printMem("VariableFactory");
	    
	    for(int i=0;i<N;++i)
		nf[i] = new NodeFactory();
	    printMem("NodeFactory");
	    
	    for(int i=0;i<N;++i)
		ft[i] = new StandardFunctionTable();
	    printMem("StandardFunctionTable");
	    
	    for(int i=0;i<N;++i)
		vt[i] = new StandardVariableTable(vf[i]);
	    printMem("StandardVariableTable");
	    
	    for(int i=0;i<N;++i)
		ot[i] = new StandardOperatorTable();
	    printMem("StandardOperatorTable");
	    
	    switch(pt) {
	    case STANDARD:
        	    for(int i=0;i<N;++i)
        		p[i] = new StandardParser();
        	    printMem("StandardParser");
	    break;
	    case CONFIG:
        	    for(int i=0;i<N;++i)
        		p[i] = new StandardConfigurableParser();
        	    printMem("ConfigurableParser");
	    break;
	    case NULL:
        	    for(int i=0;i<N;++i)
        		p[i] = new NullParser();
        	    printMem("NullParser");
	    break;
	    }

	    for(int i=0;i<N;++i)
		e[i] = new StandardEvaluator();
	    printMem("StandardEvaluator");
	    
	    for(int i=0;i<N;++i)
		pv[i] = new PrintVisitor();
	    printMem("PrintVisitor");

	    for(int i=0;i<N;++i) {
		cs.setNumberFactory(nuf[i]);
		cs.setVariableFactory(vf[i]);
		cs.setNodeFactory(nf[i]);
		cs.setVariableTable(vt[i]);
		cs.setFunctionTable(ft[i]);
		cs.setOperatorTable(ot[i]);
		cs.setEvaluator(e[i]);
		cs.setParser(p[i]);
		cs.setPrintVisitor(pv[i]);
		j[i] = new Jep(cs);
	    }
	    long finalMem = printMem("Jep");
	    System.out.println("Average total memory per instance " + ((finalMem-initMem)/N));
 
	    for(int i=0;i<N;++i) {
		LightWeightComponentSet lwcs = new LightWeightComponentSet(j[i]);
		j2[i] = new Jep(lwcs);
	    }
	    long lwMem = printMem("LightWeightJep");
	    System.out.println("Average total memory per light weight instance " + ((lwMem-finalMem)/N));
	    
    }
    
    long curMem=0;
    long printMem(String s) {
    	Runtime rt = Runtime.getRuntime();
    	rt.gc();
    	long newMem = rt.totalMemory()-rt.freeMemory();
    //		System.out.println(
    //			"Total "+rt.totalMemory()+" free "+rt.freeMemory()+" max "+rt.maxMemory()+" diff "+newMem);
    	System.out.printf("%-25s%8d%n",s, (newMem-curMem));
//    	System.out.println(s+"\t"+ (newMem-curMem));
    	curMem = newMem;
    	return newMem;
    }

	
    /**
     * @param args
     */
    public static void main(String[] args) {
	ExamineSizes ex = new ExamineSizes();
    }

}
