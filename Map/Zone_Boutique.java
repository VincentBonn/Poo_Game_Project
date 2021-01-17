package Map;

import java.util.ArrayList;

import Entity.Graine;
import Entity.Item;
import Entity.Plante;

public class Zone_Boutique extends Map 
{
	public Zone_Boutique()
	{
		super(0, 0, "Boutique",new String[] {"Champs"});
	}
	public static ArrayList<Item> getVentes()
	{
		ArrayList<Item> temp = new ArrayList<Item>();
		Item itemPlant1 = new Item(40,3, 1,"Carotte");
		Plante plante1 = new Plante(2, 1, "Pringtemps", false, itemPlant1);
		Item itemSeed1 = new Graine(3,4, 1, "Graines de Carotte",plante1);
		temp.add(itemPlant1);
		temp.add(itemSeed1);
		Item itemPlant2 = new Item(50,5, 1,"Tomate");
		Plante plante2 = new Plante(2, 1, "Pringtemps", false, itemPlant1);
		Item itemSeed2 = new Graine(5,6, 1, "Graines de Tomate",plante1);
		temp.add(itemPlant2);
		temp.add(itemSeed2);
		
		return temp;
	}
}
