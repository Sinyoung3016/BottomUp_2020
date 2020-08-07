package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import exam.Problem;
import exam.Workbook;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.StudentDataModel;
import room.BanManager;
import thread.ClientThread;
import thread.LoadingThread;

public class StudentInfoController implements Initializable{
	@FXML
	private Button btn_Join, btn_Close;
	@FXML
	private TextField tf_StudentName;
	@FXML
	private Label lb_ClassRoomName;
	
	private boolean IsTestStarted = false;
	private Socket socket;
	public BanManager banManager;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.socket = StudentDataModel.socket;
		
		new ClientThread(StudentDataModel.socket).start(); 

		String responseMessage = null;
		try {
			String requestTokens = "GetBanManager:" + StudentDataModel.code;
			BufferedReader br = new BufferedReader(
					new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter pw = new PrintWriter(
					new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8));
			pw.println(requestTokens);
			pw.flush();
			responseMessage = br.readLine();
		} catch(IOException e) {
			e.printStackTrace();
		}
		String[] responseTokens = responseMessage.split(":");
		if(responseTokens[0].equals("GetBanManager")) {
			if(!responseTokens[1].equals("Success")) {
				System.out.println(responseTokens[1]);
			}
			else {
				//Success GetBanManager
				this.banManager = new BanManager(responseTokens[2]);
				StudentDataModel.banManager = this.banManager;
				if(this.banManager.stringOfState() == "ING") {
					this.IsTestStarted = true;
				}
			}
		}
	}
	
	public void btn_Close_Action(){
		try {
			Stage primaryStage = (Stage) btn_Close.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/Home.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/Home");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_Join_Action(){
			if(StudentDataModel.banManager.stringOfState() == "ING")
				this.IsTestStarted = true;
		
			if(IsTestStarted) {
				if(tf_StudentName.getLength() != 0) {
					StudentDataModel.studentName = tf_StudentName.getText();
					String responseMessage = null;
					try {
						String requestTokens = "GetWorkbookProblem:" + StudentDataModel.banManager.BM_num();
						BufferedReader br = new BufferedReader(
								new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
						PrintWriter pw = new PrintWriter(
								new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8));
						pw.println(requestTokens);
						pw.flush();
						responseMessage = br.readLine();
					} catch(IOException e) {
						e.printStackTrace();
					}//GetWorkbook:Success:WorkbookInfo:GetProblem:Success:FirstProblemInfo
					String[] responseTokens = responseMessage.split(":");
					if(responseTokens[0].equals("GetWorkbook")) {
						if(!responseTokens[1].equals("Success")) {
							System.out.println(responseTokens[1]);
						}
						else {
							//Success GetWorkbook
							Workbook workbook = new Workbook(responseTokens[2]);
							StudentDataModel.setWorkbook(workbook);
							if(responseTokens[3].equals("GetProblem")){
								if(!responseTokens[4].equals("Success")) { 
									System.out.println(responseTokens[4]);
								}
								else {
									Problem problem = new Problem(responseTokens[5]);
									StudentDataModel.setProblem(problem);
								}
							}
						}
					}

					try {
						Stage primaryStage = (Stage) btn_Close.getScene().getWindow();
						Parent main = FXMLLoader.load(getClass().getResource("/gui/StuWorkBook.fxml"));
						Scene scene = new Scene(main);
						primaryStage.setTitle("GuessWhat/Test");
						primaryStage.setScene(scene);
						primaryStage.show();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else {
					new Alert(Alert.AlertType.WARNING, "Invalid Name", ButtonType.CLOSE).show();
				}
				
			}
			else {
			
				new Alert(Alert.AlertType.WARNING, "Class has not been opened yet. Please wait in a moment.", ButtonType.CLOSE).show();

				/*try {
					Stage primaryStage = (Stage) btn_Close.getScene().getWindow();
					Parent main = FXMLLoader.load(getClass().getResource("/gui/StuInfoToStuWB.fxml"));
					Scene scene = new Scene(main);
					primaryStage.setTitle("GuessWhat/Loading");
					primaryStage.setScene(scene);
					primaryStage.show();
				
				} catch (Exception e) {
					e.printStackTrace();
				}*/
				
			}

	}
			
		

	

	public static Object CloseButtonActione() {
		// TODO Auto-generated method stub
		return null;
	}

}
