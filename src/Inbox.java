import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
public class Inbox extends JFrame implements ActionListener{

	private JTextPane casellaCesare = new JTextPane(
			//La chiave di cesare non deve contenere nessun carattere quindi non appena viene inserita una lettere verr� subito eliminata
			new DefaultStyledDocument() 
			{
				private static final long serialVersionUID = 1L;
				@Override
				public void insertString(int offs, String str, AttributeSet a) 
				{
					try 
					{
						str = str.replaceAll("[a-z]", "");
						str = str.replaceAll("[A-Z]", "");
						super.insertString(offs, str, a);
					} catch (BadLocationException e) 
					{
						e.printStackTrace();
					}
				}
			}
			);


	private JTextPane casellaVigenere = new JTextPane(
			new DefaultStyledDocument() {
				private static final long serialVersionUID = 1L;
				//la chiave di vigenere pu� contenere solo lettere e non deve superare la lunghezza di 5 caratteri
				@Override
				public void insertString(int offs, String str, AttributeSet a) 
				{
					if((getLength() + str.length()) <=5)
						try 
					{ 
							super.insertString(offs, str, a);
					} catch (BadLocationException e) 
					{
						e.printStackTrace();
					}
				}
			}
			);


	private JButton bottone1= new JButton("Decifra");
	JPanel ButtonPanel = new JPanel();

	private JCheckBox scelta1 = new JCheckBox("Cesare");
	private JCheckBox scelta2 = new JCheckBox("Vigenere");
	private ButtonGroup gruppoScelta = new ButtonGroup();
	private ArrayList<byte[]> messaggiRicevuti = new ArrayList<>();

	JLabel etichetta2= new JLabel("Messaggi:");
	JPanel center = new JPanel();

	JLabel etichetta3= new JLabel("Cifratura");

	JLabel etichetta4= new JLabel("Chiave");

	JLabel vuoto= new JLabel();

	JLabel etichetta1= new JLabel("Secret Inbox");

	JPanel nord = new JPanel();

	JLabel etichetta5 = new JLabel("Porta: "+MainWindow.getNumeroPorta());

	JComboBox tendina = new JComboBox();

	private DatagramSocket socket;
	private DatagramPacket p;

	private ArrayList<String> listaMessaggi = new ArrayList();

	private Thread thread = new Thread();

	public Inbox() {
		super("Inbox"); //Creazione finestra

		gruppoScelta.add(scelta1);
		gruppoScelta.add(scelta2);


		casellaCesare.setVisible(true);
		casellaVigenere.setVisible(false);

		nord.setLayout(new FlowLayout());
		nord.add(etichetta1);

		center.setLayout(new GridLayout(3,3));
		center.add(etichetta2);
		center.add(etichetta3);
		center.add(etichetta4);
		center.add(tendina);
		center.add(scelta1);
		center.add(casellaCesare);
		center.add(etichetta5);
		center.add(scelta2);
		center.add(casellaVigenere);

		ButtonPanel.setLayout(new FlowLayout());
		ButtonPanel.add(bottone1);

		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());   //Assegnamento dei layout
		c.add(nord,BorderLayout.NORTH);
		c.add(center,BorderLayout.CENTER);
		c.add(ButtonPanel,BorderLayout.SOUTH);

		scelta1.addActionListener(this);
		scelta2.addActionListener(this);
		bottone1.addActionListener(this);


		this.setResizable(false);
		this.setBounds(100,100,630,230); //Dimensione
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ricezioneMessaggi(MainWindow.getNumeroPorta());
	}

	public void aggiunge() {
		tendina.removeAllItems();
		for (int i =0; i<listaMessaggi.size(); i++)
			tendina.addItem (listaMessaggi.get(i));
	}

	public byte[] decifraCesare(byte[] s , int chiave) {
		byte[] c = new byte[s.length];
		chiave = chiave % 255;
		byte k = (byte)(chiave);
		for (int i = 0; i < s.length;i++) {
			byte n = (byte) (s[i] - k);
			c[i] = n;
		}
		return c;
	}

	public byte[] decifraVigenere(byte[] s , byte[] chiave) {
		byte[] c = new byte[s.length];
		for (int i = 0; i < s.length;i++) {
			c[i] = (byte)((byte)s[i] - (byte)chiave[i%chiave.length]);
		}
		return c;
	}

	public void ricezioneMessaggi(int port) 
	{ 
		try 
		{
			//Creazione socket sulla porta che diamo in input al nostro Secret Inbox
			socket = new DatagramSocket(port);

			Thread t = new Thread() 
			{
				@Override
				public void run() 
				{
					Thread t = new Thread() 
					{
						@Override
						public void run() 
						{
							boolean corretto = true;
							while(corretto) 
							{
								byte[] buf = new byte[512];
								p = new DatagramPacket(buf, 0, buf.length);
								try 
								{
									//Ricezione messaggio
									socket.receive(p);
								} 
								catch (IOException e) 
								{
									e.printStackTrace();
								}
								String msg = new String(p.getData(), 0, p.getLength());
								byte[] m = new byte[p.getLength()];
								for(int i =0;i<p.getLength();i++) m[i] = p.getData()[i];
								System.out.println(msg); //Stampa del messaggio appena arrivato
								listaMessaggi.add(msg);  //Aggiungo il messaggio arrivato alla lista dei messaggi
								messaggiRicevuti.add(m);
								aggiunge();
							}
						}

					};
					t.start();
				}

			};
			t.start();

		} 
		catch (SocketException e) 
		{
			JOptionPane.showMessageDialog(null, "Non � stato possibile aprire la socket", "Attenzione", JOptionPane.WARNING_MESSAGE);
		}

	}


	public void actionPerformed(ActionEvent listener) {	
		int chiave;


		if(listener.getSource()==scelta1)
		{
			casellaCesare.setVisible(true);
			casellaVigenere.setVisible(false);
		} else if (listener.getSource()==scelta2)
		{
			casellaCesare.setVisible(false);
			casellaVigenere.setVisible(true);
		}

		else if(bottone1==listener.getSource()) {
			if(scelta1.isSelected()) {
				chiave = Integer.parseInt(casellaCesare.getText());
				byte[] messaggioDecifrato = new byte[512];
				messaggioDecifrato = decifraCesare(messaggiRicevuti.get(tendina.getSelectedIndex()), chiave);

				JOptionPane.showMessageDialog(null, new String(messaggioDecifrato) , "messaggio" , JOptionPane.WARNING_MESSAGE);
			}else if(scelta2.isSelected()) {
				char[] chiaveV = casellaVigenere.getText().toCharArray();
				if(chiaveV.length<5) 			
					JOptionPane.showMessageDialog(null,"La chiave deve essere una parola di 5 lettere", "errore" , JOptionPane.WARNING_MESSAGE);
				else {
					byte [] key = new byte[chiaveV.length];
					for(	int i = 0 ; i < key.length;i++)
						key[i] = (byte)chiaveV[i];
					byte[] messaggioDecifrato = new byte[512];
					messaggioDecifrato = decifraVigenere(messaggiRicevuti.get(tendina.getSelectedIndex()), key);

					JOptionPane.showMessageDialog(null, new String(messaggioDecifrato) , "messaggio" , JOptionPane.WARNING_MESSAGE);

				}
			}
			//	JOptionPane.showMessageDialog(null, new String(messaggioDecifrato) , "messaggio" , JOptionPane.WARNING_MESSAGE);

		}
	}
}