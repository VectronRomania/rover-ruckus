package org.firstinspires.ftc.teamcode.systems.autonomous;

public abstract class Task extends Thread{

    protected volatile Boolean isStopRequested = false;

    protected BackgroundChecker backgroundChecker;

    public Task() {
        backgroundChecker = new BackgroundChecker();
    }

    @Override
    public abstract void run();

    public abstract void onStopRequested();

    public void sendStopRequested() {
        isStopRequested = true;
    }
}
