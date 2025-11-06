package com.Vhospitals.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.Vhospitals.entity.FeedbackEntity;


public class FeedbackDAOImp1 {
    Connection conn;
    
	
	public FeedbackDAOImp1()
    { 
		conn=FeedbackInstance.getInstance();
    }
	public int insert(FeedbackEntity feedback)
	{
		int result=0;
	    try 
	    {
	    	PreparedStatement ps = conn.prepareStatement("INSERT INTO feed (name, age, visit_date, city, department, staff_behaviour, cleanliness, overall_rating, additional_comments) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, feedback.getName());
            ps.setInt(2, feedback.getAge());
            ps.setDate(3,  java.sql.Date.valueOf(feedback.getVisitDate()));  // Change here to set Date
            ps.setString(4, feedback.getCity());
            ps.setString(5, feedback.getDepartment());
            ps.setString(6, feedback.getStaffBehaviour());
            ps.setString(7, feedback.getCleanliness());
            ps.setString(8, feedback.getOverallRating());
            ps.setString(9, feedback.getComments());

            result=ps.executeUpdate();
            conn.close();
	    }
          
        catch (SQLException e) 
	    {
            e.printStackTrace();
	            
	    }
		return result;
    }



	
}



    