package com.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class Checkin {
		
	private String userId;
	private String tweetId;
	private double lat;
	private double longi;
	private LocalDateTime data;
	private String venueId;
	private String text;
	
	
	
}
