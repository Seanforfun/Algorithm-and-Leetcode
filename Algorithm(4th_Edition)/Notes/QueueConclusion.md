# Queue Conclusion

### Queue interface
> `boolean add(E e);`	Add and offer are used to add element into the queue.
>
> `boolean offer(E e);`
>
> `E remove();`			Retrieve and remove the head of the queue.
> 
> `E poll();`
> 
> `E element();`		Retrieves, but does not remove, the head of this queue.
> 
> `E peek();`

### Blocking and non-blocking
* Concurrent queue有两种实现方法，阻塞和非阻塞。
* 阻塞队列是通过锁实现。
* 非阻塞队列通过AQS实现。

#### ArrayBlockingQueue
* ArrayBlockingQueue ：一个由数组支持的有界队列。
* 如果到达了上界，将无法添加新的元素进入。
* FIFO
```Java
	public boolean offer(E e) {
        checkNotNull(e);
        final ReentrantLock lock = this.lock;
        lock.lock();		//在写入的过程中获取锁
        try {
            if (count == items.length)
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
        notEmpty.signal();	//取消notEmpty的await.
    }
```
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
```Java
    private E dequeue() {
        // assert lock.getHoldCount() == 1;
        // assert items[takeIndex] != null;
        final Object[] items = this.items;
        @SuppressWarnings("unchecked")
        E x = (E) items[takeIndex];
        items[takeIndex] = null;
        if (++takeIndex == items.length)
            takeIndex = 0;
        count--;
        if (itrs != null)
            itrs.elementDequeued();
        notFull.signal();
        return x;
    }
```

#### LinkedBlockingQueue
* 一个由链接节点支持的可选有界队列。
* 内部维护了一个Node类
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
```Java
	public boolean offer(E e) {
        if (e == null) throw new NullPointerException();
        final AtomicInteger count = this.count;	//此处的count为AtomicInteger，维护了原子性
        if (count.get() == capacity)
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
    
    private void enqueue(Node<E> node) {
        // assert putLock.isHeldByCurrentThread();
        // assert last.next == null;
        last = last.next = node;	//在链表的结尾，添加要插入的结点。
    }
```
```Java
	public E poll() {
        final AtomicInteger count = this.count;
        if (count.get() == 0)
            return null;
        E x = null;
        int c = -1;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            if (count.get() > 0) {
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

#### PriorityBlockingQueue 
* 一个由优先级堆支持的无界优先级队列。
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
                siftUpComparable(n, e, array);
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










































