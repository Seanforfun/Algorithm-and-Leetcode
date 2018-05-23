# ArrayBlockingQueue
* ArrayBlockingQueue : 一个由数组支持的有界队列。
* 如果到达了上界，将无法添加新的元素进入。
* FIFO
>ArrayBlockingQueue在构造时需要指定容量， 并可以选择是否需要公平性，如果公平参数被设置true，等待时间最长的线程会优先得到处理（其实就是通过将ReentrantLock设置为true来 达到这种公平性的：即等待时间最长的线程会先操作）。通常，公平性会使你在性能上付出代价，只有在的确非常需要的时候再使用它。它是基于数组的阻塞循环队 列，此队列按 FIFO（先进先出）原则对元素进行排序。

## ArrayBlockingQueue的操作
* 阻塞方法： put() <---> take()
* 非阻塞方法： offer() <---> poll()

### 添加元素
* put（）	put是ArrayBlockingQueue的方法（不是从Queue接口中继承来），在该方法中获取全局锁，如果队列满，将会阻塞直到有空间可以插入元素。条件（队列空，满）是通过**Condition**接口实现的。
```Java
    /**
     * Inserts the specified element at the tail of this queue, waiting
     * for space to become available if the queue is full.
     *
     * @throws InterruptedException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public void put(E e) throws InterruptedException {
        checkNotNull(e);
        final ReentrantLock lock = this.lock;	//获取全局的锁
        lock.lockInterruptibly();
        try {
            while (count == items.length)
                notFull.await();	//如果当前的队列满了，则一直阻塞
            enqueue(e);		//调用全局的私有入队列方法
        } finally {
            lock.unlock();
        }
    }
```
* offer() offer是Queue接口要求实现的方法，如果队列仍有位置允许插入，插入元素，如果队列已满，直接返回false，不会阻塞。
```Java
	public boolean offer(E e) {
        checkNotNull(e);
        final ReentrantLock lock = this.lock;
        lock.lock();		//在写入的过程中获取锁
        try {
            if (count == items.length)	//如果queue中的元素已经到达了上限，直接返回false
                return false;
            else {
                enqueue(e);	//调用私有的enqueue方法
                return true;
            }
        } finally {
            lock.unlock();	//释放锁
        }
    }
```
* add() add是Queue接口要求实现的方法,内部调用了offer（），如果队列仍有位置允许插入，插入元素，如果队列已满，抛出异常，不会阻塞。
```Java
    public boolean add(E e) {
        if (offer(e))
            return true;
        else
            throw new IllegalStateException("Queue full");
    }
```
* enqueue() enqueue是插入队列的核心方法，维护了一个读指针，一个写指针。
```Java
    /**
     * Inserts element at current put position, advances, and signals.
     * Call only when holding lock.
     */
    private void enqueue(E x) {
        // assert lock.getHoldCount() == 1;
        // assert items[putIndex] == null;
        final Object[] items = this.items;
        items[putIndex] = x;
        if (++putIndex == items.length)
            putIndex = 0;
        count++;
        notEmpty.signal();	//取消notEmpty的await（put方法）.
    }
```
## 读取
* take() take和put对应，是ArrayBlockingQueue私有的阻塞方法，在读取的过程中，如果发现队列为空，则会阻塞直到有元素可以读取。
```Java
    public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == 0)
                notEmpty.await();
            return dequeue();
        } finally {
            lock.unlock();
        }
    }
```
* poll() 和offer()对应，如果没有元素，不会阻塞，返回false
```Java
    public E poll() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return (count == 0) ? null : dequeue();	//判断当前队列有没有元素。有的话调用deqeueu方法。
        } finally {
            lock.unlock();
        }
    }
```

* dequeue（）
```Java
    private E dequeue() {
        // assert lock.getHoldCount() == 1;
        // assert items[takeIndex] != null;
        final Object[] items = this.items;
        @SuppressWarnings("unchecked")
        E x = (E) items[takeIndex];
        items[takeIndex] = null;
        if (++takeIndex == items.length) //循环读取数组
            takeIndex = 0;
        count--;
        if (itrs != null)
            itrs.elementDequeued();
        notFull.signal();
        return x;
    }
```

* peek() 并不会删除队列的第一个元素，单纯的读取值。调用的是itemAt方法
```Java
    public E peek() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return itemAt(takeIndex); // null when queue is empty
        } finally {
            lock.unlock();
        }
    }
```

## Test
* Producer
Producer continuously put data into queue.
```Java
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
```

* Consumer
Consumer get take data from queue 100 times
```Java
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
```
* Result
>...
Producer: put 108 into queue...
Consumer: get 98 from queue...
Producer: put 109 into queue...
Consumer: get 99 from queue...
Producer: put 110 into queue...

We can find that Producer is **blocked**.