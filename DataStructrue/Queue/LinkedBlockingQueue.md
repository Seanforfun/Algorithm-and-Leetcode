# LinkedBlockingQueue
* 一个由链接节点支持的可选有界队列。
* 内部维护了一个Node类,是一个单向链表。第一个元素为空（dummy node）。
>LinkedBlockingQueue的容量是没有上限的（说的不准确，在不指定时容量为Integer.MAX_VALUE，不要然的话在put时怎么会受阻呢），但是也可以选择指定其最大容量，它是基于链表的队列，此队列按 FIFO（先进先出）排序元素。
```Java
static class Node<E> {
        E item;
        /**
         * One of:
         * - the real successor Node
         * - this Node, meaning the successor is head.next
         * - null, meaning there is no successor (this is the last node)
         */
        Node<E> next;
        Node(E x) { item = x; }
    }
```

## 插入元素
* put() LinkedBlockingQueue的阻塞插入方法，如果队列已满，则阻塞并等待。
```Java
    /**
     * Inserts the specified element at the tail of this queue, waiting if
     * necessary for space to become available.
     *
     * @throws InterruptedException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public void put(E e) throws InterruptedException {
        if (e == null) throw new NullPointerException();
        // Note: convention in all put/take/etc is to preset local var
        // holding count negative to indicate failure unless set.
        int c = -1;
        Node<E> node = new Node<E>(e);
        final ReentrantLock putLock = this.putLock;	//写锁
        final AtomicInteger count = this.count;
        putLock.lockInterruptibly();
        try {
            /*
             * Note that count is used in wait guard even though it is
             * not protected by lock. This works because count can
             * only decrease at this point (all other puts are shut
             * out by lock), and we (or some other waiting put) are
             * signalled if it ever changes from capacity. Similarly
             * for all other uses of count in other wait guards.
             */
            while (count.get() == capacity) {
                notFull.await();	//如果队列已满，阻塞。
            }
            enqueue(node);
            c = count.getAndIncrement();
            if (c + 1 < capacity)
                notFull.signal();
        } finally {
            putLock.unlock();
        }
        if (c == 0)
            signalNotEmpty();
    }
```

* offer（）实现Queue接口的方法，如果当前队列已满，直接返回false。
```Java
	public boolean offer(E e) {
        if (e == null) throw new NullPointerException();
        final AtomicInteger count = this.count;	//此处的count为AtomicInteger，维护了原子性
        if (count.get() == capacity)	//如果队列已满，直接返回
            return false;
        int c = -1;
        Node<E> node = new Node<E>(e);
        final ReentrantLock putLock = this.putLock;
        putLock.lock();
        try {
            if (count.get() < capacity) {
                enqueue(node);
                c = count.getAndIncrement();
                if (c + 1 < capacity)
                    notFull.signal();
            }
        } finally {
            putLock.unlock();
        }
        if (c == 0)
            signalNotEmpty();
        return c >= 0;
    }
```

* enqueue（） 添加方法的核心
```Java
    private void enqueue(Node<E> node) {
        // assert putLock.isHeldByCurrentThread();
        // assert last.next == null;
        last = last.next = node;	//在链表的结尾，添加要插入的结点。
    }
```

## 取出元素
* take() 和put()相对应的方法，从队列中取出元素，如果队列为空则会阻塞。
```Java
    public E take() throws InterruptedException {
        E x;
        int c = -1;
        final AtomicInteger count = this.count;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lockInterruptibly();
        try {
            while (count.get() == 0) {	//判断当前队列为空，condition方法开始阻塞。
                notEmpty.await();
            }
            x = dequeue();	//调用统一的dequeue方法从队列中读取元素。
            c = count.getAndDecrement();
            if (c > 1)
                notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
        if (c == capacity)
            signalNotFull();
        return x;
    }
```

* poll() 从队列中获取元素，如果队列中为空则返回null。
```Java
	public E poll() {
        final AtomicInteger count = this.count;
        if (count.get() == 0)	//如果队列为空直接返回null, point 1
            return null;
        E x = null;
        int c = -1;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            if (count.get() > 0) {	//再次判断，如果在point 1 到当前点之间队列已经变成空，直接跳过读取阶段，返回空。
                x = dequeue();
                c = count.getAndDecrement();
                if (c > 1)
                    notEmpty.signal();
            }
        } finally {
            takeLock.unlock();
        }
        if (c == capacity)
            signalNotFull();
        return x;
    }
```
* dequeue() 统一的从队列头读取元素的方法
>Avoid create a new node, just set value to null.
```Java
    /**
     * Removes a node from head of queue. 
     *
     * @return the node
     */
    private E dequeue() {
        // assert takeLock.isHeldByCurrentThread();
        // assert head.item == null;
        Node<E> h = head;
        Node<E> first = h.next;
        h.next = h; // help GC
        head = first;
        E x = first.item;
        first.item = null;
        return x;
    }
```

## Test
* 向队列中放置100个元素不断读取，发现读取的进程最终阻塞
```Java
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
```

```Java
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
```
* 结果
>Producer: put 98 into queue...
Consumer: take 98 from queue...
Producer: put 99 into queue...
Consumer: take 99 from queue...
读取进程最终**阻塞**。
