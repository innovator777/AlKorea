package com.innovator.alkorea.library.utils.timer;

import android.os.Message;

public class GameTimer extends AlKoreaTimer {

  private final String TAG = GameTimer.class.getName();

  private final int startTime = 10000;
  private final int stopTime = 0;
  private final int sleepTime = 10;
  private int time = startTime;

  public GameTimer(android.os.Handler handler, AlKoreaTimerCallbackListener listener) {
    super(handler, listener);
    runnable = setRunnable();
    thread = new Thread(runnable);
  }

  @Override
  public void propertyReset() {
    time = startTime;
  }

  protected Runnable setRunnable() {
    return new Runnable() {
      @Override
      public void run() {
        while (!Thread.currentThread().isInterrupted()) {
          Message message = new Message();
          message.arg1 = time;
          handler.sendMessage(message);
          try {
            thread.sleep(sleepTime);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          if (time == stopTime) {
            thread.interrupt();
            listener.endTimer(TAG);
          }
          time -= sleepTime;
        }
      }
    };
  }
}