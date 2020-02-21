package com.example.wensambulanceapp.ui.documents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.wensambulanceapp.R

class DocumentsFragment : Fragment() {

    private lateinit var documentsViewModel: DocumentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        documentsViewModel =
            ViewModelProviders.of(this).get(DocumentsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_documents, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        documentsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}