# Question3_7

#### Solution
```Java
public class Question3_7 {
	private int order = 0;
	private LinkedList<CatWrapper> cats = new LinkedList<>();
	private LinkedList<DogWrapper> dogs = new LinkedList<>();
	private class DogWrapper{
		private int order;
		private Animal dog;
		public DogWrapper(int order, Animal dog) {
			this.order = order;
			this.dog = dog;
		}
	}
	private class CatWrapper{
		private int order;
		private Animal cat;
		public CatWrapper(int order, Animal cat) {
			this.order = order;
			this.cat = cat;
		}
	}
	public void enqueue(Animal animal){
		if(animal instanceof Cat){
			cats.add(new CatWrapper(order, animal));
		}else
			dogs.add(new DogWrapper(order, animal));
		order++;
	}
	public Dog dequeueDog(){
		return (Dog)dogs.poll().dog;
	}
	public Cat dequeueCat(){
		return (Cat)cats.poll().cat;
	}
	public Animal dequeueAny(){
		DogWrapper dog = dogs.getFirst();
		CatWrapper cat = cats.getFirst();
		if(dog.order < cat.order)
			return dequeueDog();
		else
			return dequeueCat();
	}
	public static void main(String[] args) {
		Question3_7 queue = new Question3_7();
		queue.enqueue(new Cat());
		queue.enqueue(new Cat());
		queue.enqueue(new Cat());
		queue.enqueue(new Dog());
		System.out.println(queue.dequeueDog());
	}
}

class Dog extends Animal{
}
class Cat extends Animal{
}
class Animal{
}
```
