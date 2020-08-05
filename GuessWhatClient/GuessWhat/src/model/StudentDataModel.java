package model;

import java.net.Socket;

import room.BanManager;

public class StudentDataModel {
	public static Socket socket = null;
	public static BanManager banManager;
	public static String studentName = null;
	public static BanManager.State state = null;
	public static String code = null;

}
