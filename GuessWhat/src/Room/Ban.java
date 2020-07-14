package room;
//BanManager의 모음
public class Ban {
	private int ban_num;
	private String ban_name;
	private BanManager[] banManagerSet;
	
	//Constructor start
	public Ban() {
		banManagerSet = new BanManager[10];
	}
	//Constructor end
	
	//Getter start
	public int ban_num() { return this.ban_num;}
	public String ban_name() { return this.ban_name; }
	public BanManager[] banManagerSet() { return this.banManagerSet; }
	//Getter end
}
