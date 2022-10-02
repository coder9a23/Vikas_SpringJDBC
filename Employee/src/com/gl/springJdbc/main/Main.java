package com.gl.springJdbc.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.gl.springJdbc.entity.Employee;

public class Main {

			//JdbcTemplate is spring framework jdbc Template class
			//its main work is to stabalish a connection or to understand to perform jdbc with our spring application
			static JdbcTemplate jdbcTemplateObj;
	 
		
			
			//Driver data source is our spring framework jdbc class 
			// and its responsblity is to get the data source via our application
			//to configure the database
			static SimpleDriverDataSource dataSourceObj;

			
			//1. Creating connection
			// Configure the database 
			static String USERNAME = "root";
			static String PASSWORD = "vikas12345@";
			static String URL = "jdbc:mysql://localhost:3306/sprungjdbc";
			
			public static SimpleDriverDataSource getDatabaseConnection()  {
				//2. Inform the spring code about database 
				dataSourceObj = new SimpleDriverDataSource();
				
				
				try {    
					//3. Setting Driver class
					dataSourceObj.setDriver(new com.mysql.cj.jdbc.Driver());
					dataSourceObj.setUrl(URL);
					dataSourceObj.setUsername(USERNAME);
					dataSourceObj.setPassword(PASSWORD);
				} catch(SQLException sqlException) {
					sqlException.printStackTrace();
				}
				return dataSourceObj;
			}
			
			
			
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		jdbcTemplateObj = new JdbcTemplate(getDatabaseConnection());
		
		//4. SQL insert query
		if(null!=jdbcTemplateObj) {
			
			String sqlInsertQuery = "INSERT INTO Employee (name, email, address, phoneNo) VALUES (?, ?, ?, ?)";
			for(int i=1; i<5; i++) {
				jdbcTemplateObj.update(sqlInsertQuery, "Employee " + i, "Employee" + i +"@greatlearning.in", "Gurugram", "0123456789");	 
			}
		}
		 
		//5. SQL update
		//String sqlUpdateQuery = "UPDATE Employee set email=? where name=?";
		  //jdbcTemplateObj.update(sqlUpdateQuery, "admin@greatlearning.com", "Employee 2");
		//6. SQL read 
		  String sqlSelect = " SELECT name,email,address,phoneNo FROM Employee";
		  List listEmployee=jdbcTemplateObj.query(sqlSelect,new RowMapper(){
			  public Employee mapRow(ResultSet result, int rowNum) throws SQLException{
				  Employee employeeObj=new Employee();
				  employeeObj.setName(result.getString("name"));
				  employeeObj.setEmail(result.getString("email"));
				  employeeObj.setAddress(result.getString("address"));
				  employeeObj.setPhoneno(result.getString("phoneNo"));
				  return employeeObj;
			  }
		  });
		  System.out.println(listEmployee);
		//7. SQL delete
		  String sqlDelete="DELETE from Employee WHERE name=?";
		  jdbcTemplateObj.update(sqlDelete,"Employee1");
		//8. SQL disconnect
		
	}

}
