package org.restful.books;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Christophe Coenraets
 */
public class BookDAO {

    public List<Book> findAll() {
        List<Book> list = new ArrayList<Book>();
        Connection c = null;
    	String sql = "SELECT * FROM test ORDER BY book_name";
        try {
            c = ConnectionHelper.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                list.add(processRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return list;
    }

    
    public List<Book> findByName(String book_name) {
        List<Book> list = new ArrayList<Book>();
        Connection c = null;
    	String sql = "SELECT * FROM test as e " +
			"WHERE UPPER(book_name) LIKE ? " +	
			"ORDER BY book_name";
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "%" + book_name.toUpperCase() + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(processRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return list;
    }
    
    public Book findById(int id) {
    	String sql = "SELECT * FROM test WHERE id = ?";
        Book Book = null;
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Book = processRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return Book;
    }

    public Book save(Book book)
	{
		return book.getId() > 0 ? update(book) : create(book);
	}    
    
    public Book create(Book book) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
        	
        	System.out.println(book.getBook_name());
            c = ConnectionHelper.getConnection();
            ps = c.prepareStatement("INSERT INTO test (book_name, pages, date, author) VALUES (?, ?, ?, ?)",new String[] { "ID" });
            ps.setString(1, book.getBook_name());
            ps.setString(2, book.getPages());
            ps.setString(3, book.getDate());
            ps.setString(4, book.getAuthor());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            // Update the id in the returned object. This is important as this value must be returned to the client.
            int id = rs.getInt(1);
            book.setId(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return book;
    }

    public Book update(Book book) {
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("UPDATE test SET book_name=?, pages=?, date=?, author=?, WHERE id=?");
            ps.setString(1, book.getBook_name());
            ps.setString(2, book.getPages());
            ps.setString(3, book.getDate());
            ps.setString(4, book.getAuthor());
            ps.setInt(5, book.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return book;
    }

    public boolean remove(int id) {
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM test WHERE id=?");
            ps.setInt(1, id);
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
    }

    protected Book processRow(ResultSet rs) throws SQLException {
        Book Book = new Book();
        Book.setId(rs.getInt("id"));
        Book.setBook_name(rs.getString("book_name"));
        Book.setPages(rs.getString("pages"));
        Book.setDate(rs.getString("date"));
        Book.setAuthor(rs.getString("author"));
        
        return Book;
    }
    
}
