package mypack;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.Service;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class DataInsertion {
	
	SessionFactory factory = new Configuration().configure().buildSessionFactory();

	public static void main(String[] args) {
		
		
		//new DataInsertion().obtenerUnicoRegristo();		
		//snew DataInsertion().listarRegistros();
		
		//new DataInsertion().agregarRegistro(1, "Diego", "Bejismo");
		//new DataInsertion().deleteEmployee(12);
		new DataInsertion().actualizarRegistro(18, "cucumelo");
	
	}	

	  public Integer agregarRegistro(int id, String name, String address){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      Integer employeeID = null;
	      
	      try {
	         tx = session.beginTransaction();
	         DataProvider employee = new DataProvider(id, name, address);
	         employeeID = (Integer) session.save(employee); 
	         tx.commit();
	         System.out.println("se logro escribir");
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return employeeID;
	   }

	  public void listarRegistros( ){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         List usuarios = session.createQuery("FROM Employee").list(); 
	         for (Iterator iterator = usuarios.iterator(); iterator.hasNext();){
	            DataProvider employee = (DataProvider) iterator.next(); 
	            System.out.print("ID: " + employee.getUser_id()); 
	            System.out.print("Name: " + employee.getUser_name()); 
	            System.out.println("Address: " + employee.getUser_address()); 
	         }
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	   }
	public void obtenerUnicoRegristo()
	{
		Configuration con = new Configuration();
		con.configure("hibernate.cfg.xml");
		SessionFactory SF = con.buildSessionFactory();
		Session session = SF.openSession();
		Object ob = session.load(DataProvider.class, new Integer(12));
		DataProvider DP = (DataProvider) ob;
		System.out.println("Name : " + DP.getUser_name());
		System.out.println("Address : " + DP.getUser_address());
		
	}
	
	//Queda pendiente de checkear, hay que ver el tema de "unable to locate persister"
	public void deleteRegistro(Integer EmployeeID){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         DataProvider employee = (DataProvider)session.get(DataProvider.class, EmployeeID); 
	         session.delete(employee); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	   }
			
	   public void actualizarRegistro(Integer EmployeeID, String address ){
		      Session session = factory.openSession();
		      Transaction tx = null;
		      
		      try {
		         tx = session.beginTransaction();
		         DataProvider employee = (DataProvider)session.get(DataProvider.class, EmployeeID); 
		         employee.setUser_address(address);
				 session.update(employee); 
		         tx.commit();
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close(); 
		      }
		   }

}
