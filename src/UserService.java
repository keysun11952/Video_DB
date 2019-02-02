
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;

public class UserService {
	private static final Random RANDOM = new SecureRandom();
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();
	private DatabaseConnection dbService = null;
	private static UserService us;

	private UserService(DatabaseConnection dbService) {
		this.dbService = dbService;
	}
	
	public static UserService getInstance() {
		if(us==null) {
			us=new UserService(DatabaseConnection.getInstance());
		}
		return us;
	}

	public boolean useApplicationLogins() {
		return false;
	}
	
	public boolean login(String username, String password) {
		try {
			String saltquery = "Select PassSalt FROM [User] where Username = ?";
			PreparedStatement saltstmt = this.dbService.getConnection().prepareStatement(saltquery);
			saltstmt.setString(1, username);
			ResultSet saltre = saltstmt.executeQuery();
			saltre.next();
			byte[] salt = saltre.getBytes(1);
		
			String hashquery =  "Select PassHash FROM [User] where Username = ?";
			PreparedStatement hashstmt = this.dbService.getConnection().prepareStatement(hashquery);
			hashstmt.setString(1, username);
			ResultSet hashre = hashstmt.executeQuery();
			hashre.next();
			String hash = hashre.getString(1);
		
			String result = this.hashPassword(salt, password);
			if(result.equals(hash)){
				return true;
			} else {
				JOptionPane.showMessageDialog(null,"Login Failed");
				return false;
			}
		} catch (SQLException e) {return false;}
	}

	public boolean register(String username, String password, String dob, String email) {
		//DONE: Task 6
		try {
			if(password.isEmpty() || username.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Username or password can not be empty");
				return false;
			}
			byte[] salt = this.getNewSalt();
			String hash = this.hashPassword(salt, password);
			Connection con = this.dbService.getConnection();
			CallableStatement register = con.prepareCall("{? = call Register(?,?,?,?,?)}");
			register.setString(2, username);
			register.setBytes(3, salt);
			register.setString(4, hash);
			register.setString(5, dob);
			register.setString(6, email);
			register.registerOutParameter(1, Types.INTEGER);
			register.execute();
		
			int returnValue = register.getInt(1);
			if(returnValue != 0){
				JOptionPane.showMessageDialog(null, "Registration Failed");
				return false;
			}
			return true;
		} catch (SQLException e) {return false;}
	}
	
	public byte[] getNewSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}
	
	public String getStringFromBytes(byte[] data) {
		return enc.encodeToString(data);
	}

	public String hashPassword(byte[] salt, String password) {
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f;
		byte[] hash = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		}
		return getStringFromBytes(hash);
	}

}
