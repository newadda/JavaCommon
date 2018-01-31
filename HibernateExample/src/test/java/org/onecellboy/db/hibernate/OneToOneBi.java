package org.onecellboy.db.hibernate;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.onecellboy.db.hibernate.table.People_Bi;
import org.onecellboy.db.hibernate.table.People_Info_Bi;


/*
 * Bi-directional OneToOne 양방향 1:1 관계  
 * */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OneToOneBi {

	static SessionFactory sessionFactory = null;
	static StandardServiceRegistry registry = null;

	static int select_id = 0;
	
	
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
	public void test01_Insert() {
		System.out.println("======Insert TEST======");
		Session session = sessionFactory.openSession();

		Transaction tx = session.beginTransaction();

		System.out.println("---People Insert---");
		
		People_Bi people = new People_Bi();
		people.setName("소년");
		
		System.out.println("*insert 전 people id : "+people.getId());
		
		session.save(people);
		
		System.out.println("*insert 후 people id : "+people.getId());
		
		
		
		System.out.println();
		System.out.println("---People Insert 시 자동 People_Info Insert---");
		
		people = new People_Bi();
		people.setName("소년2");
		
		
		People_Info_Bi people_Info = new People_Info_Bi();
		people_Info.setAge(12);
		people_Info.setBirthday(new Date());
		
		
		
		System.out.println("*insert 전 people_Info id : "+people_Info.getInfo_id());
		
		people.setPeople_Info(people_Info);
		people_Info.setPeople(people);
		session.save(people);
		
		
		select_id = people.getId();
		
		System.out.println("*insert 후 people_Info id : "+people_Info.getInfo_id());
		
		tx.commit();
		session.close();
		
		

	}
	
	
	@Test
	public void test02_select() {
		System.out.println("======Select TEST======");
		Session session = sessionFactory.openSession();

		Transaction tx = session.beginTransaction();

		People_Bi people = session.get(People_Bi.class, select_id);
		
		System.out.println("*people.id : "+people.getId());
		System.out.println("*people.name : "+people.getName());
		System.out.println("*people.getUpperName : "+people.getUpperName());
		
		People_Info_Bi people_Info = people.getPeople_Info();
		
		System.out.println("*people_Info.info_id : "+people_Info.getInfo_id());
		System.out.println("*people_Info.age : "+people_Info.getAge());
		System.out.println("*people_Info.birthday : "+people_Info.getBirthday());
		
		tx.commit();
		session.close();
	}
	
	
	@Test
	public void test03_delete() {
		System.out.println("======Delete TEST======");
		Session session = sessionFactory.openSession();

		Transaction tx = session.beginTransaction();
		
		People_Bi people = session.get(People_Bi.class, select_id);
		
		session.delete(people);
		
		People_Info_Bi people_Info = session.get(People_Info_Bi.class, select_id);
		
		if(people_Info==null)
		{
			System.out.println("*people_Info == null");
		}
	
		
		tx.commit();
		
	
		session.close();
	}
	
	
	
	

}
