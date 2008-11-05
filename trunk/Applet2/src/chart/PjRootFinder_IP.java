/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chart;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jv.object.PsDebug;
import jv.object.PsPanel;
import jv.object.PsUpdateIf;
import jv.project.PjProject_IP;

/**
 * Info panel of root finder demonstration with text field to edit function expression.
 * 
 * @author		Konrad Polthier
 * @version		02.10.99, 1.00 revised (kp) <br>
 *					02.10.99, 1.00 created (kp)
 */
public class PjRootFinder_IP extends PjProject_IP implements ActionListener {
	protected	PjRootFinder			m_pjRoot;
	protected	PsPanel					m_pFunction;
	protected	PsPanel					m_pBounds;
	protected	Button					m_bReset;

	public PjRootFinder_IP() {
		super();
		if (getClass() == PjRootFinder_IP.class)
			init();
	}
	public void init() {
		super.init();
		addTitle("");

		// draw a separator
		addLine(1);

		m_pFunction = new PsPanel();
		m_pFunction.setLayout(new GridLayout(1, 1));
		add(m_pFunction);
		m_pBounds = new PsPanel();
		addSubTitle(" Interval of Roots");
		m_pBounds.setLayout(new GridLayout(3, 1));
		add(m_pBounds);

		// draw a separator
		addLine(1);

		// buttons at bottom
		Panel m_pBottomButtons = new Panel();
		m_pBottomButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		add(m_pBottomButtons);
		m_bReset = new Button("Reset");
		m_bReset.addActionListener(this);
		m_pBottomButtons.add(m_bReset);
	}
	/**
	 * Set parent of panel which supplies the data inspected by the panel.
	 */
	public void setParent(PsUpdateIf parent) {
		super.setParent(parent);
		m_pjRoot = (PjRootFinder)parent;
		m_pFunction.add(m_pjRoot.m_fx.getInfoPanel());
		m_pBounds.add(m_pjRoot.m_xMin.getInfoPanel());
		m_pBounds.add(m_pjRoot.m_xMax.getInfoPanel());
		m_pBounds.add(m_pjRoot.m_discr.getInfoPanel());
	}
	/**
	 * Update the panel whenever the parent has changed somewhere else.
	 * Method is invoked from the parent or its superclasses.
	 */
	public boolean update(Object event) {
		if (PsDebug.NOTIFY) PsDebug.notify("PjRootFinder_IP.update: isShowing = "+isShowing());
		if (m_pjRoot == event) {
			setTitle(m_pjRoot.getName());
			return true;
		}
		return super.update(event);
	}
	/**
	 * Handle action events invoked from buttons, menu items, text fields.
	 */
	public void actionPerformed(ActionEvent event) {
		if (m_pjRoot==null)
			return;
		Object source = event.getSource();
		if (source == m_bReset) {
			m_pjRoot.init();
			m_pjRoot.computeGraph();
			m_pjRoot.findRoot();
			m_pjRoot.m_graph.update(m_pjRoot.m_graph);
			m_pjRoot.m_root.update(m_pjRoot.m_root);
		}
	}
}
