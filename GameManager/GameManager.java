package GameManager;

import java.util.ArrayList;

import Input.Clavier;
import Map.*;
import Entity.Character;
import Entity.Graine;
import Entity.Item;
import Entity.Plante;

public class GameManager
{
	public static ArrayList<Map> mapListe = new ArrayList<Map>();
	public static Map mapActuelle;
	public static String saisonActuelle;
	public static int jourDuMois;
	public static Character c1;
	
	public static void launch()
	{
		Map m1 = new Zone_Depart();
		mapListe.add(m1);
		Map m2 = new Zone_Champs();
		mapListe.add(m2);
		Map m3 = new Zone_Boutique();
		mapListe.add(m3);
		

		c1 = new Character("Coco",0);
		Item itemPlant1 = new Item(50,2, 1,"Radis");
		Plante plante1 = new Plante(2, 1, "Pringtemps", false, itemPlant1);
		Item i2 = new Graine(10,1, 4, "Graines de radis",plante1);
		c1.ajouterDansInventaire(i2);
		mapActuelle = m1;
		jourDuMois = 1;
		saisonActuelle = "Pringtemps";
		
	}
	public static void play()
	{
		int choice = -1;
		boolean found = false;
		while(choice != 0)
		{
			while(!found)
			{
				ArrayList<Integer> tableauChoix = afficherChoix();
				choice = Clavier.lireInt("\nDonnez votre choix :");
				for(Integer i:tableauChoix)
				{
					if(i == choice)
					{
						found = true;
					}
				}
				if(!found)
				{
					System.out.print("\n\n"+choice+" n'est pas un choix valide dans le contexte ou n'existe pas, veuillez réessayer.\n\n");
				}
			}
			switch(choice)
			{
				case 0:
					System.out.print("Vous quittez le jeu");
					break;
				case 1:
					if(mapActuelle.getNom() =="Maison")
					{
						passerLeJour();
					}
					break;
				case 2:
					chercherGraineValideInventaire();
					break;
				case 3:
					arroserLesPlantes();
					break;
				case 4:
					afficherLesPlantes();
					break;
				case 5:
					recolterLesPlantes();
					break;
				case 47:
					acheterAuxMarchands();
					break;
				case 48:
					vendreAuxMarchands();
					break;
				case 49:
					afficherInventaire();
					break;
				case 50:
					mapActuelle = mapListe.get(0);
					break;
				case 51:
					mapActuelle = mapListe.get(1);
					break;
				case 52:
					mapActuelle = mapListe.get(2);
					break;
			}
			found = false;
		}
	}
	private static void vendreAuxMarchands()
	{
		final int ACHAT_RATIO = 1;
		ArrayList<Item> objetsAVendre = c1.objetsVendables();
		if(!objetsAVendre.isEmpty())
		{
			int compteur = 0;
			int choice = 0;
			int choice2 = 0;
			boolean found = false;
			while(!found)
			{
				compteur = 0;
				while(compteur<objetsAVendre.size())
				{
					System.out.print("\n"+compteur+" - "+objetsAVendre.get(compteur).getNom()+" x"+objetsAVendre.get(compteur).getStack()+", "+objetsAVendre.get(compteur).getValeur()*ACHAT_RATIO+" $/U");
					compteur++;
				}
				choice = Clavier.lireInt("\nDonnez votre choix :");
				if(!(choice<0 || choice>= objetsAVendre.size()))
				{
					found = true;
				}
				if(!found)
				{
					System.out.print("\n\n"+choice+" n'est pas un choix valide dans le contexte ou n'existe pas, veuillez réessayer.\n\n");
				}
				found = false;
				while(!found)
				{
					choice2 = Clavier.lireInt("\nDonnez la quantité à vendre [MAX="+objetsAVendre.get(choice).getStack()+"]:");
					if(!(choice2<0 || choice2 > objetsAVendre.get(choice).getStack()))
					{
						found = true;
					}
					if(!found)
					{
						System.out.print("\n\n"+choice2+" n'est pas un choix valide dans le contexte ou n'existe pas, veuillez réessayer.\n\n");
					}
				}
				if(choice2>0)
				{
					int quantite = c1.retirerItem(objetsAVendre.get(choice).getID(),choice2);
					int valeur = objetsAVendre.get(choice).getValeur();
					c1.addArgent(quantite*valeur*ACHAT_RATIO);
				}
				else
				{
						System.out.print("\nRien n'a été vendu !");
				}
			}
		}
	}
	private static void acheterAuxMarchands()
	{
		final int VENTE_RATIO = 2;
		ArrayList<Item> ventes = ((Zone_Boutique) mapActuelle).getVentes();
		if(!ventes.isEmpty())
		{
			int compteur = 0;
			int choice = 0;
			int choice2 = 0;
			boolean found = false;
			while(!found)
			{
				compteur = 0;
				while(compteur<ventes.size())
				{
					System.out.print("\n"+compteur+" - "+ventes.get(compteur).getNom()+" x"+ventes.get(compteur).getStack()+", "+ventes.get(compteur).getValeur()*VENTE_RATIO+" $/U");
					compteur++;
				}
				choice = Clavier.lireInt("\nDonnez votre choix :");
				if(!(choice<0 || choice>= ventes.size()))
				{
					found = true;
				}
				if(!found)
				{
					System.out.print("\n\n"+choice+" n'est pas un choix valide dans le contexte ou n'existe pas, veuillez réessayer.\n\n");
				}
			}
			found = false;
			while(!found)
			{
				choice2 = Clavier.lireInt("\nDonnez la quantité à acheter :");
				if(!(choice<0))
				{
					found = true;
				}
				if(!found)
				{
					System.out.print("\n\n"+choice+" n'est pas un choix valide dans le contexte ou n'existe pas, veuillez réessayer.\n\n");
				}
			}
			if(choice2>0)
			{
				if(c1.tryRetirerArgent(ventes.get(choice).getValeur()*VENTE_RATIO*choice2))
				{
					System.out.print("CHOICE "+choice2);
					while(choice2>0)
					{
						System.out.print("Ajout en cours");
						if(ventes.get(choice) instanceof Graine)
						{
							c1.ajouterDansInventaire(new Graine ((Graine) ventes.get(choice)));
						}
						else
						{
							c1.ajouterDansInventaire(new Item (ventes.get(choice)));
						}
						choice2--;
					}
				}
				else
				{
					System.out.print("\nVous n'avez pas assez d'argent !");
				}
			}
		}
		
	}
	private static void recolterLesPlantes()
	{
		ArrayList<Plante> planteListe = mapActuelle.getPlantesSurLaCarteRecoltables();
		for(Plante p:planteListe)
		{
			System.out.print("\n\nUn ajout a été effecuté");
			c1.ajouterDansInventaire(p.getItemDrop());
			mapActuelle.retirerPlante(p);
		}

	}
	private static void afficherLesPlantes()
	{
		mapActuelle.afficherStatutPlante();
		
	}
	private static void arroserLesPlantes()
	{
		mapActuelle.arroserLesPlantes();
	}
	
	private static void afficherInventaire()
	{
		ArrayList<Item> liste = c1.getInventaire();
		String listeString = "Argent: "+c1.getArgent();
		for(Item i:liste)
		{
			if(i.getStack()>0)
			{
				listeString +=", "+i.toString();
			}
		}
		System.out.print("\nInventaire:\n"+listeString);
	}
	private static void chercherGraineValideInventaire()
	{
		ArrayList<Graine> listeGraineValide = new ArrayList<Graine>();
		for(Item g:c1.getInventaire())
		{
			if(g instanceof Graine)
			{
				if(((Graine) g).getPlante().getSaisonDePousse().contentEquals(saisonActuelle) && g.getStack()>0)
				{
					listeGraineValide.add((Graine) g);
				}
			}
		}
		if(!listeGraineValide.isEmpty())
		{
			int choice=0;
			boolean found = false;
			while(!found)
			{
				int compteur = 0;
				while(compteur<listeGraineValide.size())
				{
					System.out.print("\n"+compteur+" - "+listeGraineValide.get(compteur).toString());
					compteur++;
				}
				choice = Clavier.lireInt("\nDonnez votre choix :");
				if(!(choice<0 || choice>= listeGraineValide.size()))
				{
					found = true;
				}
				if(!found)
				{
					System.out.print("\n\n"+choice+" n'est pas un choix valide dans le contexte ou n'existe pas, veuillez réessayer.\n\n");
				}
			}			
			found = false;
			while(!found)
			{
				int choice2;
				System.out.print("\nCombien de graines voulez vous planter ?");
				choice2 = Clavier.lireInt("\nDonnez votre choix :");
				if(!(choice2<0 || choice2 > listeGraineValide.get(choice).getStack()))
				{
					found = true;
					listeGraineValide.get(choice).retirerAuStack(choice2);
					while(choice2>0)
					{
						mapActuelle.ajouterPlante(listeGraineValide.get(choice).getPlante());
						choice2--;
					}
				}
				if(!found)
				{
					System.out.print("\n\n"+choice2+" n'est pas un choix valide dans le contexte ou n'existe pas, veuillez réessayer.\n\n");
				}
			}
		}
		else
		{
			System.out.print("\n\nAucune graine valide pour la plantation");
		}
	}
	private static void passerLeJour()
	{
		System.out.print("\n\n\nVous allez passer le jour");
		if(jourDuMois+1 >= 30)
		{
			jourDuMois = 1;
			if(saisonActuelle == "Pringtemps")
			{
				saisonActuelle = "Ete";
			}
			else if(saisonActuelle == "Ete")
			{
				saisonActuelle = "Automne";
			}
			else if(saisonActuelle == "Automne")
			{
				saisonActuelle = "Hiver";
			}
			else if(saisonActuelle == "Hiver")
			{
				saisonActuelle = "Pringtemps";
			}
		}
		else
		{
			jourDuMois++;
		}
		for(Map m:mapListe)
		{
			m.augmenterJour();
		}
		
		
		System.out.print("\nJour "+jourDuMois+" de la saison "+saisonActuelle+"\n\n\n");
	}
	private static ArrayList<Integer> afficherChoix() 
	{
		ArrayList<Integer> choixPossibles = new ArrayList<Integer>();
		System.out.print("\n0 - Quitter le jeu");
		choixPossibles.add(0);
		

		if(mapActuelle.getNom() =="Maison")
		{
			System.out.print("\n1 - Passer le jour");
			choixPossibles.add(1);
		}
		if(mapActuelle.calculerPlaceLibre()>0)
		{
			System.out.print("\n2 - Planter une graine");
			choixPossibles.add(2);
		}
		if(mapActuelle.nombrePlante()>0)
		{
			System.out.print("\n3 - Arroser les plantes");
			choixPossibles.add(3);
		}
		if(mapActuelle.nombrePlante()>0)
		{
			System.out.print("\n4 - Afficher les plantes");
			choixPossibles.add(4);
		}
		if(mapActuelle.nombrePlante()>0)
		{
			System.out.print("\n5 - Récolter les plantes");
			choixPossibles.add(5);
		}
		if(mapActuelle.getNom()=="Boutique")
		{
			System.out.print("\n47 - Acheter au marchand");
			choixPossibles.add(47);
		}
		if(mapActuelle.getNom()=="Boutique")
		{
			System.out.print("\n48 - Vendre au marchand");
			choixPossibles.add(48);
		}
		System.out.print("\n49 - Afficher inventaire");
		choixPossibles.add(49);
		String[] sorties = mapActuelle.getSorties();
		if(searchFor("Maison",sorties))
		{
			System.out.print("\n50 - Se diriger dans la maison");
			choixPossibles.add(50);
		}
		if(searchFor("Champs",sorties))
		{
			System.out.print("\n51 - Se diriger vers les champs");
			choixPossibles.add(51);
		}
		if(searchFor("Boutique",sorties))
		{
			System.out.print("\n52 - Se diriger vers la boutique");
			choixPossibles.add(52);
		}

		return choixPossibles;
	}
	private static boolean searchFor(String map,String[] tableauMap)
	{
		boolean result = false;
		for(String s:tableauMap)
		{
			if(s.contentEquals(map))
			{
				result = true;
			}
		}
		return result;
	}
	public static String getSaisonActuelle()
	{
		return saisonActuelle;
	}
}
