package com.example.prototype;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.prototype.databinding.FragmentFirstBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    FirebaseFirestore firestore;
    SharedPreferences sp;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        //using shared preferences
        sp = getActivity().getSharedPreferences("myUserPrefs", Context.MODE_PRIVATE);
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);





        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//using firebase for login page
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                HashMap<String,String> fetchedCredentials = new HashMap<String,String>();
                db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String credentials = document.getData().toString();
                                        credentials.replace("{", "").replace("}", "");
                                        String username = credentials.split(",")[0].substring(10, (credentials.split(",")[0].length()));
                                        String password = credentials.split(",")[1].substring(10, (credentials.split(",")[1].length() - 1));
                                        Log.d("Tag", binding.loginPassword.getText().toString());
                                        Log.d("Tag", binding.loginUsername.getText().toString());
                                        if((binding.loginUsername.getText().toString().equals(username)) && (binding.loginPassword.getText().toString().equals(password))) {
                                            Log.d("Tag", "login success");
                                            SharedPreferences.Editor editor = sp.edit();
                                            editor.putString("Username", String.valueOf(binding.loginUsername.getText()));
                                            editor.commit();
                                            NavHostFragment.findNavController(FirstFragment.this)
                                                    .navigate(R.id.action_FirstFragment_to_SecondFragment);
                                            return;
                                        }


                                    }
                                    Log.d("Tag", "login fail");
                                    Snackbar.make(view, "Invalid username/password", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    binding.loginUsername.setText("");
                                    binding.loginPassword.setText("");
                                } else {
                                    Log.w("TAG", "Error getting documents.", task.getException());
                                }
                            }

                        });




            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}