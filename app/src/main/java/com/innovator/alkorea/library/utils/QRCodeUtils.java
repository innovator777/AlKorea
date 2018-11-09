package com.innovator.alkorea.library.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeUtils {

  public static void startQRCode(Activity activity) {
    new IntentIntegrator(activity).initiateScan();
  }

  public static Bitmap generateRQCode(String contents) {
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    Bitmap bitmap = null;
    try {
      bitmap = toBitmap(qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, 300, 300));
    } catch (WriterException e) {
      e.printStackTrace();
    }
    return bitmap;
  }

  public static Bitmap toBitmap(BitMatrix matrix) {
    int height = matrix.getHeight();
    int width = matrix.getWidth();
    Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
      }
    }
    return bmp;
  }
}
