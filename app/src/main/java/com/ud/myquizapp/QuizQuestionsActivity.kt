package com.ud.myquizapp


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat


class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener{
    private var mCurrentPosition:Int =1
    private var mQuestionsList:ArrayList<Question>?=null
    private  var mSelectedOptionPosition:Int=0

    private var mUseName :String?=null
    private var mCorrectAnswers:Int=0

    private var progressBar: ProgressBar?=null
    private var tvProgress: TextView? = null
    private var tvQuestion:TextView? = null
    private var ivImage: ImageView? = null
    private var tvOptionOne:TextView? = null
    private var tvOptionTwo:TextView? = null
    private var tvOptionThree:TextView? = null
    private var tvOptionFour:TextView? = null
    private var btnSubmit:Button?=null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUseName=intent.getStringExtra(Constants.USER_NAME)

        progressBar=findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_image)
        tvOptionOne = findViewById(R.id.tv_option1)
        tvOptionTwo = findViewById(R.id.tv_option2)
        tvOptionThree = findViewById(R.id.tv_option3)
        tvOptionFour = findViewById(R.id.tv_option4)
        btnSubmit=findViewById(R.id.btn_submit)

        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)
        mQuestionsList=Constants.getQuestions()
        setQuestion()
    }







    @SuppressLint("SetTextI18n")
    private fun setQuestion() {
        defaultOptionsView()
        val question: Question = mQuestionsList!![mCurrentPosition - 1]
        progressBar?.progress = mCurrentPosition
        tvProgress?.text = "$mCurrentPosition/${progressBar?.max}"
        tvQuestion?.text = question.question
        ivImage?.setImageResource(question.image)
        tvOptionOne?.text = question.optionOne
        tvOptionTwo?.text = question.optionTwo
        tvOptionThree?.text = question.optionThree
        tvOptionFour?.text = question.optionFour

        if(mCurrentPosition==mQuestionsList!!.size){
            btnSubmit?.text="FINISH"
        }else{
            btnSubmit?.text="NEXT"
        }

    }


    private fun answerView(answer: Int, drawableView: Int) {

        when (answer) {

            1 -> {
                tvOptionOne?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }
            2 -> {
                tvOptionTwo?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }
            3 -> {
                tvOptionThree?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }
            4 -> {
                tvOptionFour?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {

        defaultOptionsView()

        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(//sets selected color to tv
            Color.parseColor("#363A43")
        )
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this@QuizQuestionsActivity,
            R.drawable.selected_option_border
        )
    }


    private fun defaultOptionsView() {//its sets the default color to every

        val options = ArrayList<TextView>()

        tvOptionOne?.let {
            options.add(0, it)
        }
        tvOptionTwo?.let {
            options.add(1, it)
        }
        tvOptionThree?.let {
            options.add(2, it)
        }
        tvOptionFour?.let {
            options.add(3,it)
        }

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface= Typeface.DEFAULT
            option.background=ContextCompat.getDrawable(
                this,
                R.drawable.deafault_option_border_bg
            )
        }
    }




    override fun onClick(view: View?) {
        when (view?.id) {

            R.id.tv_option1 -> {//executes after on click
                tvOptionOne?.let {
                    selectedOptionView(it, 1)
                }

            }

            R.id.tv_option2 -> {
                tvOptionTwo?.let {
                    selectedOptionView(it, 2)
                }

            }

            R.id.tv_option3 -> {
                tvOptionThree?.let {
                    selectedOptionView(it, 3)
                }

            }

            R.id.tv_option4 -> {
                tvOptionFour?.let {
                    selectedOptionView(it, 4)
                }

            }
            R.id.btn_submit ->{
                //todo"implement button "

                if(mSelectedOptionPosition==0){
                    mCurrentPosition++

                    when{
                        mCurrentPosition<=mQuestionsList!!.size ->{
                            setQuestion()
                        }
                        else ->{
                            Toast.makeText(this,"Congrats for the completion",Toast.LENGTH_LONG).show()

                            val intent=Intent(this,ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME,mUseName)
                            intent.putExtra(Constants.CORRECT_ANSWERS,mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS,mQuestionsList?.size)
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left)//transition of 1 to another activity
                            finish()

                        }
                    }
                }else{
                    val question = mQuestionsList?.get(mCurrentPosition - 1)

                    // This is to check if the answer is wrong
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }else{
                        mCorrectAnswers++
                    }

                    // This is for correct answer
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if(mCurrentPosition==mQuestionsList!!.size){
                        btnSubmit?.text="FINISH"
                    }else{
                        btnSubmit?.text="NEXT"
                    }
                    mSelectedOptionPosition=0

                }

            }
        }
    }
}