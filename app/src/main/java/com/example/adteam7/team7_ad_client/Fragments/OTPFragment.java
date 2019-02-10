package com.example.adteam7.team7_ad_client.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.activities.DisbDetailAckActivity;
import com.jkb.vcedittext.VerificationCodeEditText;

/**
 * Created by Kay Thi Swe Tun
 **/
public class OTPFragment extends DialogFragment {

    public static final String RESULT = "RESULT";
    String otp;

    public OTPFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.confirm_item_otp, container, false);

        Button cancel = (Button) rootView.findViewById(R.id.cancel);
        Button confirm = rootView.findViewById(R.id.confirm);
        VerificationCodeEditText confirmOTP = rootView.findViewById(R.id.vercode_otp);
        if (getArguments().getString("OTP") != null) {
            otp = getArguments().getString("OTP");
        }
        Toast.makeText(this.getContext(), "" + getArguments().getString("OTP"), Toast.LENGTH_SHORT).show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (confirmOTP.getText().toString().equals(otp)) {
                    Toast.makeText(OTPFragment.this.getContext(), "Check ! correct", Toast.LENGTH_SHORT).show();
                    DisbDetailAckActivity p = (DisbDetailAckActivity) getActivity();
                    Intent i = new Intent()
                            .putExtra(RESULT, otp);

                    p.onActivityResult(12, Activity.RESULT_OK, i);
                    dismiss();
                }


            }
        });

        return rootView;
    }
}
