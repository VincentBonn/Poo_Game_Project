package Entity;

import java.util.ArrayList;

public class Character
{
	private String nom;
	private int argent;
	private ArrayList<Item> inventaire = new ArrayList<Item>();
	
	public Character(String nom,int argent)
	{
		this.nom = nom;
		this.argent = argent;
	}
	public ArrayList<Item> getInventaire()
	{
		return inventaire;
	}
	
	public int[] trouverItem(int ID)
	{
		int[] couple = new int[2];
		boolean notFound = true;
		couple[0] = 0;
		if(!inventaire.isEmpty() && inventaire.size()>0 && notFound)
		{
			
			while(couple[0] < inventaire.size() && notFound)
			{
				if(inventaire.get(couple[0]).getID()==ID)
				{
					notFound = false;
					couple[1]= inventaire.get(couple[0]).getStack();
				}
				else
				{
					couple[0]=couple[0]+1;
				}
			}
		}
		if(notFound)
		{
			couple[0] = -1;
			couple[1] = -1;
		}
		return couple;
	}
	public void ajouterDansInventaire(Item i)
	{
		int[] result = trouverItem(i.getID());
		{
			if(result[0] >= 0)
			{
				System.out.print("ajout au stack");
				inventaire.get(result[0]).ajouterAuStack(i.getStack());
			}
			else
			{
				System.out.print("nouveau stack");
				inventaire.add(i);
			}
		}
	}
	public boolean tryRetirerArgent(int montant)
	{
		boolean result = false;
		if(montant<=argent)
		{
			argent -= montant;
			result = true;
		}
		return result;
	}
	public int getArgent()
	{
		return argent;
	}
	public void addArgent(int montant)
	{
		argent += montant;
		System.out.print("\nVous avez été crédité de "+montant+"$ !" );
	}
	public ArrayList<Item> objetsVendables()
	{
		ArrayList<Item> temp = new ArrayList<Item>();
		for(Item i:inventaire)
		{
			if(i.getValeur()>0)
			{
				temp.add(i);
			}
		}
		return temp;
	}
	public int retirerItem(int ID, int montant)
	{
		int quantiteSoustraite = 0;
		int[] temp = trouverItem(ID);
		if(temp[0]>=0)
		{
			if(temp[1] >= montant)
			{
				quantiteSoustraite = montant;
			}
			else
			{
				quantiteSoustraite = temp[1];
			}
			inventaire.get(temp[0]).retirerAuStack(quantiteSoustraite);
		}
		return quantiteSoustraite;
	}
}
