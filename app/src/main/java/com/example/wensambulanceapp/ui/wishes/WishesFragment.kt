package com.example.wensambulanceapp.ui.wishes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wensambulanceapp.R
import com.example.wensambulanceapp.data.entities.Wish
import kotlinx.android.synthetic.main.fragment_wishes.*
import org.kodein.di.android.x.kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class WishesFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private lateinit var viewModel: WishesViewModel
    private val factory: WishesViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wishes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(this, factory).get(WishesViewModel::class.java)

        viewModel.wishes.observe(viewLifecycleOwner, Observer<List<Wish>> { wishes ->

            recycler_view.also {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = WishesAdapter(
                    wishes as ArrayList<Wish>,
                    requireContext(),
                    viewModel.volunteerRepository,
                    viewModel.volunteerRepository.getUserId()
                )
            }
        })
    }
}