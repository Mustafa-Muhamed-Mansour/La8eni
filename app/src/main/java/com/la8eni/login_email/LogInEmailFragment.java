package com.la8eni.login_email;

import androidx.lifecycle.Observer;
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
import com.la8eni.databinding.LogInEmailFragmentBinding;

public class LogInEmailFragment extends Fragment
{

    private LogInEmailFragmentBinding binding;
    private NavController navController;
    private LogInEmailViewModel logInEmailViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = LogInEmailFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        logInEmailViewModel = new ViewModelProvider(requireActivity()).get(LogInEmailViewModel.class);


        //clicked Buttons (New Account, login E-mail and login Phone)
        clickedButtons();

        //observe Login E-mail ViewModel
        observeLoginEmailViewModel();



    }

    private void clickedButtons()
    {

        binding.btnRegisterNewAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                navController.navigate(R.id.action_logInEmailFragment_to_registerFragment);
            }
        });

        binding.btnLoginPhone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                navController.navigate(R.id.action_logInEmailFragment_to_logInPhoneFragment);
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String email = binding.editEmailLogin.getText().toString();
                String password = binding.editPasswordLogin.getText().toString();

                if (TextUtils.isEmpty(email))
                {
                    Snackbar.make(binding.parentLoginEmailLinearLayout, "Please enter your email", Snackbar.LENGTH_SHORT).show();
                    binding.editEmailLogin.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password))
                {
                    Snackbar.make(binding.parentLoginEmailLinearLayout, "Please enter your password", Snackbar.LENGTH_SHORT).show();
                    binding.editPasswordLogin.requestFocus();
                    return;
                }

                else
                {
                    logInEmailViewModel.loginEmail(email, password);
                }
            }
        });
    }


    private void observeLoginEmailViewModel()
    {

        logInEmailViewModel.booleanMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Boolean>()
        {
            @Override
            public void onChanged(Boolean aBoolean)
            {
                navController.navigate(R.id.action_logInEmailFragment_to_logInPhoneFragment);
            }
        });
    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();

        logInEmailViewModel.booleanMutableLiveData.removeObservers(getViewLifecycleOwner());
    }
}