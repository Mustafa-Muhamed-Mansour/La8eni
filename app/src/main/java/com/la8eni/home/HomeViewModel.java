package com.la8eni.home;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.la8eni.constant.VariableConstant;
import com.la8eni.model.GroupModel;

import java.util.ArrayList;
import java.util.Objects;

public class HomeViewModel extends ViewModel
{

    private ArrayList<GroupModel> groupModels = new ArrayList<>();
    private StorageReference imageGroupRef = FirebaseStorage.getInstance().getReference().child("Images").child("Groups'_Image").child(Objects.requireNonNull(VariableConstant.groupRef.push().getKey()));
    private MutableLiveData<String> stringMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> booleanMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ArrayList<GroupModel>> groupModelMutableLiveData = new MutableLiveData<>();


    public void addedGroup(String groupImage, String groupName)
    {
        imageGroupRef.putFile(Uri.parse(groupImage)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                imageGroupRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                {
                    @Override
                    public void onSuccess(Uri uri)
                    {
                        booleanMutableLiveData.setValue(true);
                        GroupModel groupModel = new GroupModel(VariableConstant.groupRandomKey, VariableConstant.groupID, groupName, uri.toString());
                        VariableConstant.groupRef.child("Groups").child(Objects.requireNonNull(VariableConstant.groupRandomKey)).setValue(groupModel);
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

    public void retriveGroups()
    {
        VariableConstant.groupRef
                .child("Groups")
                .addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        groupModels.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            GroupModel groupModel = dataSnapshot.getValue(GroupModel.class);
                            groupModels.add(groupModel);
                        }
                        groupModelMutableLiveData.postValue(groupModels);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        stringMutableLiveData.setValue(error.getMessage());
                    }
                });
    }
}