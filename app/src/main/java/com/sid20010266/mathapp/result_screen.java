package com.sid20010266.mathapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class result_screen extends Fragment {

    private View view;
    private String mode;
    private int num1;
    private  int num2;
    private int result;
    private String resText;
    private TextView resultText;
    ArrayList<View> stars = new ArrayList<>();

    public result_screen() {
        // Required empty public constructor
    }


    // ON CREATE ---------------------------------------------------------------------------------------->
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    // -------------------------------------------------------------------------------------------------->


    // ON CREATE VIEW ----------------------------------------------------------------------------------->
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_result_screen, container, false);
        num1 = getArguments().getInt("num1");
        num2 = getArguments().getInt("num2");
        mode = getArguments().getString("mode");
        result = getArguments().getInt("result");

        // ----------------- [ STARS ] ----------------- \\
        stars.add(0, view.findViewById(R.id.star_1));
        stars.add(1, view.findViewById(R.id.star_2));
        stars.add(2, view.findViewById(R.id.star_3));
        stars.add(3, view.findViewById(R.id.star_4));
        stars.add(4, view.findViewById(R.id.star_5));
        stars.add(5, view.findViewById(R.id.star_6));
        stars.add(6, view.findViewById(R.id.star_7));
        stars.add(7, view.findViewById(R.id.star_8));

        ArrayList<Integer> viewStar = new ArrayList<>();

        // Hide all stars
        for (View star : stars) { star.setVisibility(star.INVISIBLE); }

        // generate 4 random numbers as star index
        for (int x = 0; x <= 4; x++) {
            int random = rand(0, 7);
            while (viewStar.contains(random) == true) { random = rand(0, 7); }
            viewStar.add(random);
        }

        // Animate 4 visible random stars
        for (Integer use : viewStar) {
            stars.get(use).setVisibility(stars.get(use).VISIBLE);

            // Spin animation
            Animation spinStarAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.spin_star_anim);

            // Flickering animation
            Animation flickerStarAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.flicker_anim);
            flickerStarAnim.setDuration(rand(1000, 3000)); // random duration for flicker effect

            // Animation set
            AnimationSet starAnim = new AnimationSet(true);
            starAnim.addAnimation(spinStarAnim);
            starAnim.addAnimation(flickerStarAnim);

            // Assign animation set to star
            stars.get(use).startAnimation(starAnim);
        }
        // --------------------------------------------- \\

        resultText = (TextView) view.findViewById(R.id.resultText);
        resultText.setTextColor(Color.parseColor("#0ce823"));

        if (mode.equals("add")) {
            resText = num1 + " + " + num2 + " = " + result;
        } else if (mode.equals("subtract")) {
            if (num1 >= num2) {
                resText = num1 + " - " + num2 + " = " + result;
            } else {
                resText = num2 + " - " + num1 + " = " + result;
            }
        }

        resultText.setText(resText);

        view.findViewById(R.id.playAgainBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle(); // hold arguments
                bundle.putString("mode", mode);
                bundle.putBoolean("countDown", false);
                Navigation.findNavController(view).navigate(R.id.action_result_screen_to_game_screen, bundle);
            }
        });
        return view;
    }
    // -------------------------------------------------------------------------------------------------->


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
                    // DEBUG: Toast toast = Toast.makeText(getActivity(), "Back button pressed", Toast.LENGTH_SHORT);
                    // DEBUG: toast.show();

                    // Argument passing: go back to game screen
                    Bundle bundle = new Bundle();
                    bundle.putString("mode", mode);
                    // bundle.putBoolean("countDown", true); // Toggle countdown
                    Navigation.findNavController(view).navigate(R.id.action_result_screen_to_game_screen, bundle);
                    return true;
                }
                return false;
            }
        });
    }
    // -------------------------------------------------------------------------------------------------->


    // RANDOM INT GENERATOR ----------------------------------------------------------------------------->
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int rand(int min, int max)
    {
        if (min > max) { throw new IllegalArgumentException("Invalid range"); }
        Random rand = new Random();
        return rand.ints(min, (max + 1)).findFirst().getAsInt();
    }


    // PROGRAMMATIC ANIMATIONS --------------------------------------------------------------------------->
    public RotateAnimation spinStar() {
        RotateAnimation spin = new RotateAnimation(0, 360, 100, 100);
        spin.setDuration(30000);
        spin.setRepeatCount(0);
        spin.setInterpolator(new LinearInterpolator());
        return spin;
    }
    // -------------------------------------------------------------------------------------------------->
}