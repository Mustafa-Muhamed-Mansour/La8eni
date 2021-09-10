package com.la8eni.home;

import static android.app.Activity.RESULT_OK;

import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.la8eni.R;
import com.la8eni.adapter.group.GroupAdapter;
import com.la8eni.databinding.HomeFragmentBinding;
import com.la8eni.model.GroupModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment
{

    private HomeFragmentBinding binding;
    private NavController navController;
    private HomeViewModel homeViewModel;

    private CircleImageView imageGroup;
    private EditText editGroupName;
    private Button buttonAddedGroup;
    private String imgGroup;

    private GroupAdapter groupAdapter;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);


        //clicked Floating Action Button (Add new group)
        clickedFAB();

        //observe retriveDataGroup ViewModel
        observeDataViewModel();

    }

    private void clickedFAB()
    {
        binding.fabAddGroup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
                bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);
                bottomSheetDialog.setCanceledOnTouchOutside(false);

                imageGroup = bottomSheetDialog.findViewById(R.id.img_bottom_sheet);
                editGroupName = bottomSheetDialog.findViewById(R.id.edit_bottom_sheet);
                buttonAddedGroup = bottomSheetDialog.findViewById(R.id.btn_bottom_sheet);

                imageGroup.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        openGallery();
                    }
                });

                buttonAddedGroup.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        String groupName = editGroupName.getText().toString();

                        if (TextUtils.isEmpty(groupName))
                        {
                            Toast.makeText(getActivity(), "Please enter Group name", Toast.LENGTH_SHORT).show();
                            editGroupName.requestFocus();
                            return;
                        }

                        if (imgGroup == null)
                        {
                            Toast.makeText(getActivity(), "Please enter Image group", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        else
                        {
                            homeViewModel.addedGroup(imgGroup, groupName);
                        }
                    }
                });
                bottomSheetDialog.show();
            }
        });
    }

    private void openGallery()
    {
        CropImage
                .activity()
                .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
                .start(getActivity(), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        CropImage.ActivityResult result = CropImage.getActivityResult(data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                Uri imagetUri = result.getUri();
                imgGroup = imagetUri.toString();

                Glide
                        .with(getActivity())
                        .load(imagetUri)
                        .into(imageGroup);

            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                Exception error = result.getError();
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        else
        {
            Toast.makeText(getActivity(), result.getError().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void observeDataViewModel()
    {
        homeViewModel.groupModelMutableLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<GroupModel>>()
        {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(ArrayList<GroupModel> groupModels)
            {
                groupAdapter = new GroupAdapter(groupModels);
                binding.rVGroups.setAdapter(groupAdapter);
                binding.rVGroups.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                binding.rVGroups.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                groupAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onStart()
    {
        super.onStart();

        homeViewModel.retriveGroups();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();

        homeViewModel.booleanMutableLiveData.removeObservers(getViewLifecycleOwner());
        homeViewModel.groupModelMutableLiveData.removeObservers(getViewLifecycleOwner());
    }
}