package com.hassanpial.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var randomInt = 0
    private var generate: Button? = null
    private var input: EditText? = null
    private var generated: TextView? = null
    private var guessText: TextView? = null
    private var newgame: Button? = null
    private var taken: Button? = null

private  var inputNumberText=""
    var inputNumber=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        generate = findViewById(R.id.generate)
        input = findViewById(R.id.Guess)
        generated = findViewById(R.id.generated)
        newgame = findViewById(R.id.newgame)
        taken = findViewById(R.id.taken)
var i=1
        newgame?.setOnClickListener(View.OnClickListener {
            //generate?.visibility = View.VISIBLE
            //generate?.isEnabled = true
            startnewgame()
             i=1
        })
//var i=0
  //   {

      taken?.setOnClickListener {
          var remain=5-i+1
          try {

              inputNumberText = input?.text.toString().trim()
               inputNumber = inputNumberText.toInt()

              if (inputNumber < randomInt) {
                  generated?.setText("Your guess is low.$remain chances left")

                 // Toast.makeText(this, ", Toast.LENGTH_LONG).show()
              } else if (inputNumber > randomInt) {
                  generated?.setText("Your guess is high.$remain chances left")
                //  Toast.makeText(this, "Your guess is too high because the number is $randomInt ", Toast.LENGTH_LONG).show()
              } else {
                  generated?.setText("Congratulations! You have guessed the correct number")
                  taken?.visibility= View.INVISIBLE
                  taken?.isEnabled=false
                  input?.visibility=View.INVISIBLE
                  input?.isEnabled=false


                  //Toast.makeText(this, "Congratulations! You guessed the correct number", Toast.LENGTH_LONG).show()
              }
          } catch (e: NumberFormatException) {
              Toast.makeText(this, "Invalid number entered", Toast.LENGTH_SHORT).show()
          }
          i++
          if(remain==0) {
              taken?.visibility= View.INVISIBLE
              taken?.isEnabled=false
              generated?.setText("No chances available.Game over.The number was $randomInt")
              input?.isEnabled=false
              input?.visibility=View.INVISIBLE

          }
       }
       // i++
       // }

        generate?.setOnClickListener {
            guessRandomNumber()
        }

        randomInt = generateRandomInt()


    }

    private fun generateRandomInt(): Int {
        return Random.nextInt(1, 11)
    }

    private fun guessRandomNumber() {
        generated?.visibility = View.VISIBLE
        generate?.visibility = View.GONE
        input?.visibility = View.VISIBLE
        input?.isEnabled = true
        taken?.visibility=View.VISIBLE
        taken?.isEnabled=true



        if (inputNumberText.isEmpty()) {
            //Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show()
            //return
        }
/*
        try {
            val inputNumber = inputNumberText.toInt()

            if (inputNumber < randomInt) {
                Toast.makeText(this, "Your guess is too low because the number is $randomInt", Toast.LENGTH_LONG).show()
            } else if (inputNumber > randomInt) {
                Toast.makeText(this, "Your guess is too high because the number is $randomInt ", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Congratulations! You guessed the correct number", Toast.LENGTH_LONG).show()
            }
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Invalid number entered", Toast.LENGTH_SHORT).show()
        }
        */

    }


    private fun startnewgame() {
        generated?.visibility = View.INVISIBLE
        generate?.visibility = View.VISIBLE
        generated?.setText("Random number generated")
        generate?.isEnabled = true
        input?.visibility = View.INVISIBLE
        input?.isEnabled = false
        input?.text?.clear()
        ///taken?.visibility=View.VISIBLE
        //randomInt = generateRandomInt()
    }



}
