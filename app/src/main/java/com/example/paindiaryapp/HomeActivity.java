package com.example.paindiaryapp;

import android.os.Bundle;

import com.example.paindiaryapp.databinding.ActivityHomeBinding;
import com.example.paindiaryapp.viewmodel.UserViewModel;
import com.example.paindiaryapp.viewmodel.WeatherViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.View;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    public UserViewModel userViewMode;
    public WeatherViewModel weatherViewModel;



    private String userEmail;

    public String getUserEmail() {
        return this.userEmail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.userEmail = getIntent().getStringExtra("email");
        binding= ActivityHomeBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.appBar.toolbar);

        ViewModelProvider provider = new ViewModelProvider(this);
        this.userViewMode = provider.get(UserViewModel.class);
        this.userViewMode.loadAllUserData(this, userEmail);
        this.weatherViewModel = provider.get(WeatherViewModel.class);
        this.weatherViewModel.init();
        mAppBarConfiguration=new AppBarConfiguration.Builder(
                R.id.fragment_home,
                R.id.fragment_daily_record,
                R.id.fragment_pain_data_entry,
                R.id.fragment_reports,
                R.id.fragment_map)
                .setOpenableLayout(binding.drawerLayout).build();

        FragmentManager fragmentManager=getSupportFragmentManager();
        NavHostFragment navHostFragment=(NavHostFragment)
                fragmentManager.findFragmentById(R.id.nav_host_fragment);
        NavController navController=navHostFragment.getNavController();

        NavigationUI.setupWithNavController(binding.navView,navController);
        NavigationUI.setupWithNavController(binding.appBar.toolbar,navController,mAppBarConfiguration);
    }}











        /**  setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}**/