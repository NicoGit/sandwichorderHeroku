package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import play.data.validation.Required;
import play.db.jpa.Model;


@Entity
public class Ingredient extends Model{

	// Le nom ne doit pas contenir d'espace
	
	@Required
	public String name;
	
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	@ManyToMany
	//(cascade=CascadeType.PERSIST)
	public List<Sandwich> listSandwich;
		
	public Ingredient(String name) {
		super();
		this.name = name;
	}

	public String toString(){
		return name;
	}
	
	public void removeSandwich(Sandwich sandwich){
		if(listSandwich.contains(sandwich)){
			listSandwich.remove(sandwich);
		}else{
			System.out.println("L'ingredient n'est pas present dans ce sandwich");
		}
	}
	
	public void addSandwich(Sandwich sandwich){
		if(!listSandwich.contains(sandwich)){
			listSandwich.add(sandwich);
		}else{
			System.out.println("L'ingredient est deja present dans ce sandwich");
		}
	}
	
	
}
