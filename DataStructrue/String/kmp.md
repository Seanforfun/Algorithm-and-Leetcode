# KMP

### Introduction
KMP算法的关键是利用匹配失败后的信息，尽量减少模式串与主串的匹配次数以达到快速匹配的目的。具体实现就是实现一个next()函数，函数本身包含了模式串的局部匹配信息。时间复杂度O(m+n)。

### 理解
1. next数组的含义：表示了匹配串的最长前缀的位置
	* abcjkdabc的最长相同前后缀是abc，这意味着在最后一个字符处出现了不匹配可以直接从j处开始新的匹配。
	* 这样就能避免很多的重复匹配。

### 实现
```Java
package ca.mcmaster.chapter.five.string;

public class Kmp {
	private static int[] getNext(String pattern){
		char[] arr = pattern.toCharArray();
		int len = pattern.length();
		int[] next = new int[len];
		next[0] = -1;
		for(int i = 1, j = -1; i < len; i++){
			while(j > -1 && arr[j + 1] != arr[i]){
				j = next[j];
			}
			if(arr[i] == arr[j + 1]) j++;
			next[i] = j;
		}
		return next;
	}
	public static int kmp(String text, String pattern){
		int[] next = getNext(pattern);
		char[] tArr = text.toCharArray();
		char[] pArr = pattern.toCharArray();
		int tLen = text.length();
		int pLen = pattern.length();
		int j = -1;
		for(int i = 0; i < tLen; i++){
			while(j > -1 && tArr[i] != pArr[j + 1]){
				j = next[j];
			}
			if(tArr[i] == pArr[j + 1]) j++;
			if(j == pLen - 1) return i - pLen + 1;
		}
		return -1;
	}
	public static void main(String[] args) {
		String a = "ababaca";
		System.out.println(kmp("bacbababadababacambabacaddababacasdsd", a));
	}
}
```