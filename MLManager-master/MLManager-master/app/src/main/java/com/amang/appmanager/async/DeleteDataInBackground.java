package com.amang.appmanager.async;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amang.appmanager.R;
import com.amang.appmanager.utils.UtilsApp;
import com.amang.appmanager.utils.UtilsDialog;
import com.amang.appmanager.utils.UtilsRoot;

public class DeleteDataInBackground extends AsyncTask<Void, String, Boolean> {
    private Context context;
    private Activity activity;
    private MaterialDialog dialog;
    private String directory;
    private String successDescription;

    public DeleteDataInBackground(Context context, MaterialDialog dialog, String directory, String successDescription) {
        this.context = context;
        this.activity = (Activity) context;
        this.dialog = dialog;
        this.directory = directory;
        this.successDescription = successDescription;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Boolean status = false;

        if (UtilsApp.checkPermissions(activity)) {
            status = UtilsRoot.removeWithRootPermission(directory);
        }

        return status;
    }

    @Override
    protected void onPostExecute(Boolean status) {
        super.onPostExecute(status);
        dialog.dismiss();
        if (status) {
            UtilsDialog.showSnackbar(activity, successDescription, null, null, 2).show();
        } else {
            UtilsDialog.showTitleContent(context, context.getResources().getString(R.string.dialog_root_required), context.getResources().getString(R.string.dialog_root_required_description));
        }
    }
}