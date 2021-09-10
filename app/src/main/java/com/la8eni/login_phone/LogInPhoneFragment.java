package com.la8eni.login_phone;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


    }
}