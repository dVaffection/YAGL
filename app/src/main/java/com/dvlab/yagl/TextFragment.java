package com.dvlab.yagl;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;

public class TextFragment extends DialogFragment {

    public static final String EXTRA_TEXT = "com.dvlab.criminalintent.text";
    private String text;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_text, null);

        text = getArguments().getString(EXTRA_TEXT);

        final EditText editText = (EditText) view.findViewById(R.id.dialog_text);
        editText.setText(text);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.new_item_dialog_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        text = editText.getText().toString();
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }

    public Bundle createArgs(String text) {
        Bundle args = new Bundle();

        args.putSerializable(EXTRA_TEXT, text);

        return args;
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TEXT, text);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}
