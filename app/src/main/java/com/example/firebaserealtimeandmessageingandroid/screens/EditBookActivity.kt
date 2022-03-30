package com.example.firebaserealtimeandmessageingandroid.screens

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.firebaserealtimeandmessageingandroid.MainActivity
import com.example.firebaserealtimeandmessageingandroid.R
import com.example.firebaserealtimeandmessageingandroid.models.Books
import com.google.firebase.database.core.operation.Merge
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.Function as Function1001

class EditBookActivity : AppCompatActivity() {
    private val bookCollectionRef = Firebase.database.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_book)

        val buttonEdit = findViewById<Button>(R.id.btn_edit_book)
        val buttonDelete = findViewById<Button>(R.id.btn_delete_book)



        buttonEdit.setOnClickListener {
            val book = setupBookData()
            val newBookMap = getNewBookData()
            updateBookData(book, newBookMap)
            Log.d("id", "----------==>: ${Books().id.toString()}")


        }

        buttonDelete.setOnClickListener {
            val book = setupBookData()
            deleteBook(book)
        }
        setupBookData()


    }

    private fun setupBookData(): Books {
        val ed_BookName = findViewById<EditText>(R.id.et_book_name_edit)
        val ed_BookAuthor = findViewById<EditText>(R.id.et_book_author_edit)
        val ed_BookLaunchYear = findViewById<EditText>(R.id.et_launch_year_edit)
        val ed_BookPrice = findViewById<EditText>(R.id.et_price_edit)

        val book = intent.getParcelableExtra<Books>("myBook")
        ed_BookName.setText(book?.bookName)
        ed_BookAuthor.setText(book?.bookAuthor)
        ed_BookLaunchYear.setText(book?.launchYear)
        ed_BookPrice.setText(book?.price.toString())
        Log.d("id", "setupBookData: $book --- ${book?.id}")

//        fun setupBookData() : String{
//            Log.d("id", "getBookId: $bookID")
//            return bookID.toString()
//        }
        val bookID = book?.id

        return Books(
            bookID,
            ed_BookName.text.toString(),
            ed_BookAuthor.text.toString(),
            ed_BookLaunchYear.text.toString(),
            ed_BookPrice.text.toString().toDouble()
        )
    }

    private fun getNewBookData(): Map<String, Any> {
        val bookName = findViewById<EditText>(R.id.et_book_name_edit).text.toString()
        val bookAuthor = findViewById<EditText>(R.id.et_book_author_edit).text.toString()
        val bookLaunchYear = findViewById<EditText>(R.id.et_launch_year_edit).text.toString()
        val bookPrice = findViewById<EditText>(R.id.et_price_edit).text.toString()
        val map = mutableMapOf<String, Any>()
        if (bookName.isNotEmpty()) {
            map["bookName"] = bookName
        }
        if (bookAuthor.isNotEmpty()) {
            map["bookAuthor"] = bookAuthor
        }
        if (bookLaunchYear.isNotEmpty()) {
            map["launchYear"] = bookLaunchYear
        }
        if (bookPrice.isNotEmpty()) {
            map["price"] = bookPrice
        }
        return map
    }

    private fun updateBookData(book: Books, newBookMap: Map<String, Any>) {

        bookCollectionRef.child("book").child("${book.id}").updateChildren(newBookMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Book Updated Successfully with key ${book.id}", Toast.LENGTH_SHORT).show()
                Log.d("update Book", "iiiiiiiiiddddd --> ${book.id}")
                startActivity(Intent(this, MainActivity::class.java))
            }.addOnFailureListener {
                Log.d("update Book", "UpdateBook Exception --> ${it.message!!}")
                Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
            }

//                if (task.isSuccessful) {
//                    if (task.result!!.documents.isNotEmpty()) {
//                        for (document in task.result!!.documents) {
//                            bookCollectionRef.document(document.id)
//                                .set(newBookMap, SetOptions.merge())
//                            startActivity(Intent(this, MainActivity::class.java))
//                        }
//                    }else{
//                        Toast.makeText(this,"No matching documents",Toast.LENGTH_LONG).show()
//                        Log.d(ContentValues.TAG, "No matching documents")
//                    }
//                }
//            }.addOnFailureListener {
//                Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
//                Log.d(ContentValues.TAG, "This is a message Error${it.message}")
    }


    private fun deleteBook(book: Books) {
        bookCollectionRef.child("book").child("${book.id}").removeValue()
            .addOnCompleteListener { task ->
                Log.d("delete Book", "Book With ID ${book.id} Deleted Successfully")
                Toast.makeText(this, "Book Deleted Successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))

            }.addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
    }

    private fun getOldBookData(): Books {
        val bookID = bookCollectionRef.key
        val bookName = findViewById<EditText>(R.id.et_book_name_edit).text.toString()
        val bookAuthor = findViewById<EditText>(R.id.et_book_author_edit).text.toString()
        val launchYear = findViewById<EditText>(R.id.et_launch_year_edit).text.toString()
        val price = findViewById<EditText>(R.id.et_price_edit).text.toString()

        return Books(bookID, bookName, bookAuthor, launchYear, price.toDouble())
        Log.d(TAG, "------------->${bookID}")
    }
}