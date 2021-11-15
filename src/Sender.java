import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Sender extends JFrame implements ActionListener
{

	private JPanel pannelloPrincipale, pannelloCentrale;
	private static final long serialVersionUID = 1L;
	private int altezza= 200, larghezza= 630;//dimensioni della finestra
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); //variabile di supporto per posizionare la finestra al centro dello schermo
	private int x =  (dim.width-larghezza)/2, y = (dim.height-altezza)/2;//coordinate x e y per posizionare la finestra in maniera dinamica
	private JTextField codiceAgente;
	private JLabel titolo;

	
	public Sender()
	{
		super("Secret Sender");
		setBounds(x, y, larghezza, altezza);
		pannelloPrincipale = (JPanel)getContentPane();
		pannelloCentrale = new JPanel();
		pannelloCentrale.setLayout(null);
		
		titolo = new JLabel("Secret sender");
		titolo.setAlignmentX(CENTER_ALIGNMENT);
		//new BowLayout
		
		pannelloPrincipale.add(titolo, BorderLayout.NORTH);
		pannelloPrincipale.add(pannelloCentrale, BorderLayout.CENTER);
		
			
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}

}
