import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
public class Inbox extends JFrame {

	private JTextField casella = new JTextField();

	private JButton bottone1= new JButton("Decifra");
	JPanel ButtonPanel = new JPanel();

	private JCheckBox scelta1 = new JCheckBox("Cesare");
	private JCheckBox scelta2 = new JCheckBox("Vigenere");
	private ButtonGroup gruppoScelta = new ButtonGroup();


	JLabel etichetta2= new JLabel("Messaggi:");
	JPanel center = new JPanel();

	JLabel etichetta3= new JLabel("Cifratura");

	JLabel etichetta4= new JLabel("Chiave");

	JLabel vuoto= new JLabel();

	JLabel etichetta1= new JLabel("Secret Inbox");
	JPanel nord = new JPanel();

	JComboBox tendina = new JComboBox();

	private ArrayList<String> listaMessaggi = new ArrayList();
	public Inbox() {
		super("Inbox"); //Creazione finestra

		gruppoScelta.add(scelta1);
		gruppoScelta.add(scelta2);

		tendina = aggiunge();

		nord.setLayout(new FlowLayout());
		nord.add(etichetta1);

		center.setLayout(new GridLayout(3,3));
		center.add(etichetta2);
		center.add(etichetta3);
		center.add(etichetta4);
		center.add(tendina);
		center.add(scelta1);
		center.add(casella);
		center.add(vuoto); 
		center.add(scelta2);

		casella.setPreferredSize(new Dimension(20,20));


		ButtonPanel.setLayout(new FlowLayout());
		ButtonPanel.add(bottone1);

		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());   //Assegnamento dei layout
		c.add(nord,BorderLayout.NORTH);
		c.add(center,BorderLayout.CENTER);
		c.add(ButtonPanel,BorderLayout.SOUTH);


		this.setBounds(100,100,630,200); //Dimensione
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private static boolean eUnNumero(String stringa) 
	{ 
		try 
		{  
			Integer.parseInt(stringa);  
			return true;
		} 
		catch(NumberFormatException e)
		{  
			return false;  
		}  
	}

	public JComboBox aggiunge() {
		for (int i =0; i<listaMessaggi.size(); i++)
			tendina.addItem (listaMessaggi.get(i));
		return tendina;
	}

	public void decifraCesare(String messaggio, int chiave) {

	}

	public void decifraVigenere(String messaggio, int chiave) {

	}
	public void actionPerformed(ActionEvent listener) {	
		int chiave;
		if(bottone1==listener.getSource() && scelta1.isSelected() && eUnNumero(casella.getText())) {
			chiave = Integer.parseInt(casella.getText());
			decifraCesare(listaMessaggi.get(), chiave);
		}else
			JOptionPane.showMessageDialog(null,"La chiave deve essere un numero", "errore" , JOptionPane.WARNING_MESSAGE);

		if(bottone1==listener.getSource() && scelta2.isSelected() && eUnNumero(casella.getText())) {
			chiave = Integer.parseInt(casella.getText());
			decifraVigenere(listaMessaggi.get(), chiave);
		}else 
			JOptionPane.showMessageDialog(null,"La chiave deve essere un numero", "errore" , JOptionPane.WARNING_MESSAGE);


	}
}