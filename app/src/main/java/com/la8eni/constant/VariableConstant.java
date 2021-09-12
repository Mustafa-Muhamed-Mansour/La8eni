package com.la8eni.constant;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VariableConstant
{

    public static final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference();
    public static final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference();
    public static final DatabaseReference groupRef = FirebaseDatabase.getInstance().getReference();
    public static final String chatRandomKey = chatRef.push().getKey();
    public static String registerImage = "";
    public static String img = "";
    public static String randomKey = "";
    public static String chatImage = "";
    public static String chatName = "";
    public static String chatUserID = firebaseAuth.getCurrentUser().getUid();
    public static final String textMessage = "text";
    public static final String imageMessage = "image";
    public static final int MESSAGE_RIGHT = 1;
    public static final int MESSAGE_LEFT = 0;
    public static final String User_ID = firebaseAuth.getCurrentUser().getUid();
    public static final String groupRandomKey = groupRef.push().getKey();
    public static final String groupID = firebaseAuth.getCurrentUser().getUid();
    public static final String userRandomKey = VariableConstant.userRef.push().getKey();



}
