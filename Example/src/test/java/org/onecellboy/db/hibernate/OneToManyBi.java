package org.onecellboy.db.hibernate;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.onecellboy.db.hibernate.table.People_Bi;
import org.onecellboy.db.hibernate.table.People_Info_Bi;
import org.onecellboy.db.hibernate.table.Phone_Bi;




/*
 * Bi-directional OneToMany 양방향 1:N 관계  
 * */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OneToManyBi {

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
	public void test01() {
		System.out.println("======Insert TEST======");
		
		int test_id=0;
		
		
		Session session = sessionFactory.openSession();

		Transaction tx = session.beginTransaction();

		People_Bi people = new People_Bi();
		people.setName("이린");
		
		List<Phone_Bi> phones = people.getPhones();
		
		
		
		Phone_Bi phone1=new Phone_Bi();
		phone1.setNumber("010-1111-1111");
		phone1.setOwner_people(people);
		
		
		Phone_Bi phone2=new Phone_Bi();
		phone2.setNumber("010-2222-2222");
		phone2.setOwner_people(people);
		
		Phone_Bi phone3=new Phone_Bi();
		phone3.setNumber("010-3333-3333");
	    phone3.setOwner_people(people);
		
		phones.add(phone1);
		phones.add(phone2);
		phones.add(phone3);
	
		
		session.save(people);
		
		System.out.println("-------People insert 후에 phone 추가해 보기-------");
		Phone_Bi phone4=new Phone_Bi();
		phone4.setNumber("010-4444-4444");
		phone4.setOwner_people(people);
	    phones.add(phone4);
		
		
		
		tx.commit();
		session.close();
		
		test_id = people.getId();
		
		System.out.println();
		System.out.println();
		
		System.out.println("======Select TEST======");
		
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		People_Bi people2 = session.get(People_Bi.class, test_id);
		System.out.println("people id : "+people2.getId());
		System.out.println("people name : "+people2.getName());
		
		for (Phone_Bi phone_Bi : people2.getPhones()) {
			System.out.println("people id : "+phone_Bi.getId());
			System.out.println("people number :"+phone_Bi.getNumber());
			System.out.println("people owner id : "+phone_Bi.getOwner_id());
		}
		
		
		System.out.println();
		System.out.println();
		System.out.println("======Delete TEST======");
		
		
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		People_Bi people3 = session.get(People_Bi.class, test_id);
		
		List<Phone_Bi> phones3 = people3.getPhones();
		System.out.println(""+phones3.size());
		
		phones3.remove(1);
		
		
		tx.commit();
		session.close();

	}

}
