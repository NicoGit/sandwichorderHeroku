package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class ReservedSandwiches extends Model {
	
	public String date;
	
	public int quantity;
	
	@ManyToOne
	public Sandwich reservedSandwich;
	
	@ManyToOne
	public User user;
	
	public boolean mayo;
	
	public boolean ketchup;
	
	public boolean moutarde;

	public ReservedSandwiches(String date, int quantity,
			Sandwich reservedSandwich, User user, boolean mayo,
			boolean ketchup, boolean moutarde) {
		super();
		this.date = date;
		this.quantity = quantity;
		this.reservedSandwich = reservedSandwich;
		this.user = user;
		this.mayo = mayo;
		this.ketchup = ketchup;
		this.moutarde = moutarde;
	}
	
	public String toRFCDate() {
		String d = date.substring(date.length()-4, date.length())+"-"+date.substring(date.length()-7, date.length()-5)+"-"+date.substring(date.length()-10, date.length()-8);
		return d;
	}

	

}
