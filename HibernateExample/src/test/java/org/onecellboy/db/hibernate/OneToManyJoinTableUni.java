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
		System.out.println("======People Insert시 People에 포함된 car도 자동 Insert======");
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		People_Uni people=new People_Uni();
		people.setName("이름");
		
		
		/*people 과 car 이 서로가 서로를 설정하지 않으면 insert는 되는데 관계가 맺어 지지 않는다.(참조가 null이 된다.)*/
		Car_Uni car1 = new Car_Uni();
		car1.setName("스포츠카");
		
		Car_Uni car2 = new Car_Uni();
		car2.setName("포르쉐");
	
		
		Car_Uni car3 = new Car_Uni();
		car3.setName("다마스");

		
		people.getCars().add(car1);
		people.getCars().add(car2);
		people.getCars().add(car3);
		
		
		session.save(people);
		test_id = people.getId();
		
		
		tx.commit();
		session.close();
		
		
		System.out.println("-------People insert 후에 car 추가해 보기-------");
		
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		people = session.get(People_Uni.class, test_id);
		
		Car_Uni car4 = new Car_Uni();
		car4.setName("포터");
		people.getCars().add(car4);
		
		tx.commit();
		session.close();
		
		
		
		System.out.println("======Delete TEST======");
		System.out.println("-------People에서 car 하나 제거-------");
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		People_Uni people2 = session.get(People_Uni.class, test_id);
		
		List<Car_Uni> cars = people2.getCars();
		System.out.println("car 삭제 전 car들 갯수 : "+cars.size());
		
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
		System.out.println("======club Insert시 club에 포함된 People도 자동 Insert======");
		
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		
		Car_Uni club = new Car_Uni();
		club.setName("test");
		
		People_Uni people = new People_Uni();
		people.setName("두둥실");
		
		session.save(people);
		
	
		people.getCars().add(club);
		
		session.save(club);
		
		
		tx.commit();
		session.close();
		
		
	}
	*/

}
