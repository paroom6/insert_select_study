package com.study.insert_and_select.servet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.study.insert_and_select.dao.Student_dao;
import com.study.insert_and_select.entity.Student;


@WebServlet("/data/list")
public class DataSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DataSelectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("조회요청");
		Student_dao dao = Student_dao.getInstance();
		Gson gson = new Gson();
		List<Student> students = dao.getStudentListAll();
		
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("data", students);

		response.setContentType("application/json");
		response.setStatus(200);
		response.getWriter().println(gson.toJson(responseMap));

		
	}

}

