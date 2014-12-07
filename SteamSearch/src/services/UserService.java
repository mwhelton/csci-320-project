package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author Kyle Jennings Program Description: Service for interacting with User
 *         table UserService.java
 */
public class UserService {
	
	private ConnectionService conService;
	//eeseineishibohpooyem

	public UserService() {
		conService = new ConnectionService();
	}

	public void createTable() throws SQLException {
		PreparedStatement preparedStatement = null;
		Connection con = null;
		String createTableSQL = "CREATE TABLE user ("
				+ "PASSWORD varchar(20) NOT NULL, "
				+ "USERNAME varchar(20) NOT NULL, " + "PRIMARY KEY (USERNAME) "
				+ ")";

		try {
			Class.forName("org.postgresql.Driver");
			con = conService.getConnection();
			preparedStatement = con.prepareStatement(createTableSQL);
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (con != null) {
				con.close();
			}

		}
	}
	
	/**
	 * Checks if password is valid for username
	 * @param password
	 * @param username
	 * @return
	 * @throws SQLException 
	 */
	public boolean checkPassword(String username, String password) throws SQLException {
		PreparedStatement preparedStatement = null;
		Connection con = null;

		String sql = "select u.password from public.user as u where u.username = ?";

		try {
			Class.forName("org.postgresql.Driver");
			con = conService.getConnection();
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();
			if(!rs.next()) {
				return false;
			} else {
				String dbPassword = rs.getString(1);
				if(dbPassword.equals(password)) {
					return true;
				}
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return false;
	}

	/**
	 * Checks to see if username is in DB
	 * 
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public boolean usernameExists(String username) throws SQLException {
		// select count(*) from user where user.username = username
		PreparedStatement preparedStatement = null;
		Connection con = null;

		String sql = "select count(*) from public.user as u where u.username = ?";

		try {
			Class.forName("org.postgresql.Driver");
			con = conService.getConnection();
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {

				int count = rs.getInt(1);
				if (count == 0) {
					return false;
				} else {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return false;
	}

	/**
	 * Creates a new user
	 * 
	 * @param username
	 * @param password
	 * @throws SQLException 
	 */
	public void createAccount(String username, String password) throws SQLException {
		PreparedStatement preparedStatement = null;
		Connection con = null;

		String sql = "insert into public.user values (?, ?, NULL)";

		try {
			Class.forName("org.postgresql.Driver");
			con = conService.getConnection();
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}
	
	/**
	 * Gets steamId for user
	 * @param username
	 * @return
	 * @throws SQLException 
	 */
	public String getSteamId(String username) throws SQLException 
	{
		String id = null;
		PreparedStatement preparedStatement = null;
		Connection con = null;

		String sql = "select u.steamid from public.user as u where u.username = ?";

		try {
			Class.forName("org.postgresql.Driver");
			con = conService.getConnection();
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				id = rs.getString("steamid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		
		return id;
	}
}
