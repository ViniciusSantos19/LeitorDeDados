package com.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectSqlite implements Connect{
	
	private String url;
	private Connection conn;
	
	public ConnectSqlite() {
		urlUsada();
		conn = null;
	}
	
	
	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}

	
	private void urlUsada() {
		this.url = "jdbc:sqlite:/home/viniciusr/Estudos_R/ProjetoBigData/database.sqlite";

	}


	@Override
	public Connection getConnection() throws SQLException { 
		if (conn == null) {
            conn = DriverManager.getConnection(this.url);
        }
        return conn;
	}
	
	@Override
	public void closeConnection() {
		if(this.conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
