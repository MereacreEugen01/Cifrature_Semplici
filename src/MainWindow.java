/**
 ** Nome programma:Cifrature semplici / MainWindow
 ** Versione programma:1.0
 ** Data:23/11/21
 ** Autore: Eugen Mereacre 
 ** Problema: Vedere relazione o consegna per testo
 ** Dati:
 ** Osservazioni: Questa classe server per scegliere il tipo di programma da avviare  
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class MainWindow extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;

	public static int numeroPorta;
	String porta;
	private JPanel pannelloNord, pannelloCentro, pannelloPrincipale, pannelloSud, pannelloCentroSu, pannelloCentroGiu;//sottopannelli
	private JLabel titolo, messaggioSu, messaggioGiu;//Messaggi a schermo per l'utente 
	private JButton procedi, annulla;//bottoni per procedere e per per chiudere la scherda
	private JCheckBox opzioneUno, opzioneDue;//checkbox per le scelte 
	private int altezza= 200, larghezza= 630;//dimensioni della finestra
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); //variabile di supporto per posizionare la finestra al centro dello schermo
	private int x =  (dim.width-larghezza)/2, y = (dim.height-altezza)/2;//coordinate x e y per posizionare la finestra in maniera dinamica
	
	public MainWindow()
	{
		super("Cifrature semplici");// utilizzo la classe padre per definire il nome 
		//settiamo la posizione in modo dinamico e le dimensioni della scheda
		setBounds(x, y, larghezza, altezza);
		pannelloPrincipale = (JPanel)getContentPane();
		pannelloNord = new JPanel();
		titolo = new JLabel("Che ruolo ha questa macchina ?");
		//aggiungo i componenti ai sottopannelli
		pannelloNord.setLayout(new FlowLayout());
		pannelloNord.add(titolo);
		//definisco le barie scelte e le aggiungo ad un buttonGroup
		opzioneUno = new JCheckBox("Secret Sender ");
		opzioneDue = new JCheckBox("Secret Inbox  "); 
		ButtonGroup gruppoScelta = new ButtonGroup();
		gruppoScelta.add(opzioneUno);
		gruppoScelta.add(opzioneDue);
		//definisco i componenti del sottopannello sud
		procedi = new JButton("Procedi");
		annulla = new JButton("Annulla");
		pannelloSud = new JPanel();
		pannelloSud.setLayout(new FlowLayout());
		pannelloSud.add(procedi);
		pannelloSud.add(annulla);
		//similente a prima c'? bisogno che gli elementi a schermo siano in colonna quindi uso un GridLayout
		messaggioSu = new JLabel("Il secret sender ? quello che cifra e invia il messaggio");
		messaggioSu.setHorizontalAlignment(JLabel.LEFT);
		messaggioSu.setForeground(Color.RED);
		messaggioGiu = new JLabel("Il secret inbox ? quello che riceve e decifra il messaggio");
		messaggioGiu.setHorizontalAlignment(JLabel.LEFT);
		messaggioGiu.setForeground(Color.RED);
		pannelloCentro = new JPanel();
		pannelloCentro.setLayout(new GridLayout(2,1));
		pannelloCentroSu = new JPanel();   
		pannelloCentroSu.setLayout(new BorderLayout());
		pannelloCentroGiu = new JPanel();
		pannelloCentroGiu.setLayout(new BorderLayout());
		//aggiungo le varie cose al pannello centrale
		pannelloCentroSu.add(opzioneUno, BorderLayout.WEST );
		pannelloCentroSu.add(messaggioSu, BorderLayout.CENTER);
		pannelloCentroGiu.add(opzioneDue, BorderLayout.WEST);
		pannelloCentroGiu.add(messaggioGiu, BorderLayout.CENTER);
		pannelloCentro.add(pannelloCentroSu);
		pannelloCentro.add(pannelloCentroGiu);
		//aggiungo al pannello prinipale tutti i vari sottopannelli nelle varie posizioni giuste
		pannelloPrincipale.add(pannelloNord, BorderLayout.NORTH);
		pannelloPrincipale.add(pannelloCentro, BorderLayout.CENTER);
		pannelloPrincipale.add(pannelloSud, BorderLayout.SOUTH);
		//aggiungo la gestione degli eventi sui pulsanti 
		procedi.addActionListener(this);
		annulla.addActionListener(this);
		//mettiamo la finestra visibile e il fatto che si chiuda realmente se chiudiamo la scheda 
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent listener) 
	{
		//verifico se almeno una delle due scelte ? selezionata 
		if(opzioneUno.isSelected()==false & opzioneDue.isSelected()==false && procedi==listener.getSource())
		{
			JOptionPane.showMessageDialog(null,"Scegliere almeno un opzione", "Attenzione", JOptionPane.WARNING_MESSAGE);
		}
		//Se viene selezionata la scelta 1 (Secret Inbox) allora apparir? un messaggio di conferma e si apparir? la finestra del Secret Sender
		if(procedi==listener.getSource() && opzioneUno.isSelected())
		{
			//JOptionPane.showMessageDialog(null,"Hai selezionato che questa macchina ? il Secret Sender", "Avviso" , JOptionPane.INFORMATION_MESSAGE);
			Sender finestraSender = new Sender();
			finestraSender.setVisible(true);
			this.dispose();
		}
		//Se viene selezionata la scelta 2 (Secret Inbox) allora apparir? un messaggio di conferma e si apparir? la finestra del Secret Inbox
		if(procedi==listener.getSource() && opzioneDue.isSelected())
		{
			do {
				porta = JOptionPane.showInputDialog(null,"Inserire il numero della porta", "Numero Porta" , JOptionPane.QUESTION_MESSAGE);
				System.out.println(porta);
			}while(!eUnNumero(porta) /*& Integer.parseInt(porta) >= 50000*/); //controllo da fixare 
			numeroPorta=Integer.parseInt(porta);
			Inbox finestraInbox = new Inbox();
			finestraInbox.setVisible(true);
			this.dispose();
		}
		//se viene premuto il tasto annulla si chiude il programma
		if(annulla==listener.getSource())
		{
			System.exit(0);
		}
	}

	public static int getNumeroPorta() {
		return numeroPorta;
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

}
