import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryUtility {

	private static SessionFactory sessionFactory ;
	
	private SessionFactoryUtility(){
	}
	
	
	public static SessionFactory getInstance(){
		if(sessionFactory == null){
			sessionFactory = new Configuration().configure().buildSessionFactory();
		}
		return sessionFactory;
	}
}
