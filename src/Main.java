import java.awt.EventQueue;

import javax.swing.JFrame;

import org.hibernate.SessionFactory;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setTitle("Login");
					frame.setSize(500, 400);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

}
