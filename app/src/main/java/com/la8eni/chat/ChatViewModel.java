package com.la8eni.chat;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.la8eni.constant.VariableConstant;
import com.la8eni.model.ChatMessageModel;

import java.util.ArrayList;

public class ChatViewModel extends ViewModel
{

    private ArrayList<ChatMessageModel> chatMessageModels = new ArrayList<>();
    private MutableLiveData<String> stringMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ArrayList<ChatMessageModel>> chatMutableLiveData = new MutableLiveData<>();

    public void retriveData()
    {
        VariableConstant
                .chatRef
                .child("Groups")
                .child(VariableConstant.randomKey)
                .child("Chats")
                .addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        chatMessageModels.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            ChatMessageModel chatMessageModel = dataSnapshot.getValue(ChatMessageModel.class);
                            chatMessageModels.add(chatMessageModel);
                        }
                        chatMutableLiveData.postValue(chatMessageModels);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        stringMutableLiveData.setValue(error.getMessage());
                    }
                });
    }
}
