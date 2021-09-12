package com.la8eni.register;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import static android.app.Activity.RESULT_OK;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.la8eni.R;
import com.la8eni.constant.VariableConstant;
import com.la8eni.databinding.RegisterFragmentBinding;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Objects;

public class RegisterFragment extends Fragment
{

    private RegisterFragmentBinding binding;
    private NavController navController;
    private RegisterViewModel registerViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = RegisterFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        registerViewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);


        //clicked Buttons (Register and login E-mail)
        clickedButtons();

        //observe Register ViewModel
        observeRegisterViewModel();
    }

    private void clickedButtons()
    {
        binding.btnHaveAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                navController.navigate(R.id.action_registerFragment_to_logInEmailFragment);
            }
        });

        binding.imgProfileRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openGallery();
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String email = binding.editEmailRegister.getText().toString();
                String password = binding.editPasswordRegister.getText().toString();
                String name = binding.editNameRegister.getText().toString();
                String city = binding.editCityRegister.getText().toString();

                if (VariableConstant.registerImage == null)
                {
                    Snackbar.make(binding.parentRegisterNestedScrollLayout, "Please enter your picture", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email))
                {
                    Snackbar.make(binding.parentRegisterNestedScrollLayout, "Please enter your email", Snackbar.LENGTH_SHORT).show();
                    binding.editEmailRegister.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password))
                {
                    Snackbar.make(binding.parentRegisterNestedScrollLayout, "Please enter your password", Snackbar.LENGTH_SHORT).show();
                    binding.editPasswordRegister.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(name))
                {
                    Snackbar.make(binding.parentRegisterNestedScrollLayout, "Please enter your name", Snackbar.LENGTH_SHORT).show();
                    binding.editNameRegister.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(city))
                {
                    Snackbar.make(binding.parentRegisterNestedScrollLayout, "Please enter your city", Snackbar.LENGTH_SHORT).show();
                    binding.editCityRegister.requestFocus();
                    return;
                }

                else
                {
                    registerViewModel.registerEmail(VariableConstant.registerImage, email, password, name, city);
                }
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
                VariableConstant.registerImage = imagetUri.toString();

                Glide
                        .with(getActivity())
                        .load(imagetUri)
                        .into(binding.imgProfileRegister);


            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                Exception error = result.getError();
                Snackbar.make(binding.parentRegisterNestedScrollLayout, Objects.requireNonNull(error.getMessage()), Snackbar.LENGTH_SHORT).show();
            }
        }

        else
        {
            Snackbar.make(binding.parentRegisterNestedScrollLayout, result.getError().toString(), Snackbar.LENGTH_SHORT).show();
        }
    }


    private void observeRegisterViewModel()
    {
        registerViewModel.booleanMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Boolean>()
        {
            @Override
            public void onChanged(Boolean aBoolean)
            {
                navController.navigate(R.id.action_registerFragment_to_homeFragment);
            }
        });
    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();

        registerViewModel.booleanMutableLiveData.removeObservers(getViewLifecycleOwner());
    }
}