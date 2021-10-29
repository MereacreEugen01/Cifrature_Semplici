

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class MainWindow extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;

	private JPanel pannelloNord, pannelloCentro, pannelloPrincipale, pannelloSud, pannelloCentroSu, pannelloCentroGiu;//sottopannelli
	private JLabel titolo, messaggioSu, messaggioGiu;//Messaggi a schermo per l'utente 
	private JButton procedi, annulla;//bottoni per procedere e per per chiudere la scherda
	private JCheckBox opzioneUno, opzioneDue;//checkbox per le scelte 
	private int altezza= 200, larghezza= 630;//dimensioni della finestra
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); //variabile di supporto per posizionare la finestra al centro dello schermo
	private int x =  (dim.width-larghezza)/2, y = (dim.height-altezza)/2;//coordinate x e y per posizionare la finestra in maniera dinamica
	
	public MainWindow()
	{
		super("Grid Computing");// utilizzo la classe padre per definire il nome 
		//settiamo la posizione in modo dinamico e le dimensioni della scheda
		setBounds(x, y, larghezza, altezza);
		pannelloPrincipale = (JPanel)getContentPane();
		pannelloNord = new JPanel();
		titolo = new JLabel("Che ruolo ha questa macchina ?");
		//aggiungo i componenti ai sottopannelli
		pannelloNord.setLayout(new FlowLayout());
		pannelloNord.add(titolo);
		//definisco le barie scelte e le aggiungo ad un buttonGroup
		opzioneUno = new JCheckBox("Grid Master  ");
		opzioneDue = new JCheckBox("Grid Node      "); 
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
		//similente a prima c'è bisogno che gli elementi a schermo siano in colonna quindi uso un GridLayout
		messaggioSu = new JLabel("Il Grid Master è il cuore del sistema ed avrà il compito di organizzare il lavoro dei Grid Node.");
		messaggioSu.setHorizontalAlignment(JLabel.LEFT);
		messaggioSu.setForeground(Color.RED);
		messaggioGiu = new JLabel("Il Grid Node è il programma che svolge effettivamente il lavoro.");
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
		//verifico se almeno una delle due scelte è selezionata 
		if(opzioneUno.isSelected()==false & opzioneDue.isSelected()==false && procedi==listener.getSource())
		{
			JOptionPane.showMessageDialog(null,"Scegliere almeno un opzione", "Attenzione", JOptionPane.WARNING_MESSAGE);
		}
		//Se viene selezionata la scelta 1 (GridMaster) allora apparirà un messaggio di conferma e si apparirà la finestra del GridMaster 
		if(procedi==listener.getSource() && opzioneUno.isSelected())
		{
			JOptionPane.showMessageDialog(null,"Hai selezionato che questa macchina è il Grid Master", "Avviso" , JOptionPane.INFORMATION_MESSAGE);
			Sender finestraMaster = new Sender();
			this.dispose();
		}
		//Se viene selezionata la scelta 2 (GridNode) allora apparirà un messaggio di conferma e si apparirà la finestra del GridNode 
		if(procedi==listener.getSource() && opzioneDue.isSelected())
		{
			JOptionPane.showMessageDialog(null,"Hai selezionato che questa macchina è un Grid Node", "Avviso" , JOptionPane.INFORMATION_MESSAGE);
			Inbox finestra = new Inbox();
			this.dispose();
		}
		//se viene premuto il tasto annulla si chiude il programma
		if(annulla==listener.getSource())
		{
			System.exit(0);
		}
	}


}
