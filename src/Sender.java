import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Sender extends JFrame implements ActionListener
{

	private JPanel pannelloPrincipale, pannelloCentrale, pannelloNord, pannelloSud, pannellOvest;
	private static final long serialVersionUID = 1L;
	private int altezza= 400, larghezza= 800;//dimensioni della finestra
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); //variabile di supporto per posizionare la finestra al centro dello schermo
	private int x =  (dim.width-larghezza)/2, y = (dim.height-altezza)/2;//coordinate x e y per posizionare la finestra in maniera dinamica
	private JTextField codiceAgente;
	private JLabel titolo, sottoTitolo, cifratura, idAgente; 
	private JTextArea  messaggio;

	
	public Sender()
	{
		super("Secret Sender");
		setBounds(x, y, larghezza, altezza);
		pannelloPrincipale = (JPanel)getContentPane();
		pannelloCentrale = new JPanel();
		pannelloCentrale.setLayout(new FlowLayout());
		
		
		//pannelloCentrale.add(codiceAgente);
		
		titolo = new JLabel("Secret Sender");
		titolo.setHorizontalAlignment(JLabel.CENTER);
		sottoTitolo = new JLabel("Mereacre Eugen & Lenzi Filippo 5IA");
		sottoTitolo.setHorizontalAlignment(JLabel.CENTER);
		
		pannelloNord = new JPanel();
		pannelloNord.setLayout(new GridLayout(2,1));
		pannelloNord.add(titolo);
		pannelloNord.add(sottoTitolo);
		
		pannellOvest = new JPanel();
		pannellOvest.setLayout(new BoxLayout(pannellOvest, BoxLayout.Y_AXIS));
		
		FlowLayout flow = new FlowLayout();
		flow.setVgap(2);
		flow.setAlignment(FlowLayout.LEFT);
		
		JPanel sottoPannello1 = new JPanel();
		sottoPannello1.setLayout(flow);
		sottoPannello1.add(new JLabel("Codice agente:"));
		
		codiceAgente = new JTextField();
		codiceAgente.setPreferredSize(new Dimension(40,20));
		JPanel sottoPannello2 = new JPanel();
		sottoPannello2.setLayout(flow);
		sottoPannello2.add(codiceAgente);
		
		JPanel sottoPannello3 = new JPanel();
		sottoPannello3.setLayout(flow);
		sottoPannello3.add(new JLabel("Messaggio:"));
		
		messaggio = new JTextArea();
		messaggio.setPreferredSize(new Dimension(400,200));
		messaggio.setLineWrap(true);
		JScrollPane scrollBar = new JScrollPane(messaggio);
		JPanel sottoPannello4 = new JPanel();
		sottoPannello4.setLayout(flow);
		sottoPannello4.add(scrollBar);	
		
		pannellOvest.add(sottoPannello1);
		pannellOvest.add(sottoPannello2);
		pannellOvest.add(sottoPannello3);
		pannellOvest.add(sottoPannello4);
		
		JPanel pannelloSinistra = new JPanel();
		pannelloSinistra.add(pannellOvest);
	
		pannelloPrincipale.add(pannelloCentrale, BorderLayout.CENTER);
		pannelloPrincipale.add(pannelloNord, BorderLayout.NORTH);
		pannelloPrincipale.add(pannelloSinistra, BorderLayout.WEST);
		
		
		
		
		setResizable(false);	
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}

}
