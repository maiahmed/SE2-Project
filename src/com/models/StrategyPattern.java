package com.models;

import java.util.Vector;

public interface StrategyPattern {
	/*stmt.setInt(1, userId);
			stmt.setInt(2, notifyID);
			stmt.setInt(3, 2);
			stmt.setInt(4, checkInID);*/  
	public void update(int userId,int notifyID,int checkInID);


}
