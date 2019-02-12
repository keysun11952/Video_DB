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
import javax.swing.JTabbedPane;

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
	private JButton btnSearchTag;
	private JButton btnSearchUser;
	private JButton btnSearchContent;
	private JButton btnBack;
	private JTable table;
	private JTextField userInput;
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
	private JTable UserVideo_1;
	private JTable PersonalContent;
	private JTextField VName;
	private JTextField VLength;
	private JTextField Category;
	private JTextField Url;
	private JTextField Videoid;
	private JLabel lblPersonalContent;
	private JButton Back;
	private JLabel lblContentname;
	private JLabel lblContentUrl;
	private JTextField Cname;
	private JTextField Curl;
	private JButton btnCreateContent;
	private JLabel lblContentId;
	private JTextField Contentid;
	private JButton btnDeleteContent;
	private JLabel lblNewLabel_5;
	private JLabel lblNewContentName;
	private JTextField newUrl;
	private JTextField newName;
	private JButton btnNewButton_4;
	private JTextField contentID;
	private JLabel lblNewLabel_6;
	private JTextField newVideoID;
	private JTextField newVideoName;
	private JTextField newVideoLength;
	private JTextField newVideoCategory;
	private JTextField newVideoUrl;
	private JLabel lblContentDetail;
	private JTextField txtTag;
	private JTable videostable;

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
		File file = new File("src/setting");
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

			userInput = new JTextField();
			userInput.setBounds(76, 383, 81, 22);
			MainPanel.add(userInput);
			userInput.setColumns(10);

			JLabel lblNewLabel = new JLabel("Enter Video ID");
			lblNewLabel.setBounds(76, 369, 116, 16);
			MainPanel.add(lblNewLabel);

			JButton btnNewButton = new JButton("Go");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						String q = "{? = call dbo.checkVideo(?)}";
						CallableStatement ss = conn.prepareCall(q);
						ss.setInt(2, new Integer(userInput.getText()));
						ss.registerOutParameter(1, java.sql.Types.INTEGER);
						ss.execute();
						boolean exists = ss.getInt(1) == 0;
						if (!exists) {
							JOptionPane.showMessageDialog(frame, "Video doesn't exist");
							return;
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(frame, "Input must be integer");
						return;	
					}
					frame.getContentPane().removeAll();
					loadVideoPanel(new Integer(userInput.getText()));
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
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(frame, "Input must be integer");
						return;	
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
					if (newpass.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Password can not be empty");
						return;
					}
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
					JOptionPane.showMessageDialog(null, "Can not change password");
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
					if (newdob.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Birthday can not be empty");
						return;
					}
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
					JOptionPane.showMessageDialog(null, "Birthday not in valid format");
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
					if (newemail.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Email can not be empty");
						return;
					}
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
					JOptionPane.showMessageDialog(null, "Email already exist");
				}
			}
		});
		btnChangeEmail.setBounds(411, 321, 150, 35);
		UserInfoPanel.add(btnChangeEmail);

		JButton btnMyVideos = new JButton("My Videos");
		btnMyVideos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.getContentPane().removeAll();
				loadVideoInfoPanel(username);
				frame.repaint();
			}
		});
		btnMyVideos.setBounds(69, 126, 150, 35);
		UserInfoPanel.add(btnMyVideos);

		JButton btnMyContents = new JButton("My Contents");
		btnMyContents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				loadContentInfoPanel(username);
				frame.repaint();
			}
		});
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
		lblSearch.setBounds(68, 52, 88, 40);
		SearchPanel.add(lblSearch);

		txtUser = new JTextField();
		txtUser.setBounds(68, 263, 342, 30);
		SearchPanel.add(txtUser);
		txtUser.setColumns(10);

		txtVideo = new JTextField();
		txtVideo.setBounds(68, 126, 342, 30);
		SearchPanel.add(txtVideo);
		txtVideo.setColumns(10);

		txtContent = new JTextField();
		txtContent.setBounds(68, 196, 342, 30);
		SearchPanel.add(txtContent);
		txtContent.setColumns(10);

		txtTag = new JTextField();
		txtTag.setColumns(10);
		txtTag.setBounds(68, 332, 342, 30);
		SearchPanel.add(txtTag);

		JButton btnSearchTag = new JButton("Search Tag");
		btnSearchTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String tag = txtTag.getText();
					Connection con = DatabaseConnection.getConnection();
					String query = "Select * From searchVideoByTag (?)";
					PreparedStatement search = con.prepareStatement(query);
					search.setString(1, tag);
					ResultSet rs = search.executeQuery();
					frame.getContentPane().removeAll();
					loadSearchTagPanel(rs);
					frame.repaint();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSearchTag.setBounds(422, 331, 135, 30);
		SearchPanel.add(btnSearchTag);

		btnSearchTag = new JButton("Search User");
		btnSearchTag.addActionListener(new ActionListener() {
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
		btnSearchTag.setBounds(422, 263, 135, 30);
		SearchPanel.add(btnSearchTag);

		btnSearchUser = new JButton("Search Video");
		btnSearchUser.addActionListener(new ActionListener() {
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
		btnSearchUser.setBounds(422, 125, 135, 30);
		SearchPanel.add(btnSearchUser);

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
		btnSearchContent.setBounds(422, 196, 135, 30);
		SearchPanel.add(btnSearchContent);

		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				loadMainPanel();
				frame.repaint();
			}
		});
		btnBack.setBounds(422, 63, 135, 30);
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

		Connection con = DatabaseConnection.getConnection();
		btnSearchUser = new JButton("Search Video");
		btnSearchUser.addActionListener(new ActionListener() {
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
		btnSearchUser.setBounds(439, 381, 135, 25);
		loadSearchVideoPanel.add(btnSearchUser);

		userInput = new JTextField();
		userInput.setBounds(217, 51, 81, 22);
		loadSearchVideoPanel.add(userInput);
		userInput.setColumns(10);

		JLabel lblNewLabel = new JLabel("Enter Video ID");
		lblNewLabel.setBounds(217, 35, 116, 16);
		loadSearchVideoPanel.add(lblNewLabel);

		JButton btnNewButton = new JButton("Go");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String q = "{? = call dbo.checkVideo(?)}";
					CallableStatement ss = con.prepareCall(q);
					ss.setInt(2, new Integer(userInput.getText()));
					ss.registerOutParameter(1, java.sql.Types.INTEGER);
					ss.execute();
					boolean exists = ss.getInt(1) == 0;
					if (!exists) {
						JOptionPane.showMessageDialog(frame, "Video doesn't exist");
						return;
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, "Input must be integer");
					return;	
				}
				frame.getContentPane().removeAll();
				loadVideoPanel(new Integer(userInput.getText()));
				frame.repaint();
			}
		});
		btnNewButton.setBounds(298, 50, 56, 25);
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
		ContentInput.setBounds(212, 49, 81, 22);
		loadSearchContentPanel.add(ContentInput);

		JLabel lblEnterContentId = new JLabel("Enter Content ID");
		lblEnterContentId.setBounds(210, 31, 116, 16);
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
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, "Input must be integer");
					return;					}
				frame.getContentPane().removeAll();
				loadContentPanel(new Integer(ContentInput.getText()));
				frame.repaint();
			}
		});
		button.setBounds(294, 48, 56, 25);
		loadSearchContentPanel.add(button);
	}

	private void loadVideoPanel(int vid) {
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 642, 437);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		btnNewButton_1 = new JButton("Back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				loadMainPanel();
				frame.repaint();
			}
		});
		btnNewButton_1.setBounds(482, 27, 128, 30);
		panel.add(btnNewButton_1);
		ResultSet rsomment = null;
		Connection conn = DatabaseConnection.getConnection();
		// show video info
		ResultSet rs = null;
		ContentInfoTable = new JTable();
		ContentInfoTable.setBounds(0, 70, 642, 37);
		panel.add(ContentInfoTable);
		try {
			String q = "{Call dbo.watchVideo(?,?)}";
			PreparedStatement ps = conn.prepareStatement(q);
			ps.setInt(1, uid);
			ps.setInt(2, vid);
			ps.execute();
		} catch (SQLException e1) {
//			e1.printStackTrace();
		}
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
		CommentTable.setBounds(51, 120, 529, 126);
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
		TagTable.setBounds(51, 295, 242, 107);
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
		CommentInput.setBounds(46, 259, 400, 22);
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
		btnPostComment.setBounds(458, 258, 134, 24);
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
		btnNewButton_2.setBounds(376, 311, 71, 22);
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
		btnDislike.setBounds(459, 311, 100, 22);
		panel.add(btnDislike);
		// donate
		DonateInput = new JTextField();
		DonateInput.setBounds(376, 357, 76, 22);
		panel.add(DonateInput);
		DonateInput.setColumns(10);

		btnNewButton_3 = new JButton("Donate");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String q = "{? = Call dbo.donateMoney(?,?,?)}";
				CallableStatement cs;
				try {
					CallableStatement ps = conn.prepareCall("{? = call dbo.getUIDfromVID(?)}");
					ps.setInt(2, vid);
					ps.registerOutParameter(1, java.sql.Types.INTEGER);
					ps.execute();
					Integer cid = ps.getInt(1);
					cs = conn.prepareCall(q);
					cs.setInt(2, uid);
					cs.setInt(3, cid);
					try {
						cs.setInt(4, new Integer(DonateInput.getText()));
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(frame, "Donate amount nust be integer");
						frame.getContentPane().removeAll();
						loadVideoPanel(vid);
						frame.repaint();
						return;
					}
					if (DonateInput.getText().isEmpty())
						return;
					cs.registerOutParameter(1, java.sql.Types.INTEGER);
					cs.execute();
					int r = cs.getInt(1);
					if (r == 4) {
						JOptionPane.showMessageDialog(frame, "Donate amount must be positive");
						frame.getContentPane().removeAll();
						loadVideoPanel(vid);
						frame.repaint();
						return;
					}
					if (r != 0)
						JOptionPane.showMessageDialog(frame, "Donate failed");
					else
						JOptionPane.showMessageDialog(frame, "Donate succeed");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.getContentPane().removeAll();
				loadVideoPanel(vid);
				frame.repaint();
			}
		});
		btnNewButton_3.setBounds(462, 356, 97, 25);
		panel.add(btnNewButton_3);

		JLabel lblVideoDetail = new JLabel("Video Detail");
		lblVideoDetail.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblVideoDetail.setBounds(51, 27, 139, 30);
		panel.add(lblVideoDetail);

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

		btnSearchTag = new JButton("Search User");
		btnSearchTag.addActionListener(new ActionListener() {
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
		btnSearchTag.setBounds(439, 381, 135, 25);
		loadSearchUserPanel.add(btnSearchTag);
		
		userInput = new JTextField();
		userInput.setBounds(217, 51, 81, 22);
		loadSearchUserPanel.add(userInput);
		userInput.setColumns(10);

		JLabel lblNewLabel = new JLabel("Enter Username");
		lblNewLabel.setBounds(217, 35, 116, 16);
		loadSearchUserPanel.add(lblNewLabel);

		JButton btnNewButton = new JButton("Go");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				loadUserVideoPanel(userInput.getText());
				frame.repaint();
			}
		});
		btnNewButton.setBounds(298, 50, 56, 25);
		loadSearchUserPanel.add(btnNewButton);

	}

	private void loadSearchTagPanel(ResultSet rs) {

		JPanel loadSearchTagPanel = new JPanel();
		loadSearchTagPanel.setBounds(0, 0, 642, 437);
		frame.getContentPane().add(loadSearchTagPanel);
		loadSearchTagPanel.setLayout(null);

		JLabel lblTag = new JLabel("Tag");
		lblTag.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTag.setBounds(62, 35, 105, 25);
		loadSearchTagPanel.add(lblTag);

		table = new JTable();
		table.setBounds(62, 86, 512, 271);
		loadSearchTagPanel.add(table);
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
		loadSearchTagPanel.add(btnBack);

		txtTag = new JTextField();
		txtTag.setBounds(62, 382, 365, 22);
		loadSearchTagPanel.add(txtTag);
		txtTag.setColumns(10);

		btnSearchTag = new JButton("Search Tag");
		btnSearchTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String tag = txtTag.getText();
					Connection con = DatabaseConnection.getConnection();
					String query = "Select * From searchVideoByTag (?)";
					PreparedStatement search = con.prepareStatement(query);
					search.setString(1, tag);
					ResultSet rs = search.executeQuery();
					frame.getContentPane().removeAll();
					loadSearchTagPanel(rs);
					frame.repaint();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSearchTag.setBounds(439, 381, 135, 25);
		loadSearchTagPanel.add(btnSearchTag);

		userInput = new JTextField();
		userInput.setBounds(217, 51, 81, 22);
		loadSearchTagPanel.add(userInput);
		userInput.setColumns(10);

		JLabel lblNewLabel = new JLabel("Enter Video ID");
		lblNewLabel.setBounds(217, 35, 116, 16);
		loadSearchTagPanel.add(lblNewLabel);

		JButton btnNewButton = new JButton("Go");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String q = "{? = call dbo.checkVideo(?)}";
					Connection con = DatabaseConnection.getConnection();
					CallableStatement ss = con.prepareCall(q);
					ss.setInt(2, new Integer(userInput.getText()));
					ss.registerOutParameter(1, java.sql.Types.INTEGER);
					ss.execute();
					boolean exists = ss.getInt(1) == 0;
					if (!exists) {
						JOptionPane.showMessageDialog(frame, "Video doesn't exist");
						return;
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, "Input must be integer");
					return;	
				}
				frame.getContentPane().removeAll();
				loadVideoPanel(new Integer(userInput.getText()));
				frame.repaint();
			}
		});
		btnNewButton.setBounds(298, 50, 56, 25);
		loadSearchTagPanel.add(btnNewButton);

	}

	private void loadContentPanel(int cid) {
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 642, 437);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		Connection con = DatabaseConnection.getConnection();

		// get subscribe number
		SubscribeNumber = new JLabel("");
		SubscribeNumber.setBounds(515, 230, 56, 30);
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
		btnNewButton_1 = new JButton("Back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				loadMainPanel();
				frame.repaint();
			}
		});
		btnNewButton_1.setBounds(467, 41, 124, 30);
		panel.add(btnNewButton_1);

		ResultSet rsomment = null;
		// show content info based on type
		ResultSet rs = null;
		ContentInfoTable = new JTable();
		ContentInfoTable.setBounds(0, 101, 642, 62);
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
		TagTable.setBounds(12, 191, 154, 199);
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
		btnSubscribe = new JButton("Subscribe / UnSubsribe");
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
		btnSubscribe.setBounds(380, 292, 208, 30);
		panel.add(btnSubscribe);

		lblNewLabel_1 = new JLabel("Subscribe# = ");
		lblNewLabel_1.setBounds(380, 230, 131, 30);
		panel.add(lblNewLabel_1);

		lblContentDetail = new JLabel("Content Detail");
		lblContentDetail.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblContentDetail.setBounds(78, 38, 154, 30);
		panel.add(lblContentDetail);
		
		videostable = new JTable();
		videostable.setBounds(178, 191, 190, 193);
		panel.add(videostable);
		try {
			String qc = "Select * from dbo.getVideoInContent(?)";
			PreparedStatement psc = con.prepareStatement(qc);
			psc.setInt(1, cid);
			ResultSet rsc = psc.executeQuery();
			videostable.setModel(DbUtils.resultSetToTableModel(rsc));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		

	}

	private void loadVideoInfoPanel(String username) {
		JPanel VideoInfoPanel = new JPanel();
		VideoInfoPanel.setBounds(0, 0, 642, 437);
		frame.getContentPane().add(VideoInfoPanel);
		VideoInfoPanel.setLayout(null);

		UserVideo_1 = new JTable();
		UserVideo_1.setBounds(26, 48, 387, 163);
		VideoInfoPanel.add(UserVideo_1);

		ResultSet rs = null;
		Connection conn = DatabaseConnection.getConnection();

		Statement st2;
		try {
			st2 = conn.createStatement();
			String query2 = "SELECT * From getVideoByUser('" + username + "')";
			ResultSet rs2 = null;
			rs2 = st2.executeQuery(query2);
			UserVideo_1.setModel(DbUtils.resultSetToTableModel(rs2));

			JLabel lblYourPersonalVideos = new JLabel("Your Personal Videos");
			lblYourPersonalVideos.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblYourPersonalVideos.setBounds(26, 23, 182, 16);
			VideoInfoPanel.add(lblYourPersonalVideos);
			{
				JLabel lblNewLabel_2 = new JLabel(" Video Name");
				lblNewLabel_2.setBounds(26, 224, 92, 19);
				VideoInfoPanel.add(lblNewLabel_2);
			}
			{
				JLabel lblNewLabel_3 = new JLabel("   Video Length");
				lblNewLabel_3.setBounds(107, 224, 101, 16);
				VideoInfoPanel.add(lblNewLabel_3);
			}
			{
				JLabel lblNewLabel_4 = new JLabel("  Category");
				lblNewLabel_4.setBounds(196, 224, 77, 16);
				VideoInfoPanel.add(lblNewLabel_4);
			}
			{
				JLabel lblUrl = new JLabel("Url");
				lblUrl.setBounds(285, 224, 56, 16);
				VideoInfoPanel.add(lblUrl);
			}

			VName = new JTextField();
			VName.setBounds(26, 241, 77, 25);
			VideoInfoPanel.add(VName);
			VName.setColumns(10);

			VLength = new JTextField();
			VLength.setBounds(117, 242, 82, 22);
			VideoInfoPanel.add(VLength);
			VLength.setColumns(10);

			Category = new JTextField();
			Category.setBounds(209, 242, 45, 22);
			VideoInfoPanel.add(Category);
			Category.setColumns(10);

			Url = new JTextField();
			Url.setBounds(262, 242, 62, 22);
			VideoInfoPanel.add(Url);
			Url.setColumns(10);

			JButton btnUpdate = new JButton("Upload");
			btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 13));
			btnUpdate.setBounds(336, 242, 77, 25);
			btnUpdate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					try {
						String VideoName = VName.getText();
						String VideoLength = VLength.getText();
						String CategoryName = Category.getText();
						String UrlName = Url.getText();
						if(VideoName.isEmpty()){
							JOptionPane.showMessageDialog(frame, "Upload failed. You must enter a Name");
							frame.getContentPane().removeAll();
							loadVideoInfoPanel(username);
							frame.repaint();
						}else if(VideoLength.isEmpty()){
							JOptionPane.showMessageDialog(frame, "Upload failed. You must enter a length");
							frame.getContentPane().removeAll();
							loadVideoInfoPanel(username);
							frame.repaint();
						}else if(CategoryName.isEmpty()){
							JOptionPane.showMessageDialog(frame, "Upload failed. You must enter a Category");
							frame.getContentPane().removeAll();
							loadVideoInfoPanel(username);
							frame.repaint();
						}else{
						Connection con = DatabaseConnection.getConnection();
						String qc = " {? = call dbo.uploadVideo(?,?,?,?,?)}";
						CallableStatement psc;
						psc = con.prepareCall(qc);
						psc.registerOutParameter(1, java.sql.Types.INTEGER);
						psc.setInt(2, uid);
						psc.setString(3, VideoName);
						psc.setString(4, VideoLength);
						psc.setString(5, CategoryName);
						psc.setString(6, UrlName);
						psc.execute();
						int sub = psc.getInt(1);
						if (sub == 0) {
							JOptionPane.showMessageDialog(frame, "Upload successfully");
						} if(sub == 2) {
							JOptionPane.showMessageDialog(frame, "Upload failed. Category not exists");
						}
						}
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(frame, "Upload failed. Video Length must be time style. Video Category must be integer");
					}
					frame.getContentPane().removeAll();
					loadVideoInfoPanel(username);
					frame.repaint();
					
				}
			});
			VideoInfoPanel.add(btnUpdate);

			JLabel lblVideoid = new JLabel("Video ID");
			lblVideoid.setBounds(26, 367, 56, 16);
			VideoInfoPanel.add(lblVideoid);

			Videoid = new JTextField();
			Videoid.setBounds(26, 383, 77, 22);
			VideoInfoPanel.add(Videoid);
			Videoid.setColumns(10);

			JButton delete = new JButton("Delete");
			delete.setFont(new Font("Tahoma", Font.BOLD, 13));
			delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						String id = Videoid.getText();
						Connection con = DatabaseConnection.getConnection();
						String qc = " {? = call dbo.deleteVideo(?)}";
						CallableStatement psc;
						psc = con.prepareCall(qc);
						psc.registerOutParameter(1, java.sql.Types.INTEGER);
						psc.setString(2, id);

						psc.execute();
						int sub = psc.getInt(1);
						if (sub == 0) {
							JOptionPane.showMessageDialog(frame, "delete successfully");
						} else if(sub == 0){
						}else{
							JOptionPane.showMessageDialog(frame, "VideoID cannot be found");
						}
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(frame, "Upload failed. VideoID must be an integer");
					}
					frame.getContentPane().removeAll();
					loadVideoInfoPanel(username);
					frame.repaint();
				}
			});
			delete.setBounds(117, 382, 82, 25);
			VideoInfoPanel.add(delete);

			JButton Back = new JButton("Back");
			Back.setFont(new Font("Tahoma", Font.BOLD, 13));
			Back.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.getContentPane().removeAll();
					loadUserInfoPanel();
					frame.repaint();
				}
			});
			Back.setBounds(481, 35, 97, 25);
			VideoInfoPanel.add(Back);

			newVideoID = new JTextField();
			newVideoID.setBounds(26, 290, 68, 22);
			VideoInfoPanel.add(newVideoID);
			newVideoID.setColumns(10);

			newVideoName = new JTextField();
			newVideoName.setBounds(107, 290, 101, 22);
			VideoInfoPanel.add(newVideoName);
			newVideoName.setColumns(10);

			newVideoLength = new JTextField();
			newVideoLength.setBounds(213, 290, 111, 22);
			VideoInfoPanel.add(newVideoLength);
			newVideoLength.setColumns(10);

			newVideoCategory = new JTextField();
			newVideoCategory.setBounds(26, 338, 56, 22);
			VideoInfoPanel.add(newVideoCategory);
			newVideoCategory.setColumns(10);

			newVideoUrl = new JTextField();
			newVideoUrl.setBounds(94, 338, 116, 22);
			VideoInfoPanel.add(newVideoUrl);
			newVideoUrl.setColumns(10);

			JLabel lblNewLabel_7 = new JLabel("Video ID");
			lblNewLabel_7.setBounds(26, 272, 56, 16);
			VideoInfoPanel.add(lblNewLabel_7);

			JLabel lblNewName = new JLabel("New Name");
			lblNewName.setBounds(122, 272, 77, 16);
			VideoInfoPanel.add(lblNewName);

			JLabel lblNewLabel_8 = new JLabel("New Length");
			lblNewLabel_8.setBounds(228, 272, 89, 16);
			VideoInfoPanel.add(lblNewLabel_8);

			JLabel lblNewCategory = new JLabel("Category");
			lblNewCategory.setBounds(26, 320, 77, 16);
			VideoInfoPanel.add(lblNewCategory);

			JLabel lblNewUrl = new JLabel(" New Url");
			lblNewUrl.setBounds(118, 320, 56, 16);
			VideoInfoPanel.add(lblNewUrl);

			JButton btnUpdate_1 = new JButton("Update");
			btnUpdate_1.setFont(new Font("Tahoma", Font.BOLD, 13));
			btnUpdate_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					try {
						String newID = newVideoID.getText();
						String newLength = newVideoLength.getText();
						String newName = newVideoName.getText();
						String newUrl = newVideoUrl.getText();
						String newCategory = newVideoCategory.getText();
						if(newID.isEmpty()){
							JOptionPane.showMessageDialog(frame, "Edit failed. You must enter a ID");
						}else if(newLength.isEmpty()){
							JOptionPane.showMessageDialog(frame, "Edit failed. You must enter a Length");
						}else if(newName.isEmpty()){
							JOptionPane.showMessageDialog(frame, "Edit failed. You must enter a name");
						}else if(newCategory.isEmpty()){
							JOptionPane.showMessageDialog(frame, "Edit failed. You must enter a Category");
						}else{
						Connection con = DatabaseConnection.getConnection();
						String qc = " {? = call dbo.editVideo(?,?,?,?,?)}";
						CallableStatement psc;
						psc = con.prepareCall(qc);
						psc.registerOutParameter(1, java.sql.Types.INTEGER);
						psc.setString(2, newID);
						psc.setString(3, newName);
						psc.setString(4, newLength);
						psc.setString(5, newCategory);
						psc.setString(6, newUrl);
						psc.execute();
						int sub = psc.getInt(1);
						if (sub == 0) {
							JOptionPane.showMessageDialog(frame, "Edit successfully");
						} else if(sub == 2){
							JOptionPane.showMessageDialog(frame, "Edit failed. Invalid category");
						}else if(sub == 3){
							JOptionPane.showMessageDialog(frame, "Edit failed. Video doesn't exists");
						}
						}
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(frame, "Edit failed. VideoID must be integer. Length must be time style. Category must be integer");
					}
					frame.getContentPane().removeAll();
					loadVideoInfoPanel(username);
					frame.repaint();
				}
			});
			btnUpdate_1.setBounds(228, 337, 92, 25);
			VideoInfoPanel.add(btnUpdate_1);

			JLabel lblNewLabel_9 = new JLabel("Category - Category Number");
			lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_9.setBounds(429, 67, 201, 16);
			VideoInfoPanel.add(lblNewLabel_9);

			JLabel lblNewLabel_10 = new JLabel("Miscellaneous - 0");
			lblNewLabel_10.setBounds(443, 87, 153, 16);
			VideoInfoPanel.add(lblNewLabel_10);

			JLabel lblNewLabel_11 = new JLabel("Cars & Vehicles - 1");
			lblNewLabel_11.setBounds(443, 107, 153, 16);
			VideoInfoPanel.add(lblNewLabel_11);

			JLabel lblNewLabel_12 = new JLabel("Comedy - 2");
			lblNewLabel_12.setBounds(443, 127, 182, 16);
			VideoInfoPanel.add(lblNewLabel_12);

			JLabel lblNewLabel_13 = new JLabel("Education - 3");
			lblNewLabel_13.setBounds(443, 147, 168, 16);
			VideoInfoPanel.add(lblNewLabel_13);

			JLabel lblNewLabel_14 = new JLabel("Entertainment - 4");
			lblNewLabel_14.setBounds(443, 167, 139, 16);
			VideoInfoPanel.add(lblNewLabel_14);

			JLabel lblNewLabel_15 = new JLabel("Gaming - 6");
			lblNewLabel_15.setBounds(443, 207, 116, 16);
			VideoInfoPanel.add(lblNewLabel_15);

			JLabel lblNewLabel_16 = new JLabel("Film & Animation - 5");
			lblNewLabel_16.setBounds(443, 187, 153, 16);
			VideoInfoPanel.add(lblNewLabel_16);

			JLabel lblNewLabel_17 = new JLabel("How-to & Style - 7");
			lblNewLabel_17.setBounds(443, 227, 116, 16);
			VideoInfoPanel.add(lblNewLabel_17);

			JLabel lblNewLabel_18 = new JLabel("Music - 8");
			lblNewLabel_18.setBounds(443, 247, 116, 16);
			VideoInfoPanel.add(lblNewLabel_18);

			JLabel lblNewLabel_19 = new JLabel("News & Politics - 9");
			lblNewLabel_19.setBounds(443, 267, 139, 16);
			VideoInfoPanel.add(lblNewLabel_19);

			JLabel lblNewLabel_20 = new JLabel("Non-profits & Activism - 10");
			lblNewLabel_20.setBounds(443, 287, 168, 16);
			VideoInfoPanel.add(lblNewLabel_20);

			JLabel lblNewLabel_21 = new JLabel("People & Blogs - 11");
			lblNewLabel_21.setBounds(443, 307, 153, 16);
			VideoInfoPanel.add(lblNewLabel_21);

			JLabel lblNewLabel_22 = new JLabel("Pets & Animals - 12");
			lblNewLabel_22.setBounds(443, 327, 116, 16);
			VideoInfoPanel.add(lblNewLabel_22);

			JLabel lblNewLabel_23 = new JLabel("Science & Technology - 13");
			lblNewLabel_23.setBounds(443, 347, 168, 16);
			VideoInfoPanel.add(lblNewLabel_23);

			JLabel lblNewLabel_24 = new JLabel("Sport - 14");
			lblNewLabel_24.setBounds(443, 367, 116, 16);
			VideoInfoPanel.add(lblNewLabel_24);

			JLabel lblNewLabel_25 = new JLabel("Travel & Events - 15");
			lblNewLabel_25.setBounds(443, 387, 153, 16);
			VideoInfoPanel.add(lblNewLabel_25);

		} catch (SQLException e) {
			e.printStackTrace();
		}




	}

	private void loadContentInfoPanel(String username) {
		JPanel ContentInfoPanel = new JPanel();
		ContentInfoPanel.setBounds(0, 0, 642, 437);
		frame.getContentPane().add(ContentInfoPanel);
		ContentInfoPanel.setLayout(null);

		PersonalContent = new JTable();
		PersonalContent.setBounds(59, 67, 520, 190);
		ContentInfoPanel.add(PersonalContent);

		ResultSet rs = null;
		Connection conn = DatabaseConnection.getConnection();

		Statement st3;
		try {
			st3 = conn.createStatement();
			String query3 = "SELECT * From searchContentByCreator('" + username + "')";
			ResultSet rs3 = null;
			rs3 = st3.executeQuery(query3);
			PersonalContent.setModel(DbUtils.resultSetToTableModel(rs3));

			lblPersonalContent = new JLabel("Personal Content");
			lblPersonalContent.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblPersonalContent.setBounds(49, 33, 229, 16);
			ContentInfoPanel.add(lblPersonalContent);

			Back = new JButton("Back");
			Back.setFont(new Font("Tahoma", Font.BOLD, 13));
			Back.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.getContentPane().removeAll();
					loadUserInfoPanel();
					frame.repaint();
				}
			});
			Back.setBounds(497, 30, 97, 25);
			ContentInfoPanel.add(Back);

			lblContentname = new JLabel("Content Name");
			lblContentname.setBounds(49, 270, 102, 16);
			ContentInfoPanel.add(lblContentname);

			lblContentUrl = new JLabel("Content URL");
			lblContentUrl.setBounds(185, 270, 118, 16);
			ContentInfoPanel.add(lblContentUrl);

			Cname = new JTextField();
			Cname.setBounds(49, 289, 116, 22);
			ContentInfoPanel.add(Cname);
			Cname.setColumns(10);

			Curl = new JTextField();
			Curl.setBounds(185, 289, 116, 22);
			ContentInfoPanel.add(Curl);
			Curl.setColumns(10);

			btnCreateContent = new JButton("Create Content");
			btnCreateContent.setFont(new Font("Tahoma", Font.BOLD, 13));
			btnCreateContent.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						String CName = Cname.getText();
						String CUrl = Curl.getText();
						if(CName.isEmpty()){
							JOptionPane.showMessageDialog(frame, "Action failed. You must enter a Name ");
							frame.getContentPane().removeAll();
							loadContentInfoPanel(username);
							frame.repaint();
						}else{
						Connection con = DatabaseConnection.getConnection();
						String qc = " {? = call dbo.createContent(?,?,?)}";
						CallableStatement psc;
						psc = con.prepareCall(qc);
						psc.registerOutParameter(1, java.sql.Types.INTEGER);
						psc.setString(2, CName);
						psc.setString(3, CUrl);
						psc.setInt(4, uid);
						psc.execute();
						int sub = psc.getInt(1);
						if (sub == 0) {
							JOptionPane.showMessageDialog(frame, "Create content successfully");
						} else {
							JOptionPane.showMessageDialog(frame, "something wrong");
						}
						}
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(frame, "something wrong");
						e1.printStackTrace();
					}
					frame.getContentPane().removeAll();
					loadContentInfoPanel(username);
					frame.repaint();

				}
			});
			btnCreateContent.setBounds(386, 288, 155, 25);
			ContentInfoPanel.add(btnCreateContent);

			lblContentId = new JLabel(" Content ID");
			lblContentId.setBounds(49, 371, 79, 16);
			ContentInfoPanel.add(lblContentId);

			Contentid = new JTextField();
			Contentid.setBounds(49, 389, 116, 22);
			ContentInfoPanel.add(Contentid);
			Contentid.setColumns(10);

			btnDeleteContent = new JButton("Delete Content");
			btnDeleteContent.setFont(new Font("Tahoma", Font.BOLD, 13));
			btnDeleteContent.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						String Cid = Contentid.getText();
						Connection con = DatabaseConnection.getConnection();
						String qc = " {? = call dbo.deleteContent(?)}";
						CallableStatement psc;
						psc = con.prepareCall(qc);
						psc.registerOutParameter(1, java.sql.Types.INTEGER);
						psc.setString(2, Cid);
						psc.execute();
						int sub = psc.getInt(1);
						if (sub == 0) {
							JOptionPane.showMessageDialog(frame, "Delete content successfully");
						} else {
							JOptionPane.showMessageDialog(frame, "Action failed. Invalid ContentID");
						}
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(frame, "Action failed. ContentID must be an integer");
					}
					frame.getContentPane().removeAll();
					loadContentInfoPanel(username);
					frame.repaint();

				}
			});
			btnDeleteContent.setBounds(185, 388, 155, 25);
			ContentInfoPanel.add(btnDeleteContent);

			lblNewLabel_5 = new JLabel("  New URL");
			lblNewLabel_5.setBounds(254, 324, 118, 16);
			ContentInfoPanel.add(lblNewLabel_5);

			lblNewContentName = new JLabel("New Content Name");
			lblNewContentName.setBounds(130, 324, 114, 16);
			ContentInfoPanel.add(lblNewContentName);

			newUrl = new JTextField();
			newUrl.setBounds(256, 340, 116, 22);
			ContentInfoPanel.add(newUrl);
			newUrl.setColumns(10);

			newName = new JTextField();
			newName.setBounds(128, 340, 116, 22);
			ContentInfoPanel.add(newName);
			newName.setColumns(10);

			btnNewButton_4 = new JButton("Edit Content");
			btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 13));
			btnNewButton_4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					try {
						String id = contentID.getText();
						String nName = newName.getText();
						String nUrl = newUrl.getText();
						if(id.isEmpty()){
							JOptionPane.showMessageDialog(frame, "Action failed. You must enter a ID");
						}else if(nName.isEmpty()){
							JOptionPane.showMessageDialog(frame, "Action failed. You must enter a Name");
						}else{
						Connection con = DatabaseConnection.getConnection();
						String qc = " {? = call dbo.editContent(?,?,?)}";
						CallableStatement psc;
						psc = con.prepareCall(qc);
						psc.registerOutParameter(1, java.sql.Types.INTEGER);
						psc.setString(2, id);
						psc.setString(3, nName);
						psc.setString(4, nUrl);
						psc.execute();
						int sub = psc.getInt(1);
						if (sub == 0) {
							JOptionPane.showMessageDialog(frame, "Edit content successfully");
						} else {
							JOptionPane.showMessageDialog(frame, "Action failed. Content not found");
						}
						}
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(frame, "ContentId must be an integer");
					}
					frame.getContentPane().removeAll();
					loadContentInfoPanel(username);
					frame.repaint();
				}
			});
			btnNewButton_4.setBounds(387, 339, 154, 25);
			ContentInfoPanel.add(btnNewButton_4);

			contentID = new JTextField();
			contentID.setBounds(49, 340, 67, 22);
			ContentInfoPanel.add(contentID);
			contentID.setColumns(10);

			lblNewLabel_6 = new JLabel(" Content ID");
			lblNewLabel_6.setBounds(49, 324, 69, 16);
			ContentInfoPanel.add(lblNewLabel_6);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void loadUserVideoPanel(String user) {

		JPanel userVideoPanel = new JPanel();
		userVideoPanel.setBounds(0, 0, 642, 437);
		frame.getContentPane().add(userVideoPanel);
		userVideoPanel.setLayout(null);

		JLabel lblVideo = new JLabel("Video Uploaded By " + user);
		lblVideo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblVideo.setBounds(62, 38, 260, 25);
		userVideoPanel.add(lblVideo);

		table = new JTable();
		table.setBounds(62, 109, 512, 271);
		userVideoPanel.add(table);

		try {
			ResultSet rs = null;
			Connection conn = DatabaseConnection.getConnection();
			String query = "SELECT * From getVideoByUser(?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, user);
			rs = stmt.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				loadSearchPanel();
				frame.repaint();
			}
		});
		btnBack.setBounds(439, 38, 135, 25);
		userVideoPanel.add(btnBack);

		txtUser = new JTextField();
		txtUser.setBounds(62, 393, 365, 22);
		userVideoPanel.add(txtUser);
		txtUser.setColumns(10);

		Connection con = DatabaseConnection.getConnection();
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
		btnSearchUser.setBounds(439, 392, 135, 25);
		userVideoPanel.add(btnSearchUser);

		userInput = new JTextField();
		userInput.setBounds(425, 72, 81, 22);
		userVideoPanel.add(userInput);
		userInput.setColumns(10);

		JLabel lblNewLabel = new JLabel("Enter Video ID");
		lblNewLabel.setBounds(333, 75, 94, 16);
		userVideoPanel.add(lblNewLabel);

		JButton btnNewButton = new JButton("Go");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String q = "{? = call dbo.checkVideo(?)}";
					CallableStatement ss = con.prepareCall(q);
					ss.setInt(2, new Integer(userInput.getText()));
					ss.registerOutParameter(1, java.sql.Types.INTEGER);
					ss.execute();
					boolean exists = ss.getInt(1) == 0;
					if (!exists) {
						JOptionPane.showMessageDialog(frame, "Video doesn't exist");
						return;
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, "Input must be integer");
					return;	
				}
				frame.getContentPane().removeAll();
				loadVideoPanel(new Integer(userInput.getText()));
				frame.repaint();
			}
		});
		btnNewButton.setBounds(518, 71, 56, 25);
		userVideoPanel.add(btnNewButton);
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
//		loadContentPanel(1);
//		loadVideoPanel(1);
//		loadRegisterPanel();
//		loadMainPanel();
//		loadUserInfoPanel();
//		loadVideoInfoPanel(username);
//		loadContentInfoPanel(username);
//		loadSearchPanel();

//		ResultSet rs = null;
//		loadUserVideoPanel(null);
//		loadSearchVideoPanel(rs);
//		loadSearchContentPanel(rs);
//		loadSearchUserPanel(rs);
//		loadSearchTagPanel(rs);
	}
}
