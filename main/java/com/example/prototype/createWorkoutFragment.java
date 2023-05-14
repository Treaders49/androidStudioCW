package com.example.prototype;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.example.prototype.databinding.FragmentCreateWorkoutBinding;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class createWorkoutFragment extends Fragment {
    FragmentCreateWorkoutBinding binding;
    Timer timer;
    TextView timerText;
    TimerTask timerTask;
    ConstraintLayout layout;
    Double time = 0.0;
    ArrayList<View> exerciseViews = new ArrayList<View>();;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        timer = new Timer();

        binding =  FragmentCreateWorkoutBinding.inflate(inflater, container, false);
        timerText = (TextView) binding.timerView;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            time++;
                                                            timerText.setText(getTimerText());
                                                        }
                                                    });



            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
        layout = binding.exerciseLayout;
        return binding.getRoot();
    }

    private String getTimerText() {
        int roundedTime = (int) Math.round(time);
        int seconds = ((roundedTime % 86400) % 3600) % 60;
        int minutes = ((roundedTime % 86400) % 3600) / 60;
        int hours = ((roundedTime % 86400) / 3600);
        return String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        binding.addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View exerciseView =  getLayoutInflater().inflate(R.layout.exercise_form, null);


                exerciseView.setId(View.generateViewId());
                layout.addView(exerciseView);

                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(layout);
                if (exerciseViews.isEmpty()) {
                    constraintSet.connect(exerciseView.getId(), ConstraintSet.TOP, binding.addExerciseButton.getId(), ConstraintSet.BOTTOM);
                } else {
                    constraintSet.connect(exerciseView.getId(), ConstraintSet.TOP, exerciseViews.get(exerciseViews.size()-1).getId(), ConstraintSet.BOTTOM);
                }

                constraintSet.connect(exerciseView.getId(), ConstraintSet.START, binding.exerciseLayout.getId(), ConstraintSet.START);
                constraintSet.connect(exerciseView.getId(), ConstraintSet.END, binding.exerciseLayout.getId(), ConstraintSet.END);
                Log.d("TAG", String.valueOf(exerciseView.getId()));
                constraintSet.applyTo(layout);
                exerciseViews.add(exerciseView);
                Log.d("tag", String.valueOf(binding.exerciseLayout.getChildCount()));
                ConstraintLayout l = (ConstraintLayout) binding.exerciseLayout.getChildAt(binding.exerciseLayout.getChildCount()-1);
                Button b = (Button) l.getChildAt(l.getChildCount() - 4);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View setView =  getLayoutInflater().inflate(R.layout.set_view, null);
                        setView.setId(View.generateViewId());
                        l.addView(setView);



                        Log.d("TAG", String.valueOf(b.getId()));
                        Log.d("TAG", String.valueOf(setView.getId()));
                        int x = 0;
                        Log.d("view", l.getChildAt(10).toString());
                        while (l.getChildCount() > x) {
                            Log.d("TAG", String.valueOf(l.getChildAt(x)));
                            x++;
                        }
                        constraintSet.clone(l);
                        constraintSet.clear(b.getId(), ConstraintSet.TOP);
                        constraintSet.clear(b.getId(), ConstraintSet.START);
                        constraintSet.connect(setView.getId(), ConstraintSet.TOP, l.getChildAt(l.getChildCount()-2).getId(), ConstraintSet.BOTTOM);
                        constraintSet.connect(setView.getId(), ConstraintSet.START, l.getChildAt(l.getChildCount()-2).getId(), ConstraintSet.START);
                        constraintSet.connect(b.getId(), ConstraintSet.TOP, setView.getId(), ConstraintSet.BOTTOM);
                        constraintSet.connect(b.getId(), ConstraintSet.START, setView.getId(), ConstraintSet.START);
                        constraintSet.applyTo(l);
                    }
                });




            }


    });
    binding.cancelWorkoutButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            NavHostFragment.findNavController(createWorkoutFragment.this)
                    .navigate(R.id.action_createWorkoutFragment_to_SecondFragment);
        }
    });

    binding.cancelWorkoutButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {




            NavHostFragment.findNavController(createWorkoutFragment.this)
                    .navigate(R.id.action_createWorkoutFragment_to_SecondFragment);
        }
    });

    }

    public createWorkoutFragment() {

    }
}