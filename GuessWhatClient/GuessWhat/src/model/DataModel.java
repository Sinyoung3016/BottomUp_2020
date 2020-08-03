package model;

import java.net.Socket;

import exam.Workbook;
import javafx.collections.ObservableList;
import room.Ban;
import room.BanManager;
import user.Professor;

public class DataModel {
	//public final static String SERVER_IP = "";
	//public final static int PORT = null;
	
	public static Socket socket = null;
	public static String ID = null;
	public static Professor professor;
	
	public static ObservableList<HBoxModel> ItemList_MyClass;
	public static ObservableList<HBoxModel> ItemList_MyWorkBook;
	public static ObservableList<HBoxModel> ItemList_MyBanManager;
	
	public static void addClass(Ban newBan) {
		ItemList_MyClass.add(newBan.getBan());
	}
	public static void removeClass(Ban newBan) {
		ItemList_MyClass.remove(newBan.getBan());
	}
	
	
	public static void addWorkBook(Workbook newWorkBook) {
		ItemList_MyWorkBook.add(newWorkBook.getWorkbook());
	}
	public static void removeWorkBook(Workbook newWorkBook) {
		ItemList_MyWorkBook.remove(newWorkBook.getWorkbook());
	}
	
	
	public static void addBanManager(BanManager newBanManager) {
		ItemList_MyBanManager.add(newBanManager.getBanManager());
	}
	public static void removeBanManager(BanManager newBanManager) {
		ItemList_MyBanManager.remove(newBanManager.getBanManager());
	}
	
	
}
