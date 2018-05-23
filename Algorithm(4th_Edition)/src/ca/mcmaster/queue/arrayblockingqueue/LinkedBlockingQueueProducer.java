package ca.mcmaster.queue.arrayblockingqueue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class LinkedBlockingQueueProducer implements Runnable {
	private LinkedBlockingQueue<Integer> q;
	private AtomicInteger ai = new AtomicInteger();
	public LinkedBlockingQueueProducer(LinkedBlockingQueue<Integer> q) {
		super();
		this.q = q;
	}
	@Override
	public void run() {
		for(int i = 0; i < 100; i++){
			try {
				q.put(ai.get());
				System.out.println("Producer: put " + ai.getAndIncrement() + " into queue...");
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
