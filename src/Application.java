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
	private JTextField txtUser;
	private JTextField txtVideo;
	private JTextField txtContent;
	private JButton btnSearchUser;
	private JButton btnSearchVideo;
	private JButton btnSearchContent;
	private JButton btnBack;
	private JTable table;

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
		RecentVideos.setBounds(76, 76, 483, 111);
		MainPanel.add(RecentVideos);

		UserVideo = new JTable();
		UserVideo.setBounds(76, 228, 480, 128);
		MainPanel.add(UserVideo);

		JLabel lblPersonalVideos = new JLabel("Personal Videos");
		lblPersonalVideos.setBounds(56, 200, 128, 30);
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

			JButton btnSearch = new JButton("Search");
			btnSearch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					frame.getContentPane().removeAll();
					loadSearchPanel();
					frame.repaint();
				}
			});
			btnSearch.setBounds(493, 382, 97, 25);
			MainPanel.add(btnSearch);
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

	private void loadSearchPanel() {

		JPanel SearchPanel = new JPanel();
		SearchPanel.setBounds(0, 0, 642, 437);
		frame.getContentPane().add(SearchPanel);
		SearchPanel.setLayout(null);

		JLabel lblSearch = new JLabel("Search");
		lblSearch.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSearch.setBounds(68, 52, 88, 41);
		SearchPanel.add(lblSearch);

		txtUser = new JTextField();
		txtUser.setBounds(68, 318, 342, 22);
		SearchPanel.add(txtUser);
		txtUser.setColumns(10);

		txtVideo = new JTextField();
		txtVideo.setBounds(68, 146, 342, 22);
		SearchPanel.add(txtVideo);
		txtVideo.setColumns(10);

		txtContent = new JTextField();
		txtContent.setBounds(68, 231, 342, 22);
		SearchPanel.add(txtContent);
		txtContent.setColumns(10);

		btnSearchUser = new JButton("Search User");
		btnSearchUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String user = txtUser.getText();
					Connection con = DatabaseConnection.getConnection();
					String query = "Select * From searchUser (?)";
					PreparedStatement search = con.prepareStatement(query);
					search.setString(1, user);
					ResultSet rs = search.executeQuery();
					frame.getContentPane().removeAll();
					loadSearchUserPanel(rs);
					frame.repaint();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSearchUser.setBounds(422, 317, 135, 25);
		SearchPanel.add(btnSearchUser);

		btnSearchVideo = new JButton("Search Video");
		btnSearchVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String video = txtVideo.getText();
					Connection con = DatabaseConnection.getConnection();
					String query = "Select * From searchVideoByName (?)";
					PreparedStatement search = con.prepareStatement(query);
					search.setString(1, video);
					ResultSet rs = search.executeQuery();
					frame.getContentPane().removeAll();
					loadSearchVideoPanel(rs);
					frame.repaint();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSearchVideo.setBounds(422, 145, 135, 25);
		SearchPanel.add(btnSearchVideo);

		btnSearchContent = new JButton("Search Content");
		btnSearchContent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String content = txtContent.getText();
					Connection con = DatabaseConnection.getConnection();
					String query = "Select * From searchContentByName (?)";
					PreparedStatement search = con.prepareStatement(query);
					search.setString(1, content);
					ResultSet rs = search.executeQuery();
					frame.getContentPane().removeAll();
					loadSearchContentPanel(rs);
					frame.repaint();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSearchContent.setBounds(422, 230, 135, 25);
		SearchPanel.add(btnSearchContent);

		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				loadMainPanel();
				frame.repaint();
			}
		});
		btnBack.setBounds(422, 63, 135, 25);
		SearchPanel.add(btnBack);

	}

	private void loadSearchVideoPanel(ResultSet rs) {

		JPanel loadSearchVideoPanel = new JPanel();
		loadSearchVideoPanel.setBounds(0, 0, 642, 437);
		frame.getContentPane().add(loadSearchVideoPanel);
		loadSearchVideoPanel.setLayout(null);

		JLabel lblVideo = new JLabel("Video");
		lblVideo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblVideo.setBounds(62, 35, 105, 25);
		loadSearchVideoPanel.add(lblVideo);

		table = new JTable();
		table.setBounds(62, 86, 512, 271);
		loadSearchVideoPanel.add(table);
		table.setModel(DbUtils.resultSetToTableModel(rs));

		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				loadSearchPanel();
				frame.repaint();
			}
		});
		btnBack.setBounds(439, 38, 135, 25);
		loadSearchVideoPanel.add(btnBack);
		
		txtVideo = new JTextField();
		txtVideo.setBounds(62, 382, 365, 22);
		loadSearchVideoPanel.add(txtVideo);
		txtVideo.setColumns(10);
		
		btnSearchVideo = new JButton("Search Video");
		btnSearchVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String video = txtVideo.getText();
					Connection con = DatabaseConnection.getConnection();
					String query = "Select * From searchVideoByName (?)";
					PreparedStatement search = con.prepareStatement(query);
					search.setString(1, video);
					ResultSet rs = search.executeQuery();
					frame.getContentPane().removeAll();
					loadSearchVideoPanel(rs);
					frame.repaint();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSearchVideo.setBounds(439, 381, 135, 25);
		loadSearchVideoPanel.add(btnSearchVideo);
		
	}

	private void loadSearchContentPanel(ResultSet rs) {

		JPanel loadSearchContentPanel = new JPanel();
		loadSearchContentPanel.setBounds(0, 0, 642, 437);
		frame.getContentPane().add(loadSearchContentPanel);
		loadSearchContentPanel.setLayout(null);

		JLabel lblContent = new JLabel("Content");
		lblContent.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblContent.setBounds(62, 35, 105, 25);
		loadSearchContentPanel.add(lblContent);

		table = new JTable();
		table.setBounds(62, 86, 512, 271);
		loadSearchContentPanel.add(table);
		table.setModel(DbUtils.resultSetToTableModel(rs));

		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				loadSearchPanel();
				frame.repaint();
			}
		});
		btnBack.setBounds(439, 38, 135, 25);
		loadSearchContentPanel.add(btnBack);
		
		txtContent = new JTextField();
		txtContent.setBounds(62, 382, 365, 22);
		loadSearchContentPanel.add(txtContent);
		txtContent.setColumns(10);

		btnSearchContent = new JButton("Search Content");
		btnSearchContent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String content = txtContent.getText();
					Connection con = DatabaseConnection.getConnection();
					String query = "Select * From searchContentByName (?)";
					PreparedStatement search = con.prepareStatement(query);
					search.setString(1, content);
					ResultSet rs = search.executeQuery();
					frame.getContentPane().removeAll();
					loadSearchContentPanel(rs);
					frame.repaint();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSearchContent.setBounds(439, 381, 135, 25);
		loadSearchContentPanel.add(btnSearchContent);
		
	}
	
	private void loadSearchUserPanel(ResultSet rs) {

		JPanel loadSearchUserPanel = new JPanel();
		loadSearchUserPanel.setBounds(0, 0, 642, 437);
		frame.getContentPane().add(loadSearchUserPanel);
		loadSearchUserPanel.setLayout(null);

		JLabel lblUser = new JLabel("User");
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUser.setBounds(62, 35, 105, 25);
		loadSearchUserPanel.add(lblUser);

		table = new JTable();
		table.setBounds(62, 86, 512, 271);
		loadSearchUserPanel.add(table);
		table.setModel(DbUtils.resultSetToTableModel(rs));

		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				loadSearchPanel();
				frame.repaint();
			}
		});
		btnBack.setBounds(439, 38, 135, 25);
		loadSearchUserPanel.add(btnBack);
		
		txtUser = new JTextField();
		txtUser.setBounds(62, 382, 365, 22);
		loadSearchUserPanel.add(txtUser);
		txtUser.setColumns(10);
		
		btnSearchUser = new JButton("Search User");
		btnSearchUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String user = txtUser.getText();
					Connection con = DatabaseConnection.getConnection();
					String query = "Select * From searchUser (?)";
					PreparedStatement search = con.prepareStatement(query);
					search.setString(1, user);
					ResultSet rs = search.executeQuery();
					frame.getContentPane().removeAll();
					loadSearchUserPanel(rs);
					frame.repaint();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSearchUser.setBounds(439, 381, 135, 25);
		loadSearchUserPanel.add(btnSearchUser);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 660, 484);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		loadLoginPanel();
//		loadRegisterPanel();
//		loadMainPanel();
//		loadUserInfoPanel();
//		loadSearchPanel();
		
//		ResultSet rs = null;
//		loadSearchVideoPanel(rs);
//		loadSearchContentPanel(rs);
//		loadSearchUserPanel(rs);
	}
}
