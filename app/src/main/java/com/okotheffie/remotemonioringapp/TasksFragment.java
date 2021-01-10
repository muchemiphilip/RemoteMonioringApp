package com.okotheffie.remotemonioringapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.okotheffie.remotemonioringapp.Util.PreferenceKeys;
import com.okotheffie.remotemonioringapp.models.User;

public class TasksFragment extends Fragment {

    private static final String TAG = "TasksFragment";
    private AppBarLayout appBarLayout;
    private TextView tvName;
    private CardView cvTasksItem;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_tasks, container, false);
        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isFirstLogin();

        cvTasksItem = view.findViewById(R.id.home_task_card_item);
        tvName = view.findViewById(R.id.user_name_txv);
        appBarLayout = view.findViewById(R.id.header_app_bar);

//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference
//                ("users")
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        ref.addValueEventListener(new ValueEventListener() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user_properties = snapshot.getValue(User.class);
//                tvName.setText(user_properties.getFirstname() + " " + user_properties.getLastname());
//                Log.d(TAG,"getUserDetails: properties: \n" + user_properties.toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        cvTasksItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigateToActivityAssignedTasks = new Intent(getActivity(),ActivityAssignedTasks.class);
                startActivity(navigateToActivityAssignedTasks);
            }
        });

    }

    private void isFirstLogin() {

        Log.d(TAG,"isFirstLogin: checking if this is the first time to login");
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean isFirstLogin = preferences.getBoolean(PreferenceKeys.FIRST_TIME_LOGIN, true);

        if (isFirstLogin){
            Log.d(TAG, "isFirstLogin: launching alert dialog");

            //Launch the info dialog for first-time-users
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireActivity());
            alertDialogBuilder.setMessage(getString(R.string.first_time_user_message));
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d(TAG, "onClick: closing dialog");
                    // now that the user has logged in, save it to shared preferences so the dialog won't
                    // pop up again
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(PreferenceKeys.FIRST_TIME_LOGIN, false);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                        editor.apply();
                    }
                    dialogInterface.dismiss();
                }
            });
            alertDialogBuilder.setTitle("Welcome To SmartFarm!!!! ");
            alertDialogBuilder.setIcon(R.drawable.tabian_dating);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}
