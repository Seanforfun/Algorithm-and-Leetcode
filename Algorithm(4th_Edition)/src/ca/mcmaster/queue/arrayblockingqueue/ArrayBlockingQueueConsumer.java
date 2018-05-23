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
			while(true){
				System.out.println("consumer...");
//				Thread.currentThread().join(10);
//				System.out.println("Consumer: get " + queue.take() + " from queue...");
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
//		ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10000);
		new Thread().run();
		new Thread(()->{System.out.println("producer...");}).run();
//		Thread.currentThread().join();
		System.out.println("Finish...");
	}
}
