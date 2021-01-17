package Map;

import java.util.ArrayList;

import Entity.*;
import GameManager.*;

public class Map
{
	private int longueur;
	private int largeur;
	private String nom;
	private String[] sorties;
	
	private ArrayList<Plante> plantesSurLaCarte;
	
	public Map(int longueur,int largeur,String nom, String[] sorties)
	{
		this.longueur = longueur;
		this.largeur = largeur;
		this.nom = nom;
		this.sorties = sorties;
		plantesSurLaCarte = new ArrayList<Plante>();
	}
	
	public String getNom()
	{
		return nom;
	}
	public int getLargeur()
	{
		return largeur;
	}
	public int getLongueur()
	{
		return longueur;
	}
	public boolean ajouterPlante(Plante p)
	{
		boolean result = false;
		if(plantesSurLaCarte.size() <  longueur*largeur)
		{
			if(p.getSaisonDePousse().contentEquals(GameManager.getSaisonActuelle()))
			result = true;
			plantesSurLaCarte.add(p);
		}
		return result;
	}

	public void augmenterJour() 
	{
		for(Plante p:plantesSurLaCarte)
		{
			p.passerJour();
			if(!p.getSaisonDePousse().contentEquals(GameManager.getSaisonActuelle()))
			{
				plantesSurLaCarte.remove(p);
			}
		}
	}
	
	public void arroserLesPlantes()
	{
		for(Plante p:plantesSurLaCarte)
		{
			p.arroser();
		}
	}
	
	public int calculerTaille()
	{
		return largeur*longueur;
	}
	
	public int calculerPlaceLibre()
	{
		return calculerTaille()-plantesSurLaCarte.size();
	}
	
	public int nombrePlante()
	{
		int compte = 0;
		if(!plantesSurLaCarte.isEmpty())
		{
			compte = plantesSurLaCarte.size();
		}
		return compte;
	}
	public String[] getSorties()
	{
		return sorties;
	}

	public void afficherStatutPlante()
	{
		for(Plante p:plantesSurLaCarte)
		{
			System.out.print("\n"+p.toString());
		}
		
	}

	public ArrayList<Plante> getPlantesSurLaCarteRecoltables()
	{
		ArrayList<Plante> temp = new ArrayList<Plante>();
		for(Plante p:plantesSurLaCarte)
		{
			if(p.getPeutEtreRamasse())
			{
				temp.add(p);
			}
		}
		return temp;
	}

	public void retirerPlante(Plante p)
	{
		plantesSurLaCarte.remove(p);
	}
}
