package blackjack;

/**
 * Diese Klasse stellt eine Spielkarte des Spiels dar. Sie enthaelt als Eigenschaften die Farbe und den Wert der Karte.
 * Ferner gibt sie den Wert der Karte als Integer zurueck.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Spielkarte {
	
	public enum Zahl {Zwei, Drei, Vier, Fünf, Sechs, Sieben, Acht, Neun, Zehn, Bube, Dame, Koenig, Ass};
	public enum Farbe {Pik,Kreuz,Herz,Karo};
	
	private Zahl zahl;
	private Farbe farbe;
	private int wert;
	private String anzeige;

	public Spielkarte(Zahl zahl, Farbe farbe) {
		this.zahl = zahl;
		this.farbe = farbe;
		
		switch(this.zahl) {
		case Zwei:
			this.wert = 2;
			this.anzeige = "2";
			break;
		case Drei:
			this.wert = 3;
			this.anzeige = "3";
			break;
		case Vier:
			this.wert = 4;
			this.anzeige = "4";
			break;
		case Fünf:
			this.wert = 5;
			this.anzeige = "5";
			break;
		case Sechs:
			this.wert = 6;
			this.anzeige = "6";
			break;
		case Sieben:
			this.wert = 7;
			this.anzeige = "7";
			break;
		case Acht:
			this.wert = 8;
			this.anzeige = "8";
			break;
		case Neun:
			this.wert = 9;
			this.anzeige = "9";
			break;
		case Zehn:
			this.wert = 10;
			this.anzeige = "10";
			break;
		case Bube:
			this.wert = 10;
			this.anzeige = "B";
			break;
		case Dame:
			this.wert = 10;
			this.anzeige = "D";
			break;
		case Koenig:
			this.wert = 10;
			this.anzeige = "K";
			break;
		case Ass:
			this.wert = 11;
			this.anzeige = "A";
			break;
		default:break;
		}
	}

	public Zahl getZahl() {
		return zahl;
	}

	public int getWert() {
		return wert;
	}

	public Farbe getFarbe() {
		return farbe;
	}
	
	public String getAnzeige() {
		return anzeige;
	}

}