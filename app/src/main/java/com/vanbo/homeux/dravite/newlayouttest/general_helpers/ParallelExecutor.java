// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.general_helpers;

import java.util.LinkedList;

public class ParallelExecutor
{
    private final LinkedList<Runnable> mQueue;
    private ParallelWorker[] mWorkers;
    
    public ParallelExecutor(int i) {
        this.mQueue = new LinkedList<Runnable>();
        this.mWorkers = new ParallelWorker[i];
        for (i = 0; i < this.mWorkers.length; ++i) {
            (this.mWorkers[i] = new ParallelWorker(this)).start();
        }
    }
    
    public void enqueue(final Runnable runnable) {
        synchronized (this.mQueue) {
            this.mQueue.add(runnable);
            this.mQueue.notify();
        }
    }
    
    public void stopAll() {
        synchronized (this.mQueue) {
            final ParallelWorker[] mWorkers = this.mWorkers;
            for (int length = mWorkers.length, i = 0; i < length; ++i) {
                mWorkers[i].cancel();
            }
            this.mQueue.notifyAll();
            this.mQueue.clear();
        }
    }
    
    public class ParallelWorker extends Thread
    {
        private boolean isRunning;
        private ParallelExecutor mPe;
        
        public ParallelWorker(ParallelExecutor pe) {
            mPe = pe;
            this.isRunning = true;
            this.setPriority(10);
        }
        
        public void cancel() {
            this.isRunning = false;
        }
        
        @Override
        public void run() {
            LinkedList<Runnable> enqueue = mPe.mQueue;
            do {
                synchronized (enqueue) {
                    boolean isEmpty = enqueue.isEmpty();
                    if (isEmpty) {
                        try {
                            enqueue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (isRunning) {
                            Runnable work = enqueue.removeFirst();
                            work.run();
                        }
                    }
                }
            }while(isRunning);
        }
    }
}
