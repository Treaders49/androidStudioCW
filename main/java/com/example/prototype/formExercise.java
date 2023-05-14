package com.example.prototype;


import android.os.Bundle;
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

import com.example.prototype.databinding.ExerciseFormBinding;



import java.util.ArrayList;


public class formExercise extends Fragment {
    ExerciseFormBinding binding;
    Button addSetButton;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding =  ExerciseFormBinding.inflate(inflater, container, false);
        addSetButton = binding.AddSetButton;

        addSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstraintLayout exerciseLayout = binding.exerciseLayout;
                View newSetView = binding.setView;
                exerciseLayout.addView(newSetView);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(exerciseLayout);
                constraintSet.connect(binding.AddSetButton.getId(), ConstraintSet.TOP, newSetView.getId(), ConstraintSet.BOTTOM);
                constraintSet.connect(binding.AddSetButton.getId(), ConstraintSet.LEFT, newSetView.getId(), ConstraintSet.LEFT);
            }
        });
        return binding.getRoot();

    }
}
