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
import org.onecellboy.db.hibernate.table.People_Bi;
import org.onecellboy.db.hibernate.table.Phone_Bi;

public class OneToManyJoinTableBi {
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
		
		int test_id;
		
		Session session =null;
		Transaction tx = null;
		
		System.out.println("======Insert TEST======");


		System.out.println("======People Insert�� People�� ���Ե� car�� �ڵ� Insert======");
		
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		People_Bi people=new People_Bi();
		people.setName("����");
		
	
		
		/*people �� car �� ���ΰ� ���θ� �������� ������ insert�� �Ǵµ� ���谡 �ξ� ���� �ʴ´�.(������ null�� �ȴ�.)*/
		Car_Bi car1 = new Car_Bi();
		car1.setName("�ƹݶ�");
		car1.setPeople(people);
		
		Car_Bi car2 = new Car_Bi();
		car2.setName("��Ʋ��");
		car2.setPeople(people);
		
		Car_Bi car3 = new Car_Bi();
		car3.setName("����");
		car3.setPeople(people);
		
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
		
		people = session.get(People_Bi.class, test_id);
		
		Car_Bi car4 = new Car_Bi();
		car4.setName("��Ƽ��");
		car4.setPeople(people);
		people.getCars().add(car4);
		
		tx.commit();
		session.close();
		
		System.out.println("======Delete TEST======");
		System.out.println("-------People���� car �ϳ� ����-------");
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		People_Bi people2 = session.get(People_Bi.class, test_id);
		
		List<Car_Bi> cars = people2.getCars();
		System.out.println("car ���� �� car�� ���� : "+cars.size());
		
		cars.remove(1);
		
		System.out.println("car ���� �� car�� ���� : "+cars.size());
		
		
		
		tx.commit();
		session.close();
		
		
		
	}

}
