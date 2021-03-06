package model;

import java.net.Socket;
import java.util.List;

import exam.Problem;
import exam.Workbook;
import javafx.collections.ObservableList;
import room.BanManager;
import user.Student;

public class StudentDataModel {
	public static Socket socket = null;
	public static BanManager banManager;
	public static String studentName = null;
	public static String code = null;
	public static Problem problem = null;
	public static int currentPB = 0;
	public static Student student = null;
	public static boolean[] hasAnswer = null;
	public static String studentIp = null;
	public static Problem[] problemList = null;
	
	public static Workbook workbook;
	
	public static ObservableList<Problem> ItemList_Problem;
	
	public static void setWorkbook(Workbook newWorkbook) {
		workbook = newWorkbook;
	}
	
	public static void setProblem(Problem newProblem) {
		problem = newProblem;
	}
	
	public static String tokenStudentData() {
		StringBuilder sb = new StringBuilder("");
		sb.append(banManager.ban_num() + ":");
		sb.append(banManager.BM_num() + ":");
		sb.append(studentName);
		System.out.println("학생" + studentName +"님이 문제를 제출하였습니다." );
		return new String(sb);
	}
	


	
}
