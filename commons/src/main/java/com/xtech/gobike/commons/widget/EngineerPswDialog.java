package com.xtech.gobike.commons.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtech.gobike.commons.R;

/**
 *
 */

public class EngineerPswDialog extends Dialog {
    private EditText et1, et2, et3, et4;
    private ImageView ivClose;
    private TextView tvRemind;
    private EngineerDialogListener engineerDialogListener;

    public EngineerPswDialog(@NonNull Context context) {
        super(context, R.style.DialogStyle);
    }

    public void setListener(final EngineerDialogListener engineerDialogListener) {
        this.engineerDialogListener = engineerDialogListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_engineer_psw);
        et1 = (EditText) findViewById(R.id.et_1);
        et2 = (EditText) findViewById(R.id.et_2);
        et3 = (EditText) findViewById(R.id.et_3);
        et4 = (EditText) findViewById(R.id.et_4);
        ivClose = (ImageView) findViewById(R.id.close);
        tvRemind = (TextView) findViewById(R.id.tv_remind);

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                engineerDialogListener.onFailed();
            }
        });

        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et1.setText("");
                et2.setText("");
                et3.setText("");
                et4.setText("");
                et1.setFocusable(true);
                et1.setFocusableInTouchMode(true);
                et1.requestFocus();
            }
        });

        final EditText[] editTexts = {et1, et2, et3, et4};
        for (int i = 0; i < editTexts.length; i++) {
            final int index = i;
            editTexts[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 0) {
                        int focusIndex = index - 1;
                        if (focusIndex < editTexts.length && focusIndex > 0) {
                            editTexts[focusIndex].setFocusable(true);
                            editTexts[focusIndex].setFocusableInTouchMode(true);
                            editTexts[focusIndex].requestFocus();
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (isValid(editTexts)) {
                        engineerDialogListener.onSucceed();
                        return;
                    }

                    if (isLengthValid(editTexts)) {
                        tvRemind.setText("密码有误,请重新输入或关闭窗口");
                        return;
                    }

                    if (index + 1 < editTexts.length) {
                        editTexts[index + 1].setFocusable(true);
                        editTexts[index + 1].setFocusableInTouchMode(true);
                        editTexts[index + 1].requestFocus();
                    }
                }
            });
        }

    }

    private boolean isValid(EditText[] editTexts) {
        String res = "";
        for (int i = 0; i < editTexts.length; i++) {
            res += editTexts[i].getText().toString();
        }
        return "1234".equals(res);
    }

    private boolean isLengthValid(EditText[] editTexts) {
        String res = "";
        for (int i = 0; i < editTexts.length; i++) {
            res += editTexts[i].getText().toString();
        }
        return res.length() == 4;
    }

    public interface EngineerDialogListener {
        void onSucceed();

        void onFailed();
    }
}