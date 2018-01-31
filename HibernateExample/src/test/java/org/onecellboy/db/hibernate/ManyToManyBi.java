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
import org.onecellboy.db.hibernate.table.Club_Bi;
import org.onecellboy.db.hibernate.table.People_Bi;

public class ManyToManyBi {

	
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
		
		int temp_id1= -1;
		int temp_id2= -1;
		int temp_id3= -1;
		
		
		Session session =null;
		Transaction tx = null;
		
		System.out.println("======Insert TEST======");
		System.out.println("======1. People1 은 club들 추가, club들은 people1을 추가하지 않음 ( 한방향에서만 관계생성 ) ======");
		System.out.println("======2. People2, people3 은 club들 추가, club들은 people2을 추가 ( 양방향에서 관계생성 ) ======");
	
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		People_Bi people1 = new People_Bi();
		people1.setName("sun 1");
		
		People_Bi people2 = new People_Bi();
		people2.setName("sun 2");
		
		People_Bi people3 = new People_Bi();
		people3.setName("sun 3");
		
		
		Club_Bi club1 = new Club_Bi();
		club1.setName("soccer");
		
		Club_Bi club2 = new Club_Bi();
		club2.setName("soccer");
		
		
		
		people1.getClubs().add(club1);
		people1.getClubs().add(club2);


		people2.getClubs().add(club1);
		people2.getClubs().add(club2);
		club1.getPeoples().add(people2);
		club2.getPeoples().add(people2);
		

		people3.getClubs().add(club1);
		people3.getClubs().add(club2);
		club1.getPeoples().add(people3);
		club2.getPeoples().add(people3);
		
		session.save(people1);
		session.save(people2);
		session.save(people3);
		
		
		temp_id1 = people1.getId();
		temp_id2 = people2.getId();
		temp_id3 = people3.getId();
		
		
		
		tx.commit();
		session.close();
		
		
		System.out.println();
		System.out.println("====== people1 과 people2 의 club 과의 관계 확인 ======");
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		people1 = session.get(People_Bi.class, temp_id1);
		people2 = session.get(People_Bi.class, temp_id2);
		
		// people1 은 club 과의 관계가 없다. 양방향 관계인데 양방향에서 관계를 설정하지 않았지 때문..
		System.out.println("people1 club size = "+ people1.getClubs().size());
		
		// people2 은 club 과의 관계가 존재한다. 양방향에서 관계를 설정하였기 때문이다.
		System.out.println("people2 club size = "+ people2.getClubs().size());
		
		
		tx.commit();
		session.close();
		
		
		System.out.println();
		System.out.println();
		System.out.println("====== people과 club 의 관계 제거 ======");
		System.out.println("people2에서 club1의 관계를 제거할 경우");
		
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
	
		people2 = session.get(People_Bi.class, temp_id2);
		Club_Bi remove = people2.getClubs().remove(0);
		remove.getPeoples().remove(people2);

		// 양방향이므로 서로가 서로를 양방향에서 관계를 제거해야한다.
		session.saveOrUpdate(people2);
		

		tx.commit();
		session.close();
		
		
		System.out.println();
		System.out.println();
		System.out.println("====== people 제거 ======");
		System.out.println("people2삭제시 people3는 어찌되는가?");
		
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		people3 = session.get(People_Bi.class, temp_id3);
		System.out.println("people2삭제전 people3의 club 갯수 = "+people3.getClubs().size());
		
		tx.commit();
		session.close();
		
		
		
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		people2 = session.get(People_Bi.class, temp_id2);
		session.delete(people2);
		
		tx.commit();
		session.close();
		
		
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		people3 = session.get(People_Bi.class, temp_id3);
		System.out.println("people2삭제후 people3의 club 갯수 = "+people3.getClubs().size());
		
		tx.commit();
		session.close();
		
		
	}

}
