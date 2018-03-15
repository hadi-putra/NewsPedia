package com.example.hadi.newspedia.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.hadi.newspedia.ArticleWebViewActivity
import com.example.hadi.newspedia.R
import com.example.hadi.newspedia.data.NewsModel
import com.example.hadi.newspedia.inflateLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.article_item.view.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by hadi on 31/01/18.
 */
class ArticleAdapter: RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    private var articles: MutableList<NewsModel> = ArrayList()

    fun setArticles(articles: List<NewsModel>){
        if (!articles.isEmpty()){
            val currentSize = this.articles.size
            val amountInserted = articles.size

            this.articles.addAll(articles)
            notifyItemRangeInserted(currentSize, amountInserted)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ArticleViewHolder {
        val view = parent!!.inflateLayout(R.layout.article_item)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder?, position: Int) {
        holder!!.bind(articles[position])
    }

    fun clearState(){
        this.articles.clear()
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val title: TextView = itemView.title
        private val time: TextView = itemView.time
        private val image: ImageView = itemView.image

        override fun onClick(p0: View?) {
            val source = articles[adapterPosition]
            val destIntent = Intent(itemView.context, ArticleWebViewActivity::class.java)
            destIntent.putExtra("article", source)
            itemView.context.startActivity(destIntent)
        }

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(article: NewsModel){
            title.text = article.title
            if(article.urlToImage != null){
                image.visibility = View.VISIBLE
                Picasso.with(itemView.context)
                        .load(article.urlToImage).into(image)
            } else {
                image.visibility = View.GONE
            }
            time.text = DateFormat.getInstance().format(article.publishedAt)
        }
    }
}