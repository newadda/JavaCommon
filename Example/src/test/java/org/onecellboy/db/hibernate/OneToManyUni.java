package org.onecellboy.db.hibernate;

import static org.junit.Assert.*;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.onecellboy.db.hibernate.table.People_Uni;
import org.onecellboy.db.hibernate.table.Phone_Uni;

public class OneToManyUni {

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
	public void test() {
	
		int test_id=0;
		
		Session session =null;
		Transaction tx = null;
		
		
		System.out.println("======People Insert시 People에 포함된 Phone도 자동 Insert======");
		
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		People_Uni people = new People_Uni();
		people.setName("단소");
		
		
		Phone_Uni phone1 = new Phone_Uni();
		phone1.setNumber("111-1111-1111");
		
		Phone_Uni phone2 = new Phone_Uni();
		phone2.setNumber("222-2222-2222");
		
		Phone_Uni phone3 = new Phone_Uni();
		phone3.setNumber("333-3333-3333");
		
		
		people.getPhones().add(phone1);
		people.getPhones().add(phone2);
		people.getPhones().add(phone3);
		
		session.saveOrUpdate(people);
		
		test_id = people.getId();
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tx.commit();
		session.close();
		
		
		System.out.println("======이미 Insert 된 People에  Phone을 추가시켰을때 자동 Insert======");
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		People_Uni people2 = session.get(People_Uni.class, test_id);
		
		
		Phone_Uni phone4 = new Phone_Uni();
		phone4.setNumber("444-444-4444");
		
		people2.getPhones().add(phone4);
		
		tx.commit();
		session.close();
		
		
		System.out.println("======Insert 된 People에 서 Phone을 제거시켰을때 자동 Delete======");
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		People_Uni people3 = session.get(People_Uni.class, test_id);
		
		Phone_Uni remove = people3.getPhones().remove(1);
	//	Phone_Uni remove2 = session.get(Phone_Uni.class, remove.getId());
		//remove2.setOwner_id(null);
		//remove2.setNumber("asdf");
		
		//session.saveOrUpdate(remove2);

		
		tx.commit();
		session.close();
		
		
	}

}
