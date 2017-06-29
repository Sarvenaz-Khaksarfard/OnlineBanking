import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class CustomerDAO {

	public void addCustomer(Customer customer) {

		Session session = SessionFactoryUtility.getInstance().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(customer);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();

		} finally {
			session.close();

		}

	}

	public Customer getCustomer(int accountNumber) {
		Session session = SessionFactoryUtility.getInstance().openSession();
		Customer customer = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			customer = (Customer) session.get(Customer.class, accountNumber);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();

		} finally {
			session.close();

		}

		return customer;
	}

	public void updateCustomer(Customer customer) {
		Session session = SessionFactoryUtility.getInstance().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(customer);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();

		} finally {
			session.close();

		}
	}

	public void deleteCustomer(Customer customer) {
		Session session = SessionFactoryUtility.getInstance().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(customer);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();

		} finally {
			session.close();

		}
	}

	public List<Customer> getAllCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		Session session = SessionFactoryUtility.getInstance().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			customers = session.createQuery("from Customer").list();
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();

		} finally {
			session.close();

		}
		return customers;
	}

	public List<Customer> customerAuthentication(String username, String password) {
		Session session = SessionFactoryUtility.getInstance().openSession();
		Transaction tx = null;
		List<Customer> customer = new ArrayList<Customer>();
		//Customer customer = null;
		try {
			tx = session.beginTransaction();

			Query query = session.createQuery("from Customer where username =? AND password=?");
			query.setParameter(0, username);
			query.setParameter(1, password);
		
			customer = (List<Customer>) query.getResultList();
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();

		} finally {
			session.close();

		}
		
		return customer;
	}
	
	public boolean checkCustomerAcNumber(int ac_num){
		Session session = SessionFactoryUtility.getInstance().openSession();
		List<Integer> account_number = new ArrayList<Integer>();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			account_number = session.createQuery("select accountNumber from Customer").list();
			session.getTransaction().commit();
			
			for(Integer ac_number : account_number){
				if(ac_number == ac_num){
					return false;
				}
			}
		}
		catch(RuntimeException e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		return true;
	}
}
