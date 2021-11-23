/**
 ** Nome programma:Cifrature semplici / Inbox
 ** Versione programma:1.0
 ** Data:23/11/21
 ** Autore: Filippo Lenzi
 ** Problema: Vedere relazione o consegna per testo
 ** Dati:
 ** Osservazioni: Questa classe server per decifrare i messaggi 
 */

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

	private static final long serialVersionUID = 1L;

	private JPanel pannelloMessaggi, pannelloCentro;
	private JTextArea  areaMessaggioCifrato, areaMessaggioDecifrato;
	private int altezza= 330, larghezza= 850;//dimensioni della finestra
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); //variabile di supporto per posizionare la finestra al centro dello schermo
	private int x =  (dim.width-larghezza)/2, y = (dim.height-altezza)/2;//coordinate x e y per posizionare la finestra in maniera dinamica

	private JTextPane casellaCesare = new JTextPane(
			//La chiave di cesare non deve contenere nessun carattere quindi non appena viene inserita una lettere verrà subito eliminata
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
			new DefaultStyledDocument() 
			{
				private static final long serialVersionUID = 1L;
				//la chiave di vigenere può contenere solo lettere e non deve superare la lunghezza di 5 caratteri
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
	private JButton bottone2= new JButton("Esci");

	JPanel ButtonPanel = new JPanel();

	private JCheckBox scelta1 = new JCheckBox("Cesare");
	private JCheckBox scelta2 = new JCheckBox("Vigenere");
	private JCheckBox scelta3 = new JCheckBox("BRUTE FORCE");
	private ButtonGroup gruppoScelta = new ButtonGroup();
	private ArrayList<byte[]> messaggiRicevuti = new ArrayList<>();

	JLabel etichetta2= new JLabel("Messaggi:");
	JPanel center = new JPanel();

	JLabel etichetta3= new JLabel("Cifratura:");

	JLabel etichetta4= new JLabel("Chiave:");

	JLabel vuoto= new JLabel();

	JLabel etichetta1= new JLabel("Secret Inbox");
	JLabel sottotitolo = new JLabel("Mereacre Eugen & Lenzi Filippo 5IA");

	JPanel nord = new JPanel();

	JLabel etichetta5 = new JLabel("Porta: "+MainWindow.getNumeroPorta());

	JLabel etichetta6 = new JLabel("IP di arrivo: ");
	
	JComboBox<String> tendina = new JComboBox<String>();

	private DatagramSocket socket;
	private DatagramPacket p;

	private ArrayList<String> listaMessaggi = new ArrayList<>();

	public Inbox() {
		super("Inbox"); //Creazione finestra

		gruppoScelta.add(scelta1);
		gruppoScelta.add(scelta2);
		gruppoScelta.add(scelta3);

		casellaCesare.setVisible(true);
		casellaVigenere.setVisible(false);

		nord.setLayout(new GridLayout(2,1));
		etichetta1.setHorizontalAlignment(JLabel.CENTER);
		sottotitolo.setHorizontalAlignment(JLabel.CENTER);
		nord.add(etichetta1);

		nord.add(sottotitolo);
		
		//pannello delle impostazioni per la decifrazione, utilizzo tutti valori assoluti per semplicità
		center.setLayout(null);
		//Etichetta per scegliere i messaggi
		etichetta2.setBounds(10, 10, 100, 20);
		center.add(etichetta2);
		//Menù per la scelta del messaggio da decifrare
		tendina.setBounds(10, 40, 150, 30);
		center.add(tendina);
		//Etichetta per scegliere il metodo di decifratura
		etichetta3.setBounds(180, 10, 100, 20);
		center.add(etichetta3);
		//Etuchetta per immettere la chiave
		etichetta4.setBounds(290, 10, 100, 20);
		center.add(etichetta4);
		//Casella per spuntare cesare
		scelta1.setBounds(175, 40, 100, 20);
		center.add(scelta1);
		//Casella per spuntare Vigenere
		scelta2.setBounds(175, 70, 100, 20);
		center.add(scelta2);

		//tasco per il brute force 
		scelta3.setBounds(175, 100, 150, 20);
		//center.add(scelta3);
		//Campo per mettere la chiave di cesare
		casellaCesare.setBounds(290, 40, 100, 20);
		center.add(casellaCesare);
		//Campo per mettere la chiave di Vigenere
		casellaVigenere.setBounds(290, 40, 100, 20);
		center.add(casellaVigenere);
		//Etichetta per sapere la porta aperta
		etichetta5.setBounds(10, 100, 100, 20);
		center.add(etichetta5);	
		
		//Etichetta per sapere da dove arriva il messaggio da decifrare
		etichetta6.setBounds(10, 130, 300, 20);
		//center.add(etichetta6);	

		ButtonPanel.setLayout(new FlowLayout());
		ButtonPanel.add(bottone1);
		ButtonPanel.add(bottone2);
		//pannello centrale con le impostazioni per la decifrazione 
		pannelloCentro = new JPanel();
		pannelloCentro.setLayout(new GridLayout(1,2));

		//pannello laterale per i messaggi
		pannelloMessaggi = new JPanel();
		pannelloMessaggi.setLayout(new GridLayout(1,2));
		//Definizione della parte destra dell'interfaccia dove ci saranno i messaggi decifrati
		JLabel messaggioCifrato = new JLabel("Messaggio Cifrato:");
		areaMessaggioCifrato = new JTextArea();
		areaMessaggioCifrato.setPreferredSize(new Dimension(200,300));
		areaMessaggioCifrato.setLineWrap(true);
		areaMessaggioCifrato.setEditable(false);

		JPanel sottoPannelloSinistra = new JPanel();
		sottoPannelloSinistra.setLayout(new FlowLayout());
		sottoPannelloSinistra.add(messaggioCifrato);
		sottoPannelloSinistra.add(areaMessaggioCifrato);

		JLabel MessaggioDecifrato = new JLabel("Messaggio Decifrato:");
		areaMessaggioDecifrato = new JTextArea();
		areaMessaggioDecifrato.setPreferredSize(new Dimension(200,300));
		areaMessaggioDecifrato.setLineWrap(true);
		areaMessaggioDecifrato.setEditable(false);


		JPanel sottoPannelloDestra = new JPanel();
		sottoPannelloDestra.setLayout(new FlowLayout());
		sottoPannelloDestra.add(MessaggioDecifrato);
		sottoPannelloDestra.add(areaMessaggioDecifrato);

		pannelloMessaggi.add(sottoPannelloSinistra);
		pannelloMessaggi.add(sottoPannelloDestra);

		center.setMaximumSize(new Dimension(100, 100));
		pannelloCentro.add(center);
		pannelloCentro.add(pannelloMessaggi);


		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());   //Assegnamento dei layout
		c.add(nord,BorderLayout.NORTH);
		c.add(pannelloCentro,BorderLayout.CENTER);
		c.add(ButtonPanel,BorderLayout.SOUTH);
		//Aggiunta ActionListener 
		scelta1.addActionListener(this);
		scelta2.addActionListener(this);
		scelta3.addActionListener(this);
		bottone1.addActionListener(this);
		bottone2.addActionListener(this);

		this.setResizable(false);
		this.setBounds(x, y, larghezza, altezza); //Dimensione
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ricezioneMessaggi(MainWindow.getNumeroPorta());
	}
	//Finzione per rendere il menù a tendina dinamico
	public void aggiunge() {
		tendina.removeAllItems();
		for (int i =0; i<listaMessaggi.size(); i++)
			tendina.addItem (listaMessaggi.get(i));
	}
	//Funzione per decifrare con Cesare
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
	//Funzione per decifrare con Vigenere 
	public byte[] decifraVigenere(byte[] s , byte[] chiave) {
		byte[] c = new byte[s.length];
		for (int i = 0; i < s.length;i++) {
			c[i] = (byte)((byte)s[i] - (byte)chiave[i%chiave.length]);
		}
		return c;
	}
	//Socket per ricevere i messaggi
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
			JOptionPane.showMessageDialog(null, "Non è stato possibile aprire la socket", "Attenzione", JOptionPane.WARNING_MESSAGE);
		}
	}
	// implementazione action listener 
	public void actionPerformed(ActionEvent listener) {	
		int chiave;
		//controllo delle varie caselle spuntate 
		if(listener.getSource()==scelta1)
		{
			casellaCesare.setVisible(true);
			casellaVigenere.setVisible(false);
		}
		else if (listener.getSource()==scelta2)
		{
			casellaCesare.setVisible(false);
			casellaVigenere.setVisible(true);
		}
		else if (listener.getSource()==scelta3)
		{
			casellaCesare.setVisible(false);
			casellaVigenere.setVisible(false);
		}
		//decifro con cesare 
		else if(bottone1==listener.getSource()) 
		{
			if(scelta1.isSelected()) 
			{
				chiave = Integer.parseInt(casellaCesare.getText());
				byte[] messaggioDecifrato = new byte[512];
				messaggioDecifrato = decifraCesare(messaggiRicevuti.get(tendina.getSelectedIndex()), chiave);
				areaMessaggioCifrato.setText(new String(messaggiRicevuti.get(tendina.getSelectedIndex())));
				areaMessaggioDecifrato.setText(new String(messaggioDecifrato));
			}//decifro con vigenere
			else if(scelta2.isSelected()) 
			{
				char[] chiaveV = casellaVigenere.getText().toCharArray();
				if(chiaveV.length<5) 			
					JOptionPane.showMessageDialog(null,"La chiave deve essere una parola di 5 lettere", "errore" , JOptionPane.WARNING_MESSAGE);
				else 
				{
					byte [] key = new byte[chiaveV.length];
					for(	int i = 0 ; i < key.length;i++)
						key[i] = (byte)chiaveV[i];
					byte[] messaggioDecifrato = new byte[512];
					areaMessaggioDecifrato.setText(new String(messaggioDecifrato));
					areaMessaggioCifrato.setText(new String(messaggiRicevuti.get(tendina.getSelectedIndex())));
					areaMessaggioDecifrato.setText(new String(messaggioDecifrato));
				}
			}//metodo con il brute force da implementare
			else if(scelta3.isSelected())
			{
				JOptionPane.showMessageDialog(null, "Coming soon..." , "messaggio" , JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if(bottone2==listener.getSource())
		{
			System.exit(0);
		}
	}
}