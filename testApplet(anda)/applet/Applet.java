package applet;


import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Applet extends JApplet 
{
	private static final long serialVersionUID = 9050623782124643515L;
	private JButton btnHola;
	private JButton btnChau;
	private JLabel lblHola;
	private JLabel lblChau;
	
	@Override
    public void init() 
	{
        
        try 
        {
            java.awt.EventQueue.invokeAndWait(new Runnable() 
            {
                public void run() 
                {
                	setUpComponentes();
                }
                
            });
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
	}
	
	private void setUpComponentes()
	{
		Container c = this.getContentPane();
		c.setLayout(new GridLayout(2,2,1,1));
		this.btnHola = new JButton("hola");
		this.btnChau = new JButton("chau");
		this.lblChau = new JLabel(" ");
		this.lblHola = new JLabel(" ");
		
		c.add(btnHola);
		c.add(btnChau);
		c.add(lblHola);
		c.add(lblChau);
		this.setSize(60, 100);
		this.btnHola.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) {
				lblHola.setText("hola");
			}
		});
		this.btnChau.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				lblChau.setText("chau");
				
			}
		});
	}
}
