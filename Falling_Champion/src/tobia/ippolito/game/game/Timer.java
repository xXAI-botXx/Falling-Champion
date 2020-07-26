package tobia.ippolito.game.game;

public class Timer implements Runnable{


	private long begin = 0;
	private long now;
	private Boolean isWorking = false;
	private boolean running = false, shouldWork = false;
	private long howLong, add = 0;
	private Boolean pause = false;
	
	public Timer(){
		
	}
	
	public void waitPls(){
		isWorking = true;
		
		now = System.currentTimeMillis();
		while(now - begin < howLong && !pause) {
			now = System.currentTimeMillis();
		}
		
		isWorking = false;
		shouldWork = false;
	}
	
	public Boolean isWorking() {
		return isWorking;
	}
	
	public void setTime(long t) {
		if(isWorking) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					while(isWorking) {
						System.out.println("Waiting for Timer");
					}
					begin += t;
					pause = false;
					shouldWork = true;
				}
			});
			thread.start();
		}else {
			begin += t;
			pause = false;
			shouldWork = true;
		}
	}
	
	public void setHowLong(long t) {
		howLong = t;
		begin = System.currentTimeMillis();
	}
	
	public void setPause() {
		pause = true;
	}
	
	public void shouldWork() {
		shouldWork = true;
	}
	
	public long getTimeTaken() {
		return (now - begin);
	}
	
	public long getRestOfTime() {
		return howLong;
	}
	
	public Boolean hasNoPause() {
		if(pause) {
			return false;
		}else {
			return true;
		}
	}
	
	public void runningOff() {
		running = false;
	}

	@Override
	public void run() {
		running = true;
		while(running) {
			if(shouldWork) {
				waitPls();
			}
			System.out.println("Timer is waiting");
		}
	}
}
