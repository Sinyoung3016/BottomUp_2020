package model;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import exam.Problem;
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
	public static BanManager banManager;
	public static Workbook workbook;
	public static Problem problem;
	public static Workbook[] WorkbookList = null;
	public static Problem [] problemList;
	public static ArrayList<Student> ip_student = new ArrayList<>();
	public static long startTime = 0;
	public static int currentPB = 0;
	public static boolean isUser = false;

	public static ObservableList<HBoxModel> ItemList_MyBanManager = FXCollections.observableArrayList();
	public static ObservableList<HBoxModel> ItemList_MyClass = FXCollections.observableArrayList();
	public static ObservableList<HBoxModel> ItemList_MyWorkBook = FXCollections.observableArrayList();
	
	public static LinkedList<Student> Students = null;
	
	public static ObservableList<Workbook> ChoiceList_MyWorkBook = FXCollections.observableArrayList();

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
	
	public static void addWBList(Workbook newWorkbook) {
		ChoiceList_MyWorkBook.add(newWorkbook);
	}

}
