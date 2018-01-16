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


		System.out.println("======People Insert시 People에 포함된 car도 자동 Insert======");
		
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		People_Bi people=new People_Bi();
		people.setName("조인");
		
	
		
		/*people 과 car 이 서로가 서로를 설정하지 않으면 insert는 되는데 관계가 맺어 지지 않는다.(참조가 null이 된다.)*/
		Car_Bi car1 = new Car_Bi();
		car1.setName("아반떼");
		car1.setPeople(people);
		
		Car_Bi car2 = new Car_Bi();
		car2.setName("벤틀리");
		car2.setPeople(people);
		
		Car_Bi car3 = new Car_Bi();
		car3.setName("투싼");
		car3.setPeople(people);
		
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
		
		people = session.get(People_Bi.class, test_id);
		
		Car_Bi car4 = new Car_Bi();
		car4.setName("마티즈");
		car4.setPeople(people);
		people.getCars().add(car4);
		
		tx.commit();
		session.close();
		
		System.out.println("======Delete TEST======");
		System.out.println("-------People에서 car 하나 제거-------");
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		People_Bi people2 = session.get(People_Bi.class, test_id);
		
		List<Car_Bi> cars = people2.getCars();
		System.out.println("car 삭제 전 car들 갯수 : "+cars.size());
		
		cars.remove(1);
		
		System.out.println("car 삭제 후 car들 갯수 : "+cars.size());
		
		
		
		tx.commit();
		session.close();
		
		
		
	}

}
