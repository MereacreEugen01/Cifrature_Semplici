
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;


public class Sender extends JFrame implements ActionListener
{
	private JPanel pannelloPrincipale, pannelloCentrale, pannelloNord, pannelloSud, pannellOvest, pannelloCentroEst;
	private static final long serialVersionUID = 1L;
	private int altezza= 400, larghezza= 800;//dimensioni della finestra
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); //variabile di supporto per posizionare la finestra al centro dello schermo
	private int x =  (dim.width-larghezza)/2, y = (dim.height-altezza)/2;//coordinate x e y per posizionare la finestra in maniera dinamica

	private JTextPane textField_chiaveCesare, textField_chiaveVigenere, codiceAgente, porta, ip;
	private JLabel titolo, sottoTitolo; 
	private JTextArea  messaggio;
	private JButton invia, esci;
	private JRadioButton cifrCesare, cifrVigenere;

	//Variabili per inviare
	private DatagramSocket socketSender;
	private DatagramPacket datagrampacket;

	//Variabili per attendere una risposta
	private byte stringaDiByte[] = new byte[64];


	public Sender()
	{
		super("Secret Sender");

		setBounds(x, y, larghezza, altezza);
		pannelloPrincipale = (JPanel)getContentPane();
		pannelloCentrale = new JPanel();
		pannelloCentrale.setLayout(new FlowLayout());

		titolo = new JLabel("Secret Sender");
		titolo.setHorizontalAlignment(JLabel.CENTER);
		sottoTitolo = new JLabel("Mereacre Eugen & Lenzi Filippo 5IA");
		sottoTitolo.setHorizontalAlignment(JLabel.CENTER);
		//pannello nord con le scritte 
		pannelloNord = new JPanel();
		pannelloNord.setLayout(new GridLayout(2,1));
		pannelloNord.add(titolo);
		pannelloNord.add(sottoTitolo);
		//pannello Ovest con il codice agente e il messaggio 
		pannellOvest = new JPanel();
		pannellOvest.setLayout(new BoxLayout(pannellOvest, BoxLayout.Y_AXIS));

		FlowLayout flow = new FlowLayout();
		flow.setVgap(2);
		flow.setAlignment(FlowLayout.LEFT);

		JPanel sottoPannello1 = new JPanel();
		sottoPannello1.setLayout(flow);
		sottoPannello1.add(new JLabel("Codice agente:"));
		//Controllo sul codice agente affinchè non ci siano lettere nel mezzo 
		codiceAgente = new JTextPane(
				new DefaultStyledDocument() 
				{
					private static final long serialVersionUID = 1L;
					@Override
					public void insertString(int offs, String str, AttributeSet a) 
					{

						if((getLength() + str.length()) <=4)	
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
		codiceAgente.setPreferredSize(new Dimension(40,20));
		JPanel sottoPannello2 = new JPanel();
		sottoPannello2.setLayout(flow);
		sottoPannello2.add(codiceAgente);

		JPanel sottoPannello3 = new JPanel();
		sottoPannello3.setLayout(flow);
		sottoPannello3.add(new JLabel("Messaggio:"));
		//Area per il messaggio
		messaggio = new JTextArea();
		messaggio.setPreferredSize(new Dimension(400,200));
		messaggio.setLineWrap(true);
		JScrollPane scrollBar = new JScrollPane(messaggio);
		JPanel sottoPannello4 = new JPanel();
		sottoPannello4.setLayout(flow);
		sottoPannello4.add(scrollBar);	


		titolo = new JLabel("Secret sender");
		titolo.setAlignmentX(CENTER_ALIGNMENT);
		//new BowLayout

		pannelloPrincipale.add(titolo, BorderLayout.NORTH);
		pannelloPrincipale.add(pannelloCentrale, BorderLayout.CENTER);

		pannellOvest.add(sottoPannello1);
		pannellOvest.add(sottoPannello2);
		pannellOvest.add(sottoPannello3);
		pannellOvest.add(sottoPannello4);

		JPanel pannelloSinistra = new JPanel();
		pannelloSinistra.add(pannellOvest);
		//sottopannello con la scelta del metodo di cifratura, la chiave, l'ip e la porta di destinazione
		pannelloCentroEst = new JPanel();
		pannelloCentroEst.setLayout(new BoxLayout(pannelloCentroEst, BoxLayout.Y_AXIS));
		cifrCesare = new JRadioButton("Cesare");
		cifrVigenere = new JRadioButton("Vigenere");
		ButtonGroup p = new ButtonGroup();
		p.add(cifrVigenere); p.add(cifrCesare);
		textField_chiaveCesare = new JTextPane(
				//La chiave di cesare non deve contenere nessun carattere quindi con appena viene inserita una lettere verrà subito eliminata
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

		cifrCesare.addActionListener(this);
		textField_chiaveCesare.setVisible(true);
		textField_chiaveCesare.setPreferredSize(new Dimension(40,20));
		textField_chiaveVigenere = new JTextPane(
				new DefaultStyledDocument() {
					private static final long serialVersionUID = 1L;
					//la chiave di vigenere può contenere sia lettere che numeri però non deve superare la lunghezza di 5 caratteri
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
		textField_chiaveVigenere.setVisible(false);
		cifrVigenere.addActionListener(this);
		textField_chiaveVigenere.setPreferredSize(new Dimension(40,20));
		//aggiunta della scelta del metodo di cifratura, la chiave, l'ip e la porta
		JPanel pannellino = new JPanel();
		pannellino .setLayout(new GridLayout(3,1));
		pannellino.add(new JLabel("Cifratura:"));
		pannellino.add(cifrCesare);
		pannellino.add(cifrVigenere);
		JPanel pannellino2 = new JPanel();
		pannellino2.add(new JLabel("Chiave: "));
		pannellino2.add(textField_chiaveCesare);
		pannellino2.add(textField_chiaveVigenere);

		pannelloCentroEst.add(pannellino);
		pannelloCentroEst.add(pannellino2);

		//controllo sull'ip affinché non ci siano lettere
		JPanel pannellino3 = new JPanel();
		ip = new JTextPane(
				new DefaultStyledDocument() 
				{
					/*
					private static final long serialVersionUID = 1L;
					@Override
					public void insertString(int offs, String str, AttributeSet a) 
					{

						if((getLength() + str.length()) <=15)	
							try {
								str = str.replaceAll("[a-z]", "");
								str = str.replaceAll("[A-Z]", "");
								super.insertString(offs, str, a);
							} catch (BadLocationException e) 
						{
								e.printStackTrace();
						}

					}
					*/
				}
				);

		ip.setPreferredSize(new Dimension(100,20));
		//Controllo sulla porta affinchè non ci siano lettere e non sia un numero troppo grosso 
		porta = new JTextPane(
				new DefaultStyledDocument() {
					private static final long serialVersionUID = 1L;
					@Override
					public void insertString(int offs, String str, AttributeSet a) 
					{

						if((getLength() + str.length()) <=5)	
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


		porta.setPreferredSize(new Dimension(100,20));
		pannellino3.setLayout(new GridLayout(2,1));
		JPanel pannellinoSu = new JPanel();
		pannellinoSu.add(new JLabel("       IP:"));
		pannellinoSu.add(ip);
		JPanel pannellinoGiù = new JPanel();
		pannellinoGiù.add(new JLabel("Porta:"));
		pannellinoGiù.add(porta);
		pannellino3.add(pannellinoSu);
		pannellino3.add(pannellinoGiù);

		pannelloCentroEst.add(pannellino3);
		//pannello sud con i bottoni esci e invia 
		pannelloSud = new JPanel();
		pannelloSud.setLayout(new FlowLayout());
		invia = new JButton("Invia");
		invia.addActionListener(this);
		esci = new JButton("Esci");
		esci.addActionListener(this);
		pannelloSud.add(invia);
		pannelloSud.add(esci);

		pannelloCentrale.add(pannelloCentroEst);
		//aggiunta dei vari sottopannelli al pannello centrale 
		pannelloPrincipale.add(pannelloCentrale, BorderLayout.CENTER);
		pannelloPrincipale.add(pannelloNord, BorderLayout.NORTH);
		pannelloPrincipale.add(pannelloSinistra, BorderLayout.WEST);
		pannelloPrincipale.add(pannelloSud, BorderLayout.SOUTH);

		setResizable(false);	
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//a seconda della scelta verrà scelto un pannello con un controllo piuttosco che con un altro
		if(e.getSource() == cifrCesare)
		{
			textField_chiaveCesare.setVisible(true);
			textField_chiaveVigenere.setVisible(false);
		} else if(e.getSource() == cifrVigenere)
		{
			textField_chiaveCesare.setVisible(false);
			textField_chiaveVigenere.setVisible(true);
		}
		if(invia == e.getSource())
		{
			//manca da prendere il messaggio che l'utente ha scritto, codificarlo e inviarlo
			char[] msg = (codiceAgente.getText()+": "+messaggio.getText()).toCharArray();
			byte [] messaggioDaCifrare = new byte[msg.length];
			for(int i = 0; i < msg.length; i++)
				messaggioDaCifrare[i] = (byte)msg[i];

			//CifrarioCesare cifratoreCesare = new CifrarioCesare(messaggioDaCifrare, Integer.parseInt(textField_chiaveCesare.getText()));
			//cifratoreCesare.cripta(messaggioDaCifrare, Integer.parseInt(textField_chiaveCesare.getText()));
			//  String pippo = new String(cifratoreCesare.cripta(messaggioDaCifrare, Integer.parseInt(textField_chiaveCesare.getText())));
			//System.out.println(		pippo);
			byte[] messaggioCifrato = null;
			if (cifrCesare.isSelected()) messaggioCifrato = cifraCesare(messaggioDaCifrare, Integer.parseInt(textField_chiaveCesare.getText()));
			else if (cifrVigenere.isSelected()) {
				byte[] key = new byte[5];
				for(int i= 0;i<key.length;i++) key[i] = (byte)(textField_chiaveVigenere.getText().charAt(i));
				messaggioCifrato = cifraVigenere(messaggioDaCifrare,key);
			}
			

			try 
			{
				socketSender = new DatagramSocket();
				datagrampacket = new DatagramPacket(messaggioCifrato, messaggioCifrato.length, InetAddress.getByName(ip.getText()), Integer.parseInt(porta.getText()));
				socketSender.send(datagrampacket);
				System.out.println("Messaggio inviato!");
			}
			catch (IOException evt) 
			{
				System.out.println("Messaggio non inviato");
				evt.printStackTrace();
			}
		}
		if(esci== e.getSource() )
		{
			socketSender.close();
			System.exit(0);
		}
	}

	public byte[] cifraCesare(byte[] s , int chiave) {
		byte[] c = new byte[s.length];
		chiave = chiave % 255;
		byte k = (byte)chiave;
		for (int i = 0; i < s.length; i++) {
			byte n = (byte) ((byte)s[i] + (byte)k);
			c[i] = n;
		}
		return c;
	}

	public byte[] cifraVigenere(byte[] s , byte[] chiave) 
	{
		byte[] c = new byte[s.length];
		for (int i = 0; i < s.length; i++) {
			c[i] = (byte)((byte)s[i] + (byte)chiave[i%chiave.length]);
		}
		return c;
	}

}
