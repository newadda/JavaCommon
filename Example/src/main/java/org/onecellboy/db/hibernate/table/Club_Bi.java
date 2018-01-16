package org.onecellboy.db.hibernate.table;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="club")
public class Club_Bi {
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CLUB_ID",columnDefinition="INT")
	private int id;
	

	@Column(name="CLUB_NAME",columnDefinition="VARCHAR(45)")
	private String name;
	
	
	@ManyToMany(fetch=FetchType.LAZY /*, cascade = CascadeType.ALL*/)
	@JoinTable(
			name="people_club",
			joinColumns= {@JoinColumn(name="PC_CLUB_ID",referencedColumnName="CLUB_ID")},
			inverseJoinColumns= { @JoinColumn(name="PC_PEOPLE_ID",referencedColumnName="PEOPLE_ID")}
			)
	private List<People_Bi> peoples = new LinkedList<People_Bi>() ;


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


	public List<People_Bi> getPeoples() {
		return peoples;
	}


	public void setPeoples(List<People_Bi> peoples) {
		this.peoples = peoples;
	}
	
	
	
	
	
	

}
