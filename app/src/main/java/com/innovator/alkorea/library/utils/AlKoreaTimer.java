package com.innovator.alkorea.library.utils;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

//Create innovator(JongChan Yang)
public class AlKoreaTimer {

  private final String TAG = AlKoreaTimer.class.getName();

  private AlKoreaTimerCallbackListener callbackListener;

  private Timer workingTimer, stopTimer;
  private TimerTask workingTimerTask, stopTimerTask;

  private int during = 0;
  private int delay = 0;
  private int period = 0;

  public AlKoreaTimer(TimerTask timerTask, int during, int delay, int period) {
    workingTimer = new Timer();
    this.workingTimerTask = timerTask;

    stopTimer = new Timer();
    stopTimerTask = new TimerTask() {
      @Override
      public void run() {
        stop();
      }
    };
    this.during = during;
    this.delay = delay;
    this.period = period;
  }

  public void start() {
    if (workingTimerTask != null)
      workingTimer.schedule(workingTimerTask, delay, period);

    if (stopTimerTask != null)
      stopTimer.schedule(stopTimerTask, during, during);
  }

  public void stop() {
    if (workingTimerTask != null)
      workingTimerTask.cancel();

    if (stopTimerTask != null)
      stopTimerTask.cancel();

    if (callbackListener != null)
      callbackListener.stopTimer();
  }

  public void setWorkingTimerTask(TimerTask timerTask) {
    this.workingTimerTask = timerTask;
  }

  public TimerTask getWorkingTimerTask() {
    return workingTimerTask;
  }

  public void setDuring(int during) {
    this.during = during;
  }

  public int getDuring(){
    return during;
  }

  public void setDelay(int delay) {
    this.delay = delay;
  }

  public int getDelay() {
    return delay;
  }

  public void setPeriod(int period) {
    this.period = period;
  }

  public int getPeriod(){
    return period;
  }

  public void setCallbackListener(AlKoreaTimerCallbackListener callback) {
    this.callbackListener = callback;
  }

  public AlKoreaTimerCallbackListener getCallbackListener() {
    return callbackListener;
  }
}
