package com.study.insert_and_select.servet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
		System.out.println(student.getName());
		System.out.println(student.getAge());
		
		Connection con = null;	//데이터베이스 연결
		String SQL = null;		//SQL 쿼리문 작성
		PreparedStatement pstmt = null;//SQL 쿼리문 입력
		int succesCount = 0; 	//SQL 실행결과(성공횟수)
		
		try {
			//외부의 클래스 파일을 객체로 불러온다. 데이터베이스 커넥터 드라이브 클래스 이름
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://mysql-db.cr0mkw8s2iwu.ap-northeast-2.rds.amazonaws.com/db_study";
			String username = "aws";
			String password = "1q2w3e4r!!";
			
			con = DriverManager.getConnection(url, username, password);//
			SQL = "insert into student_tb(student_name, student_age) values (?, ?)";
			pstmt = con.prepareStatement(SQL);
			pstmt.setString(1, student.getName());
			pstmt.setInt(2, student.getAge());
			succesCount = pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
				try {
					if(pstmt != null) {
						pstmt.close();
					} 
					if(con != null) {
						con.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			
		}
		Map<String, Object> responseMap = new HashMap();
		responseMap.put("status",201);
		responseMap.put("data","응답데이터");
		responseMap.put("succesCount",succesCount);
		
		PrintWriter writer = response.getWriter();
		response.setContentType("application/json");
		writer.println(gson.toJson(responseMap));
		

	}

}


