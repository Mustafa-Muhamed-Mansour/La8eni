package com.la8eni.chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import static android.app.Activity.RESULT_OK;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.la8eni.R;
import com.la8eni.adapter.chat.ChatAdapter;
import com.la8eni.constant.VariableConstant;
import com.la8eni.databinding.ChatFragmentBinding;
import com.la8eni.model.ChatMessageModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.util.ArrayList;

public class ChatFragment extends Fragment
{

    private ChatFragmentBinding binding;
    private NavController navController;
    private ChatViewModel chatViewModel;
    private ChatAdapter chatAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = ChatFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        chatViewModel = new ViewModelProvider(requireActivity()).get(ChatViewModel.class);

        //retriveBundle from Adapter
        retriveBundle();

        //Clicked Views (FAB and image Button to a More)
        clickedViews();


        //observe retriveDataChat ViewModel
        observeDataViewModel();

    }

    private void retriveBundle()
    {
        VariableConstant.chatImage = getArguments().getString("groupImage");
        Glide
                .with(getActivity())
                .load(VariableConstant.chatImage)
                .into(binding.circleGroupImg);

        VariableConstant.chatName = getArguments().getString("groupName");
        binding.txtGroupName.setText(VariableConstant.chatName);

        VariableConstant.randomKey = getArguments().getString("groupRandomKey");
    }


    private void clickedViews()
    {
        binding.imgBtnMore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                popupMenu.getMenuInflater().inflate(R.menu.message_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem)
                    {
                        switch (menuItem.getItemId())
                        {
                            case R.id.menu_send_message:
                                binding.editSendMessage.setVisibility(View.VISIBLE);
                                binding.circleSendImg.setVisibility(View.GONE);
                                binding.circleSendPdf.setVisibility(View.GONE);
                                binding.circleSendWord.setVisibility(View.GONE);
                                break;
                            case R.id.menu_send_image:
                                binding.circleSendImg.setVisibility(View.VISIBLE);
                                binding.editSendMessage.setVisibility(View.GONE);
                                binding.circleSendPdf.setVisibility(View.GONE);
                                binding.circleSendWord.setVisibility(View.GONE);
                                binding.circleSendImg.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View view)
                                    {
//                                        openGallery();
                                        Toast.makeText(getActivity(), "Comming Soon", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;
                            case R.id.menu_send_pdf:
                                binding.circleSendPdf.setVisibility(View.VISIBLE);
                                binding.editSendMessage.setVisibility(View.GONE);
                                binding.circleSendImg.setVisibility(View.GONE);
                                binding.circleSendWord.setVisibility(View.GONE);
                                binding.circleSendPdf.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View view)
                                    {
//                                        openFilePdf();
                                        Toast.makeText(getActivity(), "Comming Soon", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;
                            case R.id.menu_send_word:
                                binding.circleSendWord.setVisibility(View.VISIBLE);
                                binding.editSendMessage.setVisibility(View.GONE);
                                binding.circleSendImg.setVisibility(View.GONE);
                                binding.circleSendPdf.setVisibility(View.GONE);
                                binding.circleSendWord.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View view)
                                    {
//                                        openFileWord();
                                        Toast.makeText(getActivity(), "Comming Soon", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;
                            default:
                                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        binding.fabSendMessage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String sendMessage = binding.editSendMessage.getText().toString();

                if (TextUtils.isEmpty(sendMessage))
                {
                    Toast.makeText(getActivity(), "Please enter message", Toast.LENGTH_SHORT).show();
                    binding.editSendMessage.requestFocus();
                    return;
                }

                else
                {
                    ChatMessageModel chatMessageModel = new ChatMessageModel(VariableConstant.chatRandomKey, VariableConstant.chatUserID, sendMessage, VariableConstant.textMessage);
                    VariableConstant.chatRef.child("Groups").child(VariableConstant.randomKey).child("Chats").child(VariableConstant.chatRandomKey).setValue(chatMessageModel);
                    binding.editSendMessage.getText().clear();
                }
            }
        });
    }



//    private void openGallery()
//    {
//        CropImage
//                .activity()
//                .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
//                .start(getActivity(), this);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        CropImage.ActivityResult result = CropImage.getActivityResult(data);
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
//        {
//            if (resultCode == RESULT_OK)
//            {
//                Uri imagetUri = result.getUri();
//                VariableConstant.img = imagetUri.toString();
//
//                Glide
//                        .with(getActivity())
//                        .load(imagetUri)
//                        .into(binding.circleSendImg);
//
//            }
//            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
//            {
//                Exception error = result.getError();
//                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        else
//        {
//            Toast.makeText(getActivity(), result.getError().getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void openFilePdf()
//    {
//        Intent intentPdf = new Intent();
//        intentPdf.setAction(Intent.ACTION_GET_CONTENT);
//        intentPdf.setType("application/pdf");
//        startActivityForResult(Intent.createChooser(intentPdf, "Select PDF File"), 438);
//    }
//
//    private void openFileWord()
//    {
//        Intent intentMsWord = new Intent();
//        intentMsWord.setAction(Intent.ACTION_GET_CONTENT);
//        intentMsWord.setType("application/msword");
//        startActivityForResult(intentMsWord.createChooser(intentMsWord, "Select Ms Word File"), 438);
//    }

    private void observeDataViewModel()
    {
        chatViewModel.chatMutableLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<ChatMessageModel>>()
        {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(ArrayList<ChatMessageModel> chatMessageModels)
            {
                chatAdapter = new ChatAdapter(chatMessageModels);
                binding.rVChat.setAdapter(chatAdapter);
                binding.rVChat.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                chatAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onStart()
    {
        super.onStart();

        chatViewModel.retriveData();
    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();

        chatViewModel.chatMutableLiveData.removeObservers(getViewLifecycleOwner());
    }
}