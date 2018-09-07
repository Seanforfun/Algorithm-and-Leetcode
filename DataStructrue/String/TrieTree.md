# Trie Tree

### Introduction
又称单词查找树，Trie树，是一种树形结构，是一种哈希树的变种。典型应用是用于统计，排序和保存大量的字符串（但不仅限于字符串），所以经常被搜索引擎系统用于文本词频统计。它的优点是：利用字符串的公共前缀来减少查询时间，最大限度地减少无谓的字符串比较，查询效率比哈希树高。

![Trie Tree](https://i.imgur.com/TPiaR6j.jpg)

### 结点
```Java
private class TrieNode{
		boolean isLeaf;	//确定当前节点是否可以成为单词的结尾。
		TrieNode[] childs;
		public TrieNode(){
			childs = new TrieNode[26];	//对应26个字母。
		}
	}
```

### API
1. 插入
```Java
	public void insert(String word){
		int len = word.length();
		TrieNode temp = root;
		for(int i = 0; i < len; i++){
			int c = word.charAt(i) - 'a';
			if(temp.childs[c] == null)
				temp.childs[c] = new TrieNode();
			temp = temp.childs[c];
 		}
		temp.isLeaf = true;
	}
```

2. 查找
```Java
	public boolean contains(String word){
		int len = word.length();
		TrieNode temp = root;
		for(int i = 0; i < len; i++){
			int c = word.charAt(i) - 'a';
			if(temp.childs[c] == null) return false;
			temp = temp.childs[c];
		}
		return temp.isLeaf;
	}
```

3. 含有前缀
```Java
	public boolean hasPrefix(String word){
		int len = word.length();
		TrieNode temp = root;
		for(int i = 0; i < len; i++){
			int c = word.charAt(i) - 'a';
			if(temp.childs[c] == null) return false;
			temp = temp.childs[c];
		}
		return true;
	}
```