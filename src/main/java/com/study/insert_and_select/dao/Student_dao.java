package com.study.insert_and_select.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.study.insert_and_select.config.DBConfig;
import com.study.insert_and_select.entity.Student;

public class Student_dao {
	private static Student_dao instance;
	
	private Student_dao() {}
	public static Student_dao getInstance() {
		if(instance == null) {
			instance = new Student_dao();
		}
		return instance;
	}
	
	public Student findStudentByName(String name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Student student = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(DBConfig.URL, DBConfig.USERNAME, DBConfig.PASSWORD);
			String sql = "select * from student_tb where student_name =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				student = Student.builder()
								.studentId(rs.getInt(1))
								.name(rs.getString(2))
								.age(rs.getInt(3))
								.build();
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			
			try {
				if(pstmt != null) {
					pstmt.close();
				} 
				if(con != null) {
					con.close();
				}
				if(rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
	}
		
		return student;
	}
	public int saveStudent(Student student) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int succesCount = 0;
		
		try {
			//외부의 클래스 파일을 객체로 불러온다. 데이터베이스 커넥터 드라이브 클래스 이름
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			con = DriverManager.getConnection(DBConfig.URL, DBConfig.USERNAME, DBConfig.PASSWORD);
			String sql = "insert into student_tb(student_name, student_age) values (?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, student.getName());
			pstmt.setInt(2, student.getAge());
			succesCount = pstmt.executeUpdate();
			
		} catch (Exception e) {
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
		
		return succesCount;
	}
	
	public List<Student> getStudentListAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Student> students = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(DBConfig.URL, DBConfig.USERNAME, DBConfig.PASSWORD);
			String sql = "select * from student_tb";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {

				Student student = Student.builder()
								.studentId(rs.getInt(1))
								.name(rs.getString(2))
								.age(rs.getInt(3))
								.build();
				students.add(student);//문제발생
				System.out.println(student);
				System.out.println(students.get(0));
			}
		} catch (Exception e) {
		} finally {	
			try {
				if(pstmt != null) {
					pstmt.close();
				} 
				if(con != null) {
					con.close();
				}
				if(rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return students;
		

	}
	
}
