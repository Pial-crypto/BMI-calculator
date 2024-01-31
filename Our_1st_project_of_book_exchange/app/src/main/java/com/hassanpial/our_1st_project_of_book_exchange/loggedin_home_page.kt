package com.hassanpial.our_1st_project_of_book_exchange

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.health.connect.datatypes.units.Length
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.widget.Button
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hassanpial.our_1st_project_of_book_exchange.databinding.ActivityMainBinding



private lateinit var appBarConfiguration: AppBarConfiguration
private lateinit var binding: ActivityMainBinding

class loggedin_home_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loggedin_home_page)

        var binding= ActivityMainBinding.inflate(layoutInflater)
      //  setContentView(binding.root)

var message=findViewById<ImageView>(R.id.message)

        val dl = findViewById<DrawerLayout>(R.id.dl)
        val nv = findViewById<NavigationView>(R.id.navigation_view)
        val mainll = findViewById<LinearLayout>(R.id.mainll)
        val navtoolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.nav_toolbar)
        val drawerToggle = ActionBarDrawerToggle(
            this,
            dl,
            navtoolbar,
            R.string.open,
            R.string.close
        )
var search=findViewById<ImageView>(R.id.search)

        search.setOnClickListener(){
            startActivity(Intent(this,searching_book::class.java))
        }


        mainll.setBackgroundColor(resources.getColor(R.color.teal_200))
        dl.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        val headerView = nv.getHeaderView(0)
        val darkModeSwitch: SwitchCompat = headerView.findViewById(R.id.darkModeSwitch)

        darkModeSwitch.isChecked =
            AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            recreate()
        }




        var sellfaster=findViewById<Button>(R.id.sell_faster)
var exchange=findViewById<Button>(R.id.exchange_book)
        val popular = headerView.findViewById<TextView>(R.id.Popular)
        val back = headerView.findViewById<LinearLayout>(R.id.back)
var dpimage=headerView.findViewById<ShapeableImageView>(R.id.profileImageView)

        var currentUID= FirebaseAuth.getInstance().uid.toString()


        FirebaseDatabase.getInstance().getReference("Registered users").child(currentUID)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Handle the data when it changes
                    if (snapshot.exists()) {
                        // The snapshot contains the data for the specified user

                        var dp=snapshot.child("Profile Picture").getValue(String::class.java)


                        if (dp != null) {
                            // Load the image into an ImageView using Glide (or any other method you prefer)
                            Glide.with(applicationContext)
                                .load(dp)
                                .into(dpimage)

                        } else {
                            dpimage.setImageResource(R.drawable.profile_picture_not_available)
                            // Handle the case where the "Profile Picture" field is null or doesn't exist
                            // You might want to use a default image or show an error placeholder
                            // imageView.setImageResource(R.drawable.default_profile_image)
                            // or
                            // handle the error appropriately
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


        message.setOnClickListener(){
            startActivity(Intent(this,received_messages::class.java))
        }

findViewById<TextView>(R.id.newrecruit).setOnClickListener(){
startActivity(Intent(this,book_uploading_page::class.java))
}

headerView.findViewById<TextView>(R.id.edit_profile).setOnClickListener{
 startActivity(Intent(this,Editing_Profile::class.java))
}

        sellfaster.setOnClickListener(){
startActivity(Intent(this,Ready_to_Sell_Book_List_for_me::class.java))
        }

        exchange.setOnClickListener(){
            startActivity(Intent(this,Exchangable_Book_List_for_me::class.java))
        }


        back.setOnClickListener {
            if (dl.isDrawerOpen(GravityCompat.START)) {
                dl.closeDrawer(GravityCompat.START)
            }
        }

        popular.setOnClickListener {
            Log.i("Tag", "Balcal")
        }
    }

    override fun onBackPressed() {
        val dl = findViewById<DrawerLayout>(R.id.dl)
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}