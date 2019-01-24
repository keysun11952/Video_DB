import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Panel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sun.org.apache.regexp.internal.RE;

import sun.security.util.Password;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
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
	private static String userName;
	private static String password;
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
					boolean r = con.connect(userName, password);
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
		setUp();
	}
	
	private void setUp(){
		File file = new File("src\\setting");
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.userName=scanner.nextLine();
		this.password=scanner.next();
		scanner.close();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 660, 484);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel RegisterPanel = new JPanel();
		RegisterPanel.setVisible(false);
		RegisterPanel.setLayout(null);
		RegisterPanel.setBounds(0, 0, 642, 437);
		frame.getContentPane().add(RegisterPanel);
		
		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setBounds(111, 133, 63, 16);
		RegisterPanel.add(lblUsername);
		
		JLabel lblPassword_1 = new JLabel("Password");
		lblPassword_1.setBounds(111, 218, 55, 16);
		RegisterPanel.add(lblPassword_1);
		
		UserName_1 = new JTextField();
		UserName_1.setColumns(10);
		UserName_1.setBounds(209, 130, 116, 22);
		RegisterPanel.add(UserName_1);
		
		Password_1 = new JTextField();
		Password_1.setColumns(10);
		Password_1.setBounds(209, 215, 116, 22);
		RegisterPanel.add(Password_1);
		
		JButton button = new JButton("Register");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, us.register(UserName_1.getText(), Password_1.getText(), DOB.getText(), Email.getText()));
			}
		});
		button.setBounds(420, 129, 95, 25);
		RegisterPanel.add(button);
		
		JLabel lblDob = new JLabel("DOB");
		lblDob.setBounds(111, 279, 63, 16);
		RegisterPanel.add(lblDob);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(111, 364, 55, 16);
		RegisterPanel.add(lblEmail);
		
		
		DOB = new JTextField();
		DOB.setColumns(10);
		DOB.setBounds(209, 276, 116, 22);
		RegisterPanel.add(DOB);
		
		Email = new JTextField();
		Email.setColumns(10);
		Email.setBounds(209, 361, 116, 22);
		RegisterPanel.add(Email);

		JPanel LoginPanel = new JPanel();
		LoginPanel.setBounds(0, 0, 642, 437);
		frame.getContentPane().add(LoginPanel);
		LoginPanel.setLayout(null);

		LUserName = new JTextField();
		LUserName.setBounds(209, 130, 116, 22);
		LoginPanel.add(LUserName);
		LUserName.setColumns(10);

		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setBounds(111, 133, 63, 16);
		LoginPanel.add(lblUserName);
		
				LPassword = new JTextField();
				LPassword.setBounds(209, 215, 116, 22);
				LoginPanel.add(LPassword);
				LPassword.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(111, 218, 55, 16);
		LoginPanel.add(lblPassword);

		JButton btnRegister = new JButton("Register");
		btnRegister.setBounds(420, 129, 79, 25);
		LoginPanel.add(btnRegister);

		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(420, 214, 79, 25);
		LoginPanel.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(us.login(LUserName.getText(), LPassword.getText()));
			}
		});
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterPanel.setVisible(true);
				LoginPanel.setVisible(false);

			}
		});
	}
}