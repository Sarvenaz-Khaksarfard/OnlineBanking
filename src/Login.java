import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame {

	public Login() {

		JPanel panel = new JPanel();
		panel.setBackground(new Color(180, 180, 210));
		panel.setLayout(null);

		JLabel userlbl = new JLabel();
		userlbl.setText("Username");
		userlbl.setBounds(50, 70, 100, 30);
		userlbl.setFont(new Font("Arial", 20, 20));
		panel.add(userlbl);

		JTextField userText = new JTextField();
		userText.setBounds(170, 70, 200, 35);
		userText.setFont(new Font("Arial", 20, 20));
		panel.add(userText);

		JLabel passlbl = new JLabel();
		passlbl.setText("Password");
		passlbl.setBounds(50, 110, 100, 30);
		passlbl.setFont(new Font("Arial", 20, 20));
		panel.add(passlbl);

		JPasswordField passText = new JPasswordField();
		passText.setEchoChar('*');
		passText.setBounds(170, 110, 200, 35);
		passText.setFont(new Font("Arial", 20, 20));
		panel.add(passText);

		JButton loginbtn = new JButton("Login");
		loginbtn.setBounds(180, 180, 100, 50);
		loginbtn.setFont(new Font("Arial", 20, 20));
		loginbtn.setBackground(new Color(80, 80, 130));
		loginbtn.setForeground(Color.WHITE);
		panel.add(loginbtn);

		loginbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String password = new String(passText.getPassword());

				if (userText.getText().equals("admin") && password.equals("admin")) {
					new AdminGUI();
				} else {
					CustomerDAO customerHandler = new CustomerDAO();
					List<Customer> customer = (List<Customer>) customerHandler
							.customerAuthentication(userText.getText(), password);
					if (customer.size() > 0) {
						new CustomerGUI(customer.get(0));
					} else {
						JOptionPane.showMessageDialog(null, "Invalid username and password, pleease try again");
					}
				}
			}
		});

		JLabel signuplbl = new JLabel("Create a new account");
		signuplbl.setBounds(160, 250, 210, 30);
		signuplbl.setFont(new Font("Arial", 20, 16));
		panel.add(signuplbl);

		signuplbl.addMouseListener(new MouseAdapter() {

			public void mouseEntered(java.awt.event.MouseEvent evt) {
				signuplbl.setForeground(Color.BLUE);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				signuplbl.setForeground(Color.BLACK);
			}

			public void mouseClicked(MouseEvent e) {
				JTextField acNumText = new JTextField();
				JTextField userText = new JTextField();
				JTextField passText = new JTextField();
				Object[] input = { "Account Number: ", acNumText, "Username: ", userText, "Password", passText };
				int result = JOptionPane.showConfirmDialog(null, input);
				if (acNumText.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter your account number");
				} else if (!(acNumText.getText().equals(""))) {
					if (result == JOptionPane.YES_OPTION) {
						CustomerDAO customerHandler = new CustomerDAO();
						Customer customer = customerHandler.getCustomer(Integer.parseInt(acNumText.getText()));
						customer.setUsername(userText.getText());
						customer.setPassword(passText.getText());
						customerHandler.updateCustomer(customer);
					}
				}
			}
		});
		setContentPane(panel);
	}
}
