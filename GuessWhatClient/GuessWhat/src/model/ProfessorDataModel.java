package model;

import java.net.Socket;
import java.util.Arrays;

import exam.Result;
import exam.Workbook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import room.Ban;
import room.BanManager;
import user.Professor;
import user.Student;

public class ProfessorDataModel {
	// public final static String SERVER_IP = "";
	// public final static int PORT = null;

	public static Socket socket = null;
	public static String ID = null;
	public static Professor professor;

	// for test
	public static Ban ban = new Ban(1, "컴퓨터프로그래밍");
	public static Workbook workbook = new Workbook(new String[]{"workbook", "2"});
	public static BanManager banManager = new BanManager(1, "1차시", "1111", workbook);
	
	public static Result num1 = new Result("1", "MultipleChoice", "1", "문정현", "1");
	public static Result num2 = new Result("2", "MultipleChoice", "1", "창문정현", "2");
	
	public static Result num3 = new Result("1", "ShortAnswer", "2", "문정현", "apple");
	public static Result num4 = new Result("2", "ShortAnswer", "2", "창문정현", "banana");
	
	public static Student stu1 = new Student("workbook", "1","문정현","1:apple");
	public static Student stu2 = new Student("workbook", "2","창문정현","2:banana");
	//for test
	
	public static ObservableList<HBoxModel> ItemList_MyBanManager = FXCollections.observableArrayList(banManager.getBanManager());
	public static ObservableList<HBoxModel> ItemList_MyClass = FXCollections.observableArrayList(ban.getBan());
	public static ObservableList<HBoxModel> ItemList_MyWorkBook = FXCollections.observableArrayList(workbook.getWorkbook(1));

	public static ObservableList<Student> ItemList_Students = FXCollections.observableArrayList(stu1, stu2);
	public static ObservableList<Result> ItemList_Results1 = FXCollections.observableArrayList(num1, num2); 
	public static ObservableList<Result> ItemList_Results2 = FXCollections.observableArrayList(num3, num4);

	public static void addClass(Ban newBan) {
		ItemList_MyClass.add(newBan.getBan());
	}

	public static void removeClass(Ban newBan) {
		ItemList_MyClass.remove(newBan.getBan());
	}

	public static void addWorkBook(Workbook newWorkBook) {
		ItemList_MyWorkBook.add(newWorkBook.getWorkbook(1));
	}

	public static void removeWorkBook(Workbook newWorkBook) {
		ItemList_MyWorkBook.remove(newWorkBook.getWorkbook(1));
	}

	public static void addBanManager(BanManager newBanManager) {
		ItemList_MyBanManager.add(newBanManager.getBanManager());
	}

	public static void removeBanManager(BanManager newBanManager) {
		ItemList_MyBanManager.remove(newBanManager.getBanManager());
	}

}
