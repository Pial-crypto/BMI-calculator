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

    private var inputNumberText = ""
    private var inputNumber = 0
    private var i = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        generate = findViewById(R.id.generate)
        input = findViewById(R.id.Guess)
        generated = findViewById(R.id.generated)
        newgame = findViewById(R.id.newgame)
        taken = findViewById(R.id.taken)

        newgame?.setOnClickListener(View.OnClickListener {
            startnewgame()
            i = 1
        })

        taken?.setOnClickListener {
            val remain = 5 - i + 1
            try {
                inputNumberText = input?.text.toString().trim()
                inputNumber = inputNumberText.toInt()

                if (inputNumber < randomInt) {
                    generated?.text = "Your guess is low. $remain chances left"
                } else if (inputNumber > randomInt) {
                    generated?.text = "Your guess is high. $remain chances left"
                } else {
                    generated?.text = "Congratulations! You have guessed the correct number"
                    taken?.visibility = View.INVISIBLE
                    taken?.isEnabled = false
                    input?.visibility = View.INVISIBLE
                    input?.isEnabled = false
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Invalid number entered", Toast.LENGTH_SHORT).show()
            }
            i++
            if (remain == 0) {
                taken?.visibility = View.INVISIBLE
                taken?.isEnabled = false
                generated?.text = "No chances available. Game over. The number was $randomInt"
                input?.isEnabled = false
                input?.visibility = View.INVISIBLE
            }
        }

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
        taken?.visibility = View.VISIBLE
        taken?.isEnabled = true
    }

    private fun startnewgame() {
        generated?.visibility = View.INVISIBLE
        generate?.visibility = View.VISIBLE
        generated?.text = "Random number generated"
        generate?.isEnabled = true
        input?.visibility = View.INVISIBLE
        input?.isEnabled = false
        input?.text?.clear()
    }
}
