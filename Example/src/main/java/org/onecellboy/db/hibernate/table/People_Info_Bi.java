package org.onecellboy.db.hibernate.table;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="people_info")
public class People_Info_Bi {

	@Id
	@Column(name="PEOPLE_INFO_ID",columnDefinition="INT")
	@GeneratedValue(generator="SharedPrimaryKeyGenerator")
    @GenericGenerator(name="SharedPrimaryKeyGenerator",strategy="foreign",parameters =  @Parameter(name="property", value="people"))
	private int info_id;
	
	@Column(name="PEOPLE_INFO_AGE",columnDefinition="INT")
	private int age;
	
	@Column(name="PEOPLE_INFO_BIRTHDAY",columnDefinition="DATETIME")
	private Date birthday;
	
	
	@OneToOne(fetch=FetchType.LAZY)
	@PrimaryKeyJoinColumn
	// È¤Àº
	//@JoinColumn(name = "PEOPLE_INFO_ID",referencedColumnName="PEOPLE_ID")
	private People_Bi people;

	public People_Bi getPeople() {
		return people;
	}

	public void setPeople(People_Bi people) {
		this.people = people;
	}

	
	public int getInfo_id() {
		return info_id;
	}

	public void setInfo_id(int info_id) {
		this.info_id = info_id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	
	
	
}
