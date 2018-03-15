package com.example.hadi.newspedia.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.hadi.newspedia.ArticlesActivity
import com.example.hadi.newspedia.R
import com.example.hadi.newspedia.data.SourceModel
import com.example.hadi.newspedia.inflateLayout
import kotlinx.android.synthetic.main.source_item.view.*

/**
 * Created by hadi on 31/01/18.
 */
class SourceAdapter: RecyclerView.Adapter<SourceAdapter.SourceViewHolder>() {
    private var sources: List<SourceModel>? = null

    fun setSources(sources: List<SourceModel>){
        this.sources = sources
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SourceViewHolder {
        val view = parent!!.inflateLayout(R.layout.source_item)
        return SourceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (sources == null)
            0
        else
            sources!!.size
    }

    override fun onBindViewHolder(holder: SourceViewHolder?, position: Int) {
        holder!!.bind(sources!![position])
    }

    inner class SourceViewHolder(itemView: View):
            RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private val title: TextView = itemView.title

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val source = sources!![adapterPosition]
            val destIntent = Intent(itemView.context, ArticlesActivity::class.java)
            destIntent.putExtra("domain", source)
            itemView.context.startActivity(destIntent)
        }

        fun bind(source: SourceModel){
            title.text = source.name
        }

    }
}
