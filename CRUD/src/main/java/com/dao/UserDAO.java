package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    private static final String INSERT = "INSERT INTO `user`( `name`, `Class`, `isTeacher`, `level`) VALUES (?,?,?,?)";
    private static final String SELECT_BY_ID = "SELECT `name`, `Class`, `isTeacher`, `level` FROM `user` WHERE `id`=?";
    private static final String SELECT_ALL = "SELECT * FROM `user`";
    private static final String DELETE = "DELETE FROM `user` WHERE `id`=?";
    private static final String UPDATE = "UPDATE `user` SET `name`=?, `Class`=?, `isTeacher`=?, `level`=? WHERE `id`=?";

    // Establish the connection to the database
    protected Connection getConnection() {
        Connection con = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Connect to the MySQL database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/peerpulse", "root", "");
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Driver Class Not Found: " + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }



    // Define the methods for CRUD operations here:
    // createUser, updateUser, selectUserById, selectAllUsers, deleteUser, etc.
    
    public void insertUser(User user) throws SQLException{
    	try(Connection connection= getConnection();
    			PreparedStatement ps = connection.prepareStatement(INSERT)){
    		
    		ps.setString(1,user.getName());
    		ps.setString(2,user.getGrade());
    		ps.setBoolean(3,user.isTeacher());
    		ps.setInt(4, user.getLevel());
    		ps.executeUpdate();
    	}
    		catch (Exception e) {
				e.printStackTrace();
			}
    		
    	
    }
    //Update
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE)) {

            ps.setString(1, user.getName());        // First placeholder (name)
            ps.setString(2, user.getGrade());   // Second placeholder (class)
            ps.setBoolean(3, user.isTeacher());  // Third placeholder (isTeacher)
            ps.setInt(4, user.getLevel());          // Fourth placeholder (level)
            ps.setInt(5, user.getId());             // Fifth placeholder (id for WHERE clause)

            // Execute the update and check if any row was affected
            rowUpdated = ps.executeUpdate() > 0;
        }
        return rowUpdated;
    }
    
    //select user by id
    public User selectUser(int id)
    {
    	User user = null;
    	try(Connection con = getConnection();
    			PreparedStatement ps = con.prepareStatement(SELECT_BY_ID);)
    	{
    		ps.setInt(1, id);
    		System.out.println(ps);
    		
    		ResultSet rs= ps.executeQuery();
    		while(rs.next())
    		{
    			String name = rs.getString("name");
    			String grade  =rs.getString("Class");
    			boolean isTeacher = rs.getBoolean("isTeacher");
    			int level = rs.getInt("level");
    			user = new User(name,grade,isTeacher,level);
    		}
    		
    		
    	}
    	catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return user;
    }
    
    //select all users
    public List<User> selectAllUser()
    {
    	List<User> users = new ArrayList<>();
    	try(Connection con = getConnection();
    			PreparedStatement ps = con.prepareStatement(SELECT_ALL);)
    	{
    		System.out.println(ps);
    		
    		
    		ResultSet rs= ps.executeQuery();
    		while(rs.next())
    		{
    			String name = rs.getString("name");
    			String grade  =rs.getString("Class");
    			boolean isTeacher = rs.getBoolean("isTeacher");
    			int level = rs.getInt("level");
    			users.add(new User(name,grade,isTeacher,level));
    		}
    		
    		
    	}
    	catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return users;
    }
    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted = false;  // Initialize the variable
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, id);  // Set the id for deletion
            rowDeleted = ps.executeUpdate() > 0;  // Execute the update and check if any rows were deleted

        } catch (SQLException e) {
            e.printStackTrace();  // Print exception if something goes wrong
        }
        return rowDeleted;  // Return the result
    }

    
   


}
