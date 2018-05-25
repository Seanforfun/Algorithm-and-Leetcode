# ConcurrentHashMap
> ConcurrentHashMap是HashMap的线程安全实现，相比于Hashtable每次锁住整张表的情况，concurrentHashMap使用了分段锁（降低锁粒度）

### Node
>和HashMap的内部结点一致，定义了Node结点。

```Java
    static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        volatile V val;
        volatile Node<K,V> next;

        Node(int hash, K key, V val, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.val = val;
            this.next = next;
        }

        public final K getKey()       { return key; }
        public final V getValue()     { return val; }
        public final int hashCode()   { return key.hashCode() ^ val.hashCode(); }
        public final String toString(){ return key + "=" + val; }
        public final V setValue(V value) {
            throw new UnsupportedOperationException();
        }
		//比较两个节点是否相同
        public final boolean equals(Object o) {
            Object k, v, u; Map.Entry<?,?> e;
            return ((o instanceof Map.Entry) &&
                    (k = (e = (Map.Entry<?,?>)o).getKey()) != null &&
                    (v = e.getValue()) != null &&
                    (k == key || k.equals(key)) &&
                    (v == (u = val) || v.equals(u)));
        }

        /**
         * Virtualized support for map.get(); overridden in subclasses.
         */
		 //从链表中查找是否有哈希值和k均相同的结点
        Node<K,V> find(int h, Object k) {
            Node<K,V> e = this;
            if (k != null) {
                do {
                    K ek;
                    if (e.hash == h &&
                        ((ek = e.key) == k || (ek != null && k.equals(ek))))
                        return e;
                } while ((e = e.next) != null);
            }
            return null;
        }
    }
```
### 插入
* put() 调用了putVal方法
```Java
    public V put(K key, V value) {
        return putVal(key, value, false);
    }
```

* putVal()
```Java
    final V putVal(K key, V value, boolean onlyIfAbsent) {
        if (key == null || value == null) throw new NullPointerException();
        int hash = spread(key.hashCode());	//通过spread方法计算出hash值。
        int binCount = 0;
        for (Node<K,V>[] tab = table;;) {	//不断循环，和CAS配套使用
            Node<K,V> f; int n, i, fh;
            if (tab == null || (n = tab.length) == 0)
                tab = initTable();	//第一次插入数据，创建新表。(参见initTable解析)
			//根据hash原子性的获取表中对应的Node结点
            else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
				//此时该结点为空，CAS，新建结点并存入表中
                if (casTabAt(tab, i, null,
                             new Node<K,V>(hash, key, value, null)))
                    break;                   // no lock when adding to empty bin
            }
            else if ((fh = f.hash) == MOVED)	//当前结点不为空，且rehash正在进行
				//如果要扩容的新表存在，则将新值存入新表，并返回新表。
                tab = helpTransfer(tab, f);
            else {
                V oldVal = null;
				//给要修改的结点上锁
                synchronized (f) {
					//当前数据还没有被修改过，未出现脏读。不然进入下一次循环，重新进行。
                    if (tabAt(tab, i) == f) {
						//此处插入的代码和HashMap一致
                        if (fh >= 0) {
                            binCount = 1;
                            for (Node<K,V> e = f;; ++binCount) {
                                K ek;
                                if (e.hash == hash &&
                                    ((ek = e.key) == key ||
                                     (ek != null && key.equals(ek)))) {//如果存在所需结点，则更新值
                                    oldVal = e.val;
                                    if (!onlyIfAbsent)
                                        e.val = value;
                                    break;
                                }
                                Node<K,V> pred = e;
                                if ((e = e.next) == null) {//如果遍历到链表结尾仍没有找到，则添加新的对象。
                                    pred.next = new Node<K,V>(hash, key,
                                                              value, null);
                                    break;
                                }
                            }
                        }
                        else if (f instanceof TreeBin) {//如果冲突大于8，则转成树形结构，此时插入或更新结点至红黑树。
                            Node<K,V> p;
                            binCount = 2;
                            if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
                                                           value)) != null) {
                                oldVal = p.val;
                                if (!onlyIfAbsent)
                                    p.val = value;
                            }
                        }
                    }
                }
                if (binCount != 0) {
                    if (binCount >= TREEIFY_THRESHOLD)	//如果在插入元素后造成了链表长度大于8，则树化链表
                        treeifyBin(tab, i);
                    if (oldVal != null)
                        return oldVal;
                    break;
                }
            }
        }
        addCount(1L, binCount);
        return null;
    }
```

* initTable()
```Java
    /**
     * Initializes table, using the size recorded in sizeCtl.
     */
    private final Node<K,V>[] initTable() {
        Node<K,V>[] tab; int sc;
        while ((tab = table) == null || tab.length == 0) {
			//当sizeCtl小于0，说明表正在创建或rehash，放弃当前线程的控制权
            if ((sc = sizeCtl) < 0)
                Thread.yield(); // lost initialization race; just spin
			//其中第一个参数为需要改变的对象，第二个为偏移量(即之前求出来的valueOffset的值)，第三个参数为期待的值，第四个为更新后的值。->将sizeCtl设置为-1，开始创建表。
            else if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {
                try {
                    if ((tab = table) == null || tab.length == 0) {
						//理论上sc的值应该为-1，但是由于多线程操作，可能当前值被别的线程修改，如果更新后的值是大于0，则选用sc的值作为哈希表的大小。
                        int n = (sc > 0) ? sc : DEFAULT_CAPACITY;
                        @SuppressWarnings("unchecked")
                        Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n];
                        table = tab = nt;
                        sc = n - (n >>> 2);	//sc = 0.75 * sc
                    }
                } finally {
                    sizeCtl = sc;
                }
                break;
            }
        }
        return tab;
    }
```

