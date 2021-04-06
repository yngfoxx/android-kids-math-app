package com.sid20010266.mathapp;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pedromassango.doubleclick.DoubleClick;
import com.pedromassango.doubleclick.DoubleClickListener;

import org.json.JSONObject;

import java.util.Random;
import java.util.ArrayList;

import static android.graphics.Color.rgb;

public class game_screen extends Fragment {

    // GAME SCREEN VARIABLES ---------------------------------------------------------------------------->
    private int num1 = 0; // first number can have values in range of 0 - 5
    private int num2 = 0; // second number can have values in range of 0 - 4
    private int result; // stores the result of the random question
    private String mode;
    private TextView question;
    private String questionText;
    private View view;
    public int countDownNum = 3;
    private TextView countDownText;
    // [ ARRAY LISTS ]
    ArrayList<View> appleBtn = new ArrayList<>();
    ArrayList<View> numPad = new ArrayList<>();
    ArrayList<String[]> appleTrans = new ArrayList<>(); // Store all apple translate in array list
    // -------------------------------------------------------------------------------------------------->

    public game_screen() {
        // Required empty public constructor
    }

    // ON CREATE ---------------------------------------------------------------------------------------->
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    // -------------------------------------------------------------------------------------------------->

    // ON CREATE VIEW ----------------------------------------------------------------------------------->
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //----------------------------- [ Get view and button elements ] -----------------------------\\
        view = inflater.inflate(R.layout.fragment_game_screen, container, false);
        question = view.findViewById(R.id.question_txt);
        question.setTextColor(Color.parseColor("#ffffff"));
        mode = getArguments().getString("mode");
        if (mode == null) mode = "add"; // default mode

        question.setText(""); // clear any default text
        question.setVisibility(View.INVISIBLE);
        countDownText = view.findViewById(R.id.countDown);

            //-------------------------------> NUM PAD BUTTONS <-------------------------------\\
            numPad.add(0, view.findViewById(R.id.num_btn_0));
            numPad.add(1, view.findViewById(R.id.num_btn_1));
            numPad.add(2, view.findViewById(R.id.num_btn_2));
            numPad.add(3, view.findViewById(R.id.num_btn_3));
            numPad.add(4, view.findViewById(R.id.num_btn_4));
            numPad.add(5, view.findViewById(R.id.num_btn_5));
            numPad.add(6, view.findViewById(R.id.num_btn_6));
            numPad.add(7, view.findViewById(R.id.num_btn_7));
            numPad.add(8, view.findViewById(R.id.num_btn_8));
            numPad.add(9, view.findViewById(R.id.num_btn_9));
            for (int x = 0; x <= 9; x++) {
                final int finalX = x;
                numPad.get(x).setOnClickListener(new View.OnClickListener(){ @Override public void onClick(View v) {
                    // DEBUG: MediaPlayer btnsound = MediaPlayer.create(getActivity(), R.raw.numpad_sound);
                    // DEBUG: btnsound.seekTo(100);
                    // DEBUG: btnsound.start();
                    btnPressed(finalX);
                    // DEBUG: Animation bounce = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce_anim);
                    // DEBUG: numPad.get(finalX).startAnimation(bounce);
                } });
            }
            //---------------------------------------------------------------------------------\\

            //----------------------> APPLE BUTTONS <----------------------\\
            appleBtn.add(0, view.findViewById(R.id.apple_1));
            appleBtn.add(1, view.findViewById(R.id.apple_2));
            appleBtn.add(2, view.findViewById(R.id.apple_3));
            appleBtn.add(3, view.findViewById(R.id.apple_4));
            appleBtn.add(4, view.findViewById(R.id.apple_5));
            appleBtn.add(5, view.findViewById(R.id.apple_6));
            appleBtn.add(6, view.findViewById(R.id.apple_7));
            appleBtn.add(7, view.findViewById(R.id.apple_8));
            appleBtn.add(8, view.findViewById(R.id.apple_9));
            //-------------------------------------------------------------\\

            //----------------------> WHITE PLATE <----------------------\\
            view.findViewById(R.id.white_plate).setOnClickListener(new DoubleClick(new DoubleClickListener() {
                @Override
                public void onSingleClick(View view) {
                    // single click checker
                    // DEBUG: Toast toast = Toast.makeText(getActivity(), "Single click", Toast.LENGTH_SHORT);
                    // DEBUG: toast.show();
                }

                @Override
                public void onDoubleClick(View view) {
                    /////////////// MOVE APPLES BACK TO PLATE ///////////////
                    for (int x = 0; x < appleBtn.size(); x++) {
                        appleBtn.get(x).animate().x(Float.parseFloat(appleTrans.get(x)[0]));
                        appleBtn.get(x).animate().y(Float.parseFloat(appleTrans.get(x)[1]));
                    }
                    /////////////////////////////////////////////////////////
                }
            }));
            //-----------------------------------------------------------\\

        // -------------------------------------------------------------------------------------------\\


        //------------------------------------- [ Countdown timer ] -------------------------------------\\
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        CountDownTimer appleTranslate = new CountDownTimer(1000, 1000) {
            // Get default location of apples
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                // Get all apples position on screen
                for (View btn : appleBtn) {
                    btn.setOnTouchListener(touchHandler);
                    int [] cord = new int[2];
                    btn.getLocationOnScreen(cord);
                    appleTrans.add(new String[] {
                            String.valueOf(cord[0]),
                            String.valueOf((cord[1] - 70))
                    });
                }
            }
        };
        appleTranslate.start(); // get translate location of all apples
        //////////////////////////////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////////////////////////////////////////
        if (getArguments().getBoolean("countDown")) {
            // Play countdown sound
            MediaPlayer countDwnSound = MediaPlayer.create(getActivity(), R.raw.countdown);
            countDwnSound.seekTo(950);
            countDwnSound.start();

            new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilFinished){
                    countDownText.setText(String.valueOf(countDownNum));
                    if (countDownNum > 2) countDownText.setBackgroundColor(Color.RED);
                    if (countDownNum <= 2) countDownText.setBackgroundColor(rgb(230, 230, 0));
                    countDownNum -= 1; // count down not up!!!
                }
                @Override
                public void onFinish() {
                    countDownText.setBackgroundColor(Color.GREEN);
                    String startTxt = "SOLVE!";
                    countDownText.setText(startTxt);
                    Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
                    countDownText.startAnimation(fadeOut);
                    fadeOut.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) { }
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            // hide countdown timer
                            countDownText.setVisibility(View.INVISIBLE);

                            // show question
                            question.setVisibility(View.VISIBLE);
                            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
                            question.startAnimation(fadeIn);
                        }
                        @Override
                        public void onAnimationRepeat(Animation animation) {  }
                    });
                }
            }.start();
        } else {
            // hide countdown timer
            countDownText.setVisibility(View.INVISIBLE);

            // show question
            question.setVisibility(View.VISIBLE);
            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            question.startAnimation(fadeIn);
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        //----------------------------------------------------------------------------------------------\\


        //--------------------------[ QUESTION FORMAT GENERATOR ]--------------------------\\
        if (getArguments() != null ) {
            reset(mode); // reset question and answer
            if (mode.equals("add")) {
                questionText = num1 + " + " + num2 + " = ?";
            } else if (mode.equals("subtract")) {
                if (num1 >= num2) {
                    questionText = num1 + " - " + num2 + " = ?";
                } else {
                    questionText = num2 + " - " + num1 + " = ?";
                }
            }
        }
        question.setText(questionText);
        //----------------------------------------------------------------------------------\\

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
                    // go back to mode menu screen
                    Navigation.findNavController(view).navigate(R.id.action_game_screen_to_mode_menu);
                    return true;
                }
                return false;
            }
        });
    }
    // -------------------------------------------------------------------------------------------------->


    // DRAGGABLE VIEW METHOD ---------------------------------------------------------------------------->
    private View.OnTouchListener touchHandler = new View.OnTouchListener() {
        float dX, dY;
        @Override
        public boolean onTouch(View v, MotionEvent e) {
            view.performClick();
            switch (e.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    dX = v.getX() - e.getRawX();
                    dY = v.getY() - e.getRawY();
                    break;

                case MotionEvent.ACTION_MOVE:
                // v.animate().x(e.getRawX() + dX).y(e.getRawX() + dY).setDuration(0).start(); <-- SORRY BUT THIS DOES NOT WORK WELL FOR ME
                    // new touch position
                    float newY = e.getRawY() + dY;
                    float newX = e.getRawX() + dX;

                    // update touch position with views position
                    if (newY >= 950 && newY <= 1700) v.setY(newY);
                    if (newX >= 14 && newX <= 820) v.setX(newX);

                    break;
                default:
                    return false;
            }
            return true;
        }
    };
    // -------------------------------------------------------------------------------------------------->


    // RESET METHOD ------------------------------------------------------------------------------------->
    public int reset(String mathMode) {
        num1 = new Random().nextInt(5); // new value
        num2 = new Random().nextInt(4); // new value
        if (mathMode.equals("add")) {
            result = add(num1, num2); // new result
        } else if (mathMode.equals("subtract")) {
            // making sure first number is greater than second number
            if (num1 >= num2) {
                result = subtract(num1, num2);
            } else {
                result = subtract(num2, num1);
            }
        }
        return 1;
    }
    // -------------------------------------------------------------------------------------------------->


    // NUM KEY PRESSED ---------------------------------------------------------------------------------->
    public int btnPressed(int value) {
        // DEBUG: Context context = getActivity();
        // DEBUG: int duration = Toast.LENGTH_SHORT;

        if (value != result) { // Incorrect answer
            // Play error sound
            MediaPlayer errSound = MediaPlayer.create(getActivity(), R.raw.error);
            errSound.start();

            // Style and animate question
            question.setTextColor(Color.parseColor("#e80c0c"));
            question.startAnimation(shakeError());

            return 0; // exit method
        }

        // Play correct sound
        MediaPlayer correctSound = MediaPlayer.create(getActivity(), R.raw.correct);
        correctSound.start();
        // Correct answer
        question.setTextColor(Color.parseColor("#0ce823"));

        // DEBUG: Toast toast = Toast.makeText(context, "Correct", duration);
        // DEBUG: toast.show();

        // store variables in bundle
        Bundle bundle = new Bundle();
        bundle.putInt("num1", num1);
        bundle.putInt("num2", num2);
        bundle.putString("mode", mode);
        bundle.putInt("result", result);

        // navigate to result screen with question and answer
        Navigation.findNavController(view).navigate(R.id.action_game_screen_to_result_screen, bundle);
        return 1;
    }
    // -------------------------------------------------------------------------------------------------->


    // PROGRAMMATIC ANIMATION ---------------------------------------------------------------------------->
    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(1000);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }
    // -------------------------------------------------------------------------------------------------->


    // ADD ---------------------------------------------------------------------------------------------->
    public int add(int a, int b) {
        return a + b;
    }
    // -------------------------------------------------------------------------------------------------->


    // SUBTRACT ----------------------------------------------------------------------------------------->
    public int subtract(int a, int b) {
        return a - b;
    }
    // -------------------------------------------------------------------------------------------------->

}