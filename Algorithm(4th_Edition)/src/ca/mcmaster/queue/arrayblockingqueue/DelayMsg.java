package ca.mcmaster.queue.arrayblockingqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayMsg<V> implements Delayed{
	private Long expire;
	private Long insertTime;
	private V v;
	
	public V getV() {
		return v;
	}
	public Long getInsertTime() {
		return insertTime;
	}
	public DelayMsg(Long expire, Long insertTime, V v){
		this.expire = expire;
		this.insertTime = insertTime;
		this.v = v;
	}
	@Override
	public int compareTo(Delayed o) {
		@SuppressWarnings("rawtypes")
		Long delta = insertTime - ((DelayMsg)o).getInsertTime();
		if(delta < 0) return -1;
		else if(delta == 0) return 0;
		else
			return 1;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Delayed#getDelay(java.util.concurrent.TimeUnit)
	 * 用于定义延时策略
	 */
	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}
}
