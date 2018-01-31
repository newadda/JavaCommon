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
import org.onecellboy.db.hibernate.table.Apartment_Idclass;

public class ColumnLazyLoading {
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
		
		
		
		tx.commit();
		session.close();
	}

}
