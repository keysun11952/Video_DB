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
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JTable;

public class Application {

	private JFrame frame;
	private JTextField LUserName;
	private UserService us;
	private JTextField UserName_1;
	private JTextField DOB;
	private JTextField Email;
	private static String USERNAME;
	private static String PASSWORD;
	private JPasswordField passwordField;
	private JPasswordField LPassword;
	private String username = null;
	private JTable RecentVideos;
	private JTextField newEmail;

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
					boolean r = con.connect(USERNAME, PASSWORD);
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

	private void setUp() {
		File file = new File("src\\setting");
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.USERNAME = scanner.nextLine();
		this.PASSWORD = scanner.next();
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

		JPanel MainPanel = new JPanel();
		MainPanel.setBounds(0, 0, 642, 437);
		frame.getContentPane().add(MainPanel);
		MainPanel.setLayout(null);

		JLabel lblRecentVideos = new JLabel("Recent Videos");
		lblRecentVideos.setBounds(56, 51, 116, 30);
		MainPanel.add(lblRecentVideos);

		JLabel lblHello = new JLabel();
		lblHello.setBounds(515, 13, 115, 26);
		MainPanel.add(lblHello);
		
		RecentVideos = new JTable();
		RecentVideos.setBounds(79, 98, 483, 96);
		MainPanel.add(RecentVideos);
		
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

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(111, 218, 63, 16);
		LoginPanel.add(lblPassword);

		JButton btnRegister = new JButton("Register");
		btnRegister.setBounds(420, 129, 94, 25);
		LoginPanel.add(btnRegister);

		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(420, 214, 79, 25);
		LoginPanel.add(btnLogin);

		LPassword = new JPasswordField();
		LPassword.setEchoChar('*');
		LPassword.setBounds(209, 215, 116, 22);
		LoginPanel.add(LPassword);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (us.login(LUserName.getText(), new String(LPassword.getPassword()))) {
					username = LUserName.getText();
					MainPanel.setVisible(true);
					LoginPanel.setVisible(false);
					lblHello.setText("Hello, " + username);
					ResultSet rs = null;
					Connection conn = DatabaseConnection.getConnection();
					try {
						Statement st = conn.createStatement();
						String query = "SELECT * From LastUploadedVideosView";
					    rs = st.executeQuery(query);
					    RecentVideos.setModel(DbUtils.resultSetToTableModel(rs)); 
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterPanel.setVisible(true);
				LoginPanel.setVisible(false);

			}
		});
		RegisterPanel.setLayout(null);
		RegisterPanel.setBounds(0, 0, 642, 437);
		frame.getContentPane().add(RegisterPanel);

		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setBounds(111, 113, 63, 16);
		RegisterPanel.add(lblUsername);

		JLabel lblPassword_1 = new JLabel("Password");
		lblPassword_1.setBounds(111, 195, 63, 16);
		RegisterPanel.add(lblPassword_1);

		UserName_1 = new JTextField();
		UserName_1.setColumns(10);
		UserName_1.setBounds(209, 110, 116, 22);
		RegisterPanel.add(UserName_1);

		JButton btnRegister_1 = new JButton("Register");
		btnRegister_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (us.register(UserName_1.getText(), new String(passwordField.getPassword()), DOB.getText(),
						Email.getText())) {
					JOptionPane.showMessageDialog(frame, "Registration succeed");
					RegisterPanel.setVisible(false);
					LoginPanel.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(frame, "Registration failed");
				}
			}
		});
		btnRegister_1.setBounds(420, 109, 95, 25);
		RegisterPanel.add(btnRegister_1);

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

		passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		passwordField.setBounds(209, 192, 116, 22);
		RegisterPanel.add(passwordField);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RegisterPanel.setVisible(false);
				LoginPanel.setVisible(true);
			}
		});
		btnCancel.setBounds(420, 191, 95, 25);
		RegisterPanel.add(btnCancel);

		newEmail = new JTextField();
		newEmail.setBounds(335, 400, 168, 24);
		MainPanel.add(newEmail);
		newEmail.setColumns(10);
		MainPanel.setVisible(false);
		
		JButton btnChangeEmail = new JButton("Change Email");
		btnChangeEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connection conn = DatabaseConnection.getConnection();
				try {
					CallableStatement stmt = conn.prepareCall("{? = call changeEmail(?,?)}");
					stmt.setString(2, username);
					stmt.setString(3, newEmail.getText());
					stmt.registerOutParameter(1, Types.INTEGER);
					stmt.execute();
				    int re = stmt.getInt(1);
				    if (re == 0)
				    	JOptionPane.showMessageDialog(frame, "Email changed");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnChangeEmail.setBounds(515, 399, 115, 25);
		MainPanel.add(btnChangeEmail);
		
		// initialize panel
		LoginPanel.setVisible(true);
		RegisterPanel.setVisible(false);
		MainPanel.setVisible(false);
	}
}
