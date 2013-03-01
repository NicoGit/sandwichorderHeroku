package models;

import java.util.Date;

import javax.persistence.Entity;

import play.db.jpa.Model;


public class ReservedSandwichesCache extends Model{
	
	public String date;
	
	public int quantity;
	
	public Sandwich reservedSandwich;
	
	public User user;
	
	public boolean mayo;
	
	public boolean ketchup;
	
	public boolean moutarde;

	public ReservedSandwichesCache(String date, int quantity,
			Sandwich reservedSandwich, User user,
			boolean ketchup,boolean mayo, boolean moutarde) {
		super();
		this.date = date;
		this.quantity = quantity;
		this.reservedSandwich = reservedSandwich;
		this.user = user;
		this.mayo = mayo;
		this.ketchup = ketchup;
		this.moutarde = moutarde;
	}
	
	public ReservedSandwiches toReservedSandwich() {
		return new ReservedSandwiches(date, quantity, reservedSandwich, user, mayo, ketchup, moutarde);
	}

}
