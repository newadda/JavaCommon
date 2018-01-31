package org.onecellboy.db.hibernate.table;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="car")
public class Car_Uni {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CAR_ID",columnDefinition="INT")
	private int id;
	
	@Column(name="CAR_NAME",columnDefinition="VARCHAR(45)")
	private String name;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinTable(
			name="people_car",
			joinColumns= {@JoinColumn(name="PC_CAR_ID",referencedColumnName="CAR_ID")},
			inverseJoinColumns= { @JoinColumn(name="PC_PEOPLE_ID",referencedColumnName="PEOPLE_ID")}
			)
	private People_Uni people ;
	
	
	
	
	
	public People_Uni getPeople() {
		return people;
	}
/*
	public void setPeople(People_Uni people) {
		this.people = people;
	}
*/
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
	
	
	
}
