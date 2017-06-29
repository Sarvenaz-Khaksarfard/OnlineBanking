import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class CustomerGUI {

	JPanel panel;

	public CustomerGUI(Customer customer) {
		JFrame frame = new JFrame();
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
		ImageIcon icon = new ImageIcon("src/coins.jpg");
		bgImage.setIcon(icon);
		panel.add(bgImage);

		JMenuBar menubar = new JMenuBar();

		JMenu transactionsMenu = new JMenu("Transactions");
		menubar.add(transactionsMenu);

		JMenuItem depositItem = new JMenuItem("Deposit");
		JMenuItem transferItem = new JMenuItem("Transfer");
		JMenuItem withdrawItem = new JMenuItem("Withdraw");
		JMenuItem viewItem = new JMenuItem("View All transactions");

		transactionsMenu.add(depositItem);
		transactionsMenu.add(transferItem);
		transactionsMenu.add(withdrawItem);
		transactionsMenu.add(viewItem);

		depositItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				display("Deposit", customer);
			}
		});
		transferItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				display("Transfer", customer);
			}
		});
		withdrawItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				display("Withdraw", customer);
			}
		});
		viewItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				Object[] cols = { "Date", "Amount", "Type", "Current Balance" };
				TransactionDAO tranHandler = new TransactionDAO();
				List<Transactions> trans = tranHandler.getTransaction(customer.getAccountNumber());
				List<Object[]> row_vals = new ArrayList<Object[]>();
				for (Transactions t : trans) {
					row_vals.add(new Object[] { t.getDate(), t.getAmount(), t.getType(), t.getCurrent_balance() });
				}
				displayTransaction(row_vals.toArray(new Object[][] {}), cols);
			}
		});
		JMenu profileMenu = new JMenu("Profile");
		menubar.add(profileMenu);

		JMenuItem viewProfileItem = new JMenuItem("Edit Profile");
		profileMenu.add(viewProfileItem);

		viewProfileItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AdminGUI gui = new AdminGUI();
				gui.frame.setVisible(false);
				gui.customerGUI(panel, "Edit", customer);
			}
		});

		JMenuItem passwordItem = new JMenuItem("Change Password");
		profileMenu.add(passwordItem);

		passwordItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JLabel userlbl = new JLabel();
				userlbl.setText(customer.getUsername());
				JTextField passText = new JTextField();
				JTextField confirmPassText = new JTextField();
				Object[] input = { "Username ", userlbl, "Enter your password ", passText, "Confirm your password",
						confirmPassText };
				int result = JOptionPane.showConfirmDialog(null, input);
				if (passText.getText().equals("") || confirmPassText.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter your password");
				} else if (!(passText.getText().equals("")) && !(confirmPassText.getText().equals(""))) {
					if (passText.getText().equals(confirmPassText.getText()) && result == JOptionPane.YES_OPTION) {
						CustomerDAO customerHandler = new CustomerDAO();
						customer.setPassword(passText.getText());
						customerHandler.updateCustomer(customer);
					} else if (!(passText.getText().equals(confirmPassText.getText()))) {
						JOptionPane.showMessageDialog(null, "Password incorrect, try again");
					}
				}
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

	public void display(String action, Customer customer) {
		panel.removeAll();
		panel.revalidate();
		panel.repaint();

		JLabel acNumlbl = new JLabel();
		acNumlbl.setText("Account Number");
		acNumlbl.setBounds(30, 30, 180, 30);
		acNumlbl.setFont(new Font("Arial", 20, 20));
		panel.add(acNumlbl);
		JLabel acNum = new JLabel();
		acNum.setBounds(230, 30, 200, 35);
		acNum.setFont(new Font("Arial", 20, 20));
		acNum.setText(Integer.toString(customer.getAccountNumber()));
		panel.add(acNum);

		JLabel namelbl = new JLabel();
		namelbl.setText("Name");
		namelbl.setBounds(30, 75, 180, 30);
		namelbl.setFont(new Font("Arial", 20, 20));
		panel.add(namelbl);
		JLabel name = new JLabel();
		name.setText(customer.getFirstName() + " " + customer.getLastName());
		name.setBounds(230, 75, 250, 35);
		name.setFont(new Font("Arial", 20, 20));
		panel.add(name);

		JLabel currentBalancelbl = new JLabel();
		currentBalancelbl.setText("Current Balance");
		currentBalancelbl.setBounds(30, 120, 180, 30);
		currentBalancelbl.setFont(new Font("Arial", 20, 20));
		panel.add(currentBalancelbl);
		JLabel currentBalance = new JLabel();
		currentBalance.setBounds(230, 120, 200, 35);
		currentBalance.setFont(new Font("Arial", 20, 20));
		if (customer.getBalance() == null) {
			customer.setBalance(BigDecimal.ZERO);
		}
		currentBalance.setText((customer.getBalance()).toString());
		panel.add(currentBalance);

		if (action.equals("Transfer")) {
			displayTransfer(customer);
		} else {
			JLabel balancelbl = new JLabel();
			balancelbl.setText(action + " Amount:");
			balancelbl.setBounds(30, 165, 180, 30);
			balancelbl.setFont(new Font("Arial", 20, 20));
			panel.add(balancelbl);
			JTextField balanceText = new JTextField();
			balanceText.setBounds(230, 165, 200, 35);
			balanceText.setFont(new Font("Arial", 20, 20));
			panel.add(balanceText);

			JButton savebtn = new JButton("Save Transaction");
			savebtn.setFont(new Font("Arial", 20, 18));
			savebtn.setBounds(300, 230, 180, 50);
			savebtn.setBackground(new Color(80, 80, 130));
			savebtn.setForeground(Color.WHITE);
			panel.add(savebtn);

			savebtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					BigDecimal balance = customer.getBalance();
					
					if (balanceText.getText() == "" || !(isInteger(balanceText.getText()))) {
						JOptionPane.showMessageDialog(null, "Please enter a valid amount");
					} else {
						BigDecimal amount = new BigDecimal(balanceText.getText());
						CustomerDAO customerHandler = new CustomerDAO();
						Transactions tran = new Transactions();
						TransactionDAO transactionHandler = new TransactionDAO();
						tran.setCustomer(customer);

						tran.setDate(new Date());
						tran.setAmount(amount);

						if (action.equals("Deposit")) {
							customer.setBalance(balance.add(amount));
							customerHandler.updateCustomer(customer);
							tran.setType("Deposit");
							tran.setCurrent_balance(customer.getBalance());
							transactionHandler.addTransaction(tran);

							JOptionPane.showMessageDialog(null, amount + "$ has been deposited successfully");

						} else if (action.equals("Withdraw")) {
							int difference = balance.compareTo(amount);
							if (difference == 1 || difference == 0) {
								customer.setBalance(balance.subtract(amount));
								customerHandler.updateCustomer(customer);
								tran.setType("Withdraw");
								tran.setCurrent_balance(customer.getBalance());
								transactionHandler.addTransaction(tran);
								JOptionPane.showMessageDialog(null, amount + "$ has been withdrawed successfully");
							} else {
								JOptionPane.showMessageDialog(null, "Your balance is not sufficient",
										"Insufficient balance", JOptionPane.WARNING_MESSAGE);
							}
						}
					}
				}
			});
		}
	}

	Customer destination_customer;

	private void displayTransfer(Customer customer) {
		JLabel acNumlbl = new JLabel();
		acNumlbl.setText("Destination Account");
		acNumlbl.setBounds(30, 165, 180, 30);
		acNumlbl.setFont(new Font("Arial", 20, 20));
		panel.add(acNumlbl);
		JTextField acNumText = new JTextField();
		acNumText.setBounds(230, 165, 200, 35);
		acNumText.setFont(new Font("Arial", 20, 20));
		panel.add(acNumText);
		JButton searchbtn = new JButton("Search");
		searchbtn.setBounds(450, 165, 90, 30);
		searchbtn.setBackground(new Color(80, 80, 130));
		searchbtn.setForeground(Color.WHITE);
		panel.add(searchbtn);

		JLabel namelbl = new JLabel();
		namelbl.setText("Destination Name");
		namelbl.setBounds(30, 210, 180, 30);
		namelbl.setFont(new Font("Arial", 20, 20));
		panel.add(namelbl);
		JLabel name = new JLabel();
		name.setBounds(230, 210, 200, 35);
		name.setFont(new Font("Arial", 20, 20));
		panel.add(name);
		CustomerDAO customerHandler = new CustomerDAO();

		searchbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				destination_customer = customerHandler.getCustomer(Integer.parseInt(acNumText.getText()));
				name.setText(destination_customer.getFirstName() + " " + destination_customer.getLastName());
			}
		});
		JLabel amountlbl = new JLabel();
		amountlbl.setText("Transfer Amount");
		amountlbl.setBounds(30, 255, 180, 30);
		amountlbl.setFont(new Font("Arial", 20, 20));
		panel.add(amountlbl);
		JTextField amountText = new JTextField();
		amountText.setBounds(230, 255, 200, 35);
		amountText.setFont(new Font("Arial", 20, 20));
		panel.add(amountText);

		JButton transferbtn = new JButton("Transfer");
		transferbtn.setBounds(300, 320, 130, 60);
		transferbtn.setFont(new Font("Arial", 20, 18));
		transferbtn.setBackground(new Color(80, 80, 130));
		transferbtn.setForeground(Color.WHITE);
		panel.add(transferbtn);

		transferbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BigDecimal customer_balance = customer.getBalance();

				AdminGUI adminHandler = new AdminGUI();
				adminHandler.frame.setVisible(false);

				if (acNumText.getText() == "" || !(adminHandler.isInteger(acNumText.getText()))
						|| customerHandler.checkCustomerAcNumber(Integer.parseInt(acNumText.getText()))) {
					JOptionPane.showMessageDialog(null,
							"The account number does not exist, please enter a valid account number",
							"Wrong account number", JOptionPane.WARNING_MESSAGE);
				} else {
					destination_customer = customerHandler.getCustomer(Integer.parseInt(acNumText.getText()));
					BigDecimal destination_balance = destination_customer.getBalance();
					if (destination_balance == null)
						destination_balance = BigDecimal.ZERO;
					if (adminHandler.isInteger(amountText.getText())
							&& !(destination_balance.equals(BigDecimal.ZERO))) {
						BigDecimal amount = new BigDecimal(amountText.getText());

						int difference = customer_balance.compareTo(amount);
						if (difference == 1 || difference == 0) {
							customer.setBalance(customer_balance.subtract(amount));
							destination_customer.setBalance(destination_balance.add(amount));
							customerHandler.updateCustomer(customer);
							customerHandler.updateCustomer(destination_customer);
							Transactions tran = new Transactions();
							tran.setCustomer(customer);
							tran.setType("Send");
							tran.setAmount(amount);
							tran.setDate(new Date());
							tran.setCurrent_balance(customer.getBalance());
							Transactions tran2 = new Transactions();
							tran2.setCustomer(destination_customer);
							tran2.setType("Receive");
							tran2.setAmount(amount);
							tran2.setDate(new Date());
							tran2.setCurrent_balance(destination_customer.getBalance());
							TransactionDAO transactionHandler = new TransactionDAO();
							transactionHandler.addTransaction(tran);
							transactionHandler.addTransaction(tran2);
							JOptionPane.showMessageDialog(null,
									amount + "$ has been transfered to " + destination_customer.getAccountNumber() + " "
											+ destination_customer.getFirstName() + " "
											+ destination_customer.getLastName());
						} else {
							JOptionPane.showMessageDialog(null, "Your balance is not sufficient",
									"Insufficient balance", JOptionPane.WARNING_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Please enter amount of money that you want to transfer");
					}

				}
			}
		});
	}

	public void displayTransaction(Object[][] rows, Object[] cols) {
		panel.removeAll();
		panel.revalidate();
		panel.repaint();

		DefaultTableModel model = new DefaultTableModel(rows, cols);
		JTable table = new JTable(model);

		table.setBackground(Color.WHITE);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(5, 40, 620, 300);
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Arial", Font.BOLD, 13));
		header.setBackground(Color.white);
		table.setFont(new Font("Arial", Font.PLAIN, 13));
		table.setRowHeight(25);

		panel.add(scroll);
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

}
