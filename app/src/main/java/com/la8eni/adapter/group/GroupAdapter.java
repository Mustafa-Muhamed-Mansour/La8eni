package com.la8eni.adapter.group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.la8eni.R;
import com.la8eni.model.GroupModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder>
{

    ArrayList<GroupModel> groupModels;

    public GroupAdapter(ArrayList<GroupModel> groupModels)
    {
        this.groupModels = groupModels;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position)
    {
        GroupModel model = groupModels.get(position);
        holder.groupName.setText(model.getGroupName());
        Glide
                .with(holder.itemView.getContext())
                .load(model.getGroupImage())
                .into(holder.groupImage);

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Bundle chatGroupBundle = new Bundle();
                chatGroupBundle.putString("groupRandomKey", model.getGroupRandomKey());
                chatGroupBundle.putString("groupID", model.getGroupID());
                chatGroupBundle.putString("groupName", model.getGroupName());
                chatGroupBundle.putString("groupImage", model.getGroupImage());
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_chatFragment, chatGroupBundle);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return groupModels.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder
    {

        private CircleImageView groupImage;
        private TextView groupName;

        public GroupViewHolder(@NonNull View itemView)
        {
            super(itemView);

            groupImage = itemView.findViewById(R.id.item_circle_group_img);
            groupName = itemView.findViewById(R.id.item_txt_group_name);
        }
    }
}
