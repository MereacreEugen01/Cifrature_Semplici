import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
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

	
	public Sender()
	{
		super("Secret Sender");
		setBounds(x, y, larghezza, altezza);
		pannelloPrincipale = (JPanel)getContentPane();
		pannelloCentrale = new JPanel();
		pannelloCentrale.setLayout(null);
		
		codiceAgente = new JTextField();
		codiceAgente.setBounds(20,20, 40, 20 );
		codiceAgente.setVisible(true);
		pannelloCentrale.add(codiceAgente);
		
		
		pannelloPrincipale.add(pannelloCentrale);
		
			
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}

}
