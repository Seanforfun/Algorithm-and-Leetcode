# Data structure | TreeSet

## Introduction
Treeset is a navigable set which also guaranteed the unique of the elements in the tree set. The implementation of Treeset is based on TreeMap and we can inject a comparator to the tree set so we can have have the elements in the set saved in order.

![Imgur](https://i.imgur.com/CHIb1Hz.png)

## Constructor of TreeSet
TreeSet is implemtented based on TreeMap so it may require two kinds of elements which are Comparator and Collection. Comparator is used for navigable set and Collection is the container for save elements.
```Java
TreeSet(Collection<? extends E> collection);
TreeSet(Comparator<? super E> comparator);
```

## APIs of TreeSet
1. TreeSet, as well as a set, has the basic ability of CRUD of set data structure. So the API of the TreeSet CRUD is the same as HashSet:
```Java
// Add element(s) into TreeSet.
public boolean add(E e);
public boolean addAll(Collection<? extends E> c);
// Delete element in the TreeSet.
public boolean remove(Object o);
// Size of the treeset
public int size();
// Check if current TreeSet is empty.
public boolean isEmpty();
```

2. TreeSet also implements the Navigable interface, so we can use some of the APIs clarified in this interface. The main idea of this interface is to have the elements saved in this data structure in default(or customized) order.
```Java
// Return a iterator of current treeset and this iterator will return elements in ascending order.
public Iterator<E> iterator();
// Return a iterator of current treeset and this iterator will return elements in descending order.
public Iterator<E> descendingIterator();
// TreeSet also worked as a queue, so we have the API of dequeue the first element in the set.
public E pollFirst();
public E pollLast();
```

## Attention
1. TreeSet is not thread safe.

## Reference
1. [Java 集合系列17之 TreeSet详细介绍(源码解析)和使用示例](https://www.cnblogs.com/skywang12345/p/3311268.html)
