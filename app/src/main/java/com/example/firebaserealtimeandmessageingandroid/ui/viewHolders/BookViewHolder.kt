package com.example.firebaserealtimeandmessageingandroid.ui.viewHolders
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaserealtimeandmessageingandroid.R

class BookViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
    val textNameBook: TextView = viewItem.findViewById(R.id.txt_name_book)
    val textNameAuthor: TextView = viewItem.findViewById(R.id.txt_name_author)
    val textLaunchYear: TextView = viewItem.findViewById(R.id.txt_launch_year)
    val textPriceBook: TextView = viewItem.findViewById(R.id.txt_price_book)
    val firstChar: TextView = viewItem.findViewById(R.id.first_char)
    val buttonPushEdit: Button = viewItem.findViewById(R.id.btn_push_page_edit)

}