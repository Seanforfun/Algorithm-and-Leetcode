package ca.mcmaster.queue.arrayblockingqueue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.DelayQueue;

public class DelayQueueProducer implements Runnable {
	private DelayQueue<DelayMsg<Integer>> q; 
	
	public DelayQueueProducer(DelayQueue<DelayMsg<Integer>> q) {
		super();
		this.q = q;
	}

	@Override
	public void run() {
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		for(int i = 0; i < 10; i++){
			q.offer(new DelayMsg<Integer>(System.currentTimeMillis() + 10000, System.currentTimeMillis(), i));
			System.out.println(df.format(new Date(System.currentTimeMillis())) + ": Producer: put " + i + " into queue...");
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
