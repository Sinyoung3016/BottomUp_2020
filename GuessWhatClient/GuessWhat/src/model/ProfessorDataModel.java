package model;

import java.net.Socket;
import java.util.Arrays;

import exam.StuNumResult;
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
	public static Ban ban;
	public static BanManager[] banManagerList;

	// for test
	public static Ban ban = new Ban(1, 1, "컴퓨터프로그래밍1");
	public static Ban ban2 = new Ban(1, 2, "컴퓨터프로그래밍2");
	public static Workbook workbook = new Workbook(new String[] { "1", "1", "workbook", "2" });
	public static BanManager banManager = new BanManager(1, 1, 1, "1차시", "1111", "workbook");
	public static BanManager banManager1 = new BanManager(1, 1, 2, "2차시", "2222", "workbook");

	public static StuNumResult num1 = new StuNumResult(1, 1, 1, "workbook", 1, "MultipleChoice", "문정현", "1");
	public static StuNumResult num2 = new StuNumResult(1, 1, 1, "workbook", 1, "MultipleChoice", "창문정현", "2");

	public static StuNumResult num3 = new StuNumResult(1, 1, 1, "workbook", 2, "ShortAnswer", "문정현", "apple");
	public static StuNumResult num4 = new StuNumResult(1, 1, 1, "workbook", 2, "ShortAnswer", "창문정현", "banana");

	public static Student stu1 = new Student(1, 1, 1, 1, "문정현", "1:apple");
	public static Student stu2 = new Student(1, 1, 1, 2, "창문정현", "2:banana");
	// for test

	public static ObservableList<HBoxModel> ItemList_MyBanManager = FXCollections
			.observableArrayList(banManager.getBanManager(1));
	public static ObservableList<HBoxModel> ItemList_MyClass = FXCollections.observableArrayList(ban.getBan(1),
			ban2.getBan(2));
	public static ObservableList<HBoxModel> ItemList_MyWorkBook = FXCollections
			.observableArrayList(workbook.getWorkbook(1));

	public static ObservableList<Student> ItemList_Students = FXCollections.observableArrayList(stu1, stu2);
	public static ObservableList<StuNumResult> ItemList_Results = FXCollections.observableArrayList(num1, num2);
	public static ObservableList<StuNumResult> ItemList_Results2 = FXCollections.observableArrayList(num3, num4);

	public static void addClass(int n, Ban newBan) {
		ItemList_MyClass.add(newBan.getBan(n));
	}

	public static void removeClass(int n, Ban newBan) {
		ItemList_MyClass.remove(newBan.getBan(n));
}

	public static void addWorkBook(int n, Workbook newWorkBook) {
		ItemList_MyWorkBook.add(newWorkBook.getWorkbook(n));
	}

	public static void removeWorkBook(int n, Workbook newWorkBook) {
		ItemList_MyWorkBook.remove(newWorkBook.getWorkbook(n));
		}

	public static void addBanManager(int n, BanManager newBanManager) {
		ItemList_MyBanManager.add(newBanManager.getBanManager(n));
	}

	public static void removeBanManager(int n, BanManager newBanManager) {
		ItemList_MyBanManager.remove(newBanManager.getBanManager(n));
}

}
