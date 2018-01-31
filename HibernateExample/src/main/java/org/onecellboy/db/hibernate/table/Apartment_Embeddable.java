package org.onecellboy.db.hibernate.table;

import java.io.Serializable;

import javax.persistence.AttributeOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Apartment")
public class Apartment_Embeddable {
	@Embeddable
	public static class APT_PK implements Serializable{
		private static final long serialVersionUID = 1L;
		
		/*@Column(name="APT_DONG")*/  protected int dong;
		/*@Column(name="APT_HO")*/ protected int ho;
		
		public APT_PK() {
			// TODO Auto-generated constructor stub
		}
		public APT_PK(int dong, int ho)
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

	@EmbeddedId
	@AttributeOverrides(value = {
			@AttributeOverride(name="dong", column=@Column(name="APT_DONG",columnDefinition="INT")),
			@AttributeOverride(name="ho", column=@Column(name="APT_HO",columnDefinition="INT"))
		  })
	private APT_PK id;
	
	
	@Column(name="APT_OWNER_NAME",columnDefinition="VARCHAR(45)")
	private String owner_name;


	public APT_PK getId() {
		return id;
	}


	public void setId(APT_PK id) {
		this.id = id;
	}


	public String getOwner_name() {
		return owner_name;
	}


	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
	
	
	
	
}
