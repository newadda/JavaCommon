package org.onecellboy.db.hibernate;

import static org.junit.Assert.*;

import java.io.File;
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
import org.junit.Test;
import org.onecellboy.db.hibernate.table.Car_Bi;
import org.onecellboy.db.hibernate.table.Car_Uni;
import org.onecellboy.db.hibernate.table.People_Bi;
import org.onecellboy.db.hibernate.table.People_Uni;

public class OneToManyJoinTableUni {

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
	public void testPeople() {
	int test_id;
		
		Session session =null;
		Transaction tx = null;
		
		System.out.println("======Insert TEST======");
		System.out.println("======People Insert�� People�� ���Ե� car�� �ڵ� Insert======");
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		People_Uni people=new People_Uni();
		people.setName("�̸�");
		
		
		/*people �� car �� ���ΰ� ���θ� �������� ������ insert�� �Ǵµ� ���谡 �ξ� ���� �ʴ´�.(������ null�� �ȴ�.)*/
		Car_Uni car1 = new Car_Uni();
		car1.setName("������ī");
		
		Car_Uni car2 = new Car_Uni();
		car2.setName("������");
	
		
		Car_Uni car3 = new Car_Uni();
		car3.setName("�ٸ���");

		
		people.getCars().add(car1);
		people.getCars().add(car2);
		people.getCars().add(car3);
		
		
		session.save(people);
		test_id = people.getId();
		
		
		tx.commit();
		session.close();
		
		
		System.out.println("-------People insert �Ŀ� car �߰��� ����-------");
		
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		people = session.get(People_Uni.class, test_id);
		
		Car_Uni car4 = new Car_Uni();
		car4.setName("����");
		people.getCars().add(car4);
		
		tx.commit();
		session.close();
		
		
		
		System.out.println("======Delete TEST======");
		System.out.println("-------People���� car �ϳ� ����-------");
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		People_Uni people2 = session.get(People_Uni.class, test_id);
		
		List<Car_Uni> cars = people2.getCars();
		System.out.println("car ���� �� car�� ���� : "+cars.size());
		
		cars.remove(1);
		
		
		tx.commit();
		session.close();
		
		


		
	}
	
	
	/*
	@Test
	public void testClub() {
		Session session =null;
		Transaction tx = null;
		
		System.out.println("======Insert TEST======");
		System.out.println("======club Insert�� club�� ���Ե� People�� �ڵ� Insert======");
		
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		
		Car_Uni club = new Car_Uni();
		club.setName("test");
		
		People_Uni people = new People_Uni();
		people.setName("�εս�");
		
		session.save(people);
		
	
		people.getCars().add(club);
		
		session.save(club);
		
		
		tx.commit();
		session.close();
		
		
	}
	*/

}
