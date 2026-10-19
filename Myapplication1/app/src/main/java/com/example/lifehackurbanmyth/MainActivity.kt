package com.example.lifehackurbanmyth

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

// A simple native Android quiz built with Kotlin and standard Android views.
class MainActivity : Activity() {

    // Each question has a statement, the correct answer, and a short explanation.
    data class QuizQuestion(
        val statement: String,
        val isHack: Boolean,
        val explanation: String
    )

    private val questions = listOf(
        QuizQuestion(
            "Putting a wooden spoon over a boiling pot can stop it from boiling over.",
            true,
            "A spoon may break surface bubbles briefly, but it is not a reliable way to prevent boiling over. Watch the pot and lower the heat."
        ),
        QuizQuestion(
            "You can charge a phone faster by turning on Airplane Mode.",
            true,
            "Airplane Mode switches off cellular connections and can reduce power use while charging. The difference depends on the phone and charger."
        ),
        QuizQuestion(
            "Putting a wet phone in rice is the best way to repair water damage.",
            false,
            "Rice is not a reliable repair method and can leave dust in the phone. Turn the phone off and seek proper repair advice."
        ),
        QuizQuestion(
            "A cold shower is a guaranteed cure for heat exhaustion.",
            false,
            "Cooling can help, but heat exhaustion can be serious. Move to a cool place, cool the body safely, and seek medical help if symptoms are severe or do not improve."
        ),
        QuizQuestion(
            "Keeping a reusable shopping list can help you avoid forgetting items.",
            true,
            "A list helps you plan purchases and check what you need before leaving home."
        ),
        QuizQuestion(
            "Microwaves cook food from the inside out.",
            false,
            "Microwaves heat food as energy is absorbed in different areas. Heating is not simply from the inside out, and food can heat unevenly."
        ),
        QuizQuestion(
            "Turning off unused lights can reduce electricity use.",
            true,
            "Lights use electricity when switched on, so turning off lights you do not need can save energy."
        )
    )

    private var questionIndex = 0
    private var score = 0
    private var answered = false
    private var userAnswers = mutableListOf<Boolean?>()
    private var feedback = ""

    private lateinit var root: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showWelcomeScreen()
    }

    private fun setupRoot(): LinearLayout {
        root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(24, 28, 24, 24)
            setBackgroundColor(Color.rgb(245, 248, 252))
        }
        val scroll = ScrollView(this)
        scroll.addView(root)
        setContentView(scroll)
        return root
    }

    private fun addText(
        text: String,
        size: Float = 18f,
        bold: Boolean = false,
        color: Int = Color.rgb(35, 45, 60)
    ): TextView {
        return TextView(this).apply {
            this.text = text
            textSize = size
            setTextColor(color)
            if (bold) setTypeface(null, Typeface.BOLD)
            gravity = Gravity.CENTER
            setPadding(8, 12, 8, 12)
            root.addView(this, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ))
        }
    }

    private fun addButton(label: String, onClick: () -> Unit): Button {
        return Button(this).apply {
            text = label
            textSize = 16f
            isAllCaps = false
            setOnClickListener { onClick() }
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 8, 0, 8)
            root.addView(this, params)
        }
    }

    private fun showWelcomeScreen() {
        setupRoot()
        addText("LIFE HACK OR\nURBAN MYTH?", 30f, true, Color.rgb(27, 76, 125))
        addText("Test your common sense and discover which everyday tips are useful and which are myths.")
        addText("How to play", 21f, true)
        addText("Read each statement and choose Hack (True) or Myth (False). You will receive feedback and a final score.")
        addText("There are ${questions.size} questions. Try your best!")
        addButton("Start Quiz") {
            questionIndex = 0
            score = 0
            answered = false
            userAnswers = MutableList(questions.size) { null }
            feedback = ""
            showQuestionScreen()
        }
    }

    private fun showQuestionScreen() {
        setupRoot()
        if (questionIndex >= questions.size) {
            showScoreScreen()
            return
        }

        val question = questions[questionIndex]
        addText("QUESTION ${questionIndex + 1} OF ${questions.size}", 16f, true, Color.rgb(27, 76, 125))
        addText(question.statement, 23f, true)
        addText("Is this a real-life hack or an urban myth?", 16f)

        val hackButton = addButton("Hack (True)") {
            submitAnswer(true)
        }
        val mythButton = addButton("Myth (False)") {
            submitAnswer(false)
        }
        hackButton.isEnabled = !answered
        mythButton.isEnabled = !answered

        if (answered) {
            addText(feedback, 17f, true, if (userAnswers[questionIndex] == question.isHack)
                Color.rgb(30, 125, 75) else Color.rgb(180, 55, 55))
            addText("Explanation: ${question.explanation}", 16f)
            addButton(if (questionIndex == questions.lastIndex) "View Score" else "Next Question") {
                questionIndex++
                answered = false
                feedback = ""
                if (questionIndex < questions.size) showQuestionScreen() else showScoreScreen()
            }
        }
    }

    private fun submitAnswer(answer: Boolean) {
        if (answered) return
        val question = questions[questionIndex]
        userAnswers[questionIndex] = answer
        if (answer == question.isHack) {
            score++
            feedback = "Correct! Well done."
        } else {
            feedback = "Not quite! Check the explanation below."
        }
        answered = true
        showQuestionScreen()
    }

    private fun showScoreScreen() {
        setupRoot()
        val percentage = (score * 100) / questions.size
        addText("QUIZ COMPLETE!", 28f, true, Color.rgb(27, 76, 125))
        addText("Your score: $score out of ${questions.size}", 25f, true)
        addText("Score: $percentage%", 22f, true)
        val message = when {
            percentage >= 80 -> "Great job! You are a Life Hack Master."
            percentage >= 50 -> "Good effort! Keep checking facts before trying a hack."
            else -> "Keep practising! Learning to question viral advice takes time."
        }
        addText(message, 18f)
        addButton("Review Answers") { showReviewScreen() }
        addButton("Play Again") {
            questionIndex = 0
            score = 0
            answered = false
            userAnswers = MutableList(questions.size) { null }
            feedback = ""
            showWelcomeScreen()
        }
    }

    private fun showReviewScreen() {
        setupRoot()
        addText("ANSWER REVIEW", 27f, true, Color.rgb(27, 76, 125))
        questions.forEachIndexed { index, question ->
            val correctAnswer = if (question.isHack) "Hack (True)" else "Myth (False)"
            val yourAnswer = when (userAnswers.getOrNull(index)) {
                true -> "Hack (True)"
                false -> "Myth (False)"
                null -> "Not answered"
            }
            addText("${index + 1}. ${question.statement}", 17f, true)
            addText("Correct answer: $correctAnswer\nYour answer: $yourAnswer", 15f)
            addText(question.explanation, 15f)
        }
        addButton("Back to Score") { showScoreScreen() }
        addButton("Play Again") {
            questionIndex = 0
            score = 0
            answered = false
            userAnswers = MutableList(questions.size) { null }
            feedback = ""
            showWelcomeScreen()
        }
    }
}
