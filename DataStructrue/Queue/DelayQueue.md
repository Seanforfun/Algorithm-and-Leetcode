# DelayQueue
>DelayQueue（**基于PriorityQueue来实现的**）是一个存放Delayed 元素的无界阻塞队列，只有在延迟期满时才能从中提取元素。该队列的头部是延迟期满后保存时间最长的 Delayed 元素。如果延迟都还没有期满，则队列没有头部，并且poll将返回null。当一个元素的 getDelay(TimeUnit.NANOSECONDS) 方法返回一个小于或等于零的值时，则出现期满，poll就以移除这个元素了。此队列不允许使用 null 元素。
>DelayQueue中存储的是Delayed接口的实现

## Delayed
```Java
public interface Delayed extends Comparable<Delayed> {

    /**
     * Returns the remaining delay associated with this object, in the
     * given time unit.
     *
     * @param unit the time unit
     * @return the remaining delay; zero or negative values indicate
     * that the delay has already elapsed
     */
    long getDelay(TimeUnit unit);
}
```
我们要实现getDelay()方法，返回还需要等待的时间。
同时Delayed接口继承了Comparable方法，所以要实现compareTo方法（用于在优先级队列中排序）。

### 一个Delayed接口的实现类
```Java
public class DelayMsg<V> implements Delayed{
	private Long expire;	//用于存储什么时候当前对象可以生效。
	private Long insertTime;	//当前对象插入队列的时间，用于优先级排列，基于延迟的基础上的FIFO
	private V v;
	public V getV() {
		return v;
	}
	public Long getInsertTime() {
		return insertTime;
	}
	public DelayMsg(Long expire){
		this.expire = expire;
	}
	@Override
	public int compareTo(Delayed o) {
		Long delta = insertTime - ((DelayMsg)o).getInsertTime();
		if(delta > 0) return 1;
		else if(delta == 0) return 0;
		else
			return -1;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Delayed#getDelay(java.util.concurrent.TimeUnit)
	 * 用于定义延时策略,根据需求定义。
	 */
	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);//判断是否当前时间已经超过了生效时间
	}
}
```

## DelayQueue的API
### 添加元素
* 因为是无界队列，所以不存在插入元素时的阻塞。
* put() 通过offer()方法实现。
```Java
    public void put(E e) {
        offer(e);
    }
```

* add() 通过offer()方法实现。
```Java
    public boolean add(E e) {
        return offer(e);
    }
```

* offer() 事实上offer方法调用了priorityQueue的插入元素方法
```Java
    public boolean offer(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            q.offer(e);
            if (q.peek() == e) {	//说明当前队列中只有一个元素，解除阻塞。
                leader = null;
                available.signal();
            }
            return true;
        } finally {
            lock.unlock();
        }
    }
```

### 读取元素
* poll() 返回根节点，如果根节点为空或根节点未完成其延时要求，返回null，非阻塞
```Java
    public E poll() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            E first = q.peek();	//取出根节点
            if (first == null || first.getDelay(NANOSECONDS) > 0)
                return null;
            else
                return q.poll();//完成延时的要求，从优先队列中取出根节点并返回。
        } finally {
            lock.unlock();
        }
    }
```

* take()
>leader-follower
>所有线程会有三种身份中的一种：leader和 follower，以及一个干活中的状态：proccesser。它的基本原则就是，永远最多只有一个leader。而所有follower都在等待成为 leader。线程池启动时会自动产生一个Leader负责等待网络IO事件，当有一个事件产生时，Leader线程首先通知一个Follower线程将 其提拔为新的Leader，然后自己就去干活了，去处理这个网络事件，处理完毕后加入Follower线程等待队列，等待下次成为Leader。这种方法 可以增强CPU高速缓存相似性，及消除动态内存分配和线程间的数据交换。

```Java
    public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            for (;;) {
                E first = q.peek();
                if (first == null)	//判断队列中是否有元素
                    available.await();
                else {
                    long delay = first.getDelay(NANOSECONDS);//如果有元素，则判断有没有完成延时要求
                    if (delay <= 0)
                        return q.poll();	//完成延时要求，返回元素
                    first = null; // don't retain ref while waiting
                    if (leader != null)		//如果leader不为空，则继续进入等待
                        available.await();
                    else {		//如果leader为空，则将当前处理的线程提升为leader并继续进入等待
                        Thread thisThread = Thread.currentThread();
                        leader = thisThread;
                        try {
                            available.awaitNanos(delay);
                        } finally {
                            if (leader == thisThread)
                                leader = null;		//完成所有操作后，将leader置空
                        }
                    }
                }
            }
        } finally {
            if (leader == null && q.peek() != null)
                available.signal();
            lock.unlock();
        }
    }
```

### Test
我们创建10个元素放入延迟队列中，设置延时10s，消费者通过while（true）不断读取。
* DelayQueueProducer
```Java
public class DelayQueueProducer implements Runnable {
	private DelayQueue<DelayMsg<Integer>> q; 
	public DelayQueueProducer(DelayQueue<DelayMsg<Integer>> q) {
		super();
		this.q = q;
	}
	@Override
	public void run() {
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		for(int i = 0; i < 10; i++){	//创建10个元素放入延迟队列
			q.offer(new DelayMsg<Integer>(System.currentTimeMillis() + 10000, System.currentTimeMillis(), i));
			System.out.println(df.format(new Date(System.currentTimeMillis())) + ": Producer: put " + i + " into queue...");
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
```
* DelayQueueConsumer一直循环读取元素
```Java
public class DelayQueueConsumer implements Runnable {
	private DelayQueue<DelayMsg<Integer>> q;
	public DelayQueueConsumer(DelayQueue<DelayMsg<Integer>> q) {
		super();
		this.q = q;
	}
	@Override
	public void run() {
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		while (true) {
			try {
				System.out.println(df.format(new Date(System.currentTimeMillis())) + ": Consumer: take " + ((DelayMsg<Integer>)q.take()).getV() + " from queue...");
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		DelayQueue<DelayMsg<Integer>> q = new DelayQueue<>();
		new Thread(new DelayQueueConsumer(q)).start();
		new Thread(new DelayQueueProducer(q)).start();
		System.out.println("Main finish...");
	}
}
```
结果
Main finish...
15:54:00: Producer: put 0 into queue...
15:54:00: Producer: put 1 into queue...
15:54:00: Producer: put 2 into queue...
15:54:00: Producer: put 3 into queue...
15:54:00: Producer: put 4 into queue...
15:54:00: Producer: put 5 into queue...
15:54:00: Producer: put 6 into queue...
15:54:00: Producer: put 7 into queue...
15:54:00: Producer: put 8 into queue...
15:54:00: Producer: put 9 into queue...
15:54:00: Consumer: take 0 from queue...
15:54:10: Consumer: take 1 from queue...
15:54:10: Consumer: take 2 from queue...
15:54:10: Consumer: take 3 from queue...
15:54:10: Consumer: take 4 from queue...
15:54:10: Consumer: take 5 from queue...
15:54:10: Consumer: take 6 from queue...
15:54:10: Consumer: take 7 from queue...
15:54:10: Consumer: take 8 from queue...
15:54:10: Consumer: take 9 from queue...
>延迟10s才从队列中取出元素，之后因为队列为空开始阻塞。