package com.meeple.meeple.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;

public class DialogMaker {
    private AlertDialog.Builder alertBuilder;
    private ProgressDialog.Builder progressBuilder;

    public DialogMaker(Activity activity) {
        this.alertBuilder = new AlertDialog.Builder(activity);
        this.progressBuilder = new ProgressDialog.Builder(activity);
    }

    public AlertDialog getAlert(String title, String message) {
        alertBuilder.setMessage(message);
        alertBuilder.setTitle(title);
        return alertBuilder.create();
    }

    public AlertDialog getProgress() {
        progressBuilder.setTitle("title");
        progressBuilder.setMessage("message");
        return progressBuilder.create();
    }
}
