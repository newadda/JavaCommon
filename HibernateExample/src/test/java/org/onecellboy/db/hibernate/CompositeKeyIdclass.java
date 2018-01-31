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
import org.onecellboy.db.hibernate.table.APT_PK_Idclass;
import org.onecellboy.db.hibernate.table.Apartment_Embeddable;
import org.onecellboy.db.hibernate.table.Apartment_Idclass;

public class CompositeKeyIdclass {
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
		
		Apartment_Idclass apartment1 = new Apartment_Idclass();
		apartment1.setOwner_name("¶¥ºÎÀÚ");
		
		// primary key ¼³Á¤
		apartment1.setDong(104);
		apartment1.setHo(1303);

		session.save(apartment1);
		
		
		tx.commit();
		session.close();
		
		
		System.out.println();
		System.out.println();
		System.out.println("======Select TEST======");
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		APT_PK_Idclass apt_pk=new APT_PK_Idclass(apartment1.getDong(),apartment1.getHo());
	
		Apartment_Idclass apartment2 =session.get(Apartment_Idclass.class, apt_pk);
		
		System.out.println("*** Dong = "+apartment2.getDong());
		System.out.println("*** Ho = "+apartment2.getHo());
		System.out.println("*** Owner = "+apartment2.getOwner_name());
		
		tx.commit();
		session.close();
	}

}
