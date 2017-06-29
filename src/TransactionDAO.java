import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TransactionDAO {

	public void addTransaction(Transactions transaction){
		Session session = SessionFactoryUtility.getInstance().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(transaction);
			session.getTransaction().commit();
		}
		catch (RuntimeException e) {
			if (tx != null)
			e.printStackTrace();
		}
		finally{
			session.close();
		}
	}
	
	public List<Transactions> getTransaction(int account_number){
		Session session = SessionFactoryUtility.getInstance().openSession();
		List<Transactions> trans = new ArrayList<Transactions>();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			trans= session.createQuery("from Transactions where accountNumber = " + account_number).list();			
			session.getTransaction().commit();
		}
		catch (RuntimeException e) {
			if (tx != null)
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		return trans;
		
	}
	
	public List<Transactions> getAllTransaction(){
		Session session = SessionFactoryUtility.getInstance().openSession();
		List<Transactions> trans = new ArrayList<Transactions>();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			trans= session.createQuery("from Transactions").list();			
			session.getTransaction().commit();
		}
		catch (RuntimeException e) {
			if (tx != null)
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		return trans;
		
	}
}
