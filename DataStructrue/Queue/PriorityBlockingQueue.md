# PriorityBlockingQueue 
* 一个由优先级堆支持的无界优先级队列。内部是通过数组实现的。
* 通过[完全二叉堆](https://github.com/Seanforfun/Algorithm/blob/master/DataStructrue/Tree/%E5%AE%8C%E5%85%A8%E4%BA%8C%E5%8F%89%E6%A0%91CompleteBinaryTree.md)实现
>PriorityBlockingQueue是一个带优先级的 队列，而不是先进先出队列。元素按优先级顺序被移除，该队列也没有上限（看了一下源码，PriorityBlockingQueue是对 PriorityQueue的再次包装，是基于堆数据结构的，而PriorityQueue是没有容量限制的，与ArrayList一样，所以在优先阻塞 队列上put时是不会受阻的。虽然此队列逻辑上是无界的，但是由于资源被耗尽，所以试图执行添加操作可能会导致 OutOfMemoryError），但是如果队列为空，那么取元素的操作take就会阻塞，所以它的检索操作take是受阻的。另外，往入该队列中的元 素要具有比较能力。

## 优先队列的操作
### 插入元素
>插入元素有三个函数put, take和offer，事实上三个方法均是调用了offer方法。
>插入元素不会阻塞,因为二叉堆是无界的。
* add()
```Java
	public boolean add(E e) {
        return offer(e);
    }
```

* put()
```Java
    public void put(E e) {
        offer(e); // never need to block
    }
```

* offer() 将要插入的元素放在数组的末尾，并通过swim方法使堆有序。
```Java
    public boolean offer(E e) {
        if (e == null)
            throw new NullPointerException();
        final ReentrantLock lock = this.lock;
        lock.lock();
        int n, cap;
        Object[] array;
        while ((n = size) >= (cap = (array = queue).length))
            tryGrow(array, cap);
        try {
            Comparator<? super E> cmp = comparator;
            if (cmp == null)
                siftUpComparable(n, e, array);	//根据comparable或者comparator进行堆排序
            else
                siftUpUsingComparator(n, e, array, cmp);
            size = n + 1;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
        return true;
    }
```

### 读取元素
* take() 阻塞读取方法
```Java
    public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        E result;
        try {
            while ( (result = dequeue()) == null)
                notEmpty.await();
        } finally {
            lock.unlock();
        }
        return result;
    }
```

* poll() 非阻塞方法，如果当前队列为空，直接返回null
```Java
    public E poll() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return dequeue();
        } finally {
            lock.unlock();
        }
    }
```

* dequeue() 通用的从二叉堆中读取根节点方法，通过将最小的元素替换根节点并进行sink维护堆的有序性。
```Java
    /**
     * Mechanics for poll().  Call only while holding lock.
     */
    private E dequeue() {
        int n = size - 1;
        if (n < 0)
            return null;
        else {
            Object[] array = queue;
            E result = (E) array[0];
            E x = (E) array[n];
            array[n] = null;
            Comparator<? super E> cmp = comparator;
            if (cmp == null)
                siftDownComparable(0, x, array, n);
            else
                siftDownUsingComparator(0, x, array, n, cmp);
            size = n;
            return result;
        }
    }
```

### Test
* PriorityBlockingQueueProducer
```Java
public class PriorityBlockingQueueProducer implements Runnable {
	private PriorityBlockingQueue<Integer> q;
	public PriorityBlockingQueueProducer(PriorityBlockingQueue<Integer> q) {
		super();
		this.q = q;
	}
	@Override
	public void run() {
		while(true){
			Random random = new Random();
			int nextInt = random.nextInt(100);
			q.put(nextInt);
			System.out.println("Producer: put " + nextInt + " into queue...");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
```

* PriorityBlockingQueueConsumer
```Java
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
```















