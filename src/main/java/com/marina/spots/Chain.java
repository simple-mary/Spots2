package com.marina.spots;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Marry on 22.09.2016.
 */
public class Chain {


    java.util.Queue queue = new LinkedList<Peak>();
    HashSet<java.util.Queue<Peak>> queues = new HashSet<java.util.Queue<Peak>>();

    public java.util.Queue getQueue() {
        return queue;
    }

    public void setQueue(java.util.Queue queue) {
        this.queue = queue;
    }

    public void setQueues(HashSet<java.util.Queue<Peak>> queues) {
        this.queues = queues;
    }



    public HashSet<java.util.Queue<Peak>> getQueues() {
        return queues;
    }
}
