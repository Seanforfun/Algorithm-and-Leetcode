# ConcurrentLinkedQueue
* ** 非阻塞**的链表队列
* 通过CAS实现无锁非阻塞
>基于链接节点的、无界的、线程安全。此队列按照 FIFO（先进先出）原则对元素进行排序。队列的头部 是队列中时间最长的元素。队列的尾部 是队列中时间最短的元素。新的元素插入到队列的尾部，队列检索操作从队列头部获得元素。当许多线程共享访问一个公共 collection 时，ConcurrentLinkedQueue 是一个恰当的选择。此队列不允许 null 元素。

## Compare and swap(CAS)
* CAS是一种替代锁存在的无锁并发机制。
* 该种无锁机制也是乐观锁的体现。
* Why need CAS:一个修改操作可以分为：从内存中读取值->修改值->写回内存，在高并发的过程中，这一个过程失去了其原子性，所以修改值的原始值可能已经被其他线程修改了。
		 i != i
这种操作在单线程中一定是true，在多线程中i的值可能在别的线程被更改。
* 如何实现CAS：在循环中读取这次循环的内存中存储值，进行修改，在写回内存中时比较内存中的值是否还是第一步取出的值，不是的话说明这次循环失败，重新开始，是的话写入新的值。
```Java
private static final Unsafe unsafe = Unsafe.getUnsafe(); //JNI实现的Unsafe类。
public final boolean compareAndSet(long expect, long update) {
	return unsafe.compareAndSwapLong(this, valueOffset, expect, update);
}
```

## Node
* ConcurrentLinkedQueue的内部私有类
* 使用了通过C++编写的Unsafe类
```Java
   private static class Node<E> {
        volatile E item;
        volatile Node<E> next;
        /**
         * Constructs a new node.  Uses relaxed write because item can
         * only be seen after publication via casNext.
         */
        Node(E item) {
            UNSAFE.putObject(this, itemOffset, item);
        }
		//通过CAS保证了操作的原子性。
		//CAS应用：表示设置当前Node的item值。第一个参数为期望值，第二个参数为设置目标值。当当前值等于期望值时（就是没有被其他人改过），就会将目标设置为val。
        boolean casItem(E cmp, E val) {
            return UNSAFE.compareAndSwapObject(this, itemOffset, cmp, val);
        }
        void lazySetNext(Node<E> val) {
            UNSAFE.putOrderedObject(this, nextOffset, val);
        }
		//CAS 下一个结点
        boolean casNext(Node<E> cmp, Node<E> val) {
            return UNSAFE.compareAndSwapObject(this, nextOffset, cmp, val);
        }
        // Unsafe mechanics
        private static final sun.misc.Unsafe UNSAFE;
        private static final long itemOffset;
        private static final long nextOffset;
        static {
            try {
                UNSAFE = sun.misc.Unsafe.getUnsafe();
                Class<?> k = Node.class;
                itemOffset = UNSAFE.objectFieldOffset
                    (k.getDeclaredField("item"));
                nextOffset = UNSAFE.objectFieldOffset
                    (k.getDeclaredField("next"));
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }
```

## ConcurrentLinkedQueue's API
### 插入元素
* add() 通过offer方法添加元素。
```Java
    public boolean add(E e) {
        return offer(e);
    }
```

* offer()
![clq](https://i.imgur.com/QONSTB2.jpg)

```Java
    public boolean offer(E e) {
        checkNotNull(e);
        final Node<E> newNode = new Node<E>(e);

        for (Node<E> t = tail, p = t;;) {
            Node<E> q = p.next;
            if (q == null) {
                // 当尾结点就是指向链表的最后一个元素，尝试CAS新的结点
                if (p.casNext(null, newNode)) {	//将新的结点添加到链表的底部
                    // Successful CAS is the linearization point
                    // for e to become an element of this queue,
                    // and for newNode to become "live".
                    if (p != t) // 如上图所示，如果当前p不是tail结点，就将新接入的结点当做尾结点。相当于尾结点一次性跳跃两个结点。
                        casTail(t, newNode);  // Failure is OK.
                    return true;
                }
                // Lost CAS race to another thread; re-read next
            }
            else if (p == q)	//哨兵结点情况
                // We have fallen off list.  If tail is unchanged, it
                // will also be off-list, in which case we need to
                // jump to head, from which all live nodes are always
                // reachable.  Else the new tail is a better bet.
				//如果在读取中，t的值被更新了，“打赌”t被更新正确了，不然重新从头开始。
                p = (t != (t = tail)) ? t : head;
            else	//q不是最后一个结点
                // Check for tail updates after two hops.
				//如果在该操作过程中，t的值被更新了，我们“打赌”t的值被更新正确
				//不然我们将p指针向后移动一位，重新进入循环
                p = (p != t && t != (t = tail)) ? t : q;
        }
    }
```
[高效读写的队列：深度剖析ConcurrentLinkedQueue](https://blog.csdn.net/chenguibao/article/details/51773322)

### 出队列
![cdl](https://i.imgur.com/G5Irgel.jpg)
* poll()
```Java
    public E poll() {
        restartFromHead:
        for (;;) {
            for (Node<E> h = head, p = h, q;;) {
                E item = p.item;
				//取出头元素的值，并将其CAS成null。
                if (item != null && p.casItem(item, null)) {
                    // Successful CAS is the linearization point
                    // for item to be removed from this queue.
					//"打赌"，假设头结点head已经被更新，则沿用h，不然更新为下一个结点
                    if (p != h) // hop two nodes at a time
                        updateHead(h, ((q = p.next) != null) ? q : p);
                    return item;
                }
				//当前结点为空，下一个结点也为空，队列为空,退出循环，返回null。
                else if ((q = p.next) == null) {
                    updateHead(h, p);
                    return null;
                }
				//遇到哨兵结点，从新从头开始。
                else if (p == q)
                    continue restartFromHead;
				//此时q == p.next， 将指针向右移动一位。
                else
                    p = q;
            }
        }
    }
```