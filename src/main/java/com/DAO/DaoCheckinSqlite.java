package com.DAO;

import java.security.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.models.Checkin;

public class DaoCheckinSqlite implements DaoCheckin{

	private ConnectSqlite conn;
	
	public DaoCheckinSqlite() {
		this.conn = new ConnectSqlite();
	}
	
	@Override
	public void createTable() {
		String createTableSQL = "CREATE TABLE IF NOT EXISTS checkin ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "userId TEXT NOT NULL,"
                + "tweetId TEXT NOT NULL,"
                + "lat REAL NOT NULL,"
                + "longi REAL NOT NULL,"
                + "data DATETIME NOT NULL,"
                + "venueId TEXT,"
                + "text TEXT)";
		
		try {
			PreparedStatement stmt = conn.getConnection().prepareStatement(createTableSQL);
			stmt.execute();
			System.out.println("tabela criada com sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("erro ao criar tabela");
		}finally {
			conn.closeConnection();
		}
		
	}

	@Override
	public void insertTable(List<Checkin> checkins) {
		String insertSQL = "INSERT INTO checkin (userId, tweetId, lat, longi, data, venueId, text) VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		

        // Prepare a instrução de inserção em lote
        try {
        	conn.getConnection().setAutoCommit(false);
			PreparedStatement stmt = conn.getConnection().prepareStatement(insertSQL);
				        
	        for (int i = 0; i < checkins.size(); i++) {
	            Checkin checkin = checkins.get(i);
	            stmt.setString(1, checkin.getUserId());
	            stmt.setString(2, checkin.getTweetId());
	            stmt.setDouble(3, checkin.getLat());
	            stmt.setDouble(4, checkin.getLongi());
	            stmt.setObject(5, checkin.getData());
	            stmt.setString(6, checkin.getVenueId());
	            stmt.setString(7, checkin.getText());

	            stmt.addBatch();

	        }
						
	       int [] insertCount = stmt.executeBatch();
            conn.getConnection().commit();
            stmt.clearBatch();
	        
			System.out.println("batch de "+insertCount.length+" adicionado com sucesso");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 System.err.println("Erro ao inserir em lote: " + e.getMessage());
		}finally {
			conn.closeConnection();
		}
		
	}

	@Override
	public List<Checkin> selectTable() {
		try {
			String selectSql = "select * from checkin limit 10";
			Statement stmt = conn.getConnection().createStatement();
			ResultSet rSet = stmt.executeQuery(selectSql);
			List<Checkin> checkins = new ArrayList<Checkin>();
			
			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

			
			while (rSet.next()) {
	            String userId = rSet.getString("userId");
	            String tweetId = rSet.getString("tweetId");
	            double lat = rSet.getDouble("lat");
	            double longi = rSet.getDouble("longi");
	            LocalDateTime data = LocalDateTime.parse( rSet.getString("data"), inputFormatter);
	            String venueId = rSet.getString("venueId");
	            String text = rSet.getString("text");

	            Checkin checkin = new Checkin(userId, tweetId, lat, longi, data, venueId, text);
	            checkins.add(checkin);
	        }
			return checkins;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			conn.closeConnection();
		}
		
		return null;
	}

}
