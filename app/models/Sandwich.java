package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Sandwich extends Model{
	
	@Required
	public String name;
	@Required
	public String description;
	@Required
	public double prix;
	
	public String firstInCarousel;
	
	public boolean disponible=true;
	
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	@ManyToMany
	public List<Ingredient> listIngredient;
	
	public Sandwich(){
	}
	
	public Sandwich(String name, String description, double prix, Boolean dispo,String firstInCarousel){
		this.name = name;
		this.description = description;
		this.prix = prix;
		this.firstInCarousel=firstInCarousel;
		this.disponible=dispo;
		listIngredient = new ArrayList<Ingredient>();
	}
	
	public String toString(){
		return name;
	}
	
	public void removeIngredient(Ingredient ingredient){
		if(listIngredient.contains(ingredient)){
			listIngredient.remove(ingredient);
		}else{
			System.out.println("Le sandwich ne contient pas cet ingredient");
		}
	}
	
	public void addIngredient(Ingredient ingredient){
		if(!listIngredient.contains(ingredient)){
			listIngredient.add(ingredient);
		}else{
			System.out.println("Le sandwich a deja cet ingredient");
		}
	}
	
}
