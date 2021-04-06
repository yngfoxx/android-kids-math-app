package com.sid20010266.mathapp;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class start_screen extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        final Button playBtn = view.findViewById(R.id.playBtn);
        final Animation bounceAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce_anim);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clk_view) {
                playBtn.startAnimation(bounceAnim);
                Navigation.findNavController(clk_view).navigate(R.id.start_to_mode_menu);
            }
        });

        MainActivity.bgMusic.start();
        return view;
    }

    // ON RESUME ---------------------------------------------------------------------------------------->
    @Override
    //Pressed return button - returns to the results menu
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    // exit app when back button is pressed in start screen
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });
    }

    // -------------------------------------------------------------------------------------------------->
}