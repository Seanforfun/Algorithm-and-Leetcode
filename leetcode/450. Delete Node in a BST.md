## 450. Delete Node in a BST

### Question
Given a root node reference of a BST and a key, delete the node with the given key in the BST. Return the root node reference (possibly updated) of the BST.

Basically, the deletion can be divided into two stages:

Search for a node to remove.
If the node is found, delete the node.
Note: Time complexity should be O(height of tree).

```
Example:

root = [5,3,6,2,4,null,7]
key = 3

    5
   / \
  3   6
 / \   \
2   4   7

Given key to delete is 3. So we find the node with value 3 and delete it.

One valid answer is [5,4,6,2,null,null,7], shown in the following BST.

    5
   / \
  4   6
 /     \
2       7

Another valid answer is [5,2,6,null,4,null,7].

    5
   / \
  2   6
   \   \
    4   7
```

### Solution
* Method 1: recursion
  ![Imgur](https://i.imgur.com/rShtr1p.png)
  ```Java
  /**
   * Definition for a binary tree node.
   * public class TreeNode {
   *     int val;
   *     TreeNode left;
   *     TreeNode right;
   *     TreeNode(int x) { val = x; }
   * }
   */
  class Solution {
      public TreeNode deleteNode(TreeNode root, int key) {
          if(root == null) return root;
          if(key < root.val) root.left = deleteNode(root.left, key);
          else if(key > root.val) root.right = deleteNode(root.right, key);
          else{
              TreeNode res = null;
              if(root.left == null) res = root.right;
              else if(root.right == null) res = root.left;
              else{ // both left and right are not null.
                  TreeNode parent = root;
                  res = root.right;
                  while(res.left != null){
                      parent = res;
                      res = res.left;
                  }
                  if(parent != root){
                      parent.left = res.right;
                      res.right = root.right;
                  }
                  res.left = root.left;
              }
              return res;
          }
          return root;
      }
  }
  ```

### Reference
1. [花花酱 LeetCode 450. Delete Node in a BST](http://zxi.mytechroad.com/blog/tree/leetcode-450-delete-node-in-a-bst/)
