package puertoricotr.stockageoutilsjeux;

public class Banque {
	private int nbDoublon;
	private int nbColon;
	private int nbPointVictoire;

	public Banque(int doublon, int colon, int pointVictoire) {
		this.nbDoublon = doublon;
		this.nbColon = colon;
		this.nbPointVictoire = pointVictoire;
	}

	public int getNbDoublon() {
		return this.nbDoublon;
	}
	public int getNbColon() {
		return this.nbColon;
	}
	public int getNbPointVictoire() {
		return nbPointVictoire;
	}

	public String getAffichage(){

		return "Banque \t\t\t: PV : " +  this.nbPointVictoire + "\tDoublons : " + this.nbDoublon
				                      + "\t\tColons : " + this.nbColon;
	}

	public void setNbDoublon(int doublon) {
		this.nbDoublon = doublon;
	}
	public void setNbColon(int colon) {
		this.nbColon = colon;
	}
	public void setNbPointVictoire(int nbPointVictoire) {
		this.nbPointVictoire = nbPointVictoire;
	}

	public int decrementeNbDoublon(int nbDoublon){
		int doublon = Math.min(this.nbDoublon, nbDoublon);
		this.nbDoublon -= doublon;
		return doublon;
	}

	public int decrementeNbPointVictoire(int nbPointVictoire){
		int pointVictoire = Math.min(this.nbPointVictoire, nbPointVictoire);
		this.nbPointVictoire -= pointVictoire;

		return pointVictoire;
	}

	public void ajouteNbDoublon(int nbdoublon){
		this.nbDoublon += nbdoublon;
	}

	public void decrementeNbColon(int nbColon){
		this.nbColon -= Math.min(this.nbColon, nbColon);
	}
}
