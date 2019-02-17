# 桶排序
### 介绍
桶排序是一种分治技术的体现，我们通过横向对比的方法研究桶排序的优势。快速排序的时间复杂度是O(NlgN), N的影响是很大的。所以我们希望找到一种方法，将（原本基数很大的）数组分别处理（分治技术）。

谈到分治，重要的是三点：
1. 如何分
2. 怎么治
3. 怎么合

此次介绍桶排序，就是一种在排序中的分的技术。

### 原理分析
1. 我们有一组小数，在0-1之间：
    | 0.21 | 0.33 |  0.27 | 0.98 | 0.45 | 0.41 | 0.66 | 0.71 | 0.69 | 0.11 |
    |---|---|---|---|---|---|---|---|---|---|
2. 我们创建一个哈希桶，键：小数乘以10以后的整数部分， 值：链（或者数组）用于存储符合当前键的所有小数。我们按顺序遍历小数并将他们放入桶中。
    | 键 | 值 |
    |---|---|
    | 0 ||
    | 1 |0.11|
    | 2 |0.21, 0.27|
    | 3 |0.33|
    | 4 |0.45, 0.41|
    | 5 ||
    | 6 |0.66, 0.69|
    | 7 |0.71|
    | 8 ||
    | 9 |0.98|
3. 此时已经完成了分的过程我们只需要对每一个数组进行排序。
4. 再根据index将所有排序好的进行合并。

### 代码示例
```Java
public class BucketSort {
    public static Object[] bucketSort(float[] arr){
        ArrayList[] lists = new ArrayList[10];
        for(int i = 0; i < 10; i++)
            lists[i] = new ArrayList<Float>();
        for(float f : arr){
            lists[(int)(f * 10)].add(f);
        }
        for(ArrayList list : lists){
            list.sort((f1, f2) -> {return ((Float)f1 > (Float)f2) ? -1: 1;} );
        }
        ArrayList<Float> res = new ArrayList<>();
        for(ArrayList list : lists)
            res.addAll(list);
        return res.toArray();
    }

    public static void main(String[] args) {
        Object[] result = bucketSort(new float[]{0.21F, 0.33F, 0.27F, 0.98F,  0.45F, 0.41F, 0.66F, 0.71F, 0.69F, 0.11F});
        for(Object f : result)
            System.out.println(f + "  ");
        System.out.println();
    }
}
```
    