package com.ticketswap.assessment.features.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.ticketswap.assessment.R
import com.ticketswap.assessment.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binder: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binder.root)

        val host = NavHostFragment.create(R.navigation.search_navigation)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_host, host)
            .setPrimaryNavigationFragment(host).commit()
    }
}