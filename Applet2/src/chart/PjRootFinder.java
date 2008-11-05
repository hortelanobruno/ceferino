/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chart;

import java.awt.Color;

import jv.function.PuFunction;
import jv.geom.PgPointSet;
import jv.geom.PgPolygon;
import jv.number.PuDouble;
import jv.number.PuInteger;
import jv.object.PsDebug;
import jv.project.PvCameraIf;
import jv.project.PvDisplayIf;
import jv.project.PjProject;
import jv.vecmath.PdVector;
import jvx.numeric.PnRootFinder;

/**
 * Demo project to find roots of real-valued functions of one variable.
 * Function expression may be interactively edited.
 * 
 * @see			jvx.numeric.PnRootFinder
 * @author		Konrad Polthier
 * @version		05.12.00, 1.10 revised (kp) Instance variable m_rootFinder replaced with static class.<br>
 *					03.10.99, 1.00 created (kp)
 */
public class PjRootFinder extends PjProject {
	/** Graph of function. */
	protected	PgPolygon				m_graph;
	/** Roots of function as poit set. */
	protected	PgPointSet				m_root;
	/** Currently active function as typed by user. */
	protected	PuFunction				m_fx;
	/** Default equation shown initially. */
	private		String					m_defaultEquation = "u*cos(u+1.)-sin(u)";
	/** Discretization of polygon for display purpose only. */
	protected	PuInteger				m_discr;
	/** Left interval border of shown graph. */
	protected	PuDouble					m_xMin;
	/** Right interval border of shown graph. */
	protected	PuDouble					m_xMax;

	public PjRootFinder() {
		super("Root Finder");
		m_graph			= new PgPolygon(2);
		m_graph.setName("Graph of Function");
		m_root			= new PgPointSet(2);
		m_root.setName("Roots of Function");
		m_discr			= new PuInteger("Discr of Polygon", this);
		m_xMin			= new PuDouble("xMin", this);
		m_xMax			= new PuDouble("xMax", this);
		m_fx				= new PuFunction(1, 1);
		m_fx.setName("Function Expression");
		m_fx.setParent(this);
		if (getClass() == PjRootFinder.class)
		  init();
	}
	public void init() {
		super.init();
		m_xMin.setDefBounds(-20., 20., 0.1, 1.0);
		m_xMin.setDefValue(-10.);
		m_xMin.init();
		m_xMax.setDefBounds(-20., 20., 0.1, 1.0);
		m_xMax.setDefValue( 10.);
		m_xMax.init();
		m_discr.setDefBounds(2, 200, 1, 5);
		m_discr.setDefValue(200);
		m_discr.init();
		m_graph.setNumVertices(m_discr.getValue());
		m_root.showIndices(true);
		m_root.setGlobalVertexColor(Color.blue);
		m_root.setGlobalVertexSize(4.);
		setEquation(m_defaultEquation);
	}
	public void start() {
		if (PsDebug.NOTIFY) PsDebug.notify("start: ");
		computeGraph();
		findRoot();

		addGeometry(m_graph);
		addGeometry(m_root);
		selectGeometry(m_root);

		m_graph.update(m_graph);
		m_root.update(m_root);

		PvDisplayIf disp = getDisplay();
		if (disp != null) {
			disp.showGrid(true);
			disp.selectCamera(PvCameraIf.CAMERA_ORTHO_XY);		// project onto xy-plane
			disp.setMajorMode(PvDisplayIf.MODE_INITIAL_PICK);	// force picking of initial point
		}
		super.start();
	}
	/**
	 * Set function expression from outside the project.
	 */
	public void setEquation(String eq) {
		m_fx.setExpression(eq);
		m_fx.update(m_fx);
	}
	/**
	 * Compute planar graph of function expression.
	 */
	public void computeGraph() {
		int nov		= m_graph.getNumVertices();
		double x		= m_xMin.getValue();
		double dx	= (m_xMax.getValue()-x)/(nov-1.);
		for (int i=0; i<nov; i++) {
			double y = m_fx.eval(x);
			m_graph.setVertex(i, x, y);
			x += dx;
		}
	}
	/**
	 * Compute roots of function in a specified interval.
	 * Method just invokes a numerical algorithm, and assigns roots to pointset of roots.
	 */
	public void findRoot() {
		int maxNumRoots = m_discr.getValue();
		PdVector root = PnRootFinder.findRoots(m_fx, m_xMin.getValue(), m_xMax.getValue(), maxNumRoots);
		if (root == null) {
			if (PsDebug.WARNING) PsDebug.warning("roots not found.");
			return;
		}
		int numRoots = root.getSize();
		m_root.setNumVertices(numRoots);
		for (int i=0; i<numRoots; i++) {
			m_root.setVertex(i, root.getEntry(i), 0.);
		}
	}

	/**
	 * Update the class whenever a child has changed.
	 * Method is usually invoked from the children.
	 */
	public boolean update(Object event) {
		if (PsDebug.NOTIFY) PsDebug.notify("called");
		if (event == m_xMin) {
			computeGraph();
			findRoot();
			m_graph.update(m_graph);
			m_root.update(m_root);
			return true;
		} else if (event == m_xMax) {
			computeGraph();
			findRoot();
			m_graph.update(m_graph);
			m_root.update(m_root);
			return true;
		} else if (event == m_discr) {
			m_graph.setNumVertices(m_discr.getValue());
			computeGraph();
			findRoot();
			m_graph.update(m_graph);
			m_root.update(m_root);
			return true;
		} else if (event == m_fx) {
			computeGraph();
			findRoot();
			m_graph.update(m_graph);
			m_root.update(m_root);
			return true;
		}
		return false;
	}    
}
