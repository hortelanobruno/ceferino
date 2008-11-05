/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chart;

import java.applet.Applet;
import java.awt.*;

import jv.object.*;
import jv.viewer.PvViewer;


/**
 * Applet to find roots of real-valued functions of one variable.
 * 
 * @see			jv.viewer.PvViewer
 * @author		Konrad Polthier
 * @version		02.10.99, 1.00 revised (kp) <br>
 *					02.10.99, 1.00 created (kp)
 */
public class PaRootFinder extends Applet {
	/** frame if run standalone, null if run as applet. */
	public		Frame				m_frame			= null;
	/** 3D-viewer window for graphics output and which is embedded into the applet. */
	protected	PvViewer			m_viewer;

	/** Interface of applet to inform about author, version, and copyright. */
	public String getAppletInfo() {
		return "Name: "		+ this.getClass().getName()+ "\r\n" +
				 "Author: "		+ "Konrad Polthier" + "\r\n" +
				 "Version: "	+ "1.00" + "\r\n" +
				 "Applet to find roots of real-valued functions of one variable." + "\r\n";
	}

	/**
	 * Configure and initialize the viewer, load system and user projects.
	 * One of the user projects must be selected here.
	 */
	public void init() {
		// Create viewer for viewing 3d geometries
		m_viewer = new PvViewer(this, m_frame);

		// Create and load a project
		PjRootFinder test = new PjRootFinder();
		m_viewer.addProject(test);
		m_viewer.selectProject(test);

		// Get 3d display from viewer and add it to applet
		setLayout(new BorderLayout());
		add((Component)m_viewer.getDisplay(), BorderLayout.CENTER);
		add(m_viewer.getPanel(PsViewerIf.PROJECT), BorderLayout.EAST);
		validate();
	}
	/**
	 * Standalone application support. The main() method acts as the applet's
	 * entry point when it is run as a standalone application. It is ignored
	 * if the applet is run from within an HTML page.
	 */
	public static void main(String args[]) {
		PaRootFinder va	= new PaRootFinder();
		// Create toplevel window of application containing the applet
		Frame frame	= new jv.object.PsMainFrame(va, args);
		frame.pack();
		va.m_frame = frame;
		va.init();
		va.start();
		frame.setBounds(new Rectangle(420, 5, 640, 550));
		frame.setVisible(true);
	}
	
	/** Print info while initializing applet and viewer. */
	public void paint(Graphics g) {
		g.setColor(Color.blue);
		g.drawString("Geometry Browser, Version "+PsConfig.getVersion(), 20, 40);
		g.drawString("Loading Projects .....", 20, 60);
	}

	/**
	 * Does clean-up when applet is destroyed by the browser.
	 * Here we just close and dispose all our control windows.
	 */
	public void destroy()	{ m_viewer.destroy(); }

	/** Start viewer, e.g. start animation if requested */
	public void start()		{ m_viewer.start(); }

	/** Stop viewer, e.g. stop animation if requested */
	public void stop()		{ m_viewer.stop(); }
}