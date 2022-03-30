package com.example.firebaserealtimeandmessageingandroid.ui
import com.example.firebaserealtimeandmessageingandroid.screens.EditBookActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaserealtimeandmessageingandroid.R
import com.example.firebaserealtimeandmessageingandroid.models.Books
import com.example.firebaserealtimeandmessageingandroid.ui.viewHolders.BookViewHolder
import java.util.*

class BookAdapter(
    val list: ArrayList<Books>
) : RecyclerView.Adapter<BookViewHolder>() {

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val currentBook = list[position]
        holder.apply {
            textNameBook.text = currentBook.bookName
            textNameAuthor.text = currentBook.bookAuthor
            textLaunchYear.text = currentBook.launchYear
            textPriceBook.text = "$${currentBook.price.toString()}"
            firstChar.text = "${holder.adapterPosition + 1}"

                buttonPushEdit.setOnClickListener {
                    val intent = Intent(holder.itemView.context, EditBookActivity::class.java)
                    intent.putExtra("id", currentBook.id)
                    intent.putExtra("myBook", currentBook)
                    holder.itemView.context.startActivity(intent)
                }
        }


    }

    override fun getItemCount() = list.size

}