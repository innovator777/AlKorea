package com.innovator.alkorea.library.utils;

import android.os.Handler;
import android.util.Log;

abstract public class AlKoreaTimer {

  abstract public void propertyReset();
  abstract protected Runnable setRunnable();

  private final String TAG = AlKoreaTimer.class.getName();

  protected Handler handler;
  protected AlKoreaTimerCallbackListener listener;
  protected Thread thread;
  protected Runnable runnable;

  public AlKoreaTimer(Handler handler, AlKoreaTimerCallbackListener listener) {
    this.handler = handler;
    this.listener = listener;
  }

  public void startTimer() {
    Log.i(TAG, thread.getState().toString());
    if (thread.getState() == Thread.State.NEW) {
      thread.start();
    } else if (thread.getState() == Thread.State.TERMINATED) {
      thread = null;
      propertyReset();
      thread = new Thread(runnable);
      thread.start();
    }
  }

  public void stopTimer() {
    thread.interrupt();
  }
}
