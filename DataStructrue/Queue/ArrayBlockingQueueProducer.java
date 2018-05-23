package ca.mcmaster.queue.arrayblockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ArrayBlockingQueueProducer implements Runnable {
	private ArrayBlockingQueue<Integer> queue;
	public ArrayBlockingQueueProducer(ArrayBlockingQueue<Integer> queue) {
		super();
		this.queue = queue;
	}
	private volatile AtomicInteger ai = new AtomicInteger(0);
	@Override
	public void run() {
		while(true){
			try {
				queue.put(ai.getAndIncrement());
				System.out.println("Producer: put " + ai.get() + " into queue...");
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
