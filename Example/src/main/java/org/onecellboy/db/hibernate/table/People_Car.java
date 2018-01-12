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
@Table(name="people_car")
public class People_Car {

	 @Embeddable
	private static class PK_People_Club implements Serializable{
		private static final long serialVersionUID = 1L;
		
		@Column(name="PC_PEOPLE_ID",columnDefinition="INT")
		private int people_id;
		
		@Column(name="PC_CAR_ID",columnDefinition="INT")
		private int car_id;
		
		public PK_People_Club() {
			// TODO Auto-generated constructor stub
		}
		public PK_People_Club(int people_id, int car_id)
		{
			this.people_id = people_id;
			this.car_id = car_id;
		}
		
	}

	 @EmbeddedId
	/* @AttributeOverrides(value={@AttributeOverride(name="uc_coupon_id", column=@Column(name="uc_coupon_id")),
			   @AttributeOverride(name="uc_user_id", column=@Column(name="uc_user_id"))
		   })
*/
	 public PK_People_Club id;

	
}
