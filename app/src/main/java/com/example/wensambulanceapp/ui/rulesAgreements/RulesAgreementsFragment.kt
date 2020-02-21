package com.example.wensambulanceapp.ui.rulesAgreements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.wensambulanceapp.R

class RulesAgreementsFragment : Fragment() {

    private lateinit var rulesAgreementsViewModel: RulesAgreementsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rulesAgreementsViewModel =
            ViewModelProviders.of(this).get(RulesAgreementsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_rules_agreements, container, false)
        val textView: TextView = root.findViewById(R.id.text_tools)
        rulesAgreementsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}