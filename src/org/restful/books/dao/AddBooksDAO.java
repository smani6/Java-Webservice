package org.restful.books.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.restful.books.beans.AddBooks;
import org.restful.books.connectionHelper.NewConnectionHelper;;


public class AddBooksDAO {

   
    public AddBooks create(AddBooks addbooks) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
        	
        	System.out.println(addbooks.getTitle());
            c = NewConnectionHelper.getConnection();
            ps = c.prepareStatement("INSERT INTO addbooks (title, author, description, pub_year, pub_month, pub_day, publisher, isbn13, isbn10, pages) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",new String[] { "ID" });
            ps.setString(1, addbooks.getTitle());
            ps.setString(2, addbooks.getAuthor());
            ps.setString(3, addbooks.getDescription());
            ps.setString(4, addbooks.getPub_year());
            ps.setString(5, addbooks.getPub_month());
            ps.setString(6, addbooks.getPub_day());
            ps.setString(7, addbooks.getPublisher());
            ps.setString(8, addbooks.getIsbn13());
            ps.setString(9, addbooks.getIsbn10());
            ps.setString(10, addbooks.getPages());
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            // Update the id in the returned object. This is important as this value must be returned to the client.
            int id = rs.getInt(1);
            addbooks.setId(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			NewConnectionHelper.close(c);
		}
        return addbooks;
    }

  
}
