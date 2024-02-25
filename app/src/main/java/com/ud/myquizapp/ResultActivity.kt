package com.ud.myquizapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvName:TextView=findViewById(R.id.tv_name)
        val tvScore:TextView=findViewById(R.id.tv_score)
        val tvFinish:TextView=findViewById(R.id.btn_finish)


        tvName.text=intent.getStringExtra(Constants.USER_NAME)

        val totalQuestions=intent.getIntExtra(Constants.TOTAL_QUESTIONS,0)
        val correctAnswer=intent.getIntExtra(Constants.CORRECT_ANSWERS,0)

        tvScore.text=("Your Score is $correctAnswer out of $totalQuestions")

        tvFinish.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))//another form to write
            finish()
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left)//transition of 1 to another activity
        }
    }
}