package org.onecellboy.db.hibernate.table;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="phone")
public class Phone_Bi {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PHONE_ID",columnDefinition="INT")
	private int id;
	
	@Column(name="PHONE_NUMBER",columnDefinition="VARCHAR(45)")
	private String number;
	
	
	@Column(name="PHONE_OWNER_ID",columnDefinition="INT",insertable=false, updatable=false)
	private Integer owner_id;

	
	@ManyToOne(optional = false)
	@JoinColumn(name="PHONE_OWNER_ID",referencedColumnName="PEOPLE_ID" )
	private People_Bi owner_people;
	


	public People_Bi getOwner_people() {
		return owner_people;
	}

	public void setOwner_people(People_Bi owner_people) {
		this.owner_people = owner_people;
	}
	
	

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
	
	
	
	
	
}
