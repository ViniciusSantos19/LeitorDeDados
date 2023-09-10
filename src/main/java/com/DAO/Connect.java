package com.DAO;

import java.sql.Connection;
import java.sql.SQLException;

public interface Connect {
	public Connection getConnection() throws SQLException;
	public void closeConnection();
}
