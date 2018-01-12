package org.onecellboy.db.hibernate.table;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="people")
public class People_Uni {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PEOPLE_ID",columnDefinition="INT")
	private int id;
	
	@Column(name="PEOPLE_NAME",columnDefinition="VARCHAR(45)")
	private String name;

	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name="PHONE_OWNER_ID",referencedColumnName="PEOPLE_ID")
	private List<Phone_Uni> phones = new LinkedList<Phone_Uni>();
	
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval=true)
	@JoinTable(
			name="people_car",
			joinColumns= {@JoinColumn(name="PC_PEOPLE_ID",referencedColumnName="PEOPLE_ID")},
			inverseJoinColumns= {@JoinColumn(name="PC_CAR_ID",referencedColumnName="CAR_ID")}
			)
	private List<Car_Uni> cars = new LinkedList<Car_Uni>();
	

	
	

	public List<Car_Uni> getCars() {
		return cars;
	}


	public void setCars(List<Car_Uni> cars) {
		this.cars = cars;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<Phone_Uni> getPhones() {
		return phones;
	}


	public void setPhones(List<Phone_Uni> phones) {
		this.phones = phones;
	}
	
	
	
	
}
