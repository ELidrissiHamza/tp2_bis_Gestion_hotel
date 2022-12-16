package tp2_bis.gestion_hotel;

public class Chambre {

	private int numChambre;
	private double prixChambre ;
	private int categorieChambre;
	private int capaciteChambre;
	private char etatChambre;
	
	Chambre(int num,double prix,int cat,int cap,char etat)
	{
		this.numChambre=num;
		this.prixChambre=prix;
		this.categorieChambre=cat;
		this.capaciteChambre=cap;
		this.etatChambre=etat;
	}
	public int getCategorieChambre() {
		return categorieChambre;
	}
	public void setCategorieChambre(int categorieChambre) {
		this.categorieChambre = categorieChambre;
	}
	Chambre(int num,double prix,int cat,int cap)
	{
		this.numChambre=num;
		this.prixChambre=prix;
		this.categorieChambre=cat;
		this.capaciteChambre=cap;
		this.etatChambre='L';
	}
	
	
	@Override
	public String toString() {
		return "Chambre [numChambre=" + numChambre + ", prixChambre=" + prixChambre + ", categorieChambre="
				+ categorieChambre + ", capaciteChambre=" + capaciteChambre + ", etatChambre=" + etatChambre + "]";
	}
	public int getNumChambre() {
		return numChambre;
	}
	public void setNumChambre(int numChambre) {
		this.numChambre = numChambre;
	}
	public int getCapaciteChambre() {
		return capaciteChambre;
	}
	public void setCapaciteChambre(int capaciteChambre) {
		this.capaciteChambre = capaciteChambre;
	}
	public char getEtatChambre() {
		return etatChambre;
	}
	public void setEtatChambre(char etatChambre) {
		this.etatChambre = etatChambre;
	}
	public double getPrixChambre() {
		return prixChambre;
	}
	public void setPrixChambre(double prixChambre) {
		this.prixChambre = prixChambre;
	}
	
}
