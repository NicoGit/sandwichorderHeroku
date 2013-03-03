package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity(name="SandwichUser")
public class User extends Model{
	
	@Required
	public String iden;
	
	@Required
	public String name;
	@Required
	public String forename;

	public String email;
	
	public boolean admin=false;

	public User(String iden, String name, String forename, String email, boolean admin) {
		super();
		this.iden = iden;
		this.name = name;
		this.forename = forename;
		this.email = email;
		this.admin = admin;
	}

	@ManyToMany
	public List<Sandwich> likedSandwiches;
	
	public String toString(){
		return name+"-"+forename;
	}
	
	public static User connect(String email, String password) {
	    return find("byEmailAndPassword", email, password).first();
	}

}
