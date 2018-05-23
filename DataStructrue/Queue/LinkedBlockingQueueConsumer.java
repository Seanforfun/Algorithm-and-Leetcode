package ca.mcmaster.queue.arrayblockingqueue;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueConsumer implements Runnable {
	private LinkedBlockingQueue<Integer> q;
	public LinkedBlockingQueueConsumer(LinkedBlockingQueue<Integer> q) {
		super();
		this.q = q;
	}
	@Override
	public void run() {
		try {
			while(true){
				System.out.println("Consumer: take " + q.take() + " from queue...");
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
		new Thread(new LinkedBlockingQueueConsumer(queue)).start();
		new Thread(new LinkedBlockingQueueProducer(queue)).start();
		Thread.currentThread().join();
		System.out.println("Finish...");
	}
}
