package com.models;

import java.util.Vector;


public class converter { 
	private StrategyPattern strategy;

	   public converter(StrategyPattern strategy){
	      this.strategy = strategy;
	   }

	   public void executeStrategy(int userId, int notifyID,int  checkInID){
	       strategy.update(userId, notifyID, checkInID);
	   }

}
