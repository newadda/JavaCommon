package org.onecellboy.db.hibernate.table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="phone")
public class Phone_Uni {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PHONE_ID",columnDefinition="INT")
	private int id;
	
	@Column(name="PHONE_NUMBER",columnDefinition="VARCHAR(45)")
	private String number;
	
	
	@Column(name="PHONE_OWNER_ID",columnDefinition="INT")
	private Integer owner_id;

	
	@ManyToOne
	@JoinColumn(name="PHONE_OWNER_ID",referencedColumnName="PEOPLE_ID",insertable=false, updatable=false)
	private People_Uni owner_people;

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNumber() {
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}


	public Integer getOwner_id() {
		return owner_id;
	}


	public void setOwner_id(Integer owner_id) {
		this.owner_id = owner_id;
	}


	public People_Uni getOwner_people() {
		return owner_people;
	}


	public void setOwner_people(People_Uni owner_people) {
		this.owner_people = owner_people;
	}
	
	
	
	
}
