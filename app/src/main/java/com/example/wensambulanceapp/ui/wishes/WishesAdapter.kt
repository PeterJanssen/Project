package com.example.wensambulanceapp.ui.wishes

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wensambulanceapp.R
import com.example.wensambulanceapp.data.entities.Wish
import com.example.wensambulanceapp.data.repositories.VolunteerRepository
import com.example.wensambulanceapp.databinding.ItemWishBinding
import com.squareup.picasso.Picasso
import java.util.*


class WishesAdapter(
    private var wishes: ArrayList<Wish>,
    val context: Context,
    val volunteerRepository: VolunteerRepository,
    val userId: String
) : RecyclerView.Adapter<WishesAdapter.WishViewHolder>() {

    override fun getItemCount() = wishes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WishViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_wish,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: WishViewHolder, position: Int) {
        wishes.sortWith(Comparator { lhs, rhs ->
            if (lhs.title > rhs.title) -1 else if (lhs.date < rhs.date) 1 else 0
        })
        val wish: Wish = wishes[position]
        holder.itemWishBinding.view = wish

        holder.itemWishBinding.root.setOnClickListener {
            val builder =
                AlertDialog.Builder(context)
            builder
                .setPositiveButton("Nee") { dialog, _ ->
                    dialog.dismiss()
                }
                .setNegativeButton("Ja") { dialog, _ ->
                    wishes.remove(wishes[position])
                    notifyDataSetChanged()
                    volunteerRepository.addVolunteerToWish(userId, wish.wishId)
                    dialog.dismiss()

                }
                .setTitle("Ben je zeker dat je wilt deelnemen?")
                .setMessage(wish.toString())

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()

            val buttonbackground: Button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
            buttonbackground.setTextColor(Color.GRAY)

        }

        Picasso.get().load(Uri.parse(wish.image))
            .placeholder(R.drawable.ic_stars_black_24dp)
            .into(holder.itemWishBinding.imageWish)
    }


    inner class WishViewHolder(
        val itemWishBinding: ItemWishBinding
    ) : RecyclerView.ViewHolder(itemWishBinding.root)

}