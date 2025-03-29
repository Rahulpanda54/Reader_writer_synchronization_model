import java.util.concurrent.locks.ReentrantReadWriteLock;

public class reader_writer_problem{

    private int sharedData = 0;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    // Reader task
    class Reader extends Thread{
        private String readername;

        Reader(String readername){
           this.readername = readername;
        }

        public void run(){
            lock.readLock().lock();

            //acquire the read lock

            try{
                System.out.println(readername  + "   is reading  " + sharedData);

                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            finally{
                lock.readLock().unlock();
            }
        }
    }

    // writer task

    class writer extends Thread{
        private String writername;
        private int newvalue;

        writer(String name, int val){
            this.writername = name;
            this.newvalue = val;
        }

        public void run(){
            lock.writeLock().lock();

            // acquire lock

            try{
                System.out.println(writername + "  is writing newdata is  " + newvalue);

                sharedData = newvalue;
                Thread.sleep(1000);

                System.out.println(writername + " has finished writing  ");
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            finally{
                lock.writeLock().unlock();
            }
        }
    }


    public static void main(String[] args) {
        reader_writer_problem problem = new reader_writer_problem();

        Reader r1 = problem.new Reader("reader 1");
        Reader r2 = problem.new Reader("reader 2");

         writer w1 = problem.new writer("writer 1", 10);
         writer w2 = problem.new writer("writer 2", 20);

         r1.start();
         w1.start();
         r2.start();
         w2.start();

         try{
            r1.join();
            w1.join();
            r2.join();
            w2.join();
         }catch(InterruptedException e){
            e.printStackTrace();
         }
    }
}