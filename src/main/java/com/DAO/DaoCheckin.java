package com.DAO;

import java.util.List;

import com.models.Checkin;

public interface DaoCheckin {
	public void createTable();
	public void insertTable(List<Checkin> checkins);
	public List<Checkin> selectTable();
}
