package com.triplebro.domineer.takeoutfood.utils.dialogUtils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;

public class SingleChooseDialog extends DialogFragment {

    private DialogInterface.OnClickListener singleChooseCallback;

    private String title;

    private String[] chooseItems;

    public void show(String title, String[] chooseItems, DialogInterface.OnClickListener singleChooseCallback,FragmentManager fragmentManager) {
        this.title = title;
        this.chooseItems = chooseItems;
        this.singleChooseCallback = singleChooseCallback;
        show(fragmentManager, "SingleChooseDialog");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setTitle(title);
        builder.setSingleChoiceItems(chooseItems, -1, singleChooseCallback);
        return builder.create();
    }
}
