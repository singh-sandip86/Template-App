package com.example.templateapp.ui.screen.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.templateapp.R
import com.example.templateapp.common.LoadingEvent
import com.example.templateapp.common.ToolbarType
import com.example.templateapp.databinding.ActivityHomeBinding
import com.example.templateapp.ui.base.BaseActivity
import com.example.templateapp.utils.empty
import com.example.templateapp.utils.show
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.atomic.AtomicInteger

class HomeActivity : BaseActivity(R.layout.activity_home) {

    private val binding: ActivityHomeBinding by viewBinding()

    private lateinit var navController: NavController

    private var toolbarType: ToolbarType? = null

    companion object {
        private var progressCounter = AtomicInteger(0)

        fun startActivity(context: Context) {
            Intent(context, HomeActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setupViews()
        setupCollectors()
        handleError()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    override fun setupViews() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        toolbarType?.let {
            setToolBar(it)
        }
    }

    override fun setupCollectors() {}

    override fun handleError() {}

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoadingEvent(loadingEvent: LoadingEvent) {
        if (loadingEvent.isLoading) showLoading() else hideLoading()
    }

    private fun showLoading() {
        progressCounter.getAndIncrement()
        binding.progressIndicator.show()
    }

    private fun hideLoading() {
        progressCounter.getAndDecrement().let {
            if (it >= 0) {
                binding.progressIndicator.hide()
                progressCounter.set(0)
            }
        }
    }

    fun setToolBar(toolbarType: ToolbarType) {
        this.toolbarType = toolbarType

        binding.apply {
            when(toolbarType) {
                ToolbarType.Default -> {
                    toolbar.show()
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                is ToolbarType.Title -> {
                    toolbar.show()
                    supportActionBar?.setDisplayHomeAsUpEnabled(toolbarType.enableBackButton)
                    supportActionBar?.setHomeAsUpIndicator(toolbarType.backButtonIcon)
                    toolbar.title = String.empty()
                    toolbarTitle.text = toolbarType.title
                }
            }
        }
    }
}