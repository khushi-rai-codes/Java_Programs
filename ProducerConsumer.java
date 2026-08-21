class SharedData {
    private int value;
    private boolean available = false;
  synchronized void produce(int value) {
        while (available) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.value = value;
        available = true;
        System.out.println("Produced: " + value);
        notify();
    }
    synchronized void consume() {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Consumed: " + value);
        available = false;
        notify();
    }
}
class Producer extends Thread {
    private SharedData data;
  Producer(SharedData data) {
        this.data = data;
    }
    public void run() {
        for (int i = 1; i <= 5; i++) {
            data.produce(i);
        }
    }
}
class Consumer extends Thread {
    private SharedData data;
    Consumer(SharedData data) {
        this.data = data;
    }
    public void run() {
        for (int i = 1; i <= 5; i++) {
            data.consume();
        }
    }
}public class ProducerConsumer {
    public static void main(String[] args) {
        SharedData data = new SharedData();
        Producer producer = new Producer(data);
        Consumer consumer = new Consumer(data);
        producer.start();
        consumer.start();
    }
}
