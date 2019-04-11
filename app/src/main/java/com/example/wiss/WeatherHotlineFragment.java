package com.example.wiss;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.net.Uri;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class WeatherHotlineFragment extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_hotlines, container, false);
        ((Button) view.findViewById(R.id.button_neh)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.button_dswd1)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.button_dswd2)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.button_dswd3)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.button_dswd4)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.button_prc1)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.button_prc2)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.button_prc3)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.button_prc4)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.button_prc5)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.button_prc6)).setOnClickListener(this);



        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        return view;
    }
    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_neh:
                dialContactPhone("911");
                break;
            case R.id.button_dswd1:
                dialContactPhone("09189122813");
                break;
            case R.id.button_dswd2:
                dialContactPhone("(02)9318101");
                break;
            case R.id.button_dswd3:
                dialContactPhone("8563665");
                break;
            case R.id.button_dswd4:
                dialContactPhone("8528081");
                break;
            case R.id.button_prc1:
                dialContactPhone("143");
                break;
            case R.id.button_prc2:
                dialContactPhone("(02)5270000");
                break;
            case R.id.button_prc3:
                dialContactPhone("(02)5278385");
                break;
            case R.id.button_prc4:
                dialContactPhone("134");
                break;
            case R.id.button_prc5:
                dialContactPhone("132");
                break;
            case R.id.button_prc6:
                dialContactPhone("133");
                break;
            case R.id.button_mmda1:
                dialContactPhone("136");
                break;
            case R.id.button_mmda2:
                dialContactPhone("(02)882415077");
                break;
            case R.id.button_mmda3:
                dialContactPhone("337");
                break;
            case R.id.button_mmda4:
                dialContactPhone("(02)8820925");
                break;
        }
    }
}
