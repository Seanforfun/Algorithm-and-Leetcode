package ca.mcmaster.queue.arrayblockingqueue;

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueConsumer implements Runnable {
	private ArrayBlockingQueue<Integer> queue;
	public ArrayBlockingQueueConsumer(ArrayBlockingQueue<Integer> queue) {
		super();
		this.queue = queue;
	}
	@Override
	public void run() {
		try {
			for(int i = 0; i < 100; i++){
				Thread.currentThread().join(10);
				System.out.println("Consumer: get " + queue.take() + " from queue...");
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
		new Thread(new ArrayBlockingQueueConsumer(queue)).start();
		new Thread(new ArrayBlockingQueueProducer(queue)).start();
		Thread.currentThread().join();
		System.out.println("Finish...");
	}
}
