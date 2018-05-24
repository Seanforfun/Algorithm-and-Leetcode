package ca.mcmaster.queue.arrayblockingqueue;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentLinkQueueProducer implements Runnable{
	private ConcurrentLinkedQueue<Integer> q;
	public ConcurrentLinkQueueProducer(ConcurrentLinkedQueue<Integer> q) {
		super();
		this.q = q;
	}
	@Override
	public void run() {
		AtomicInteger al = new AtomicInteger(0);
		while(true){
			q.offer(al.get());
			System.out.println("Producer: put " + al.getAndIncrement() + " into queue...");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
