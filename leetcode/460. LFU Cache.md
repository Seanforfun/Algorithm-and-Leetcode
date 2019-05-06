## 460. LFU Cache

### Question
Design and implement a data structure for Least Frequently Used (LFU) cache. It should support the following operations: get and put.

get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
put(key, value) - Set or insert the value if the key is not already present. When the cache reaches its capacity, it should invalidate the least frequently used item before inserting a new item. For the purpose of this problem, when there is a tie (i.e., two or more keys that have the same frequency), the least recently used key would be evicted.

Follow up:
* Could you do both operations in O(1) time complexity?

```
Example:

LFUCache cache = new LFUCache( 2 /* capacity */ );

cache.put(1, 1);
cache.put(2, 2);
cache.get(1);       // returns 1
cache.put(3, 3);    // evicts key 2
cache.get(2);       // returns -1 (not found)
cache.get(3);       // returns 3.
cache.put(4, 4);    // evicts key 1.
cache.get(1);       // returns -1 (not found)
cache.get(3);       // returns 3
cache.get(4);       // returns 4
```

### Solutions:
* Method 1: HashMap + PriorityQueue Both get and put are O(NlgN)
    * We create a class node, which saves the freq, tick, key and val.
    * We have a hashMap to save the key and Node.
    * We create a priorityQueue to to save the nodes, the comparator of the priorityQueue is
        1. if freq is not same, compare the freq.
        2. if freq is the same, we compare the tick.
    ```Java
    class LFUCache {
        private static final class Node{
            int key, val;
            Node pre, next;
            int freq, tick;
            public Node(int key, int val, int freq, int tick){
                this.key = key;
                this.val = val;
                this.freq = freq;
                this.tick = tick;
            }
        }
        private Map<Integer, Node> map;
        private PriorityQueue<Node> pq;
        private int capacity;
        private int size;
        private int tick;
        public LFUCache(int capacity) {
            this.map = new HashMap<>();
            this.capacity = capacity;
            pq = new PriorityQueue<Node>((n1, n2)->{
                return n1.freq == n2.freq ? n1.tick - n2.tick: n1.freq - n2.freq;
            });
        }
        public int get(int key) {  
            if(!map.containsKey(key) || capacity == 0) return -1;
            Node node = map.get(key);
            pq.remove(node);
            node.freq++;
            node.tick = tick++;
            pq.offer(node);
            return node.val;
        }
        public void put(int key, int value) {
            if(capacity == 0) return;
            Node node = null;
            if(map.containsKey(key)){
                node = map.get(key);
                node.val = value;
                pq.remove(node);
                map.remove(key);
                size--;
            }else{
                node = new Node(key, value, 0, tick);
            }
            node.freq++;
            node.tick = tick++;
            map.put(key, node);
            if(size < capacity){
                pq.offer(node);
                size++;
            }else{
                Node lfu = pq.poll();
                map.remove(lfu.key);
                pq.offer(node);
            }
        }
    }
    /**
     * Your LFUCache object will be instantiated and called as such:
     * LFUCache obj = new LFUCache(capacity);
     * int param_1 = obj.get(key);
     * obj.put(key,value);
     */
    ```
 
 * Method 2: Two hashMap + doubleLinkedList O(1)
    ```Java
    class LFUCache {
        private static final class Node{
            int key, val, freq;
            Node pre, next;
            public Node(int key, int val){
                this.key = key;
                this.val = val;
                this.freq = 1;
            }
        }
        private static final class DLLList{
            Node head, tail;
            int size;
            DLLList(){
                head = new Node(0, 0);
                tail = new Node(0, 0);
                head.next = tail;
                tail.pre = head;
            }
            
            void add(Node node){
                head.next.pre = node;
                node.next = head.next;
                node.pre = head;
                head.next = node;
                size++;
            }
            void remove(Node node){
                node.pre.next = node.next;
                node.next.pre = node.pre;
                size--;
            }
            Node removeLast(){
                if(size > 0){
                    Node node = tail.pre;
                    remove(node);
                    return node;
                }
                return null;
            }
        }
        private Map<Integer, Node> map;
        private Map<Integer, DLLList> countMap;
        private int size;
        private int capacity;
        private int min;
        public LFUCache(int capacity) {
            this.capacity = capacity;
            this.map = new HashMap<>();
            this.countMap = new HashMap<>();
        }
        
        public int get(int key) {
            if(!map.containsKey(key) || size == 0) return -1;
            Node node = map.get(key);
            update(node);
            return node.val;
        }
        
        public void put(int key, int value) {
            if(capacity == 0) return;
            Node node = null;
            if(map.containsKey(key)){
                node = map.get(key);
                node.val = value;
                update(node);
            }else{
                node = new Node(key, value);
                map.put(key, node);
                if(size == capacity){
                    DLLList lastList = countMap.get(min);
                    map.remove(lastList.removeLast().key);
                    size--;
                }
                size++;
                min = 1;
                DLLList newList = countMap.getOrDefault(node.freq, new DLLList());
                newList.add(node);
                countMap.put(node.freq, newList);
            }
        }
        private void update(Node node){
            DLLList oldList = countMap.get(node.freq);
            oldList.remove(node);
            if(node.freq == min && oldList.size == 0) min++;
            node.freq++;
            DLLList newList = countMap.getOrDefault(node.freq, new DLLList());
            newList.add(node);
            countMap.put(node.freq, newList);
        }
    }
    
    /**
     * Your LFUCache object will be instantiated and called as such:
     * LFUCache obj = new LFUCache(capacity);
     * int param_1 = obj.get(key);
     * obj.put(key,value);
     */
    ```

### Reference
1. [花花酱 LeetCode 460. LFU Cache](http://zxi.mytechroad.com/blog/hashtable/leetcode-460-lfu-cache/)