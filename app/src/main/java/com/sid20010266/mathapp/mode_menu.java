package com.sid20010266.mathapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class mode_menu extends Fragment {

    private View view;

    public mode_menu() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mode_menu, container, false);

        // Buttons
        final Button additionBtn = view.findViewById(R.id.btn_add_mode);
        final Button subtractionBtn = view.findViewById(R.id.btn_subtract_mode);

        // Addition click listener
        additionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Animation
                final Animation bounceAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce_anim);
                additionBtn.startAnimation(bounceAnim);

                // Argument passing
                Bundle bundle = new Bundle();
                bundle.putString("mode", "add");
                bundle.putBoolean("countDown", true);
                Navigation.findNavController(view).navigate(R.id.action_mode_menu_to_game_screen, bundle);
            }
        });


        // Subtraction click listener
        subtractionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Animation
                final Animation bounceAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce_anim);
                subtractionBtn.startAnimation(bounceAnim);

                // Argument passing
                Bundle bundle = new Bundle();
                bundle.putString("mode", "subtract");
                bundle.putBoolean("countDown", true);
                Navigation.findNavController(view).navigate(R.id.action_mode_menu_to_game_screen, bundle);
            }
        });

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
                    // DEBUG: Toast toast = Toast.makeText(getActivity(), "Back button pressed", Toast.LENGTH_SHORT);
                    // DEBUG: toast.show();

                    // go back to start screen
                    Navigation.findNavController(view).navigate(R.id.mode_menu_to_start);
                    return true;
                }
                return false;
            }
        });
    }
    // -------------------------------------------------------------------------------------------------->
}