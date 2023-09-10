package com;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.DAO.DaoCheckin;
import com.DAO.DaoCheckinSqlite;
import com.models.Checkin;

public class Principal {
	public static void main(String[] args) {
		//lerArquivo();
		DaoCheckin dao = new DaoCheckinSqlite();
		List<Checkin> checkins = dao.selectTable();
		
		checkins.stream().forEach(a -> {
			System.out.println("**************************");
			System.out.println(a.getUserId());
			System.out.println("\n");
			System.out.println(a.getText());
			System.out.println("\n");
			System.out.println(a.getVenueId());
			System.out.println("\n");
			System.out.println(a.getLat());
			System.out.println("\n");
			System.out.println(a.getLongi());
			System.out.println("**************************");
		});
		
		//DaoCheckin dao = new DaoCheckinSqlite();
		//dao.createTable();
	}
	
	
	public static void lerArquivo() {
		try {
			String inputFile = "/home/viniciusr/Estudos_R/ProjetoBigData/checkin_data_foursquare.txt";
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			
			int BATCH_SIZE = 1000;
			List<Checkin> checkins = new ArrayList<Checkin>();
			String line;
			while ((line = reader.readLine()) != null) {
				
				String[] fields = line.split("\t");

				
				if (fields.length >= 7) {
					Checkin checkin = createCheckinFromParts(fields);
					checkins.add(checkin);
					
					if(checkins.size() == BATCH_SIZE) {
						insertBathc(checkins);
						checkins.clear();
					}
					
				}
				
				
			}
			
			if (!checkins.isEmpty()) {
                insertBathc(checkins);
            }
			
			reader.close();
			
			System.out.println("todos os dados inseridos com sucesso");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static void insertBathc(List<Checkin> checkins) {
		DaoCheckin dao = new DaoCheckinSqlite();
		dao.insertTable(checkins);
		
	}


	private static Checkin createCheckinFromParts(String[] parts) {
      
        Checkin checkin = new Checkin();
        checkin.setUserId(parts[0]);
        checkin.setTweetId(parts[1]);
        checkin.setLat(Double.parseDouble(parts[2]));
        checkin.setLongi(Double.parseDouble(parts[3]));
       
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(parts[4], formatter);
         checkin.setData(dateTime);
         checkin.setVenueId(parts[5]);
         checkin.setText(parts[6]);


        return checkin;
    }
	
}
