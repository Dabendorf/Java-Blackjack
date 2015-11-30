package blackjack;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import blackjack.Spielkarte.Farbe;
import blackjack.Spielkarte.Zahl;

/**
 * Dies ist die Hauptklasse des Blackjackspiels. Sie steuert sowohl die Generierung der graphischen Oberflaeche als auch die Steuerung des Spielalgorithmus.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Blackjack {

	private JFrame frame1 = new JFrame("Blackjack");
	private JButton ButtonKarte = new JButton("Karte");
	private JButton ButtonFertig = new JButton("Fertig");
	private JButton ButtonNeustart = new JButton("Neustart"); 
	private JLabel LabelKartenSp = new JLabel();
	private JLabel LabelKartenKi = new JLabel();
	  
	private ArrayList<Spielkarte> karten = new ArrayList<Spielkarte>();
	private ArrayList<Spielkarte> kartensp = new ArrayList<Spielkarte>();
	private ArrayList<Spielkarte> kartenki = new ArrayList<Spielkarte>();
	private int sumsp, sumki;
	private int pktsp=0, pktki=0;
	
	public Blackjack() {
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(445,125);
		frame1.setResizable(false);
		
		Container cp = frame1.getContentPane();
		cp.setLayout(null);
		
		ButtonKarte.setBounds(350, 10, 90, 25);
		ButtonKarte.setVisible(true);
		ButtonKarte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				neuekarte();
			}
		});
		cp.add(ButtonKarte);
		
		ButtonFertig.setBounds(350, 40, 90, 25);
		ButtonFertig.setVisible(true);
		ButtonFertig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				auswertung();
			}
		});
		cp.add(ButtonFertig);
		
		ButtonNeustart.setBounds(350, 70, 90, 25);
		ButtonNeustart.setVisible(true);
		ButtonNeustart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				neustart();
			}
		});
		cp.add(ButtonNeustart);
		
		LabelKartenKi.setBounds(15, 15, 300, 20);
		LabelKartenKi.setText("Computer:");
		LabelKartenKi.setVisible(true);
		cp.add(LabelKartenKi);
		
		LabelKartenSp.setBounds(15, 55, 300, 20);
		LabelKartenSp.setText("Spieler:");
		LabelKartenSp.setVisible(true);
		cp.add(LabelKartenSp);
		
		frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
		
		kartenmischen();
		computerstartkarten();
	}
	
	/**
	 * Diese Methode generiert sechs 52er Decks Karten und mischt diese. Anschliessend werden dem Spieler zwei Karten ausgeteilt.
	 */
	private void kartenmischen() {
		for(int i=0;i<6;i++) {
			for(Farbe farbe:Farbe.values()) {
				for(Zahl zahl:Zahl.values()) {
					karten.add(new Spielkarte(zahl, farbe));
				}
			}
		}
		Collections.shuffle(karten);
		neuekarte();
		neuekarte();
	}
	
	/**
	 * Diese Methode generiert die zwei Startkarten des Computers.
	 */
	private void computerstartkarten() {
		for(int i=0;i<2;i++) {
			kartenki.add(karten.get(0));
			karten.remove(0);
		}
		LabelKartenKi.setText("Computer: "+kartenki.get(0).getAnzeige()+" *");
	}
	
	/**
	 * Diese Methode laesst den Spieler eine neue Karte ziehen. Es wird ueberprueft, ob er dabei die 21 Punkte ueberschreitet.
	 */
	private void neuekarte() {
		kartensp.add(karten.get(0));
		sumsp += karten.get(0).getWert();
		karten.remove(0);
		
		String anzeige = "";
		for(Spielkarte k:kartensp) {
			anzeige += " "+k.getAnzeige();
		}
		LabelKartenSp.setText("Spieler:"+anzeige);
		if(verloren21plus(true) == true) {
			auswertung();
		}
	}
	
	/**
	 * Diese Methode ueberprueft, ob beim Ziehen einer Karte 21 Punkte ueberschritten wurden.<br>
	 * Sollte dies passiert sein, aber ein Ass im Spiel sein, wird die Summe um 10 gesenkt und der Asswert zu 1.
	 * @param spieler Nimmt einen boolean entgegen, ob eine Spieler oder eine Computerkarte gezogen wurde.
	 * @return Gibt einen boolean zurueck, ob das Spiel verloren wurde.
	 */
	private boolean verloren21plus(boolean spieler) {
		if(sumsp > 21) {
			if(spieler) {
				sumsp = 0;
				for(Spielkarte k:kartensp) {
					if(k.getZahl().equals(Zahl.Ass)) {
						sumsp ++;
					} else {
						sumsp += k.getWert();
					}
				}
			} else {
				sumki = 0;
				for(Spielkarte k:kartenki) {
					if(k.getZahl().equals(Zahl.Ass)) {
						sumki ++;
					} else {
						sumki += k.getWert();
					}
				}
			}
			if(sumsp > 21) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Diese Methode wertet aus, wer gewonnen hat, uebergibt den Siegstring der Methode auswertungsdialog() und startet eine neue Partie.
	 */
	private void auswertung() {
		computerbeenden();
		if(verloren21plus(true) == true) {
			pktki += 1;
			auswertungsdialog("Du hast die 21-Punkte-Grenze überschritten.\nDamit hast Du verloren.", "Verloren");
		} else if(sumki > 21) {
			pktsp += 1;
			auswertungsdialog("Der Computer hat mehr als 21 Punkte.\nDamit hast Du gewonnen.", "Gewonnen");
		} else if(sumki > sumsp) {
			pktki += 1;
			auswertungsdialog("Der Computer liegt näher an 21 dran als Du.\nDamit hast Du verloren.", "Verloren");
		} else if(sumki == sumsp) {
			auswertungsdialog("Ihr habt die gleiche Summe.\nDieses Spiel endet unentschieden.", "Unentschieden");
		} else {
			pktsp += 1;
			auswertungsdialog("Du liegst näher an 21 dran als der Computer.\nDamit hast Du gewonnen.", "Gewonnen");
		}
		neuepartie();
	}
	
	/**
	 * Diese Methode sagt an, wie der Spielstand ist und nimmt das Ergebnis der aktuellen Partie entgegen.
	 * @param str Nimmt den Auswertungsstring entgegen.
	 * @param title Nimmt den Auswertungstitel entgegen.
	 */
	private void auswertungsdialog(String str, String title) {
		if(pktki > pktsp) {
			JOptionPane.showMessageDialog(null, str+"\nDer Computer führt mit "+pktki+" zu "+pktsp+".\nEine neue Runde wird gestartet.", title, JOptionPane.PLAIN_MESSAGE);
		} else if (pktki == pktsp) {
			JOptionPane.showMessageDialog(null, str+"\nEs herrscht Gleichstand mit "+pktki+" zu "+pktsp+".\nEine neue Runde wird gestartet.", title, JOptionPane.PLAIN_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, str+"\nDu liegst mit "+pktsp+" zu "+pktki+" vorn.\nEine neue Runde wird gestartet.", title, JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	/**
	 * Diese Methode guckt, wann der Computer aufhoert mit dem Ziehen von Karten. Dies erfolgt, wenn er die Summe 17 erreicht hat.
	 */
	private void computerbeenden() {
		String anzeige = "";
		for(Spielkarte k:kartenki) {
			sumki += k.getWert();
			anzeige += " "+k.getAnzeige();
		}
		while(sumki < 17) {
			kartenki.add(karten.get(0));
			sumki += karten.get(0).getWert();
			anzeige += " "+karten.get(0).getAnzeige();
			karten.remove(0);
		}
		LabelKartenKi.setText("Computer: "+anzeige);
	}
	
	/**
	 * Diese Methode startet eine neue Partie, nicht zu verwechseln mit einem komplett neuen Spiel.
	 */
	private void neuepartie() {
		karten.clear();
		kartensp.clear();
		kartenki.clear();
		sumsp = 0;
		sumki = 0;
		kartenmischen();
		computerstartkarten();
	}

	/**
	 * Diese Methode startet ein neues Spiel mit dem Spielstand 0:0.
	 */
	private void neustart() {
		JOptionPane.showMessageDialog(null, "Ein neues Spiel wird gestartet und die Punkte zurückgesetzt.", "Neustart", JOptionPane.PLAIN_MESSAGE);
		pktsp = 0;
		pktki = 0;
		neuepartie();
	}
	
	public static void main(String[] args) {
		new Blackjack();
	}
}