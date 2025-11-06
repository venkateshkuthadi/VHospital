package com.Vhospitals.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Vhospitals.dao.FeedbackDAOImp1;
import com.Vhospitals.entity.FeedbackEntity;

@WebServlet("/FeedbackServlet")

public class FeedbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         int result = 0;
        request.setCharacterEncoding("UTF-8"); 
        response.setContentType("text/html;charset=UTF-8");
           
            String name = request.getParameter("name");
            int age = Integer.parseInt(request.getParameter("age"));
            String visitDate = request.getParameter("visitDate");
            
            String city = request.getParameter("cityname");

            String[] departments = request.getParameterValues("department");
            String departmentStr = (departments != null) ? String.join(", ", departments) : "";
            String staffBehaviour = request.getParameter("rating");
           
            String cleanliness = request.getParameter("cleanliness");
            String overallRating = request.getParameter("overallRating");
            String comments = request.getParameter("feedback");
            
            FeedbackEntity f = new FeedbackEntity();
            f.setName(name);
            f.setAge(age);
            f.setVisitDate(visitDate);
            f.setCity(city);
            f.setDepartment(departmentStr);
            f.setStaffBehaviour(staffBehaviour);
            f.setCleanliness(cleanliness);
            f.setOverallRating(overallRating);
            f.setComments(comments);
           
            FeedbackDAOImp1 d = new FeedbackDAOImp1();
             result = d.insert(f);
            
            if (result > 0) {
//              response.getWriter().println("✅ Thank you! Your feedback has been submitted successfully.");
                response.sendRedirect("thankYou.html");

          }
     }
}
