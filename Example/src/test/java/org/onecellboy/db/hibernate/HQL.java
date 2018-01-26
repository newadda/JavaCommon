package org.onecellboy.db.hibernate;

import static org.junit.Assert.*;

import java.io.File;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.onecellboy.db.hibernate.table.Club_Bi;
import org.onecellboy.db.hibernate.table.Course;
import org.onecellboy.db.hibernate.table.NotMappingStudent;
import org.onecellboy.db.hibernate.table.People_Bi;
import org.onecellboy.db.hibernate.table.Score;
import org.onecellboy.db.hibernate.table.Student;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HQL {

	
	
	
	
	static SessionFactory sessionFactory = null;
	static StandardServiceRegistry registry = null;

	@BeforeClass
	public static void setUp()
	{
		registry = new StandardServiceRegistryBuilder().configure(new File("./conf/hibernate/hibernate.cfg.xml")) 
				.build();
				sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

	}
	
	@AfterClass
	public static void setDown()
	{
		sessionFactory.close();
	}
	
	@After
	public void testAfter()
	{
		System.out.println();
		System.out.println();
	}
	
	
	
	
	
	@Test
	public void test01() {
		
		Session session =null;
		Transaction tx = null;
		
		System.out.println("======기본 HQL SELECT======");
	
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		Query query=null;
		List resultList=null ;
		
		query = session.createQuery("FROM Student");
		resultList = query.getResultList();
		for(Student st : (List<Student>)resultList)
		{
			System.out.println("-----------------");
			System.out.println("id="+st.getId());
			System.out.println("name="+st.getName());
			System.out.println("birthday="+st.getBirthday());
		
		}
		
		
		query = session.createQuery("FROM Student Where id=:id");
		query.setParameter("id", 1);
		resultList = query.getResultList();
		for(Student st : (List<Student>)resultList)
		{
			System.out.println("-----------------");
			System.out.println("id="+st.getId());
			System.out.println("name="+st.getName());
			System.out.println("birthday="+st.getBirthday());
		
		}
		
		
		System.out.println();
		System.out.println();
		System.out.println("======Select 사용  SELECT======");
		query = session.createQuery("SELECT sdt FROM Student sdt");
		resultList = query.getResultList();
		for(Student st : (List<Student>)resultList)
		{
			System.out.println("-----------------");
			System.out.println("id="+st.getId());
			System.out.println("name="+st.getName());
			System.out.println("birthday="+st.getBirthday());
		
		}
		
		
		System.out.println();
		System.out.println();
		System.out.println("====== JOIN 사용해서 class로 맵핑 ======");
		query = session.createQuery("SELECT sdt,sc,cr FROM Student sdt INNER JOIN Score sc ON sdt.id = sc.id.student_id INNER JOIN Course cr ON sc.id.course_id = cr.id ");
		resultList = query.getResultList();
		for(Object[] values : (List<Object[]>)resultList)
		{
			
			System.out.println("--------types---------");
			for(Object o : values)
			{
				System.out.println("type = "+o.getClass().getTypeName());
			}
		}
		
		System.out.println("--------Data---------");
		for(Object[] values : (List<Object[]>)resultList)
		{
			Student student = (Student)values[0];
			Score score = (Score)values[1];
			Course course = (Course)values[2];
			System.out.print("name="+student.getName());
			System.out.print(" course="+course.getName());
			System.out.print(" score="+score.getScore());
			System.out.println();
		}
		
		
		System.out.println();
		System.out.println();
		System.out.println("====== 다른 클래스의 생성자로 맵핑하기 ======");
		query = session.createQuery("SELECT NEW org.onecellboy.db.hibernate.table.NotMappingStudent(sdt.id, sdt.name, sdt.birthday) FROM Student sdt");
		List<NotMappingStudent> notMaps=(List<NotMappingStudent>)query.getResultList();
		
		
		for(NotMappingStudent notMap : notMaps)
		{
			System.out.println("-----------------");
			System.out.println("id="+notMap.getId());
			System.out.println("name="+notMap.getName());
			System.out.println("birthday="+notMap.getBirthday());
		
		}
		
	
		
		
		
		
		System.out.println();
		System.out.println();
		System.out.println("======필요한 column 만  SELECT======");
		query = session.createQuery("SELECT sdt.id, sdt.name FROM Student sdt ORDER BY sdt.name");
		resultList = query.getResultList();
		for(Object[] values : (List<Object[]>)resultList)
		{
			
			System.out.println("--------types---------");
			for(Object o : values)
			{
				System.out.println("type = "+o.getClass().getTypeName());
			}
		}
		
		for(Object[] values : (List<Object[]>)resultList)
		{
			
			System.out.println("-----------------");
			System.out.println("id="+(Integer)values[0]);
			System.out.println("name="+(String)values[1]);
			
		}
		
		System.out.println();
		System.out.println();
		System.out.println("======SELECT(명시적으로 패키지명까지 붙여서) WHERE 도 ======");
		query = session.createQuery("SELECT sdt.id, sdt.name FROM org.onecellboy.db.hibernate.table.Student sdt WHERE sdt.id = :id");
		query.setParameter("id", 1);
		resultList = query.getResultList();
		for(Object[] values : (List<Object[]>)resultList)
		{
			
			System.out.println("--------types---------");
			for(Object o : values)
			{
				System.out.println("type = "+o.getClass().getTypeName());
			}
		}
		
		for(Object[] values : (List<Object[]>)resultList)
		{
			
			System.out.println("-----------------");
			System.out.println("id="+(Integer)values[0]);
			System.out.println("name="+(String)values[1]);
			
		}
		
		
		System.out.println();
		System.out.println();
		System.out.println("====== 그룹 함수사용 하나 일때 ======");
		query = session.createQuery("SELECT count(sdt.id) FROM Student sdt");
		Object singleResult = query.getSingleResult();
		Long count = (Long)singleResult;
		System.out.println("Student count()="+count);
		
		
		System.out.println();
		System.out.println("====== 그룹 함수사용 두개 일때 ======");
		query = session.createQuery("SELECT count(sdt.id), count(sdt.id) FROM Student sdt");
		Object[] result = (Object[])query.getSingleResult();
		Long count1 = (Long)result[0];
		Long count2 = (Long)result[1];
		System.out.println("Student count()="+count1);
		System.out.println("Student count()="+count2);
		
		

		
		
		tx.commit();
		session.close();
	}
	
	
	@Test
	public void test02()
	{
		Session session =null;
		Transaction tx = null;
		
	
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		System.out.println("======== UPDATE =========");
		Query query = session.createQuery("UPDATE FROM Student SET name = :name WHERE id = :id ");
		query.setParameter("id", 1);
		query.setParameter("name", "Guest");
		int affectedCount = query.executeUpdate();
		System.out.println("row affected : ="+affectedCount);
		
		System.out.println();
		System.out.println("======== INSERT =========");
		query = session.createQuery("INSERT INTO Student (id,name,birthday)  Select (a.id)+99,a.name,a.birthday from Student a");

		affectedCount = query.executeUpdate();
		System.out.println("row affected : ="+affectedCount);
		
		
		tx.commit();
		session.close();
		
	}

}
