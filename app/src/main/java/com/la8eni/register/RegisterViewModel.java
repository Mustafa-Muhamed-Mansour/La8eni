package com.la8eni.register;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.la8eni.model.UserEmailModel;

import java.util.Objects;

public class RegisterViewModel extends ViewModel
{

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference();
    private StorageReference imageUserRef = FirebaseStorage.getInstance().getReference().child("Images").child("Users'_Image").child(Objects.requireNonNull(userRef.push().getKey()));
    private MutableLiveData<String> stringMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> booleanMutableLiveData = new MutableLiveData<>();


    public void registerEmail(String image, String email, String password, String name, String city)
    {
        firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>()
                {
                    @Override
                    public void onSuccess(AuthResult authResult)
                    {
                        String uRandomKey = userRef.push().getKey();
                        String uID = firebaseAuth.getCurrentUser().getUid();
                        imageUserRef.putFile(Uri.parse(image)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                        {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                            {
                                imageUserRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                                {
                                    @Override
                                    public void onSuccess(Uri uri)
                                    {
                                        booleanMutableLiveData.setValue(true);
                                        UserEmailModel userEmailModel = new UserEmailModel(uRandomKey, uID, uri.toString(), email, name, city);
                                        userRef.child("Users").child(uID).setValue(userEmailModel);
                                    }
                                }).addOnFailureListener(new OnFailureListener()
                                {
                                    @Override
                                    public void onFailure(@NonNull Exception e)
                                    {
                                        booleanMutableLiveData.setValue(false);
                                        stringMutableLiveData.setValue(e.getMessage());
                                    }
                                });
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        booleanMutableLiveData.setValue(false);
                        stringMutableLiveData.setValue(e.getMessage());
                    }
                });
    }

}