package com.la8eni.adapter.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.la8eni.R;
import com.la8eni.constant.VariableConstant;
import com.la8eni.model.ChatMessageModel;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder>
{


    private ArrayList<ChatMessageModel> chatMessageModels;

    public ChatAdapter(ArrayList<ChatMessageModel> chatMessageModels)
    {
        this.chatMessageModels = chatMessageModels;
    }


//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
//    {
//        View view;
//        if (viewType == MSG_TYPE_LEFT)
//        {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left_chat, parent, false);
//        }
//
//        else
//        {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_right_chat, parent, false);
//        }
//        return new ChatViewHolder(view);
//    }

//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
//    {
//       ChatModel model = chatModels.get(position);
//
//       ChatViewHolder chatViewHolder = (ChatViewHolder) holder;
//
//       if (model.getMessage() != null)
//       {
//           chatViewHolder.textMessage.setText(model.getMessage());
//       }
//    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        if (viewType == VariableConstant.MESSAGE_LEFT)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left_chat, parent, false);
        }

        else
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_right_chat, parent, false);
        }
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position)
    {
        ChatMessageModel model = chatMessageModels.get(position);

//        ChatViewHolder chatViewHolder = (ChatViewHolder) holder;

        if (model.getMessage() != null)
        {
            holder.textMessage.setText(model.getMessage());
        }
    }


    @Override
    public int getItemCount()
    {
        return chatMessageModels.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder
    {

        private TextView textMessage;


        public ChatViewHolder(@NonNull View itemView)
        {
            super(itemView);

            textMessage = itemView.findViewById(R.id.item_txt_chat_message);
        }
    }


    @Override
    public int getItemViewType(int position)
    {
        ChatMessageModel chatMessageModel =  chatMessageModels.get(position);


        if (chatMessageModel.getChatUserID().equals(VariableConstant.User_ID))
        {
            return VariableConstant.MESSAGE_LEFT;
        }

        else
        {
            return VariableConstant.MESSAGE_RIGHT;
        }
    }


}
