package ca.mcmaster.queue.arrayblockingqueue;

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueConsumer implements Runnable {

	private PriorityBlockingQueue<Integer> q;
	public PriorityBlockingQueueConsumer(PriorityBlockingQueue<Integer> q) {
		super();
		this.q = q;
	}
	@Override
	public void run() {
		while(true){
			try {
				System.out.println("Consumer: take " + q.take() + " from queue...");
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
		Thread producer = new Thread(new PriorityBlockingQueueProducer(q));
		producer.start();
		producer.join(200);
		new Thread(new PriorityBlockingQueueConsumer(q)).start();
	}
}
