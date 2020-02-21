package com.example.wensambulanceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wensambulanceapp.ui.auth.ChooseLoginMethodFragment

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar)
        setContentView(R.layout.activity_auth)
        if (savedInstanceState == null) {
            setStarterFragment()
        }
    }

    private fun setStarterFragment() {
        val chooseLoginMethodFragment =
            ChooseLoginMethodFragment()
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.ActivityAuthFragmentContainer, chooseLoginMethodFragment)
        transaction.commit()
    }
}
