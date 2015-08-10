package org.restful.books;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FormDAO {

	  public Form create(Form form) {
	        Connection c = null;
	        PreparedStatement ps = null;
	        try {
	        	
	        	System.out.println(form.getName());
	            c = ConnectionHelper.getConnection();
	            ps = c.prepareStatement("INSERT INTO user_registration (name, username, email, password) VALUES (?, ?, ?, ?)",new String[] { "ID" });
	            ps.setString(1, form.getName());
	            ps.setString(2, form.getUsername());
	            ps.setString(3, form.getEmail());
	            ps.setString(4, form.getPassword());
	            ps.executeUpdate();
	            ResultSet rs = ps.getGeneratedKeys();
	            rs.next();
	            // Update the id in the returned object. This is important as this value must be returned to the client.
	            int id = rs.getInt(1);
	            form.setId(id);
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException(e);
			} finally {
				ConnectionHelper.close(c);
			}
	        return form;
	    }
	
	
}
