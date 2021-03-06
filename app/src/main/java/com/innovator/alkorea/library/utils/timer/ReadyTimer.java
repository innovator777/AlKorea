package com.innovator.alkorea.library.utils.timer;

import android.os.Handler;
import android.os.Message;

public class ReadyTimer extends AlKoreaTimer {

  private final String TAG = ReadyTimer.class.getName();

  private final int startCount = 0;
  private final int stopCount = 4;
  private final int decreaseCount = 1;
  private final int sleepTime = 1000;
  private int count = startCount;


  public ReadyTimer(Handler handler, AlKoreaTimerCallbackListener listener) {
    super(handler, listener);
    runnable = setRunnable();
    thread = new Thread(runnable);
  }

  @Override
  public void propertyReset() {
    count = startCount;
  }


  protected Runnable setRunnable() {
    return new Runnable() {
      @Override
      public void run() {
        while (!Thread.currentThread().isInterrupted()) {
          Message message = new Message();
          message.arg1 = count;
          handler.sendMessage(message);
          try {
            thread.sleep(sleepTime);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          count += decreaseCount;
          if (count == stopCount) {
            thread.interrupt();
            listener.endTimer(TAG);
          }
        }
      }
    };
  }
}
