package org.onecellboy.db.hibernate.table;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.onecellboy.db.hibernate.table.Apartment_Embeddable.APT_PK;

@Entity
@Table(name="Score")
public class Score {
	@Embeddable
	public static class Score_PK implements Serializable{
		private static final long serialVersionUID = 1L;
		
		protected int student_id;
		protected int course_id;
		
		public Score_PK() {
			// TODO Auto-generated constructor stub
		}
		public Score_PK(int student_id, int course_id)
		{
			this.student_id = student_id;
			this.course_id = course_id;
		}
		
		
		public int getStudent_id() {
			return student_id;
		}
		public void setStudent_id(int student_id) {
			this.student_id = student_id;
		}
		public int getCourse_id() {
			return course_id;
		}
		public void setCourse_id(int course_id) {
			this.course_id = course_id;
		}
		
		
		@Override
		 public boolean equals(Object o) { 
		        return ((o instanceof Score_PK) && student_id == ((Score_PK)o).getStudent_id() && course_id == ((Score_PK) o).getCourse_id());
		    }
		@Override
		 public int hashCode() { 
		        return (int)(student_id ^ course_id); 
		 }

	}

	@EmbeddedId
	@AttributeOverrides(value = {
			@AttributeOverride(name="student_id", column=@Column(name="STUDENT_ID",columnDefinition="INT")),
			@AttributeOverride(name="course_id", column=@Column(name="COURSE_ID",columnDefinition="INT"))
		  })
	private Score_PK id;
	
	@Column(name="SCORE",columnDefinition="INT")
	private int score;

	public Score_PK getId() {
		return id;
	}

	public void setId(Score_PK id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	

	
}
