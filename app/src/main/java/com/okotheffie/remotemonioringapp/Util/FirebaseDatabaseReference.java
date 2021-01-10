package com.okotheffie.remotemonioringapp.Util;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseReference {

    private FirebaseDatabaseReference() {
    }
    public static final FirebaseDatabase DATABASE = FirebaseDatabase.getInstance();
}
