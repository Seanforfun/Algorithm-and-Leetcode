# Question9_10

#### Solution
* 放箱子问题， 未经过测试
```Java
package ca.mcmaster.offer;

import java.util.ArrayList;
import java.util.List;

public class Question9_10 {
	public static class Box{
		int w;
		int h;
		int d;
		public Box(int w, int h, int d){
			this.w = w;
			this.h = h;
			this.d = d;
		}
	}
	public static void getPossibleOrders(Box[] box, Box bottom, ArrayList<Box> order, boolean[] used, List<ArrayList<Box>> result){
		if(order.size() == box.length){
			result.add(order);
			return;
		}
		for(int i = 0; i < used.length; i++){
			if(!used[i]){
				boolean[] cloneUsed = used.clone();
				cloneUsed[i] = true;
				ArrayList<Box> copyBoxs = new ArrayList<>();
				for(Box b : order)
					copyBoxs.add(b);
				if(copyBoxs.size() == 0 || canPutAbove(bottom, box[i])){
					copyBoxs.add(box[i]);
					getPossibleOrders(box, box[i], copyBoxs, cloneUsed, result);
				}else{
					result.add(order);
					return;
				}
			}
		}
	}
	private static boolean canPutAbove(Box b1, Box b2){
		if(b1.w > b2.w && b1.h > b2.h && b1.d > b2.d)
			return true;
		return false;
	}
}

```
