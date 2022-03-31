package com.example.firebaserealtimeandmessageingandroid.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.firebaserealtimeandmessageingandroid.R
import com.example.firebaserealtimeandmessageingandroid.MainActivity
import com.example.firebaserealtimeandmessageingandroid.models.Books
import com.example.firebaserealtimeandmessageingandroid.models.NotificationContentData
import com.example.firebaserealtimeandmessageingandroid.models.PushNotification
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddBookActivity : AppCompatActivity() {
    private val bookCollectionRef = Firebase.database.reference
    lateinit var binding: AddBookActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        val addButton = findViewById<Button>(R.id.btn_add_book)

        addButton.setOnClickListener {
            val bookID = bookCollectionRef.child("book").push().key
            val bookName = findViewById<EditText>(R.id.et_book_name).text.toString()
            val bookAuthor = findViewById<EditText>(R.id.et_book_author).text.toString()
            val bookLaunchYear = findViewById<EditText>(R.id.et_launch_year).text.toString()
            val bookPrice = findViewById<EditText>(R.id.et_price).text.toString()

            if (bookName.isNotEmpty() && bookAuthor.isNotEmpty() && bookLaunchYear.isNotEmpty() && bookPrice.isNotEmpty()) {
                val book = Books(bookID, bookName, bookAuthor, bookLaunchYear, bookPrice.toDouble())
                saveBooks(book)
            }else{
                Toast.makeText(this,"Please fill in all fields", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun saveBooks(books: Books) {
        bookCollectionRef.child("book/${books.id}").setValue(books).addOnCompleteListener {task->
            if (task.isSuccessful){
                Toast.makeText(this,"successfully added Book", Toast.LENGTH_LONG).show()

                startActivity(Intent(this, MainActivity::class.java))
            }else{
                Toast.makeText(this,"error", Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener{
            println(it.message)
        }
    }

}
