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
import org.onecellboy.db.hibernate.table.Apartment_Embeddable;
import org.onecellboy.db.hibernate.table.Apartment_Embeddable.APT_PK;

public class CompositePrimaryKey {
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
	public void test() {
		Session session =null;
		Transaction tx = null;
		
		System.out.println("======Insert TEST======");
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		Apartment_Embeddable apartment1 = new Apartment_Embeddable();
		apartment1.setOwner_name("¶¥ºÎÀÚ");
		
		// primary key ¼³Á¤
		Apartment_Embeddable.APT_PK id=new Apartment_Embeddable.APT_PK(103, 1204);
		apartment1.setId(id);
		
		
		session.save(apartment1);
		
		
		tx.commit();
		session.close();
		
		
		System.out.println();
		System.out.println();
		System.out.println("======Select TEST======");
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		Apartment_Embeddable.APT_PK apt_pk=new Apartment_Embeddable.APT_PK(apartment1.getId().getDong(), apartment1.getId().getHo());
		
		 
		Apartment_Embeddable apartment2 =session.get(Apartment_Embeddable.class, apt_pk);
		
		System.out.println("*** Dong = "+apartment2.getId().getDong());
		System.out.println("*** Ho = "+apartment2.getId().getHo());
		System.out.println("*** Owner = "+apartment2.getOwner_name());
		
		tx.commit();
		session.close();
		
		
	}

}
