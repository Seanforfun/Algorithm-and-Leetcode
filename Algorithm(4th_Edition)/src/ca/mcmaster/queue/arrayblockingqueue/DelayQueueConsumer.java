package ca.mcmaster.queue.arrayblockingqueue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.DelayQueue;

public class DelayQueueConsumer implements Runnable {
	private DelayQueue<DelayMsg<Integer>> q;
	public DelayQueueConsumer(DelayQueue<DelayMsg<Integer>> q) {
		super();
		this.q = q;
	}
	@Override
	public void run() {
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		while (true) {
			try {
				System.out.println(df.format(new Date(System.currentTimeMillis())) + ": Consumer: take " + ((DelayMsg<Integer>)q.take()).getV() + " from queue...");
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		DelayQueue<DelayMsg<Integer>> q = new DelayQueue<>();
		new Thread(new DelayQueueConsumer(q)).start();
		new Thread(new DelayQueueProducer(q)).start();
		System.out.println("Main finish...");
	}
}
