import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Panel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sun.security.util.Password;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Application {

	private JFrame frame;
	private JTextField LUserName;
	private JTextField LPassword;
	private UserService us;
	private JTextField UserName_1;
	private JTextField Password_1;
	private JTextField DOB;
	private JTextField Email;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frame.setVisible(true);
					DatabaseConnection con = DatabaseConnection.getInstance();
					boolean r = con.connect("SodaBaseUserwangj1429", "Password123");
					System.out.println(r);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Application() {
		initialize();
		us = UserService.getInstance();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 660, 484);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(0, 0, 642, 437);
		frame.getContentPane().add(panel_1);
		
		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setBounds(111, 133, 63, 16);
		panel_1.add(lblUsername);
		
		JLabel lblPassword_1 = new JLabel("Password");
		lblPassword_1.setBounds(111, 218, 55, 16);
		panel_1.add(lblPassword_1);
		
		UserName_1 = new JTextField();
		UserName_1.setColumns(10);
		UserName_1.setBounds(209, 130, 116, 22);
		panel_1.add(UserName_1);
		
		Password_1 = new JTextField();
		Password_1.setColumns(10);
		Password_1.setBounds(209, 215, 116, 22);
		panel_1.add(Password_1);
		
		JButton button = new JButton("Register");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, us.register(UserName_1.getText(), Password_1.getText(), DOB.getText(), Email.getText()));
			}
		});
		button.setBounds(420, 129, 79, 25);
		panel_1.add(button);
		
		JLabel lblDob = new JLabel("DOB");
		lblDob.setBounds(111, 279, 63, 16);
		panel_1.add(lblDob);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(111, 364, 55, 16);
		panel_1.add(lblEmail);
		
		DOB = new JTextField();
		DOB.setColumns(10);
		DOB.setBounds(209, 276, 116, 22);
		panel_1.add(DOB);
		
		Email = new JTextField();
		Email.setColumns(10);
		Email.setBounds(209, 361, 116, 22);
		panel_1.add(Email);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 642, 437);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		LUserName = new JTextField();
		LUserName.setBounds(209, 130, 116, 22);
		panel.add(LUserName);
		LUserName.setColumns(10);

		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setBounds(111, 133, 63, 16);
		panel.add(lblUserName);
		
				LPassword = new JTextField();
				LPassword.setBounds(209, 215, 116, 22);
				panel.add(LPassword);
				LPassword.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(111, 218, 55, 16);
		panel.add(lblPassword);

		JButton btnRegister = new JButton("Register");
		btnRegister.setBounds(420, 129, 79, 25);
		panel.add(btnRegister);

		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(420, 214, 79, 25);
		panel.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(us.login(LUserName.getText(), LPassword.getText()));
			}
		});
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
	}
}
