package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.Ingredient;
import models.ReservedSandwiches;
import models.ReservedSandwichesCache;
import models.Sandwich;
import models.User;
import play.cache.Cache;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

//@With(Secure.class)
public class Administration extends Controller{
	
	/*
	 * ---------------------------------------------------------------------------------------
	 * 									PARTIE PAGE D'ACCUEIL - PANNEAU ADMINISTRATION
	 * ---------------------------------------------------------------------------------------
	 */
	
//	@Before
//	public static void enterAdminPanel(){
//		String status = Cache.get(session.getId()+"STATUS", String.class);
//		if(status==null || status.length()<11 && !status.equals("ADMIN-SODEXO")){
//			flash.error("Not allowed to connect as ADMIN");
//			Application.sandwich();
//		}
//	}
	
	
	public static void sandwich(){
		// voici comment on recupere les champs de l'utilisateur connecte
    	// c'est valable dans toutes les actions des controlleurs
    	String userid = Cache.get(session.getId()+"USERID", String.class);
    	String firstname = Cache.get(session.getId()+"FIRSTNAME", String.class);
    	String lastname = Cache.get(session.getId()+"LASTNAME", String.class);
    	String email = Cache.get(session.getId()+"EMAIL", String.class);
    	String status = Cache.get(session.getId()+"STATUS", String.class);
    	
		List<Ingredient> ingredients = Ingredient.findAll();
		List<Sandwich> sandwiches = Sandwich.findAll();
		
		render(ingredients,sandwiches,userid);
	}
	
	public static void addIngredientToDB(String newName){
    	Ingredient ingredientTest = Ingredient.find("byName", newName).first();
    	if(ingredientTest == null){
    		new Ingredient(newName).save();
    	} else{
    		System.out.println("L'ingredient à ajouter existe deja!");
    	}
    	sandwich();
    }
	
	public static void deleteIngredientFromDB(String name){
		Ingredient ingredientFound = Ingredient.find("byName", name).first();
    	if(ingredientFound != null){
			int i=0;
			while(ingredientFound.listSandwich.size()!=0){
				Sandwich sandwichFound = Sandwich.find("byName", ingredientFound.listSandwich.get(i).name).first();
    	    	if(sandwichFound != null){
    	    		sandwichFound.removeIngredient(ingredientFound);
    	    		ingredientFound.removeSandwich(sandwichFound);
    	    		sandwichFound.save();
    	    		ingredientFound.save();
    	    	}
    	    	else{
    	    		i++;
    	    	}
			}	
    		ingredientFound.delete();
    	} else{
    		System.out.println("L'ingredient à supprimer n'existe pas!");
    	}
    	sandwich();
    }
	
	public static void modifyIngredientName(String name, String newName){
		Ingredient ingredientFound = Ingredient.find("byName", name).first();
    	if(ingredientFound != null){
    		ingredientFound.name=newName;
    		ingredientFound.save();
    	} else{
    		System.out.println("L'ingredient à supprimer n'existe pas!");
    	}
    	sandwich();
    }
	
	public static void addIngredientToSandwich(String name, String nameIngredient) {
		Ingredient ingredientFound = Ingredient.find("byName", nameIngredient).first();
		Sandwich sandwichTest = Sandwich.find("byName", name).first();
    	if(ingredientFound != null){
    		sandwichTest.addIngredient(ingredientFound);
    		ingredientFound.addSandwich(sandwichTest);
    		sandwichTest.save();
    		ingredientFound.save();
    	}
    	else{
    		System.out.println("Pas trouvé l'ingredient");
    	}
    	sandwich();
	}
	
	public static void removeIngredientToSandwich(String name, String nameIngredient) {
		Ingredient ingredientFound = Ingredient.find("byName", nameIngredient).first();
		Sandwich sandwichTest = Sandwich.find("byName", name).first();
    	if(ingredientFound != null){
    		sandwichTest.removeIngredient(ingredientFound);
    		ingredientFound.removeSandwich(sandwichTest);
    		sandwichTest.save();
    		ingredientFound.save();
    	}
    	else{
    		System.out.println("Pas trouvé l'ingredient");
    	}
    	sandwich();
	}
	
	
    public static void addSandwichToDB(String newName, String description, double prix,Boolean disponible){
    	Sandwich sandwichTest = Sandwich.find("byName", newName).first();
    	if (disponible==null) {
    		disponible = false;
    	}
    	if(sandwichTest == null){
    		new Sandwich(newName, description, prix,disponible,"").save();
    	} else{
    		System.out.println("Le sandwich à ajouter existe deja!");
    	}
    	sandwich();
    }
    
    public static void deleteSandwichFromDB(String name){
    	Sandwich sandwichFound = Sandwich.find("byName", name).first();
    	if(sandwichFound != null){
    			int i=0;
    			while(sandwichFound.listIngredient.size()!=0){
    				Ingredient ingredientFound = Ingredient.find("byName", sandwichFound.listIngredient.get(i).name).first();
	    	    	if(ingredientFound != null){
	    	    		sandwichFound.removeIngredient(ingredientFound);
	    	    		ingredientFound.removeSandwich(sandwichFound);
	    	    		sandwichFound.save();
	    	    		ingredientFound.save();
	    	    	}
	    	    	else{
	    	    		i++;
	    	    	}
    			}	
    		sandwichFound.delete();
    	} else{
    		System.out.println("Le sandwich à supprimer n'existe pas!");
    	}
    	sandwich();
    }
        
    public static void modifySandwichInDB(String name, String newName, String description, double prix, Boolean disponible){
    	Sandwich sandwichToModify = Sandwich.find("byName", name).first();
    	if (disponible==null) {
    		disponible = false;
    	}
    	if(sandwichToModify != null){
    		sandwichToModify.name=newName;
    		sandwichToModify.description=description;
    		sandwichToModify.prix=prix;
    		sandwichToModify.disponible=disponible;
    		sandwichToModify.save();
    	} else{
    		System.out.println("Le sandwich à modifier n'existe pas!");
    	}
    	sandwich(); 
    }
    
    public static void deleteUser(String name){
    	User userToDelete = User.find("byName", name).first();
    	if(userToDelete != null){
    		userToDelete.delete();
    	}else{
    		System.out.println("L'utilisateur à supprimer n'existe pas!");
    	}
    	sandwich(); //à changer vers la page d'administration correspondante...
    }
    
    public static void addUser(String iden, String name, String forename, String email, boolean admin){
    	User userToAdd = User.find("byName", name).first(); //si plusieurs personnes ont le même name? Cherchez par ID? Comment?
    	if(userToAdd == null){
    		new User(iden, name, forename, email, admin).save();
    	} else{
    		System.out.println("L'utilisateur à ajouter existe deja!");
    	}
    	sandwich(); //à changer vers la page d'administration correspondante...
    }
    
    
	/*
	 * ---------------------------------------------------------------------------------------
	 * 						PARTIE PAGE DE GESTION DES COMMANDES - PANNEAU ADMINISTRATION
	 * ---------------------------------------------------------------------------------------
	 */
    
    public static void commandes(){
    	String userid = Cache.get(session.getId()+"USERID", String.class);
    	String firstname = Cache.get(session.getId()+"FIRSTNAME", String.class);
    	String lastname = Cache.get(session.getId()+"LASTNAME", String.class);
    	String email = Cache.get(session.getId()+"EMAIL", String.class);
    	String status = Cache.get(session.getId()+"STATUS", String.class);
    	
    	Date actuelle = new Date();
     	
   	 	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
   	 
   	 	String todayDate = dateFormat.format(actuelle);
    	
   	 	List<Sandwich> s = Sandwich.findAll();
   	 	List<Sandwich> sandwiches = new ArrayList<Sandwich>();
   	 	for (Sandwich sandwich : s) {
   		 	if (sandwich.disponible)
   		 		sandwiches.add(sandwich);
   	 	}
    	
    	List<ReservedSandwichesCache> reservedSandwiches = Cache.get(session.getId()+"cachedSandwich", List.class);
    	
    	List<ReservedSandwichesCache> panier = new ArrayList<ReservedSandwichesCache>();
    	
    	double totalPrice = 0;
    	int totalQuantity = 0;
    	
    	if (reservedSandwiches!=null) {
	    	for (ReservedSandwichesCache reservedSandwichCache : reservedSandwiches) {
				if (reservedSandwichCache.quantity!=0) {
					panier.add(reservedSandwichCache);
					totalPrice += reservedSandwichCache.reservedSandwich.prix*reservedSandwichCache.quantity;
					totalQuantity += reservedSandwichCache.quantity;
				}
			}
    	}
    	
    	// On limite à deux décimales
    	
    	totalPrice = Math.floor(Math.round(100*totalPrice))/100;
    	
    	double totalPriceCommande = 0;
    	int totalQuantityCommande = 0;
    	double todayTotalPriceCommande = 0;
    	int todayTotalQuantityCommande = 0;
    	
    	List<ReservedSandwiches> allCommandes = ReservedSandwiches.find("order by date").fetch();
    	List<ReservedSandwiches> commandes = new ArrayList<ReservedSandwiches>();
    	List<ReservedSandwiches> todayCommandes = new ArrayList<ReservedSandwiches>();
    	
    	if (allCommandes!=null) {
        	for (ReservedSandwiches commande : allCommandes) {
        			if(commande.date.equals(todayDate)) {
    				todayTotalPriceCommande += commande.reservedSandwich.prix*commande.quantity;
    				todayTotalQuantityCommande += commande.quantity;
    				todayCommandes.add(commande);
        			}
        			else {
        				Date  d = null;
        				try {
							d = dateFormat.parse(commande.date);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        				if (actuelle.compareTo(d)<0) {
	        				totalPriceCommande += commande.reservedSandwich.prix*commande.quantity;
	        				totalQuantityCommande += commande.quantity;
	        				commandes.add(commande);
        				}
        			}
    		}
        }
    	
    	List<ReservedSandwichesCache> commandesGraph = new ArrayList<ReservedSandwichesCache>();
    	for(int i=1;i<=5;i++) {
	    	for (Sandwich sand : s){
	    		commandesGraph.add(new ReservedSandwichesCache(""+i, 0, sand, null, true, true, true));
	    	}
    	}
    	for (ReservedSandwiches comm : allCommandes) {
    		Date d = null;
    		Date dateComm = null;
    		
			try {
				d = dateFormat.parse(todayDate);
				dateComm = dateFormat.parse(comm.date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		int j = d.getDay();
    		Calendar calendar1 = Calendar.getInstance();
		    Calendar calendar2 = Calendar.getInstance();
		    calendar1.setTime(dateComm);
		    calendar2.setTime(d);
		    long milsecs1= calendar1.getTimeInMillis();
		    long milsecs2 = calendar2.getTimeInMillis();
		    long diff = milsecs2 - milsecs1;
		    diff = diff / (24 * 60 * 60 * 1000);
    		if (diff<=6+j&&diff>=2+j) {
    		for (ReservedSandwichesCache graphSand : commandesGraph){
    			if (graphSand.reservedSandwich.name.equals(comm.reservedSandwich.name)
    					&&graphSand.date.equals(""+dateComm.getDay())) {
    						graphSand.quantity += comm.quantity;
    						
    			}
    		}
    		}
    	}
    	
    	
    	// ON SUPPRIME LES SANDWICH QUI NONT PAS ETE COMMANDE CETTE SEMAINE
    	
    	for (Sandwich sand : s){
    		boolean b = true;
    	for(int i=1;i<=5;i++) {
	    	for (ReservedSandwichesCache graphSand : commandesGraph){
		    		if (graphSand.quantity!=0 && graphSand.reservedSandwich.name.equals(sand.name)
		    								  && graphSand.date.equals(""+i))
		    				b = false;
    		}
    	}
    	if (b&&commandesGraph!=null) {
    		for(int i=1;i<=5;i++) {
    			int j=0;
    			while(commandesGraph!=null && j<commandesGraph.size()){
    		    		if (commandesGraph.get(j).reservedSandwich.name.equals(sand.name)
    		    					&&commandesGraph.get(j).date.equals(""+i))
    		    						commandesGraph.remove(commandesGraph.get(j));
    		    		else{
    		    			j++;
    		    		}
    	    		}
    	    	}
    	}
    	}
    	
    	totalPriceCommande = Math.floor(Math.round(100*totalPriceCommande))/100;
    	todayTotalPriceCommande = Math.floor(Math.round(100*todayTotalPriceCommande))/100;

        render(commandesGraph,sandwiches,panier,totalPrice,totalQuantity,userid,commandes,totalPriceCommande,totalQuantityCommande,todayDate,todayCommandes,todayTotalPriceCommande,todayTotalQuantityCommande);
    }
    
    public static void modifyReservedSandwich(String date, int quantity,
			String nameSandwich, long id) {
    	ReservedSandwiches reservedSandwichToModidfy = ReservedSandwiches.find("byId", id).first();
    	Sandwich s = Sandwich.find("byName", nameSandwich).first();
    	date = date.substring(date.length()-2,date.length())+"-"+date.substring(date.length()-5, date.length()-3)+"-"+date.substring(date.length()-10, date.length()-6);
    	
    	if(reservedSandwichToModidfy != null){
    		reservedSandwichToModidfy.date=date;
    		reservedSandwichToModidfy.quantity=quantity;
    		reservedSandwichToModidfy.reservedSandwich=s;
    		reservedSandwichToModidfy.save();
    	} else{
    		System.out.println("La commande de sandwich à modifier n'existe pas!");
    	}
    	commandes(); 
    }
    
    public static void deleteReservedSandwich(long id) {
    	
    	ReservedSandwiches reservedSandwichToDelete = ReservedSandwiches.find("byId", id).first();
    	if(reservedSandwichToDelete != null){
    		reservedSandwichToDelete.delete();
    	}else{
    		System.out.println("La commande de sandwich à supprimer n'existe pas!");
    	}
    	commandes();
    }
}
