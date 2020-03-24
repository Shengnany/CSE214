/**
 * This class represents an Exception when the queue is empty
 */
package hw4;
public class EmptyQueueException extends Exception {
    EmptyQueueException(){
        super("The queue is empty.");
    }
}


