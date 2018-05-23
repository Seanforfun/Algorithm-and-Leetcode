package ca.mcmaster.queue.arrayblockingqueue;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueProducer implements Runnable {
	private PriorityBlockingQueue<Integer> q;
	public PriorityBlockingQueueProducer(PriorityBlockingQueue<Integer> q) {
		super();
		this.q = q;
	}
	@Override
	public void run() {
		while(true){
			Random random = new Random();
			int nextInt = random.nextInt(100);
			q.put(nextInt);
			System.out.println("Producer: put " + nextInt + " into queue...");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
