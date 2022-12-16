package tp2_bis.gestion_hotel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.Vector;



class ControlerPrix extends Exception{
	private double prix;
	public ControlerPrix(double prix_)
	{
		this.prix=prix_;
		
	}
	public String toString()
	{
		return "le prix  est inferieur a 0  !!!\n";
	}
}

class ControlerCapaciter extends Exception{
	private int cap;
	public ControlerCapaciter(int cap_) {
		this.cap=cap_;
	}
	public String toString() {
		return "La capacite est inferieur a 1 ou superieur a 4 \n";
	}
}

public class GestionChambre {

	private Chambre[] chambres;
	private int nbmax;
	private int ind;
	GestionChambre(int nbmax_){
		this.nbmax=nbmax_;
		this.ind=0;
		this.chambres=new Chambre[this.nbmax];
	}

	public Integer convertInt(String chaine) {
		try {
			return Integer.parseInt(chaine);
		} catch (Exception e) {
			System.out.println("Probleme de convertion de : " + chaine);
			return null;
		}
	}

	public Double convertDouble(String chaine) {
		try {
			return Double.parseDouble(chaine);
		} catch (Exception e) {
			System.out.println("Probleme de convertion de : " + chaine);
			return null;
		}
	}
	
	public void TabSature()
	{
		try {
		if(this.ind==this.nbmax)
		{
		throw new Exception();	
		}
		}catch(Exception e)
		{
			System.out.println("Tableau des chambre Sature !! "+e);
		}
	}
	
	public void TabVide()
	{
		try {
		if(this.ind==0)
		{
		throw new Exception();	
		}
		}catch(Exception e)
		{
			System.out.println("Tableau des chambre est vide !! "+e);
		}
	}

	public Chambre SaisieChambre() throws ControlerPrix, ControlerCapaciter {
		Scanner clavier = new Scanner(System.in);
		String tmp;
		Integer numch_, capch_, catch_;
		Double prixch_;
		while(true) {
		System.out.println("Entrer le numero du chambre \n");
		tmp = clavier.next();
		numch_ = this.convertInt(tmp);
		if(this.ChambreExist(numch_)==-1) break;
		else System.out.println("Le numero de chambre que vous avez entrer exist deja !!");
		}
		System.out.println("Entrer le prix du chambre \n");
		tmp = clavier.next();
		prixch_ = this.convertDouble(tmp);
		System.out.println("Entrer la categorie du chambre \n");
		tmp = clavier.next();
		catch_ = this.convertInt(tmp);
		System.out.println("Entrer la capacite du chambre \n");
		tmp = clavier.next();
		capch_ = this.convertInt(tmp);
		try {
			if (prixch_ < 0) {
				
				throw new ControlerPrix(prixch_);
				
			}
		} catch (ControlerPrix e) {
			System.out.println(e);
			return null;
		}
		try {
			if (capch_ < 1 || capch_ > 4)
				throw new ControlerCapaciter(capch_);
		} catch (ControlerCapaciter e) {
			System.out.println(e);
			return null;
		}

		return  new Chambre(numch_, prixch_, catch_, capch_);
		
	}

	void SaisieTabChambre() throws ControlerPrix, ControlerCapaciter {

		
	 for(int i = 0; i < this.nbmax; i++) {
			this.chambres[i] = this.SaisieChambre();
			this.ind++;
		}
		
	}

	public void AfficherTabCat(int categorie) {
		for (int i = 0; i < this.ind; i++) {
			if (chambres[i].getCategorieChambre() == categorie)
				System.out.println(chambres[i]);
		}
	}

	public void AfficherTab() {
		for (int i = 0; i < this.ind; i++) {

			System.out.println(chambres[i]);
		}
	}

	public void Trier_Tab_Chambre() {

		int i, j;
		Chambre c;
		for (i = 0; i < this.ind; i++) {
			for (j = i + 1; j < this.ind; j++) {
				if (chambres[i].getCapaciteChambre() > chambres[j].getCapaciteChambre()) {
					c = chambres[i];
					chambres[i] = chambres[j];
					chambres[j] = c;
				}
			}
		}
	}

	
	public void deplacerFich()
	{
		try {
			// On crée un flux
			DataOutputStream dos = new DataOutputStream(new FileOutputStream("chambres.dat"));
			// On écrit quelques données de base
			try {
				dos.writeInt(this.ind);
			// On écrit les données
				for(int i=0;i<this.ind;i++) {
			if(this.chambres[i]!=null) {		
			dos.writeInt(chambres[i].getNumChambre());
			
			dos.writeDouble(chambres[i].getPrixChambre());
		
			dos.writeInt(chambres[i].getCategorieChambre());
			
			dos.writeInt(chambres[i].getCapaciteChambre());
			
			dos.writeChar(chambres[i].getEtatChambre());
			}
				}
			// Puis on vide le buffer
			dos.flush();
			} finally {
			dos.close();
			}
			} catch(IOException e) {
				System.out.println(e);
			}
	}
	
	@SuppressWarnings("deprecation")
	public void recupererFichier()
	{
		try {
			// On cree un flux
			DataInputStream dis = new DataInputStream(new FileInputStream("chambres.dat"));	
			String chaine;
			try {
					int nbrchambres;
					int nbch ;
					double prixch;
					int catech,capch;
					char etatch;
					nbrchambres=dis.readInt();
					for(int i=0;i<nbrchambres;i++) {
						nbch=dis.readInt();
						//System.out.println("****nbch*"+nbch);
						prixch=dis.readDouble();
						//System.out.println("****prixch*"+nbch);
						catech=dis.readInt();
						//System.out.println("****catech*"+catech);
						capch=dis.readInt();
						//System.out.println("****capch*"+capch);
						etatch=dis.readChar();
						//System.out.println("****etatch*"+etatch);
						this.chambres[this.ind++]=new Chambre(nbch,prixch,catech,capch,etatch);
						
					}
					
			} finally {
			dis.close();
			}
			
			} catch(IOException e){
				e.printStackTrace();
			}
	}
	
	public int ChambreExist(int num)
	{
		for(int i=0;i<this.ind;i++)
		{
			if(this.chambres[i].getNumChambre()==num)
				return i;
		}
		return -1;
	}
	
	public void AjouterChambre() throws ControlerPrix, ControlerCapaciter
	{
		this.TabSature();
		System.out.println("Entrer Les informations du chambre :");
		Chambre ch = this.SaisieChambre();
		if(ch!=null) {
		this.chambres[this.ind]=ch;
		this.ind++;}
		else
			System.out.println("Probleme d'ajout !");
	}
	
	public void SupprimerChambre(int num) {
		this.TabVide();
		int indice=this.ChambreExist(num);
		if(indice==-1)
		{
			System.out.println("Chambre n'existe pas !!");
		}
		else
		{
			for(int i=indice;i<this.ind-1;i++)
				this.chambres[i]=this.chambres[i+1];
			this.ind--;
		}
		
	}
	
	
	public void ModifierChambre(int num)
	{
		this.TabVide();
		int indice=this.ChambreExist(num);
		if(indice==-1)
		{
			System.out.println("Chambre n'existe pas !!");
		}
		else
		{
			this.ModifierChambre2(num);
		}
		
	}
	
	public void ModifierChambre2(int num)
	{
		Scanner clavier = new Scanner(System.in);
		char choix,etat;
		String tmp;
		
		do {
		System.out.println("Entrer votre choix :");
		System.out.println(" a : Pour modifier le prix \n"
				+ " b : Pour modifier la categorie \n"
				+ " c : Pour modifier la capacite \n"
				+ " d : Pour modifier l'etat \n"
				+ " q : Pour sortir \n ");
		choix = clavier.next().charAt(0);
		 switch(choix)
		 {
		 case 'a' : System.out.println("Donner le nouveau prix ");
		 			tmp=clavier.next();
		 			this.chambres[num].setPrixChambre(this.convertDouble(tmp));
			 	break;
		 case 'b' :System.out.println("Donner la nouvelle categorie ");
					tmp=clavier.next();
					this.chambres[num].setCategorieChambre(this.convertInt(tmp));
			   break;
		 case 'c' :System.out.println("Donner la nouvelle capacite ");
					tmp=clavier.next();
					this.chambres[num].setCapaciteChambre(this.convertInt(tmp));
			   break;
		 case 'd' :System.out.println("Donner la nouvelle etat ");
		 			etat=clavier.next().charAt(0);
		 			this.chambres[num].setEtatChambre(etat);
			   break;
		 case 'q' :System.out.println("fin");
		 			break;
		 default :System.out.println("choix incorrecte !!");			
		 }
		}while(choix!='q');

	}
	
	void consulter_chambre(int num)
	{
		if(this.ChambreExist(num)==-1)
			System.out.println("Chambre n'existe pas !!");
		else
			System.out.println(this.chambres[num]);
	}
	
	public void deplacer_FichSeq(int cat) throws IOException
	{
		try {
			// On ouvre un fichier
			File f = new File("chambres_seq.dat");
			// On ouvre un flux à accées aléatoire
			// "rw" signifie lecture écriture
			 RandomAccessFile raf = new RandomAccessFile(f, "rw");
			 for(int i=0;i<this.ind;i++) {
				 if(this.chambres[i].getCategorieChambre()==cat) {
			 raf.writeInt(this.chambres[i].getNumChambre());
			 raf.writeDouble(this.chambres[i].getPrixChambre());
			 raf.writeInt(this.chambres[i].getCategorieChambre());
			 raf.writeInt(this.chambres[i].getCapaciteChambre());
			 raf.writeChar(this.chambres[i].getEtatChambre());
			 raf.seek(f.length());
				 }
				 
				 
			 }
			// Puis on ferme le flux
			 raf.close();
			} catch(FileNotFoundException ef){}
			
	}

	public void recup_seq() throws IOException
	{
		try {
			// On ouvre un fichier
			File f = new File("chambres_seq.dat");
			// On ouvre un flux à accées aléatoire
			// "rw" signifie lecture écriture
			 RandomAccessFile raf = new RandomAccessFile(f, "r");
			 int nbch ;
				double prixch;
				int catech,capch;
				char etatch;
				nbch=raf.readInt();
				System.out.println("****nbch*"+nbch);
				prixch=raf.readDouble();
				System.out.println("****prixch*"+prixch);
				catech=raf.readInt();
				System.out.println("****catech*"+catech);
				capch=raf.readInt();
				System.out.println("****capch*"+capch);
				etatch=raf.readChar();
				System.out.println("****etatch*"+etatch);
				 
			 			// Puis on ferme le flux
			 raf.close();
			} catch(FileNotFoundException ef){}
	}
	
	
	
	public Vector<Chambre> copier_chambre_libre()
	{
		Vector<Chambre> vect=new Vector<Chambre> ();
		for(int i=0;i<this.ind;i++)
		{
			if(this.chambres[i].getEtatChambre()=='L')
			   vect.add(this.chambres[i]);
		}
		return vect;
	}
	
	public double recette_maximale()
	{
		double somme=0.0;
		for(int i=0;i<this.ind;i++)
		{
			somme+=this.chambres[i].getPrixChambre();
		}
		return somme;
	}
	
	public double recette_jour()
	{
		double somme=0.0;
		for(int i=0;i<this.ind;i++)
		{
			if(this.chambres[i].getEtatChambre()=='O')
			   somme+=this.chambres[i].getPrixChambre();
		}
		return somme;
	}
	
	
	
	
	
	public void menu() throws ControlerPrix, ControlerCapaciter, IOException
	{
		
		Scanner clavier = new Scanner(System.in);
		System.out.println(" Application : Gestion des chambres  ");
		int choix;
		int num;
		char rep;
		Vector<Chambre> vec = new Vector<Chambre>();
		do
		{
			
			System.out.println("Entrer votre choix ...");
			System.out.println(" 1 : pour Ajouter une chambre \n"
							+ " 2 : pour supprimer une chambre \n"
							+ " 3 : pour modifier une chambre \n"
							+ " 4 : pour afficher les chambres d'une categorie \n"
							+ " 5 : pour afficher toutes les chambres \n"
							+ " 6 : pour remplir toutes les chambres une seule fois \n"
							+ " 7 : pour trier les chambres selon la capacite \n"
							+ " 8 : pour deplacer les chambres d'une categorie vers un fichier sequentiel \n"
							+ " 9 : pour remplir un vecteur avec les chambres libres \n "
							+ "10 : pour calculer la recette maximale \n"
							+ " 11 : pour calculer la recette du jour \n"
							+ " 12 : pour consulter une chambe   \n"
							+ " 13 : pour sortir ");
							
			
			choix=clavier.nextInt();
			switch(choix)
			{
			case 1 : this.AjouterChambre();
					 this.deplacerFich();
					 break;
			case 2 :System.out.println("Entrer le numero de chambre que vous voulez supprimer");		 
					num=clavier.nextInt();
					this.SupprimerChambre(num);
					this.deplacerFich();
					break;
			case 3 :System.out.println("Entrer le numero de chambre que vous voulez modifier");		 
					num=clavier.nextInt(); 
					this.ModifierChambre(num);
					this.deplacerFich();
					break;
			case 4 : System.out.println("Entrer la categorie ");		 
					num=clavier.nextInt(); 
					this.AfficherTabCat(num);
					break;
			case 5 :this.AfficherTab();
					break;
			case 6 : this.SaisieTabChambre();
					  this.deplacerFich();
					break;
			case 7 : this.Trier_Tab_Chambre();
					 this.deplacerFich();	
					break;
			case 8 : System.out.println("Entrer la categorie ");		 
					num=clavier.nextInt(); 
					this.deplacer_FichSeq(num);	
					break;
			case 9 : vec=this.copier_chambre_libre();
					System.out.println("Vou voulez afficher ce vecteur ?");
					rep=clavier.next().charAt(0);
					if(rep=='y'||rep=='Y')
					{
						for(Chambre elem:vec)
							System.out.println(elem);
					}
					break;
			case 10 : System.out.println("La recette maximale est : "+this.recette_maximale());
						break;
			case 11 : System.out.println("La recette reel est : "+this.recette_jour());
					break;
			case 12 : System.out.println("Entrer le numero de chambre que vous voulez consulter");		 
					num=clavier.nextInt();
					this.consulter_chambre(num);
					break;
			case 13 : System.out.println("FIN...");break;
			default : System.out.println("Choix incorrecte !!");
			
			}
			
			
		}while(choix!=13);
	}
	
	
	
	public static void main(String[] args) throws ControlerPrix, ControlerCapaciter, IOException {
		GestionChambre gc = new GestionChambre(6);
		//gc.SaisieTabChambre();
//		gc.AfficherTabCat(3);
//		gc.Trier_Tab_Chambre();
//		System.out.println("**********************************");
//		gc.AfficherTab();
//		gc.deplacerFich();
//	gc.AjouterChambre();
//		gc.AjouterChambre();
//		gc.AjouterChambre();
//		
//		gc.AjouterChambre();
//		gc.deplacerFich();
//		gc.AfficherTab();
//		gc.ModifierChambre(0);
//		gc.AfficherTab();
		gc.recupererFichier();
		//gc.AfficherTab();
		//gc.deplacer_FichSeq(1);
		//gc.recup_seq();
		//System.out.println(gc.recette_jour());
		gc.menu();
	}

}


