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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import java.awt.Font;

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
	private int uid = -1;
	private JTable RecentVideos;
	private JTextField newEmail;
	private JTable UserVideo;
	private JTextField deletedID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					DatabaseConnection con = DatabaseConnection.getInstance();
					boolean r = con.connect(USERNAME, PASSWORD);
					window.frame.setVisible(true);
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
		setUp();
		us = UserService.getInstance();
		initialize();
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

	private void loadLoginPanel() {

		JPanel LoginPanel = new JPanel();
		LoginPanel.setBounds(0, 0, 642, 437);
		frame.getContentPane().add(LoginPanel);
		LoginPanel.setLayout(null);

		LoginPanel.setVisible(true);
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

		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				loadRegisterPanel();
				frame.repaint();
			}
		});

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (us.login(LUserName.getText(), new String(LPassword.getPassword()))) {
					username = LUserName.getText();
					frame.getContentPane().removeAll();
					loadMainPanel();
					frame.repaint();
				}
			}
		});
	}

	private void loadRegisterPanel() {

		JPanel RegisterPanel = new JPanel();
		RegisterPanel.setVisible(true);

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
					frame.getContentPane().removeAll();
					loadLoginPanel();
					frame.repaint();
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
				frame.getContentPane().removeAll();
				loadLoginPanel();
				frame.repaint();
			}
		});
		btnCancel.setBounds(420, 191, 95, 25);
		RegisterPanel.add(btnCancel);

	}

	private void loadMainPanel() {

		JPanel MainPanel = new JPanel();
		MainPanel.setBounds(0, 0, 642, 437);
		frame.getContentPane().add(MainPanel);
		MainPanel.setLayout(null);

		MainPanel.setVisible(true);

		JLabel lblRecentVideos = new JLabel("Recent Videos");
		lblRecentVideos.setBounds(56, 47, 116, 30);
		MainPanel.add(lblRecentVideos);

		JLabel lblHello = new JLabel();
		lblHello.setBounds(335, 24, 168, 26);
		MainPanel.add(lblHello);
		lblHello.setText("Hello, " + username);

		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.getContentPane().removeAll();
				loadUserInfoPanel();
				frame.repaint();
			}
		});
		btnEdit.setBounds(515, 25, 75, 25);
		MainPanel.add(btnEdit);

		RecentVideos = new JTable();
		RecentVideos.setBounds(79, 90, 483, 111);
		MainPanel.add(RecentVideos);

		UserVideo = new JTable();
		UserVideo.setBounds(79, 260, 480, 128);
		MainPanel.add(UserVideo);

		JLabel lblPersonalVideos = new JLabel("Personal Videos");
		lblPersonalVideos.setBounds(56, 214, 128, 30);
		MainPanel.add(lblPersonalVideos);

		ResultSet rs = null;
		Connection conn = DatabaseConnection.getConnection();
		CallableStatement stmt = null;
		try {
			Statement st = conn.createStatement();
			String query = "SELECT * From LastUploadedVideosView";
			rs = st.executeQuery(query);
			RecentVideos.setModel(DbUtils.resultSetToTableModel(rs));
			String query3 = "SELECT dbo.getIdByUName('" + username + "')";
			Statement st3 = conn.createStatement();
			ResultSet rs3 = null;
			rs3 = st3.executeQuery(query3);
			rs3.next();
			uid = rs3.getInt(1);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			Statement st2 = conn.createStatement();
			String query2 = "SELECT * From getVideoByUser('" + username + "')";
			ResultSet rs2 = null;
			rs2 = st2.executeQuery(query2);
			UserVideo.setModel(DbUtils.resultSetToTableModel(rs2));
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

	private void loadUserInfoPanel() {

		JPanel UserInfoPanel = new JPanel();
		UserInfoPanel.setBounds(0, 0, 642, 437);
		frame.getContentPane().add(UserInfoPanel);
		UserInfoPanel.setLayout(null);

		JLabel lblHello = new JLabel();
		lblHello.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblHello.setBounds(69, 44, 200, 50);
		UserInfoPanel.add(lblHello);
		lblHello.setText("Hello, " + username);

		String email = "";

		try {
			Connection con = DatabaseConnection.getConnection();
			String emailquery = "Select Email FROM [User] where Username = ?";
			PreparedStatement emailstmt = con.prepareStatement(emailquery);
			emailstmt.setString(1, username);
			ResultSet emailre = emailstmt.executeQuery();
			emailre.next();
			email = emailre.getString(1);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		JLabel lblEmail_1 = new JLabel("Email: " + email);
		lblEmail_1.setBounds(69, 321, 300, 35);
		UserInfoPanel.add(lblEmail_1);

		String dob = "";

		try {
			Connection con = DatabaseConnection.getConnection();
			String dobquery = "Select DOB FROM [User] where Username = ?";
			PreparedStatement dobstmt = con.prepareStatement(dobquery);
			dobstmt.setString(1, username);
			ResultSet dobre = dobstmt.executeQuery();
			dobre.next();
			dob = dobre.getString(1);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		JLabel lblBirthday = new JLabel("Birthday: " + dob);
		lblBirthday.setBounds(69, 273, 300, 35);
		UserInfoPanel.add(lblBirthday);

		JButton btnChangePassword = new JButton("Change Password");
		btnChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String newpass = JOptionPane.showInputDialog(frame, "Enter new password: ");
					byte[] salt = us.getNewSalt();
					String hash = us.hashPassword(salt, newpass);
					Connection con = DatabaseConnection.getConnection();
					CallableStatement change = con.prepareCall("{? = call changePass(?,?,?)}");
					change.setString(2, username);
					change.setBytes(3, salt);
					change.setString(4, hash);
					change.registerOutParameter(1, Types.INTEGER);
					change.execute();
					int returnValue = change.getInt(1);
					if (returnValue != 0)
						JOptionPane.showMessageDialog(null, "Can not change password");
					else {
						JOptionPane.showMessageDialog(null, "Password Changed");
						username = null;
						frame.getContentPane().removeAll();
						loadLoginPanel();
						frame.repaint();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnChangePassword.setBounds(411, 184, 150, 35);
		UserInfoPanel.add(btnChangePassword);

		JButton btnChangeBirthday = new JButton("Change Birthday");
		btnChangeBirthday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String newdob = JOptionPane.showInputDialog(frame, "Enter new birthday: ");
					Connection con = DatabaseConnection.getConnection();
					CallableStatement change = con.prepareCall("{? = call changeDOB(?,?)}");
					change.setString(2, username);
					change.setString(3, newdob);
					change.registerOutParameter(1, Types.INTEGER);
					change.execute();
					int returnValue = change.getInt(1);
					if (returnValue != 0)
						JOptionPane.showMessageDialog(null, "Can not change birthday");
					else
						JOptionPane.showMessageDialog(null, "Birthday Changed");
					frame.getContentPane().removeAll();
					loadUserInfoPanel();
					frame.repaint();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnChangeBirthday.setBounds(411, 273, 150, 35);
		UserInfoPanel.add(btnChangeBirthday);

		JButton btnChangeEmail = new JButton("Change Email");
		btnChangeEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String newemail = JOptionPane.showInputDialog(frame, "Enter new email: ");
					Connection con = DatabaseConnection.getConnection();
					CallableStatement change = con.prepareCall("{? = call changeEmail(?,?)}");
					change.setString(2, username);
					change.setString(3, newemail);
					change.registerOutParameter(1, Types.INTEGER);
					change.execute();
					int returnValue = change.getInt(1);
					if (returnValue != 0)
						JOptionPane.showMessageDialog(null, "Can not change email");
					else
						JOptionPane.showMessageDialog(null, "Email Changed");
					frame.getContentPane().removeAll();
					loadUserInfoPanel();
					frame.repaint();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnChangeEmail.setBounds(411, 321, 150, 35);
		UserInfoPanel.add(btnChangeEmail);

		JButton btnMyVideos = new JButton("My Videos");
		btnMyVideos.setBounds(69, 126, 150, 35);
		UserInfoPanel.add(btnMyVideos);

		JButton btnMyContents = new JButton("My Contents");
		btnMyContents.setBounds(69, 184, 150, 35);
		UserInfoPanel.add(btnMyContents);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				loadMainPanel();
				frame.repaint();
			}
		});
		btnBack.setBounds(411, 126, 150, 35);
		UserInfoPanel.add(btnBack);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 660, 484);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

//		loadLoginPanel();
//		loadRegisterPanel();
//		loadMainPanel();
//		loadUserInfoPanel();
	}
}
