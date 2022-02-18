package application;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import application.domain.Interruption;
import application.domain.Thread;
import application.domain.ThreadState;

public class OperationalSystem {
    private String name;
    private String version;
    private Stack<Interruption> interruptions;
    private List<Thread> threads;
    private Timer executor;
    private int current_execution = 0;
    private final int THREAD_LIMIT = 30;
    private final int EXECUTION_LIMIT = 30;
    private final int PERIOD_EXECUTE = 1300;
    private final int DELAY_TO_EXECUTE = 70;

    public OperationalSystem(String name, String version) {
        this.name = name;
        this.version = version;
        this.interruptions = new Stack<>();
        this.threads = new ArrayList<>();
        this.executor = new Timer();
    }

    public void initialize() {
        System.out.println("Initializing " + name + " " + version);

        this.generateThreads();

        var task = new TimerTask(){
            @Override
            public void run(){
                if(++current_execution > EXECUTION_LIMIT) {
                    executor.cancel();

                    System.out.println("\nOperating system " + name + " " + version +  " finished");

                    return;
                }

                execute();
            }
        };

        this.executor.scheduleAtFixedRate(task, DELAY_TO_EXECUTE, PERIOD_EXECUTE);
    }

    public void execute() {
        this.generateIterruption();

        this.handleInterruption();
    }

    private void generateThreads() {
        for(var i = 0; i < THREAD_LIMIT; i++) {
            var thread = new Thread();
            thread.run();

            this.threads.add(thread);
        }
    }

    private void generateIterruption() {
        var threadsRunning = this.threads.stream().filter(t -> t.getState() == ThreadState.RUNNING).collect(Collectors.toList());

        var randomThread = threadsRunning.get((int) (Math.random() * threadsRunning.size()));
        
        var interruption = new Interruption(randomThread.getPid());

        this.interruptions.add(interruption);

        randomThread.block();

        this.threads.add(this.threads.indexOf(randomThread), randomThread);

        this.printGenerateInterruption(interruption, randomThread);
    }

    private void handleInterruption() {
        try {
            TimeUnit.SECONDS.sleep(8);
        } catch(Exception ex) { 
            //ignore 
        }

        var interruption = this.interruptions.pop();

        var thread = this.threads.stream().filter(t -> t.getPid() == interruption.getThreadPid()).findFirst().get();

        thread.unBlock();

        this.threads.add(this.threads.indexOf(thread), thread);

        this.printHandelInterruption(interruption, thread);
    }

    private void printGenerateInterruption(Interruption interruption, Thread thread) {
        System.out.println("\nGenerated interrupt " + interruption.getType() + " for thread " + thread.getPid());
        System.out.println("Thread " + thread.getPid() + " state: " + thread.getState());
    }

    private void printHandelInterruption(Interruption interruption, Thread thread) {
        System.out.println("\nTreated interruption " + interruption.getType() + " for thread " + thread.getPid());
        System.out.println("Thread " + thread.getPid() + " state: " + thread.getState());
    }
}
