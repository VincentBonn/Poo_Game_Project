package Entity;

import GameInterface.*;

public class Plante implements PasserJour,Recolter
{
	private int jourCroissanceTotal;
	private int jourCroissanceActuel;
	private String saisonDePousse;
	private Item itemDrop;
	private boolean peutEtreRamasse;
	private boolean arrosee = false;
	
	public Plante(int jourCroissanceTotal,int jourCroissanceActuel,String saisonDePousse,boolean peutEtreRamasse,Item itemDrop)
	{
		this.jourCroissanceTotal = jourCroissanceTotal;
		this.jourCroissanceActuel = jourCroissanceActuel;
		this.saisonDePousse = saisonDePousse;
		this.peutEtreRamasse = peutEtreRamasse;
		this.itemDrop = itemDrop;
	}
	public Plante(Plante p)
	{
		this.jourCroissanceTotal = p.getJourCroissanceTotal();
		this.jourCroissanceActuel = p.getJourCroissanceActuel();
		this.saisonDePousse = p.getSaisonDePousse();
		this.peutEtreRamasse = p.getPeutEtreRamasse();
		this.itemDrop = new Item(p.getItemDrop());
	}
	private int getJourCroissanceTotal()
	{
		return jourCroissanceTotal;
	}
	private int getJourCroissanceActuel()
	{
		return jourCroissanceActuel;
	}
	public String getSaisonDePousse()
	{
		return saisonDePousse;
	}

	@Override
	public void passerJour()
	{
		if(jourCroissanceActuel < jourCroissanceTotal)
		{
			if(arrosee)
			{
				jourCroissanceActuel++;
				if(jourCroissanceActuel == jourCroissanceTotal)
				{
					peutEtreRamasse = true;
				}
			}
		}
		arrosee = false;
	}

	@Override
	public Item recolter()
	{
		Item result = null;
		if(peutEtreRamasse)
		{
			result = itemDrop;
		}
		return result;
	}
	
	public void arroser()
	{
		arrosee = true;
	}
	
	public boolean getPeutEtreRamasse()
	{
		return peutEtreRamasse;
	}
	@Override
	public String toString()
	{
		return itemDrop.getNom()+", Jour de Croissance: "+jourCroissanceActuel+" Total de jour de pousse: "+jourCroissanceTotal+" Est Arrosee: "+arrosee+" Peut être récolté: "+peutEtreRamasse;
	}
	public Item getItemDrop()
	{
		return itemDrop;
	}
}