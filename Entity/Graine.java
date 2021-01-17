package Entity;

public class Graine extends Item{

	private Plante p;
	public Graine(int valeur, int ID, int stack, String nom,Plante p)
	{
		super(valeur, ID, stack, nom);
		this.p = p;
	}
	public Graine(Graine graine) 
	{
		this(graine.getValeur(), graine.getID(), graine.getStack(), graine.getNom(), graine.getPlante());
		
	}
	@Override
	public String toString()
	{
		return super.toString()+" Saison de pousse "+p.getSaisonDePousse();
	}
	public Plante getPlante()
	{
		return p;
	}
}
