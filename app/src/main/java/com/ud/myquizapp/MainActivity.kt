package com.ud.myquizapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val btn:Button =findViewById(R.id.btn_start)
        val etName:EditText=findViewById(R.id.et_text)

        btn.setOnClickListener {
            if(etName.text.isNotEmpty()){
                val intent=Intent(this,QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.USER_NAME,etName.text.toString())
                startActivity(intent)

                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left)//transition of 1 to another activity

                finish()//after Intented to another screen when we pressed
            // back button it closes the app


            }else{
                Toast.makeText(this," Pls !! Enter Your Name !! ",Toast.LENGTH_SHORT).show()
            }
        }
    }
}