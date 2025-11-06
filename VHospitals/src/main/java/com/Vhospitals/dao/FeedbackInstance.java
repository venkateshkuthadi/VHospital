package com.Vhospitals.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class FeedbackInstance 
{
	
		private static  FeedbackInstance sql=new FeedbackInstance();
		Connection conn;
		private FeedbackInstance()
		{
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_feed", "root","root");
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			
		}
		public static Connection getInstance()
		{
			return sql.conn;
		}
		public static void main(String[] args)
		{
			System.out.println(FeedbackInstance.getInstance());
		}
	}
