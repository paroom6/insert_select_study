package com.study.insert_and_select.servet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes.Name;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.cj.jdbc.Driver;
import com.study.insert_and_select.dao.Student_dao;
import com.study.insert_and_select.entity.Student;


@WebServlet("/data/addition")
public class DataInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DataInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder builder = new StringBuilder();
		String readData = null;
//		String builder2 = "";
		BufferedReader reader = request.getReader();// Buffer 한번에 처리 
		while ((readData = request.getReader().readLine()) != null) {
			builder.append(readData);//캡에 걸리기 전에는 새로 할당하지 않는다.
//			builder2 += readData; 대입하기 때문에 매번 새로 데이터를 할당해야한다.
		}
		//Json -> map
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Map<String, Object> map = gson.fromJson(builder.toString(), Map.class);
		System.out.println(map);
		System.out.println(map.get("name"));
		System.out.println(map.get("age"));
		
		//Json -> Entity 객체 보통 재활용이 용이하게 하기 위해 Entity를 사용
		Student student = gson.fromJson(builder.toString(), Student.class);
		
		Student_dao dao = Student_dao.getInstance(); 
		
		Student findStudent = dao.findStudentByName(student.getName());
		
		if(findStudent != null) {
			Map<String, Object> errorMap = new HashMap<>();
			errorMap.put("errorMasseage","이미 등록된 이름입니다.");
			
			response.setStatus(400);
			response.setContentType("application/json");
			response.getWriter().println(gson.toJson(errorMap));
			return;
		}
		int succesCount = dao.saveStudent(student);
		Map<String, Object> responseMap = new HashMap();
		responseMap.put("status",201);
		responseMap.put("data","응답데이터");
		responseMap.put("succesCount",succesCount);
		response.setStatus(201);
		PrintWriter writer = response.getWriter();
		response.setContentType("application/json");
		writer.println(gson.toJson(responseMap));
		

	}

}


