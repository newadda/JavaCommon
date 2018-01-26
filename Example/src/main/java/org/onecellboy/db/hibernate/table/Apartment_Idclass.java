package org.onecellboy.db.hibernate.table;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "Apartment")
@IdClass(APT_PK_Idclass.class)
public class Apartment_Idclass {
	
	
	// IdClass 의 멤버변수와 변수명이 같아야 한다.
	@Id
	@Column(name="APT_DONG",columnDefinition="INT")
	private int dong;
	
	// IdClass 의 멤버변수와 변수명이 같아야 한다.
	@Id
	@Column(name="APT_HO",columnDefinition="INT")
	private int ho;
	
	

	@Column(name="APT_OWNER_NAME",columnDefinition="VARCHAR(45)")
	private String owner_name;

	@Column(name="APT_TEXT",columnDefinition="VARCHAR(45)")
	@Basic(fetch=FetchType.LAZY)
	private String Text;
	
	
	


	public String getText() {
		return Text;
	}



	public void setText(String text) {
		Text = text;
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



	public String getOwner_name() {
		return owner_name;
	}



	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
	
	
}
