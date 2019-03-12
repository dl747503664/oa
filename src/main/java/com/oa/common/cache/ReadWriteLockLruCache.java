package com.oa.common.cache;

import java.util.LinkedHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import com.sun.swing.internal.plaf.synth.resources.synth;

public class ReadWriteLockLruCache<K,V> {//类泛型
	private int maxSize;
	private LinkedHashMap<K, V> cacheMap;
	//JDK1.5
	private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	//获取读锁
	private ReadLock readLock=readWriteLock.readLock();
	//获取写锁
	private WriteLock writeLock=readWriteLock.writeLock();
	public ReadWriteLockLruCache(int maxSize) {
		this.maxSize=maxSize;
		cacheMap=new LinkedHashMap<K,V>(maxSize,0.75f,true){
		private static final long serialVersionUID = -4982533463846617770L;
		  @Override
		  protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
			return size()>maxSize;
		  }
	    };
	}
	public  V put(K key,V value){//只能有一个现在执行写操作
		try{
		writeLock.lock();
		return cacheMap.put(key, value);
		}finally{
		writeLock.unlock();
		}
	}
	public  V get(K key){//运行多个线程并发读
		try{
		readLock.lock();
		return cacheMap.get(key);
		}finally{
		readLock.unlock();
		}
	}
}




