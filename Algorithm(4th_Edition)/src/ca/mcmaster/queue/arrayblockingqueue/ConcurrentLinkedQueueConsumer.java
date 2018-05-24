package ca.mcmaster.queue.arrayblockingqueue;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueConsumer implements Runnable {
	private ConcurrentLinkedQueue<Integer> q;
	public ConcurrentLinkedQueueConsumer(ConcurrentLinkedQueue<Integer> q) {
		super();
		this.q = q;
	}
	@Override
	public void run() {
		try {
			while(true){
				System.out.println("Consumer: get " + q.poll() + " from queue...");
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ConcurrentLinkedQueue<Integer> q = new ConcurrentLinkedQueue<>();
		Thread producer = new Thread(new ConcurrentLinkQueueProducer(q));
		producer.start();
		producer.join(100);
		new Thread(new ConcurrentLinkedQueueConsumer(q)).start();
	}
}
