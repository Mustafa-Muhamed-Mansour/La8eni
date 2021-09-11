package com.la8eni.login_phone;

import androidx.lifecycle.ViewModelProvider;

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

import com.google.android.material.snackbar.Snackbar;
import com.la8eni.R;
import com.la8eni.databinding.LogInPhoneFragmentBinding;

public class LogInPhoneFragment extends Fragment
{

    private LogInPhoneFragmentBinding binding;
    private NavController navController;
    private LogInPhoneViewModel logInPhoneViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = LogInPhoneFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        logInPhoneViewModel = new ViewModelProvider(requireActivity()).get(LogInPhoneViewModel.class);


        //Clicked Views (New account, login E-mail and login Phone)
        clickedViews();

        //observe Login Phone ViewModel
        observeLoginPhoneViewModel();
    }

    private void clickedViews()
    {
        binding.txtRegisterNewAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                navController.navigate(R.id.action_logInPhoneFragment_to_registerFragment);
            }
        });

        binding.txtLoginEmail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                navController.navigate(R.id.action_logInPhoneFragment_to_logInEmailFragment);
            }
        });

        binding.btnPhoneLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String name = binding.editName.getText().toString();
                String phone = binding.editPhone.getText().toString();

                if (TextUtils.isEmpty(name))
                {
                    Snackbar.make(binding.parentLoginPhoneLinearLayout, "Please enter your name", Snackbar.LENGTH_SHORT).show();
                    binding.editName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(phone))
                {
                    Snackbar.make(binding.parentLoginPhoneLinearLayout, "Please enter your phone", Snackbar.LENGTH_SHORT).show();
                    binding.editPhone.requestFocus();
                    return;
                }

                else
                {
                }
            }
        });
    }

    private void observeLoginPhoneViewModel()
    {
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();


    }
}