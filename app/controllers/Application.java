package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import models.ReservedSandwiches;
import models.ReservedSandwichesCache;
import models.Sandwich;
import models.User;
import play.cache.Cache;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

//@With(Secure.class)
public class Application extends Controller {

//    @Before
//    static void checkUser() {
//    	String userid = Cache.get(session.getId()+"USERID", String.class);
//    	User user = User.find("byIden", userid).first();
//        if(userid != null && user == null) {
//        	String firstname = Cache.get(session.getId()+"FIRSTNAME", String.class);
//        	String lastname = Cache.get(session.getId()+"LASTNAME", String.class);
//        	String email = Cache.get(session.getId()+"EMAIL", String.class);
//        	String status = Cache.get(session.getId()+"STATUS", String.class);
//        	
//        	//Enresgistrement dans la base de données
//        	new User(userid, firstname, lastname, email, status=="ADMIN-SODEXO").save();
//        }
//    }
	
	@Before
    static void checkAuthentification() {
        Cache.set(session.getId()+"USERID",        "nhumeau");
        Cache.set(session.getId()+"FIRSTNAME",  "Nicolas");
        Cache.set(session.getId()+"LASTNAME",   "Humeau");
        Cache.set(session.getId()+"EMAIL",        "nicolas.humeau@mines-nantes.fr");
        Cache.set(session.getId()+"STATUS",     "ADMIN-SODEXO");
    }

    public static void deconnect(){
    	try {
			
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	/*
	 * ---------------------------------------------------------------------------------------
	 * 						PARTIE PAGE DE LA LISTE DE SANDWICHS
	 * ---------------------------------------------------------------------------------------
	 */

	public static void sandwich(){
		// voici comment on recupere les champs de l'utilisateur connecte
    	// c'est valable dans toutes les actions des controlleurs
    	String userid = Cache.get(session.getId()+"USERID", String.class);
    	String firstname = Cache.get(session.getId()+"FIRSTNAME", String.class);
    	String lastname = Cache.get(session.getId()+"LASTNAME", String.class);
    	String email = Cache.get(session.getId()+"EMAIL", String.class);
    	String status = Cache.get(session.getId()+"STATUS", String.class);
    	
    	 Date actuelle = new Date();
     	
    	 DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    	 
    	 String todayDate = dateFormat.format(actuelle);
		
    	 //User user = new User("jpasto09", "Le pouple", "secret", "jp.lepoulpe@hotmail.com",true).save();
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

		 render(sandwiches,panier,totalPrice,totalQuantity,userid,todayDate);
    }
    
    public static void commandes(){
    	String userid = Cache.get(session.getId()+"USERID", String.class);
    	String firstname = Cache.get(session.getId()+"FIRSTNAME", String.class);
    	String lastname = Cache.get(session.getId()+"LASTNAME", String.class);
    	String email = Cache.get(session.getId()+"EMAIL", String.class);
    	String status = Cache.get(session.getId()+"STATUS", String.class);
    	
    	Date actuelle = new Date();
     	
   	 	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
   	 
   	 	String todayDate = dateFormat.format(actuelle);
    	
    	List<Sandwich> sandwiches = Sandwich.findAll();
    	
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
//        		if (commande.user.iden.equals(userid)) {
//        			if(commande.date.equals(todayDate)) {
//    				todayTotalPriceCommande += commande.reservedSandwich.prix*commande.quantity;
//    				todayTotalQuantityCommande += commande.quantity;
//    				todayCommandes.add(commande);
//        			}
//        			else {
//        				totalPriceCommande += commande.reservedSandwich.prix*commande.quantity;
//        				totalQuantityCommande += commande.quantity;
//        				commandes.add(commande);
//        			}
//    				
//        		}
    		}
        }
    	
    	totalPriceCommande = Math.floor(Math.round(100*totalPriceCommande))/100;
    	todayTotalPriceCommande = Math.floor(Math.round(100*todayTotalPriceCommande))/100;
    	
       // render(sandwiches,panier,totalPrice,totalQuantity,userid,commandes,totalPriceCommande,totalQuantityCommande,todayDate,todayCommandes,todayTotalPriceCommande,todayTotalQuantityCommande);
    	
   	 	render();
    	
    }  

    /*
	 * ---------------------------------------------------------------------------------------
	 * 						PARTIE GESTION DU PANIER
	 * ---------------------------------------------------------------------------------------
	 */
	
	public static void addSandwichToCart(String name, String date, int quantity, Boolean ketchup, Boolean mayo, Boolean moutarde) {
    	
    	String userid = Cache.get(session.getId()+"USERID", String.class);
    	String firstname = Cache.get(session.getId()+"FIRSTNAME", String.class);
    	String lastname = Cache.get(session.getId()+"LASTNAME", String.class);
    	String email = Cache.get(session.getId()+"EMAIL", String.class);
    	String status = Cache.get(session.getId()+"STATUS", String.class);
    	User user = User.find("byIden", userid).first();
    	
    	if (ketchup==null) {
    		ketchup=false;
    	}
    	if (mayo==null) {
    		mayo=false;
    	}
    	if (moutarde==null) {
    		moutarde=false;
    	}
    	
    	String d = date.substring(date.length()-2,date.length())+"-"+date.substring(date.length()-5, date.length()-3)+"-"+date.substring(date.length()-10, date.length()-6);
    	
    	Sandwich toReserve = Sandwich.find("byName", name).first();
    	
    	List<ReservedSandwichesCache> reservedSandwiches = Cache.get(session.getId()+"cachedSandwich", List.class);
    	
    	if (reservedSandwiches==null) {
			List<ReservedSandwichesCache> reservedSandwich = new ArrayList<ReservedSandwichesCache>();
			reservedSandwich.add(new ReservedSandwichesCache(d, quantity,toReserve, user, ketchup, mayo, moutarde));
			Cache.set(session.getId()+"cachedSandwich", reservedSandwich);
    	}
    	else {
    		boolean b = false;
    		for (ReservedSandwichesCache reservedSandwichCache : reservedSandwiches) {
    			if (reservedSandwichCache.reservedSandwich.name.equals(name)) {
    					if (reservedSandwichCache.date.equals(d)&&!b) {
    						b = true;
		    				reservedSandwichCache.quantity=reservedSandwichCache.quantity+quantity;
		    				reservedSandwichCache.ketchup=ketchup;
		    				reservedSandwichCache.mayo=mayo;
		    				reservedSandwichCache.moutarde=moutarde;
    					}
    			}
    		}
    		if (!b){
				List<ReservedSandwichesCache> reservedSandwich = new ArrayList<ReservedSandwichesCache>();
				reservedSandwich.add(new ReservedSandwichesCache(d, quantity,toReserve, user, ketchup, mayo, moutarde));
				reservedSandwich.addAll(reservedSandwiches);
				Cache.set(session.getId()+"cachedSandwich", reservedSandwich);
			}
    	}
    	sandwich();
    }
    
    public static void deleteSandwichFromCart(String name, int quantity,String date) {
    	List<ReservedSandwichesCache> reservedSandwiches = Cache.get(session.getId()+"cachedSandwich", List.class);
    	for (ReservedSandwichesCache reservedSandwichCache : reservedSandwiches) {
			if (reservedSandwichCache.reservedSandwich.name.equals(name)) {
				if (reservedSandwichCache.date.equals(date))
						reservedSandwichCache.quantity=0;
			}
    	}
    	sandwich();
    }
    
    public static void cartValidationFromCommande() {
    	
    	List<ReservedSandwichesCache> reservedSandwiches1 = Cache.get(session.getId()+"cachedSandwich", List.class);
    	
    	List<ReservedSandwichesCache> panier = new ArrayList<ReservedSandwichesCache>();
    	
    	if (reservedSandwiches1!=null) {
    	for (ReservedSandwichesCache reservedSandwichCache : reservedSandwiches1) {
			if (reservedSandwichCache.quantity!=0) {
				panier.add(reservedSandwichCache);
			}
		}
    	}
    	
    	// ON AJOUTE LES SANDWICHES RESERVE DANS LA BD
    	
    	for (ReservedSandwichesCache reservedSandwichCache : panier) {
			ReservedSandwiches reservedSandwiches = reservedSandwichCache.toReservedSandwich();
			reservedSandwiches.save();
		}
    	
    	// ON REMET LE PANIER A ZERO
    	
    	List<ReservedSandwichesCache> reservedSandwiches = Cache.get(session.getId()+"cachedSandwich", List.class);
    	for (ReservedSandwichesCache reservedSandwichCache : reservedSandwiches) {
				reservedSandwichCache.quantity=0;
		}
    	
    	commandes();
    	
    }
    
    public static void cartValidation() {
    	
    	List<ReservedSandwichesCache> reservedSandwiches1 = Cache.get(session.getId()+"cachedSandwich", List.class);
    	
    	List<ReservedSandwichesCache> panier = new ArrayList<ReservedSandwichesCache>();
    	
    	if (reservedSandwiches1!=null) {
	    	for (ReservedSandwichesCache reservedSandwichCache : reservedSandwiches1) {
				if (reservedSandwichCache.quantity!=0) {
					panier.add(reservedSandwichCache);
				}
			}
    	}
    	
    	// ON AJOUTE LES SANDWICHES RESERVE DANS LA BD
    	
    	for (ReservedSandwichesCache reservedSandwichCache : panier) {
			ReservedSandwiches reservedSandwiches = reservedSandwichCache.toReservedSandwich();
			reservedSandwiches.save();
		}
    	
    	// ON REMET LE PANIER A ZERO
    	
    	List<ReservedSandwichesCache> reservedSandwiches = Cache.get(session.getId()+"cachedSandwich", List.class);
    	for (ReservedSandwichesCache reservedSandwichCache : reservedSandwiches) {
				reservedSandwichCache.quantity=0;
		}
    	
    	sandwich();
    	
    }
    
	/*
	 * ---------------------------------------------------------------------------------------
	 * 						PARTIE PAGE DES INFORMATIONS SUR LA CAFETERIA
	 * ---------------------------------------------------------------------------------------
	 */
 
    public static void cafeteria(){
    	String userid = Cache.get(session.getId()+"USERID", String.class);
    	
    	List<Sandwich> sandwiches = Sandwich.findAll();
    	//User user = new User(userid, "Le pouple", "secret", "jp.lepoulpe@hotmail.com").save();
    	
    	
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
    	if (panier!=null)
            render(sandwiches,panier,totalPrice,totalQuantity,userid);
        else
        	render(sandwiches,userid);
    }
}

	