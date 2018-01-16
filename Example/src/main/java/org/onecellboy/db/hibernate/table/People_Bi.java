package org.onecellboy.db.hibernate.table;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="people")
public class People_Bi {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PEOPLE_ID",columnDefinition="INT")
	private int id;
	
	@Column(name="PEOPLE_NAME",columnDefinition="VARCHAR(45)")
	private String name;

	
	@OneToOne(fetch=FetchType.LAZY,mappedBy="people", cascade = CascadeType.ALL)
	private People_Info_Bi people_Info;
	
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="owner_people", cascade = CascadeType.ALL,orphanRemoval=true)
	//@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	//@JoinColumn(name="PHONE_OWNER_ID",referencedColumnName="PEOPLE_ID")
	private List<Phone_Bi> phones = new LinkedList<Phone_Bi>();
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="people", cascade = CascadeType.ALL,orphanRemoval=true)
	private List<Car_Bi> cars = new LinkedList<Car_Bi>();
	
	
	@ManyToMany(fetch=FetchType.LAZY,mappedBy="peoples", cascade = CascadeType.ALL)
	private List<Club_Bi> clubs = new LinkedList<Club_Bi>();
	
	
	
	
	
	public List<Club_Bi> getClubs() {
		return clubs;
	}

	public void setClubs(List<Club_Bi> clubs) {
		this.clubs = clubs;
	}

	public List<Car_Bi> getCars() {
		return cars;
	}

	public void setCars(List<Car_Bi> cars) {
		this.cars = cars;
	}

	public List<Phone_Bi> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone_Bi> phones) {
		this.phones = phones;
	}

	public People_Info_Bi getPeople_Info() {
		return people_Info;
	}

	public void setPeople_Info(People_Info_Bi people_Info) {
		this.people_Info = people_Info;
	}
	
	
	/* People에서 People_Info 설정시 자동으로 People_Info에 People을 역으로 설정할 수 있게 하는 코드이다. 
	 * save 할 때마다 People_Info.setPeople() 을 하지 않아도 되는 이점이 존재한다.
	 * */
	/*
	public void setPeople_Info(People_Info_Bi people_Info) {
		 if(this.people_Info!=null)
		 {
		   this.people_Info.setPeople(null);
		 }
		 this.people_Info = people_Info;

		 if(people_Info!=null)
		 {
		 people_Info.setPeople(this);
		 }
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
