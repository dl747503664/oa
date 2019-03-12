package com.oa.common.cache;

import java.util.LinkedHashMap;

import com.sun.swing.internal.plaf.synth.resources.synth;

/**
 * 假设此类是一个缓存对象：
 * 此对象如何设计？基于LRU算法
 * 何为LRU算法？最近最少使用算法
 * 基于LinkedHashMap实现一个Lru算的缓存对象设计
 * 例如LruCache.
 */
public class SynchronizedLruCache<K,V> extends LinkedHashMap<K, V>{
	private static final long serialVersionUID = 9037171816206279789L;
	private int maxSize;
	public SynchronizedLruCache(int maxSize) {
		super(maxSize,.75f,true);
		this.maxSize=maxSize;
	}
	@Override
	protected synchronized boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		return size()>maxSize;
	}
	//悲观锁(别人只能等者)
	//排他锁(有一个线程获取锁对象以后其它线程只能等待)
	@Override
	public synchronized V put(K key, V value) {
		// TODO Auto-generated method stub
		return super.put(key, value);
	}
	@Override
	public synchronized V get(Object key) {
		// TODO Auto-generated method stub
		return super.get(key);
	}
	
	
}
