package sample.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sample.R

class FortuneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val idLabel: TextView = itemView.findViewById(R.id.item_fortune__label__id)
    val fortuneLabel: TextView = itemView.findViewById(R.id.item_fortune__label__fortune)
}