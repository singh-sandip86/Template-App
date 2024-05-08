package com.example.templateapp.ui.screen.launcher

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.templateapp.R
import com.example.templateapp.common.Constants
import com.example.templateapp.databinding.ActivityLauncherBinding
import com.example.templateapp.preferences.SharedPrefManager
import com.example.templateapp.ui.base.BaseActivity
import com.example.templateapp.ui.screen.home.HomeActivity

class LauncherActivity : BaseActivity(R.layout.activity_launcher) {

    private val binding: ActivityLauncherBinding by viewBinding()

    companion object { }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Android 12 splash screen.
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setupViews()
        setupCollectors()
        handleError()
    }

    override fun setupViews() {
        if (SharedPrefManager.isUserLoggedIn) {
            HomeActivity.startActivity(this)
        } else {
            loadFragment(Constants.SCREEN_SIGN_IN)
        }
    }

    override fun setupCollectors() { }

    override fun handleError() { }

    private fun loadFragment(screenName: String, bundle: Bundle = Bundle()) {
        var fragment: Fragment? = null
        when (screenName) {
            Constants.SCREEN_SIGN_IN -> {
                fragment = LogInFragment()
            }
        }

        if (fragment != null) {
            fragment.arguments = bundle

            val fm: FragmentManager = supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, 0, 0, 0)
            fragmentTransaction.replace(R.id.frameLayoutSignIn, fragment)
            fragmentTransaction.addToBackStack(null).commit()
        }
    }
}