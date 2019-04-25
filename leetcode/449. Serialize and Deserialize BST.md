## 449. Serialize and Deserialize BST

### Question
Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.

Design an algorithm to serialize and deserialize a binary search tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary search tree can be serialized to a string and this string can be deserialized to the original tree structure.

The encoded string should be as compact as possible.

Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms should be stateless.

### Solution:
* Method 1: dfs
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
  public class Codec {
      private static final String empty = "#";
      private static final String split = ",";
      // Encodes a tree to a single string.
      public String serialize(TreeNode root) {
          StringBuilder sb = new StringBuilder();
          serialize(root, sb);
          return sb.toString();
      }
      private void serialize(TreeNode node, StringBuilder sb){
          if(node == null){
              sb.append(empty).append(split);
          }else{
              sb.append(node.val).append(split);
              serialize(node.left, sb);
              serialize(node.right, sb);
          }
      }
      // Decodes your encoded data to tree.
      public TreeNode deserialize(String data) {
          String[] tokens = data.split(split);
          LinkedList<String> q = new LinkedList<>();
          q.addAll(Arrays.asList(tokens));
          return deserialize(q);
      }
      private TreeNode deserialize(Queue<String> q){
          String token = q.poll();
          if(token.equals(empty)) return null;
          else{
              TreeNode node = new TreeNode(Integer.parseInt(token));
              node.left = deserialize(q);
              node.right = deserialize(q);
              return node;
          }
      }
  }

  // Your Codec object will be instantiated and called as such:
  // Codec codec = new Codec();
  // codec.deserialize(codec.serialize(root));
  ```

* Method 2: Use the property of BST
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
  public class Codec {

      // Encodes a tree to a single string.
      public String serialize(TreeNode root) {
          StringBuilder sb = new StringBuilder();
          dfs(root, sb);
          return sb.toString();
      }
      private void dfs(TreeNode node, StringBuilder sb){
          if(node == null) return;
          else{
              sb.append(node.val).append(",");
              dfs(node.left, sb);
              dfs(node.right, sb);
          }
      }

      // Decodes your encoded data to tree.
      public TreeNode deserialize(String data) {
          if(data.trim().length() == 0) return null;
          String[] tokens = data.split(",");
          TreeNode root = new TreeNode(Integer.parseInt(tokens[0]));
          for(int i = 1; i < tokens.length; i++){
              insert(root, Integer.parseInt(tokens[i]));
          }
          return root;
      }
      private TreeNode insert(TreeNode node, int num){
          if(node == null) return new TreeNode(num);
          else if(num < node.val || node.val == num){
              node.left = insert(node.left, num);
          }else if(num > node.val){
              node.right = insert(node.right, num);
          }
          return node;
      }
  }

  // Your Codec object will be instantiated and called as such:
  // Codec codec = new Codec();
  // codec.deserialize(codec.serialize(root));
  ```

### Second Time
* Method 1: recursion
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
	public class Codec {
		private static final char NULL = '#';
		private static final char split = ',';
		// Encodes a tree to a single string.
		public String serialize(TreeNode root) {
			StringBuilder sb = new StringBuilder();
			serialize(root, sb);
			return sb.toString();
		}
		private void serialize(TreeNode node, StringBuilder sb){
			if(node == null){
				sb.append(NULL).append(split);
				return;
			}else{
				sb.append(node.val).append(split);
				serialize(node.left, sb);
				serialize(node.right, sb);
			}
			
		}
		// Decodes your encoded data to tree.
		public TreeNode deserialize(String data) {
			String[] tokens = data.split(",");
			Queue<String> q = new LinkedList<>();
			for(String token : tokens){
				q.offer(token);
			}
			return deserialize(q);
		}
		private TreeNode deserialize(Queue<String> q){
			String val = q.poll();
			if(val.equals("" + NULL)) return null;
			else{
				TreeNode node = new TreeNode(Integer.parseInt(val));
				node.left = deserialize(q);
				node.right = deserialize(q);
				return node;
			}
		}
	}

	// Your Codec object will be instantiated and called as such:
	// Codec codec = new Codec();
	// codec.deserialize(codec.serialize(root));
	```