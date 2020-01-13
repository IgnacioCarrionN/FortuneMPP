package sample.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import api.Fortune
import sample.R

class FortuneAdapter : RecyclerView.Adapter<FortuneViewHolder>() {

    val fortuneList: MutableList<Fortune> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FortuneViewHolder =
        FortuneViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_fortune, parent, false))


    override fun getItemCount(): Int = fortuneList.size

    override fun onBindViewHolder(holder: FortuneViewHolder, position: Int) {
        holder.idLabel.text = position.toString()
        holder.fortuneLabel.text = fortuneList[position].fortune
    }

    fun updateData(newList: List<Fortune>) {
        val diffResult = DiffUtil.calculateDiff(FortuneDiffUtil(fortuneList, newList))
        fortuneList.clear()
        fortuneList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class FortuneDiffUtil(private val oldList: List<Fortune>, private val newList: List<Fortune>) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].fortune == newList[newItemPosition].fortune

        override fun getOldListSize(): Int = oldList.size


        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].fortune == newList[newItemPosition].fortune
    }

}