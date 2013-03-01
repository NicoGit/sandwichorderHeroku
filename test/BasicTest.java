import org.junit.*;

import groovy.ui.text.FindReplaceUtility;

import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

	
	@Before
    public void setup() {
        Fixtures.deleteDatabase();
    }
	
    @Test
    public void addASandwichToCart() {
        //Cart panier = new Cart(new Sandwich("MonSandwichTest", "Jambon-Gruyère", 3.00)).save();
    	
        //panier. = Cart.find("byName","MonSandwichTest").fetch();
        
        //Test
        //assertNotNull(panier);
        
    	//assertEquals(2, 1 + 1);
    }
    
//    @Test
//    public void createAndRetrieveUser() {
//        // Create a new user and save it
//        new User("aa","jp", "Le pouple", "secret", "jp.lepoulpe@hotmail.com",true).save();
//        
//        // Retrieve the user with email address bob@gmail.com
//        User bob = User.find("byEmail", "jp.lepoulpe@hotmail.com").first();
//        
//        // Test 
//        assertNotNull(bob);
//        assertEquals("JP", bob.name);
//    }
//    
//    @Test
//    public void tryConnectAsUser() {
//        // Create a new user and save it
//        new User("aa","jp", "Le pouple", "secret", "jp.lepoulpe@hotmail.com",true).save();
//        
//        // Test 
//        assertNotNull(User.connect("lepoulpe@hotmail.com", "secret"));
//        assertNull(User.connect("lepoulpe@hotmail.com", "badpassword"));
//        assertNull(User.connect("lemauvaispoulpe@hotmail.com", "secret"));
//    }
//    
    @Test
    public void createSandwich() {
    	
    	new Ingredient("Thon").save();
    	
    	Ingredient thon = Ingredient.find("byName","Thon").first();
    	
    	Ingredient salade = new Ingredient("Salade").save();
    	
    	Ingredient tomate = new Ingredient("Tomate").save();
    	
    	new Sandwich("Sandwich au thon", "Thon, salade, tomates", 3.5,true,"");
    	
    	Sandwich sandwich = Sandwich.find("byName", "Sandwich au thon").first();
    	
    	List<Ingredient> ingredients = Ingredient.all().fetch();
    	
    	System.out.println(ingredients);
    	
    	//sandwich.addNewIngredients(ingredients);
    	
    	assertNotNull(sandwich);
    	
    	assertNotNull(tomate);
    	
    	assertEquals(3, sandwich.listIngredient.size());
    	
    	assertEquals("Salade", salade.name);
    	
    	
    }

}
