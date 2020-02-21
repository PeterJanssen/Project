package com.example.wensambulanceapp.ui.auth

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.wensambulanceapp.MainActivity

import com.example.wensambulanceapp.R
import com.example.wensambulanceapp.databinding.FragmentChooseLoginMethodBinding
import kotlinx.android.synthetic.main.fragment_choose_login_method.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ChooseLoginMethodFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private lateinit var viewModel: AuthViewModel
    private val factory: AuthViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentChooseLoginMethodBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_choose_login_method,
            container,
            false
        )
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        viewModel.user?.let {
            val intent = Intent(activity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity?.startActivity(intent)
            activity?.finish()
        }
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val fragmentTransaction: FragmentTransaction? = parentFragmentManager.beginTransaction()
        fragmentTransaction?.setCustomAnimations(
            R.anim.slide_in_left,
            R.anim.slide_out_right,
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )
        loginButton.setOnClickListener {
            val loginFragment = LoginFragment()
            fragmentTransaction?.replace(R.id.ActivityAuthFragmentContainer, loginFragment)
            fragmentTransaction?.addToBackStack(loginFragment.toString())
            fragmentTransaction?.commit()
        }

        registerTextView.setOnClickListener {
            val createAccountFragment = CreateAccountFragment()
            fragmentTransaction?.replace(R.id.ActivityAuthFragmentContainer, createAccountFragment)
            fragmentTransaction?.addToBackStack(createAccountFragment.toString())
            fragmentTransaction?.commit()
        }
    }

}
