package com.example.quanli

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quanli.fragment.TeamDetailFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val tabIndex = intent.getIntExtra("TAB_INDEX", 0)
        
        if (savedInstanceState == null) {
            val fragment = TeamDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt("INITIAL_TAB", tabIndex)
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }

        UserHeaderUtils.setupUserHeader(this)

        val navId = if (tabIndex == 0) R.id.nav_history else R.id.nav_detail
        NavigationUtils.setupBottomNavigation(this, navId)
    }

    override fun onNewIntent(intent: android.content.Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        val tabIndex = intent.getIntExtra("TAB_INDEX", 0)
        
        val fragment = TeamDetailFragment().apply {
            arguments = Bundle().apply {
                putInt("INITIAL_TAB", tabIndex)
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

        UserHeaderUtils.setupUserHeader(this)

        val navId = if (tabIndex == 0) R.id.nav_history else R.id.nav_detail
        NavigationUtils.setupBottomNavigation(this, navId)
    }
}
