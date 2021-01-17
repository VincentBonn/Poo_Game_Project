package Entity;

public class Item 
{
	private int valeur;
	private int ID;// unique, se référer au tableau des objets
	private int stack;
	private String nom;
	
	public Item(int valeur,int ID,int stack,String nom)
	{
		this.valeur = valeur;
		this.ID = ID;
		this.stack = stack;
		this.nom = nom;
	}
	public Item(Item i)
	{
		this.valeur = i.getValeur();
		this.ID = i.getID();
		this.stack = i.getStack();
		this.nom = i.getNom();
	}
	public int getValeur()
	{
		return valeur;
	}
	public int getID()
	{
		return ID;
	}
	public int getStack()
	{
		return stack;
	}
	public void ajouterAuStack(int quantite)
	{
		stack += quantite;
	}
	public int retirerAuStack(int quantite)
	{
		int montantPreleve = 0;
		if(quantite <= stack)
		{
			stack -= quantite;
			montantPreleve = quantite;
		}
		else
		{
			montantPreleve = stack;
			stack = 0;
		}
		return montantPreleve;
	}
	public String toString()
	{
		return nom+": "+stack +" Valeur: "+ valeur; 
	}
	public String getNom()
	{
		return nom;
	}
}
