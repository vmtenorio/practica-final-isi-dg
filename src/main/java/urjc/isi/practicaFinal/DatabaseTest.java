package urjc.isi.practicaFinal;

import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;
import org.junit.*;
import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class DatabaseTest {
	
	private static Database db;
	private static Statement statement;
	private static Connection connection;
	
	
	/*@Before
	public void SetUp() throws 
		ClassNotFoundException, SQLException {
		//port(Main.getHerokuAssignedPort());
		
		
		
		// Connect to SQLite sample.db database
		// connection will be reused by every query in this simplistic example
		Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
		
		// Prepare SQL to create table
		Statement statement = connection.createStatement();
		statement.setQueryTimeout(30); // set timeout to 30 sec.
		statement.executeUpdate("drop table if exists films");
		statement.executeUpdate("create table films (film string, actor string)");
		
		Database db = new Database(connection);
		
	}*/
	
	@Before
	public void SetUp() {
		
		connection = null;
		
	    try
	    {
	      // create a database connection
	      connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
	      statement = connection.createStatement();
	      statement.setQueryTimeout(30);  // set timeout to 30 sec.
	      db = new Database(connection);
	      statement.executeUpdate("drop table if exists films");
	      statement.executeUpdate("create table films (title string, year string)");
	    }
	    catch(SQLException e)
	    {
	      // if the error message is "out of memory", 
	      // it probably means no database file is found
	      System.err.println(e.getMessage());
	    }
	}
	
	@After      // Tear down - Called after every test method.
	public void tearDown()
	{
          try
          {
            if(connection != null)
              connection.close();
          }
          catch(SQLException e)
          {
            // connection close failed.
            System.err.println(e);
          }
	}
	
	
	
	//Test para insertFilm
	@Test
	public void test1() throws SQLException {
		
		db.insertFilm("Disney's Mouseworks Spaceship (1999)");
		db.insertFilm("Dr. Goldfoot and the Bikini Machine (1965)");
		db.insertFilm("Doll's House, A (1973 I)");
		
		ResultSet rs = statement.executeQuery("select * from films");
	    /*while(rs.next())
	    {
	      // read the result set
	      System.out.println("title = " + rs.getString("title"));
	      System.out.println("year = " + rs.getInt("year") + "\n");
	    }*/
	    rs.next();
	    assertEquals("Disney's Mouseworks Spaceship", rs.getString("title"));
	    assertEquals("1999", rs.getString("year"));
	    rs.next();
	    assertEquals("Dr. Goldfoot and the Bikini Machine", rs.getString("title"));
	    assertEquals("1965", rs.getString("year"));
	}
	
	@Test
	public void test2() throws SQLException {
		
		db.insertFilm("Disney's Mouseworks Spaceship");
		
		ResultSet rs = statement.executeQuery("select * from films");
	    /*while(rs.next())
	    {
	      // read the result set
	      System.out.println("title = " + rs.getString("title"));
	      System.out.println("year = " + rs.getInt("year") + "\n");
	    }*/
	    assertEquals("Disney's Mouseworks Spaceship", rs.getString("title"));
	    assertEquals("0", rs.getString("year"));
	}
	
	
	@Test
	public void test3() throws SQLException {
		db.insertFilm("");
		
		ResultSet rs = statement.executeQuery("select * from films");
	    /*while(rs.next())
	    {
	      // read the result set
	      System.out.println("title = " + rs.getString("title"));
	      System.out.println("year = " + rs.getInt("year") + "\n");
	    }*/
	    assertEquals("", rs.getString("title"));
	    assertEquals("0", rs.getString("year"));
	}
	
	
	@Test
	public void test4() throws SQLException {
		db.insertFilm("(1999) Disney's Mouseworks Spaceship");
		
		ResultSet rs = statement.executeQuery("select * from films");
	    /*while(rs.next())
	    {
	      // read the result set
	      System.out.println("title = " + rs.getString("title"));
	      System.out.println("year = " + rs.getInt("year") + "\n");
	    }*/
	    assertEquals("(1999) Disney's Mouseworks Spaceship", rs.getString("title"));
	    assertEquals("0", rs.getString("year"));
	}
	
	@Test
	public void test5() throws SQLException {
		
	    try {
	    	db.insertFilm(null);
			ResultSet rs = statement.executeQuery("select * from films");
	    } catch (NullPointerException e) {
	       return;
	    }
	    fail ("NullPointerException expected");
	}

}
