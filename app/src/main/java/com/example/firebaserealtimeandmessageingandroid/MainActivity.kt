package com.example.firebaserealtimeandmessageingandroid
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaserealtimeandmessageingandroid.models.Books
import com.example.firebaserealtimeandmessageingandroid.screens.AddBookActivity
import com.example.firebaserealtimeandmessageingandroid.ui.BookAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private val bookCollectionRef = Firebase.database.reference
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookArrayList: ArrayList<Books>
    private lateinit var myAdapter: BookAdapter
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonPushEditBook = findViewById<Button>(R.id.btn_push_page_edit)

        val buttonPushAddBook = findViewById<FloatingActionButton>(R.id.btn_push_page_add_book)
        buttonPushAddBook.setOnClickListener {
            startActivity(Intent(this, AddBookActivity::class.java))
        }


        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        bookArrayList = arrayListOf()
        myAdapter = BookAdapter(bookArrayList)
        recyclerView.adapter = myAdapter
        retrieveData()
    }

    private fun retrieveData() {
        bookCollectionRef.child("book").addValueEventListener (
            object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    bookArrayList.clear()

                    for (doc in snapshot.children) {
                        val book = doc.getValue<Books>()
                        book?.let {
                            bookArrayList.add(it)
                        }
                    }
                    Log.d("List Book", "onDataChange: List Book --->$bookArrayList")
                    myAdapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.w("List Book", "onCancelled: get All com.example.firebaserealtimeandmessageingandroid.models.Books => ${error.toException().message}")                }

            }
//            if (task.isSuccessful) {
//                for (document in task.result?.documents!!) {
//                    val book = document.toObject<com.example.firebaserealtimeandmessageingandroid.models.Books>()
//                    book?.let {
//                        bookArrayList.add(it)
//                    }
//                    Log.d(TAG, "This is a id: ${document.id} => This is a data: ${document.data}")
//
//                }
//                myAdapter.notifyDataSetChanged()
//                Toast.makeText(this, "Done", Toast.LENGTH_LONG).show()
//
//
//            } else {
//                Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
//            }
//        }.addOnFailureListener {
//            println(it.message)
//        }
        )
    }
}

