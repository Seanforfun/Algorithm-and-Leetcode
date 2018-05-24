# 哈希表 HashMap
> 1. 哈希表的实现就是一个哈希桶，通过哈希算法计算出hash值并定位存储的位置。
> 2. 允许使用null值和null键。此类不保证映射的顺序，特别是它不保证该顺序恒久不变。
> 3. 线程不安全！没看到任何锁机制。
> 4. JDK1.8以后添加了TreeNode代替链表的实现，即当哈希冲突巨大，链表上有TREEIFY_THRESHOLD+个结点，则将链表转换为红黑树。

## Node
>HashMap内部是由数组实现的，而对于每一个元素，它的值可能链接的是一个链表，所以单向链表连接的结点组成的，内部类Node结点继承了Map的内部Entry接口。

```Java
    static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;	//每个结点构成了一张哈希表。
		//每个Node内部存储哈希值，键，值，以及链表的下一个元素
        Node(int hash, K key, V value, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey()        { return key; }
        public final V getValue()      { return value; }
        public final String toString() { return key + "=" + value; }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            //...
        }
    }
```

## Fields
* transient Node<K,V>[] table;
table不会在创建对象时初始化，而是会在插入对象时通过resize方法创建。
```Java
@SuppressWarnings({"rawtypes","unchecked"})
Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
table = newTab;
```

* transient Set<Map.Entry<K,V>> entrySet;
维护一个entrySet

* transient int size;
维护哈希桶的大小

* transient int modCount;
用于维护fail-fast机制
>fail-fast，它是Java集合的一种错误检测机制。当多个线程对集合进行结构上的改变的操作时，有可能会产生fail-fast机制。记住是有可能，而不是一定。例如：假设存在两个线程（线程1、线程2），线程1通过Iterator在遍历集合A中的元素，在某个时候线程2修改了集合A的结构（是结构上面的修改，而不是简单的修改集合元素的内容），那么这个时候程序就会抛出 ConcurrentModificationException 异常，从而产生fail-fast机制。

* int threshold;
* final float loadFactor;
用于哈希桶扩容。

## Constructor
```Java
    public HashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                                               initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;	//哈希桶的最大深度：2^30
        if (loadFactor <= 0 || Float.isNaN(loadFactor))	//扩充桶深度的因子，当超过lf后桶的深度会增加
            throw new IllegalArgumentException("Illegal load factor: " +
                                               loadFactor);
        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity);	//将桶的深度扩充成2的次方。
    }
```

## HashMap API
* hash()计算哈希值，用于确定存储位置。
```Java
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
```

### 插入
* put() 实际上调用putVal()方法
```Java
    public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }
```

* putVal()
```Java
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
		//第一次添加元素，创建table数组
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
		//通过计算哈希值得到存入元素的位置，如果没有哈希冲突就创建出元素并存入。
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);
        else {	//产生哈希冲突的处理方式，p所计算出的位置存储的结点。
            Node<K,V> e; K k;
			//如果要插入的元素和储存的哈希值一致，键一致，将存储元素存在e(existing)中
			//实际上说明两次插入的是同一个对象。
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
			//如果当前结点所挂的已经是一个红黑树，则直接添加，因为平衡性已经通过树形结构进行了优化。
            else if (p instanceof TreeNode)
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
						//将新的结点插入链表的尾端
                        p.next = newNode(hash, key, value, null);
						//如果链表的长度超过8则转为红黑树
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
					//如果要插入的元素已经存在在链表里退出循环
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
				//如果onlyIfAbsent为true，则不进行更改，为空则添加值。
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);	//用户可以重载自行添加方法
                return oldValue;
            }
        }
        ++modCount;
        if (++size > threshold)
            resize();		//如果超过了容量则进行扩容
        afterNodeInsertion(evict);	//用户可以重载自行添加方法
        return null;
    }
```

* resize() 如果内部存储的哈希超过了threshold，则要开始扩容。
```Java
   final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) {
			//规定存储的数量上限，若double以后没有到达上线就double
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold
        }
        else if (oldThr > 0) // initial capacity was placed in threshold
            newCap = oldThr;
        else {               // zero initial threshold signifies using defaults
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                      (int)ft : Integer.MAX_VALUE);
        }
        threshold = newThr;
		//创建一棵新的数组用作存储对象
        @SuppressWarnings({"rawtypes","unchecked"})
            Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
		//旧表不为空，将元素复制进来
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;	//帮助GC回收
                    if (e.next == null)	//如果只有单结点则直接插入新表
                        newTab[e.hash & (newCap - 1)] = e;
                    else if (e instanceof TreeNode)	//如果该结点上链接的是红黑树则插入红黑树
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    else { // 如果当前结点上连接的是一条链表则将链表挂在新的表中
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }
```

### 获取元素
* get(key)
```Java
    public V get(Object key) {
        Node<K,V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }
```

* getNode()
```Java
    final Node<K,V> getNode(int hash, Object key) {
        Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
		//非空判定，通过hash找到那一个bucket.
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (first = tab[(n - 1) & hash]) != null) {
			//如果第一个元素的hash值一值则返回（同时说明了该bucket中装载的是单一结点而不是链表或树形结构）
            if (first.hash == hash && // always check first node
                ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
			//为链表或树形结构
            if ((e = first.next) != null) {
				//遍历树
                if (first instanceof TreeNode)
                    return ((TreeNode<K,V>)first).getTreeNode(hash, key);
				//遍历链表
                do {
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }
```

### 删除结点
* remove() 删除结点
内部调用了removeNode()方法
```Java
    public V remove(Object key) {
        Node<K,V> e;
        return (e = removeNode(hash(key), key, null, false, true)) == null ?
            null : e.value;
    }
```

* removeNode()
删除方法和查找方法类似，只是找到了结点以后置为空
```Java
    final Node<K,V> removeNode(int hash, Object key, Object value,
                               boolean matchValue, boolean movable) {
        Node<K,V>[] tab; Node<K,V> p; int n, index;
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (p = tab[index = (n - 1) & hash]) != null) {
            Node<K,V> node = null, e; K k; V v;
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                node = p;
            else if ((e = p.next) != null) {
                if (p instanceof TreeNode)
                    node = ((TreeNode<K,V>)p).getTreeNode(hash, key);
                else {
                    do {
                        if (e.hash == hash &&
                            ((k = e.key) == key ||
                             (key != null && key.equals(k)))) {
                            node = e;
                            break;
                        }
                        p = e;
                    } while ((e = e.next) != null);
                }
            }
            if (node != null && (!matchValue || (v = node.value) == value ||
                                 (value != null && value.equals(v)))) {
                if (node instanceof TreeNode)
                    ((TreeNode<K,V>)node).removeTreeNode(this, tab, movable);
                else if (node == p)
                    tab[index] = node.next;
                else
                    p.next = node.next;
                ++modCount;	//更新modCount，为fail-fast准备
                --size;
                afterNodeRemoval(node);
                return node;
            }
        }
        return null;
    }
```

### 线程不安全
1. rehash会造成死锁。
2. 数据脏读。

### Reference
1. [HashMap的实现原理](https://tracylihui.github.io/2015/07/01/Java%E9%9B%86%E5%90%88%E5%AD%A6%E4%B9%A01%EF%BC%9AHashMap%E7%9A%84%E5%AE%9E%E7%8E%B0%E5%8E%9F%E7%90%86/)
2. [谈谈HashMap线程不安全的体现](http://www.importnew.com/22011.html)