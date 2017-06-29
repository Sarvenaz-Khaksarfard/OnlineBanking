import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class AdminGUI {

	JFrame frame;
	JPanel panel;
	Object[] cols = { "Account Number", "Name", "Amount", "Type", "Current Balance", "Date" };

	public AdminGUI() {
		frame = new JFrame();
		frame.setSize(650, 470);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(180, 180, 210));
		frame.add(panel);

		JLabel bgImage = new JLabel();
		bgImage.setBounds(20, 20, 580, 350);
		ImageIcon icon = new ImageIcon("src/money.jpg");
		bgImage.setIcon(icon);
		panel.add(bgImage);

		JMenuBar menubar = new JMenuBar();

		JMenu customersMenu = new JMenu("Customers");
		menubar.add(customersMenu);

		JMenuItem viewItem = new JMenuItem("View All Customers");

		viewItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object[] cols = { "Account Number", "Name", "Occupation", "Phone Number", "Address" };
				CustomerDAO customerHandler = new CustomerDAO();
				List<Customer> customers = customerHandler.getAllCustomers();
				List<Object[]> row = new ArrayList<Object[]>();
				for (Customer c : customers) {
					row.add(new Object[] { c.getAccountNumber(), c.getFirstName() + " " + c.getLastName(),
							c.getOccupation(), c.getPhone(), c.getAddress() });
				}
				displayTable(false, row.toArray(new Object[][] {}), cols);
			}
		});
		JMenuItem addItem = new JMenuItem("Add a new Customer");

		addItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Customer customer = new Customer();
				customerGUI(panel, "Save", customer);
			}
		});
		JMenuItem editItem = new JMenuItem("Edit customer");

		editItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String inputAcNumber = JOptionPane.showInputDialog(panel, "Enter your account number");
				CustomerDAO customerHandler = new CustomerDAO();
				if (!(inputAcNumber.equals("")) && isInteger(inputAcNumber)) {
					Customer customer = customerHandler.getCustomer(Integer.parseInt(inputAcNumber));
					if (customer == null) {
						JOptionPane.showMessageDialog(panel,
								"Account number is invalid, please enter a valid account number");
					} else {
						customerGUI(panel, "Edit", customer);
					}
				} else {
					JOptionPane.showMessageDialog(panel, "Please enter a valid account number");
				}
			}
		});
		JMenuItem removeItem = new JMenuItem("Remove customer");

		removeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String inputAcNumber = JOptionPane.showInputDialog(panel, "Enter your account number");
				if (!(inputAcNumber.equals("")) && isInteger(inputAcNumber)) {
					CustomerDAO customerHandler = new CustomerDAO();
					Customer customer = customerHandler.getCustomer(Integer.parseInt(inputAcNumber));
					if (customer == null) {
						JOptionPane.showMessageDialog(panel,
								"Account number is invalid, please enter a valid account number");
					} else {
						int result = JOptionPane.showConfirmDialog(panel,
								"Are you sure you want to remove this customer?\n" + "Account Number: "
										+ customer.getAccountNumber() + "\n" + "Name: " + customer.getFirstName() + " "
										+ customer.getLastName() + "\n" + "Occupation: " + customer.getOccupation()
										+ "\n" + "Phone Number: " + customer.getPhone() + "\n" + "Address: "
										+ customer.getAddress());
						if (result == JOptionPane.YES_OPTION) {
							customerHandler.deleteCustomer(customer);
							JOptionPane.showMessageDialog(panel, "Customer is deleted");
						}
					}
				}
				else{
					JOptionPane.showMessageDialog(panel, "Account number is invalid, please enter a valid account number");
				}
			}
		});

		customersMenu.add(viewItem);
		customersMenu.add(addItem);
		customersMenu.add(editItem);
		customersMenu.add(removeItem);

		JMenu transactionMenu = new JMenu("Transactions");
		menubar.add(transactionMenu);

		JMenuItem transItem = new JMenuItem("View Transactions");
		transactionMenu.add(transItem);

		transItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				TransactionDAO transHandler = new TransactionDAO();
				List<Transactions> trans = transHandler.getAllTransaction();
				List<Object[]> row_vals = new ArrayList<Object[]>();
				for (Transactions t : trans) {
					row_vals.add(new Object[] { t.getCustomer().getAccountNumber(),
							t.getCustomer().getFirstName() + " " + t.getCustomer().getLastName(), t.getAmount(),
							t.getType(), t.getCurrent_balance(), t.getDate() });
				}
				displayTable(true, row_vals.toArray(new Object[][] {}), cols);
			}
		});

		JMenu logoutMenu = new JMenu("Logout");
		menubar.add(logoutMenu);

		JMenuItem logoutItem = new JMenuItem("Logout");
		logoutMenu.add(logoutItem);
		logoutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
			}
		});
		frame.setJMenuBar(menubar);

	}

	public void customerGUI(JPanel panel, String btnLabel, Customer customer) {

		panel.removeAll();
		panel.revalidate();
		panel.repaint();

		JLabel acNumlbl = new JLabel();
		acNumlbl.setText("Account Number");
		acNumlbl.setBounds(30, 30, 180, 30);
		acNumlbl.setFont(new Font("Arial", 20, 20));
		panel.add(acNumlbl);
		JTextField acNumText = new JTextField();
		acNumText.setBounds(230, 30, 200, 35);
		acNumText.setFont(new Font("Arial", 20, 20));
		if (customer.getAccountNumber() != 0)
			acNumText.setText(Integer.toString(customer.getAccountNumber()));
		panel.add(acNumText);

		JLabel fnamelbl = new JLabel();
		fnamelbl.setText("First Name");
		fnamelbl.setBounds(30, 75, 180, 30);
		fnamelbl.setFont(new Font("Arial", 20, 20));
		panel.add(fnamelbl);
		JTextField fnameText = new JTextField();
		fnameText.setBounds(230, 75, 200, 35);
		fnameText.setFont(new Font("Arial", 20, 20));
		fnameText.setText(customer.getFirstName());
		panel.add(fnameText);

		JLabel lnamelbl = new JLabel();
		lnamelbl.setText("Last Name");
		lnamelbl.setBounds(30, 120, 180, 30);
		lnamelbl.setFont(new Font("Arial", 20, 20));
		panel.add(lnamelbl);
		JTextField lnameText = new JTextField();
		lnameText.setBounds(230, 120, 200, 35);
		lnameText.setFont(new Font("Arial", 20, 20));
		lnameText.setText(customer.getLastName());
		panel.add(lnameText);

		JLabel occupationlbl = new JLabel();
		occupationlbl.setText("Occupation");
		occupationlbl.setBounds(30, 165, 180, 30);
		occupationlbl.setFont(new Font("Arial", 20, 20));
		panel.add(occupationlbl);
		JTextField occupationText = new JTextField();
		occupationText.setBounds(230, 165, 200, 35);
		occupationText.setFont(new Font("Arial", 20, 20));
		occupationText.setText(customer.getOccupation());
		panel.add(occupationText);

		JLabel tellbl = new JLabel();
		tellbl.setText("Phone Number");
		tellbl.setBounds(30, 210, 180, 30);
		tellbl.setFont(new Font("Arial", 20, 20));
		panel.add(tellbl);
		JTextField telText = new JTextField();
		telText.setBounds(230, 210, 200, 35);
		telText.setFont(new Font("Arial", 20, 20));
		telText.setText(customer.getPhone());
		panel.add(telText);

		JLabel addresslbl = new JLabel();
		addresslbl.setText("Address");
		addresslbl.setBounds(30, 255, 180, 30);
		addresslbl.setFont(new Font("Arial", 20, 20));
		panel.add(addresslbl);
		JTextField addressText = new JTextField();
		addressText.setBounds(230, 255, 200, 35);
		addressText.setFont(new Font("Arial", 20, 20));
		addressText.setText(customer.getAddress());
		panel.add(addressText);

		JButton savebtn = new JButton();
		savebtn.setText(btnLabel);
		savebtn.setBounds(300, 310, 130, 50);
		savebtn.setFont(new Font("Arial", 20, 20));
		savebtn.setBackground(new Color(80, 80, 130));
		savebtn.setForeground(Color.WHITE);
		panel.add(savebtn);

		if (btnLabel.equals("Edit")) {
			acNumText.setEnabled(false);
			fnameText.setEnabled(false);
			lnameText.setEnabled(false);
		}

		savebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (isInteger((acNumText.getText()))) {
					customer.setAccountNumber(Integer.parseInt(acNumText.getText()));
					customer.setFirstName(fnameText.getText());
					customer.setLastName(lnameText.getText());
					customer.setOccupation(occupationText.getText());
					customer.setPhone(telText.getText());
					customer.setAddress(addressText.getText());

					CustomerDAO customerHandler = new CustomerDAO();

					if (btnLabel.equals("Save")) {
						if (customerHandler.checkCustomerAcNumber(customer.getAccountNumber())) {
							customerHandler.addCustomer(customer);
							JOptionPane.showMessageDialog(panel, "Customer is added");
						} else {
							JOptionPane.showMessageDialog(panel, "This account number belongs to another user");
						}
					} else if (btnLabel.equals("Edit")) {
						customerHandler.updateCustomer(customer);
						JOptionPane.showMessageDialog(panel, "Customer is updated");
					}
				} else {
					JOptionPane.showMessageDialog(panel, "Account number has to be a number");
				}
			}
		});
	}

	public boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

	public void displayTable(boolean check, Object[][] rows, Object[] cols) {
		panel.removeAll();
		panel.revalidate();
		panel.repaint();

		if (check == true) {
			JButton searchbtn = new JButton("Filter transactions by account number");
			searchbtn.setBounds(30, 10, 250, 40);
			searchbtn.setBackground(new Color(80, 80, 130));
			searchbtn.setForeground(Color.WHITE);
			panel.add(searchbtn);

			JTextField searchtext = new JTextField();
			searchtext.setBounds(300, 15, 120, 30);
			panel.add(searchtext);

			searchbtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					TransactionDAO transHandler = new TransactionDAO();
					List<Transactions> trans = transHandler.getTransaction(Integer.parseInt(searchtext.getText()));
					List<Object[]> row_vals = new ArrayList<Object[]>();
					for (Transactions t : trans) {
						row_vals.add(new Object[] { t.getCustomer().getAccountNumber(),
								t.getCustomer().getFirstName() + " " + t.getCustomer().getLastName(), t.getType(),
								t.getAmount(), t.getCurrent_balance(), t.getDate() });
					}
					displayTable(true, row_vals.toArray(new Object[][] {}), cols);
				}
			});
		}

		DefaultTableModel model = new DefaultTableModel(rows, cols);
		JTable table = new JTable(model);

		// table.setSelectionMode(1);
		table.setBackground(Color.WHITE);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(5, 70, 620, 300);
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Arial", Font.BOLD, 13));
		header.setBackground(Color.white);
		table.setFont(new Font("Arial", Font.PLAIN, 13));
		table.setRowHeight(25);

		panel.add(scroll);
	}
}
