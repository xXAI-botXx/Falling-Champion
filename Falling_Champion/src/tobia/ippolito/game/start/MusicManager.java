package tobia.ippolito.game.start;

public class MusicManager implements Runnable{

	private Boolean running = false;
	
	//musics
	private MusicLoader collision;
	private MusicLoader shield;
	private MusicLoader start;
	private MusicLoader check;	//für buttons,...
	private MusicLoader credits;
	private MusicLoader gameMain;
	private MusicLoader menu;
	private MusicLoader gameOver1;
	private MusicLoader gameOver2;
	private MusicLoader backToTitle;
	private MusicLoader timebeamDeath;
	private MusicLoader item1;
	private MusicLoader startA1;
	private MusicLoader startA2;
	
	public MusicManager() {
		running = true;

		//init musics
		check = new MusicLoader();
		check.loadWav("check");
		
		collision = new MusicLoader();
		collision.loadWav("collision");
		
		start = new MusicLoader();
		start.loadWav("start2");
		
		startA1 = new MusicLoader();
		startA1.loadWav("time_stop");	//timeSlowsDown
		
		startA2 = new MusicLoader();
		startA2.loadWav("chillmusic");	
		
		shield = new MusicLoader();
		shield.loadWav("shieldsActivated");
		
		menu = new MusicLoader();
		menu.loadWav("menu1");
		
		credits = new MusicLoader();
		credits.loadWav("credits1");
		
		gameMain = new MusicLoader();
		gameMain.loadWav("gameMain");
		
		gameOver1 = new MusicLoader();
		gameOver1.loadWav("gameover1");
		
		gameOver2 = new MusicLoader();
		gameOver2.loadWav("gameover2");
		
		backToTitle = new MusicLoader();
		backToTitle.loadWav("backToTitle");
		
		timebeamDeath = new MusicLoader();
		timebeamDeath.loadWav("timebeamdeath");
		
		item1 = new MusicLoader();
		item1.loadWav("item1");
	}
	
	
	public void exit() {
		running = false;
	}
	
	public void playWait() {
		startA2.playWhile();
	}
	
	public void playAlternativeStart() {
		stopAll();
		
		startA1.play();
		//startA2.playWhile();
	}
	
	public void playGame() {
		stopAll();
		
		gameMain.playWhile();
	}
	
	public void playMenu() {
		stopAll();
		
		menu.playWhile();
	}
	
	public void playCredits() {
		stopAll();
		
		credits.playWhile();
	}
	
	public void playShield() {
		shield.play();
	}
	
	public void playStart() {
		stopAll();
	
		start.playWhile();
	}
	
	public void playGameover() {
		stopAll();
		
		gameOver1.play();
		gameOver2.playWhile();
	}
	
	public void playCollision() {
		collision.play();
	}
	
	public void playBackToTitle() {
		stopAll();
		
		backToTitle.play();
		//menu.playWhile();
		startA2.playWhile();
	}
	
	public void playCheck() {
		check.play();
	}
	
	public void playTimebeamDeath() {
		timebeamDeath.play();
	}
	
	public void playItem1() {
		item1.play();
	}
	
	public void stopAll() {
		check.stop();
		start.stop();
		shield.stop();
		collision.stop();
		gameOver1.stop();
		gameOver2.stop();
		backToTitle.stop();
		gameMain.stop();
		menu.stop();
		credits.stop();
		timebeamDeath.stop();
		item1.stop();//kurez sounds überhaupt stoppen?
		startA2.stop();
	}
	
	@Override
	public void run() {
		while(running) {
			//music will be called
		}
	}

}
