package com.example.wensambulanceapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.wensambulanceapp.MainActivity
import com.example.wensambulanceapp.R
import com.example.wensambulanceapp.databinding.FragmentLoginBinding
import com.example.wensambulanceapp.util.ViewModelListener
import com.example.wensambulanceapp.util.toast
import kotlinx.android.synthetic.main.fragment_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class LoginFragment : Fragment(), ViewModelListener, KodeinAware {

    override val kodein by kodein()

    private lateinit var viewModel: AuthViewModel
    private val factory: AuthViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        binding.viewmodel = viewModel
        viewModel.authListener = this
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

        backArrow.setOnClickListener {
            val fm: FragmentManager = requireActivity().supportFragmentManager
            fm.popBackStack()
        }

        goToRegisterText.setOnClickListener {
            val registerFragment = CreateAccountFragment()
            fragmentTransaction?.replace(R.id.ActivityAuthFragmentContainer, registerFragment)
            fragmentTransaction?.commit()
        }
    }

    override fun onStarted() {
        toast("Logging in")
    }

    override fun onSuccess() {
        toast("Login succes!")
        val intent = Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("EXIT", true)
        activity?.startActivity(intent)
        activity?.finish()
    }

    override fun onFailure(message: String) {
        toast(message)
    }


}
