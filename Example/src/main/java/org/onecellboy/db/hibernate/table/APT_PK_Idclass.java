package org.onecellboy.db.hibernate.table;

import java.io.Serializable;

import org.onecellboy.db.hibernate.table.Apartment_Embeddable.APT_PK;

public class APT_PK_Idclass  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int dong;
	private int ho;
	
	public APT_PK_Idclass() {
		// TODO Auto-generated constructor stub
	}
	public APT_PK_Idclass(int dong, int ho)
	{
		this.dong = dong;
		this.ho = ho;
	}
	public int getDong() {
		return dong;
	}
	public void setDong(int dong) {
		this.dong = dong;
	}
	public int getHo() {
		return ho;
	}
	public void setHo(int ho) {
		this.ho = ho;
	}
	
	@Override
	 public boolean equals(Object o) { 
	        return ((o instanceof APT_PK) && dong == ((APT_PK)o).getDong() && ho == ((APT_PK) o).getHo());
	    }
	@Override
	 public int hashCode() { 
	        return (int)(dong ^ ho); 
	 }


}
