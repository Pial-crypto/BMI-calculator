package com.hassanpial.our_1st_project_of_book_exchange

import BookAdapter
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Ready_to_Sell_Book_List_for_me : AppCompatActivity(), BookAdapter.OnItemClickListener {
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BookAdapter
    var currentUID=FirebaseAuth.getInstance().uid.toString()
    val allBooks= mutableListOf<Book>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ready_to_sell_book_list_for_me)
        searchView = findViewById(R.id.searchView)
        recyclerView = findViewById(R.id.recyclerView)

        FirebaseDatabase.getInstance().getReference("Registered users").child(  FirebaseAuth.getInstance().uid.toString()).child("Book")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Handle the data when it changes
                    if (snapshot.exists()) {

                        for (bookSnapshot in snapshot.children) {

                           if (bookSnapshot.child("Status").value.toString() == "Available to Sell" ||bookSnapshot.child("Status").value.toString() == "Available to Sell & Exchange"){
                            val author = bookSnapshot.child("Author").value.toString()
                            val genre = bookSnapshot.child("Genre").value.toString()
                            val name = bookSnapshot.child("Name").value.toString()
                            val picture = bookSnapshot.child("Picture of book").value.toString()
                            val status = bookSnapshot.child("Status").value.toString()
                            val owner = bookSnapshot.child("Owner").value.toString()
var bookid=bookSnapshot.child("Book id").value.toString()
                            val book = Book(
                                picture,
                                name,
                                author,
                                genre,
                                status,
                                owner,
                                bookid

                            )

                            allBooks.add(book)
                       }
                    }


                    } else {
                        //  userok=false
                        // Toast.makeText(this, "Google Sign-In failed", Toast.LENGTH_SHORT).show()
                        // Handle the case when the user data doesn't exist
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle errors, if any
                    println("Error: ${error.message}")
                }
            })


        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = BookAdapter(allBooks)

        // Set the click listener
        //  adapter.setOnItemClickListener(this)

        recyclerView.adapter = adapter


// Set a query listener for the SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override   fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submission if needed
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter your RecyclerView data based on the search query
                adapter.filter(newText)
                return true
            }
        })

        adapter.setOnItemClickListener(this)




    }


    override fun onItemClick(position: Int, book: Book) {
      //  Log.d("BookAdapter", "Item clicked at position $position with book name ${book.bookid}")
       // var bookstatus="eredr"


showExchangeDialog(book.bookid,position)




    }





    private fun showExchangeDialog(bookid:String,position: Int) {
        val builder = AlertDialog.Builder(this)

        // Set the message for the Alert
        builder.setMessage("Was your book sold? " )

        // Set Alert Title
        builder.setTitle("Alert!")

        // Set Cancelable false for when the user clicks outside the Dialog Box, then it will remain shown
        builder.setCancelable(false)

        // Set the positive button with "Yes" name
        builder.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                // When the user clicks "Yes", you can add your logic here
                // For example, you can handle the exchange process
               //startActivity(go_to_send_message)

                val updateMap= mapOf("Status" to "Already sold")

                FirebaseDatabase.getInstance()
                    .getReference("Registered users")
                    .child(currentUID)
                    .child("Book")
                    .child(bookid)
                    .updateChildren(updateMap)
                    .addOnSuccessListener {
                        // Successfully updated
                        Log.d("sucesssg","successsdgf")
                    }
                    .addOnFailureListener {
                        Log.d("sucessafa","successbsgf")
                    }

            .addOnFailureListener { exception ->
                Log.d("sucess","success")
            }

                allBooks.removeAt(position)
                adapter.notifyItemRemoved(position)







                dialog?.dismiss() // Dismiss the dialog if needed
            }
        })

        // Set the Negative button with "No" name
        builder.setNegativeButton("No", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                // If the user clicks "No", you can add your logic here
                // For example, you can cancel the exchange process
                dialog?.dismiss() // Dismiss the dialog if needed
            }
        })

        // Create the Alert dialog
        val alertDialog: AlertDialog = builder.create()

        // Show the Alert Dialog box
        alertDialog.show()
    }

}
