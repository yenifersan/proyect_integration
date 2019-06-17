package com.santiago.proyect_integration.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiago.proyect_integration.R;


public class Fragment01 extends Fragment {
    private static final String TAG = Fragment01.class.getSimpleName();

    public Fragment01() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.activity_layout_file, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onCreateView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onCreateView");
    }

}
