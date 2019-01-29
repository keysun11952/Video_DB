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
	private JTable UserVideo;
	private JTextField txtUser;
	private JTextField txtVideo;
	private JTextField txtContent;
	private JButton btnSearchUser;
	private JButton btnSearchVideo;
	private JButton btnSearchContent;
	private JButton btnBack;
	private JTable table;
	private JTextField VIDInput;
	private JButton btnNewButton_1;
	private JTable ContentInfoTable;
	private JTable CommentTable;
	private JTable TagTable;
	private JTextField CommentInput;
	private JButton btnPostComment;
	private JButton btnNewButton_2;
	private JButton btnDislike;
	private JTextField DonateInput;
	private JButton btnNewButton_3;
	private JTextField ContentInput;
	private JButton btnSubscribe;
	private JLabel lblNewLabel_1;
	private JLabel SubscribeNumber;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					DatabaseConnection con = DatabaseConnection.getInstance();
					con.connect(USERNAME, PASSWORD);
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

			VIDInput = new JTextField();
			VIDInput.setBounds(76, 383, 81, 22);
			MainPanel.add(VIDInput);
			VIDInput.setColumns(10);

			JLabel lblNewLabel = new JLabel("Enter Video ID");
			lblNewLabel.setBounds(76, 369, 116, 16);
			MainPanel.add(lblNewLabel);

			JButton btnNewButton = new JButton("Go");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						String q = "{? = call dbo.checkVideo(?)}";
						CallableStatement ss = conn.prepareCall(q);
						ss.setInt(2, new Integer(VIDInput.getText()));
						ss.registerOutParameter(1, java.sql.Types.INTEGER);
						ss.execute();
						boolean exists = ss.getInt(1) == 0;
						if (!exists) {
							JOptionPane.showMessageDialog(frame, "Video doesn't exist");
							return;
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					frame.getContentPane().removeAll();
					loadVideoPanel(new Integer(VIDInput.getText()));
					frame.repaint();
				}
			});
			btnNewButton.setBounds(158, 382, 56, 25);
			MainPanel.add(btnNewButton);

			ContentInput = new JTextField();
			ContentInput.setColumns(10);
			ContentInput.setBounds(244, 383, 81, 22);
			MainPanel.add(ContentInput);

			JLabel lblEnterContentId = new JLabel("Enter Content ID");
			lblEnterContentId.setBounds(244, 369, 116, 16);
			MainPanel.add(lblEnterContentId);

			JButton button = new JButton("Go");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						String q = "{? = call dbo.checkContent(?) }";
						CallableStatement ss = conn.prepareCall(q);
						ss.setInt(2, new Integer(ContentInput.getText()));
						ss.registerOutParameter(1, java.sql.Types.INTEGER);
						ss.execute();
						boolean exists = ss.getInt(1) == 0;
						if (!exists) {
							JOptionPane.showMessageDialog(frame, "Content doesn't exist");
							return;
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					frame.getContentPane().removeAll();
					loadContentPanel(new Integer(ContentInput.getText()));
					frame.repaint();
				}
			});
			button.setBounds(330, 382, 56, 25);
			MainPanel.add(button);

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
		txtVideo.setBounds(162, 382, 365, 22);
		loadSearchVideoPanel.add(txtVideo);
		txtVideo.setColumns(10);

		Connection con = DatabaseConnection.getConnection();
		btnSearchVideo = new JButton("Search Video");
		btnSearchVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String video = txtVideo.getText();
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
		
		VIDInput = new JTextField();
		VIDInput.setBounds(210, 53, 81, 22);
		loadSearchVideoPanel.add(VIDInput);
		VIDInput.setColumns(10);

		JLabel lblNewLabel = new JLabel("Enter Video ID");
		lblNewLabel.setBounds(226, 29, 116, 16);
		loadSearchVideoPanel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Go");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String q = "{? = call dbo.checkVideo(?)}";
					CallableStatement ss = con.prepareCall(q);
					ss.setInt(2, new Integer(VIDInput.getText()));
					ss.registerOutParameter(1, java.sql.Types.INTEGER);
					ss.execute();
					boolean exists = ss.getInt(1) == 0;
					if (!exists) {
						JOptionPane.showMessageDialog(frame, "Video doesn't exist");
						return;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.getContentPane().removeAll();
				loadVideoPanel(new Integer(VIDInput.getText()));
				frame.repaint();
			}
		});
		btnNewButton.setBounds(300, 52, 56, 25);
		loadSearchVideoPanel.add(btnNewButton);
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
		Connection con = DatabaseConnection.getConnection();

		btnSearchContent = new JButton("Search Content");
		btnSearchContent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String content = txtContent.getText();
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

		ContentInput = new JTextField();
		ContentInput.setColumns(10);
		ContentInput.setBounds(200, 52, 81, 22);
		loadSearchContentPanel.add(ContentInput);

		JLabel lblEnterContentId = new JLabel("Enter Content ID");
		lblEnterContentId.setBounds(234, 20, 116, 16);
		loadSearchContentPanel.add(lblEnterContentId);

		JButton button = new JButton("Go");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String q = "{? = call dbo.checkContent(?) }";
					CallableStatement ss = con.prepareCall(q);
					ss.setInt(2, new Integer(ContentInput.getText()));
					ss.registerOutParameter(1, java.sql.Types.INTEGER);
					ss.execute();
					boolean exists = ss.getInt(1) == 0;
					if (!exists) {
						JOptionPane.showMessageDialog(frame, "Content doesn't exist");
						return;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.getContentPane().removeAll();
				loadContentPanel(new Integer(ContentInput.getText()));
				frame.repaint();
			}
		});
		button.setBounds(280, 52, 56, 25);
		loadSearchContentPanel.add(button);
	}

	private void loadVideoPanel(int vid) {
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 642, 437);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		btnNewButton_1 = new JButton("Back To Main Page");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				loadMainPanel();
				frame.repaint();
			}
		});
		btnNewButton_1.setBounds(12, 13, 187, 30);
		panel.add(btnNewButton_1);
		ResultSet rsomment = null;
		Connection conn = DatabaseConnection.getConnection();
		// show video info
		ResultSet rs = null;
		ContentInfoTable = new JTable();
		ContentInfoTable.setBounds(12, 56, 618, 30);
		panel.add(ContentInfoTable);
		try {
			String q = "Select * from dbo.getVideoInfo(?)";
			PreparedStatement ps = conn.prepareStatement(q);
			ps.setInt(1, vid);
			rs = ps.executeQuery();
			ContentInfoTable.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// show comments
		ResultSet rsc = null;
		CommentTable = new JTable();
		CommentTable.setBounds(51, 120, 502, 90);
		panel.add(CommentTable);
		try {
			String qc = "Select * from dbo.getComment(?)";
			PreparedStatement psc = conn.prepareStatement(qc);
			psc.setInt(1, vid);
			rsc = psc.executeQuery();
			CommentTable.setModel(DbUtils.resultSetToTableModel(rsc));

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// show tags
		TagTable = new JTable();
		TagTable.setBounds(51, 256, 121, 146);
		panel.add(TagTable);
		try {
			String qc = "Select Tag from dbo.VideoTag Where VID = ?";
			PreparedStatement psc = conn.prepareStatement(qc);
			psc.setInt(1, vid);
			rsc = psc.executeQuery();
			TagTable.setModel(DbUtils.resultSetToTableModel(rsc));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// input comment
		CommentInput = new JTextField();
		CommentInput.setBounds(51, 221, 413, 22);
		panel.add(CommentInput);
		CommentInput.setColumns(10);

		btnPostComment = new JButton("Post Comment");
		btnPostComment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String q = "{Call dbo.commentVideo(?,?,?)}";
				CallableStatement cs;
				try {
					cs = conn.prepareCall(q);
					cs.setInt(1, uid);
					cs.setInt(2, vid);
					cs.setString(3, CommentInput.getText());
					cs.execute();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.getContentPane().removeAll();
				loadVideoPanel(vid);
				frame.repaint();
			}
		});
		btnPostComment.setBounds(462, 217, 150, 30);
		panel.add(btnPostComment);
		// like button
		btnNewButton_2 = new JButton("Like");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String q = "{Call dbo.likeOrDislike(?,?,?)}";
				CallableStatement cs;
				try {
					cs = conn.prepareCall(q);
					cs.setInt(1, uid);
					cs.setInt(2, vid);
					cs.setByte(3, (byte) 1);
					cs.execute();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.getContentPane().removeAll();
				loadVideoPanel(vid);
				frame.repaint();
			}
		});
		btnNewButton_2.setBounds(429, 265, 71, 22);
		panel.add(btnNewButton_2);
		// dislike button
		btnDislike = new JButton("Dislike");
		btnDislike.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String q = "{Call dbo.likeOrDislike(?,?,?)}";
				CallableStatement cs;
				try {
					cs = conn.prepareCall(q);
					cs.setInt(1, uid);
					cs.setInt(2, vid);
					cs.setByte(3, (byte) 0);
					cs.execute();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.getContentPane().removeAll();
				loadVideoPanel(vid);
				frame.repaint();
			}
		});
		btnDislike.setBounds(512, 265, 100, 22);
		panel.add(btnDislike);
		// donate
		DonateInput = new JTextField();
		DonateInput.setBounds(429, 311, 76, 22);
		panel.add(DonateInput);
		DonateInput.setColumns(10);

		btnNewButton_3 = new JButton("Donate");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String q = "{Call dbo.donateMoney(?,?,?)}";
				CallableStatement cs;
				try {
					cs = conn.prepareCall(q);
					cs.setInt(1, uid);
					CallableStatement ps = conn.prepareCall("{? = call dbo.getUIDfromVID(?)}");
					ps.setInt(2, vid);
					ps.registerOutParameter(1, java.sql.Types.INTEGER);
					ps.execute();
					Integer cid = ps.getInt(1);
					cs.setInt(2, cid);
					cs.setInt(3, new Integer(DonateInput.getText()));
					cs.execute();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(frame, "Donate succeed");
				frame.getContentPane().removeAll();
				loadVideoPanel(vid);
				frame.repaint();
			}
		});
		btnNewButton_3.setBounds(515, 310, 97, 25);
		panel.add(btnNewButton_3);

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

	private void loadContentPanel(int cid) {
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 642, 437);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		Connection con = DatabaseConnection.getConnection();

		// get subscribe number
		SubscribeNumber = new JLabel("");
		SubscribeNumber.setBounds(526, 114, 56, 16);
		panel.add(SubscribeNumber);
		try {
			String qc = "Select dbo.getSubNumByContent(?)";
			PreparedStatement psc = con.prepareStatement(qc);
			psc.setInt(1, cid);
			ResultSet rsc = psc.executeQuery();
			rsc.next();
			SubscribeNumber.setText(Integer.toString(rsc.getInt(1)));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// back to main page button
		btnNewButton_1 = new JButton("Back To Main Page");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				loadMainPanel();
				frame.repaint();
			}
		});
		btnNewButton_1.setBounds(12, 13, 187, 30);
		panel.add(btnNewButton_1);

		ResultSet rsomment = null;
		// show content info based on type
		ResultSet rs = null;
		ContentInfoTable = new JTable();
		ContentInfoTable.setBounds(12, 56, 618, 30);
		panel.add(ContentInfoTable);
		try {
			String q1 = "Select dbo.getContentType(?)";
			PreparedStatement ps1 = con.prepareStatement(q1);
			ps1.setInt(1, cid);
			ResultSet rs1 = ps1.executeQuery();
			rs1.next();
			int type = rs1.getInt(1);
			String q = null;
			switch (type) {
			case 1:
				q = "Select * from dbo.getTVSeriesInfo(?)";
				break;
			case 2:
				q = "Select * from dbo.getChannelInfo(?)";
				break;
			default:
				q = "Select * from dbo.getLiveStreamInfo(?)";
				break;
			}
			PreparedStatement ps = con.prepareStatement(q);
			ps.setInt(1, cid);
			rs = ps.executeQuery();
			ContentInfoTable.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// show tags
		TagTable = new JTable();
		TagTable.setBounds(51, 256, 121, 146);
		panel.add(TagTable);
		try {
			String qc = "Select Tag from dbo.ContentTag Where ContentID = ?";
			PreparedStatement psc = con.prepareStatement(qc);
			psc.setInt(1, cid);
			ResultSet rsc = psc.executeQuery();
			TagTable.setModel(DbUtils.resultSetToTableModel(rsc));

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// subscribe button
		btnSubscribe = new JButton("Subscribe");
		btnSubscribe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String qc = " {? = call dbo.subscribeContent(?,?)}";
					CallableStatement psc;
					psc = con.prepareCall(qc);
					psc.registerOutParameter(1, java.sql.Types.INTEGER);
					psc.setInt(2, uid);
					psc.setInt(3, cid);
					psc.execute();
					int sub = psc.getInt(1);
					if (sub == 0) {
						JOptionPane.showMessageDialog(frame, "subscribe successfully");
					} else if (sub == 1) {
						JOptionPane.showMessageDialog(frame, "unsubscribe successfully");
					} else {
						JOptionPane.showMessageDialog(frame, "something wrong");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.getContentPane().removeAll();
				loadContentPanel(cid);
				frame.repaint();
			}
		});
		btnSubscribe.setBounds(390, 152, 108, 47);
		panel.add(btnSubscribe);

		lblNewLabel_1 = new JLabel("Subscribe# = ");
		lblNewLabel_1.setBounds(390, 107, 131, 30);
		panel.add(lblNewLabel_1);

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
		// loadContentPanel(1);
		// loadVideoPanel(1);
		// loadRegisterPanel();
		// loadMainPanel();
		// loadUserInfoPanel();
		// loadSearchPanel();

		// ResultSet rs = null;
		// loadSearchVideoPanel(rs);
		// loadSearchContentPanel(rs);
		// loadSearchUserPanel(rs);
	}

}
