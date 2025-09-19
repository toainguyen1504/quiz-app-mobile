package com.example.quizapp.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizapp.Domain.QuestionModel
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window: Window = this@MainActivity.window
        window.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.grey)

        binding.apply {
            bottomMenu.setItemSelected(R.id.home)
            bottomMenu.setOnItemSelectedListener {
                if (it == R.id.board) {
                    startActivity(Intent(this@MainActivity, LeaderActivity::class.java))
                }
            }

            singleBtn.setOnClickListener {
                val intent = Intent(this@MainActivity, QuestionActivity::class.java)
                intent.putParcelableArrayListExtra("list", ArrayList(questionList()))
                startActivity(intent)
            }
        }
    }

//    This is a list of questions and this for example. You can get question from your API service.
    private fun questionList(): MutableList<QuestionModel> {
        val question: MutableList<QuestionModel> = mutableListOf()

        question.add(
            QuestionModel(
                1,
                "Which planet is the largest planet in the solar system?",
                "Earth",
                "Mars",
                "Neptune",
                "Jupiter",
                "d",
                5,
                "q_1",
                null
            )
        )

        question.add(
            QuestionModel(
                2,
                "What is the capital city of France?",
                "Berlin",
                "Madrid",
                "Paris",
                "Rome",
                "c",
                5,
                "q_2",
                null
            )
        )

        question.add(
            QuestionModel(
                3,
                "Which gas do plants absorb from the atmosphere?",
                "Oxygen",
                "Carbon Dioxide",
                "Nitrogen",
                "Hydrogen",
                "b",
                5,
                "q_3",
                null
            )
        )

        question.add(
            QuestionModel(
                4,
                "Who developed the theory of relativity?",
                "Isaac Newton",
                "Albert Einstein",
                "Galileo Galilei",
                "Nikola Tesla",
                "b",
                5,
                "q_4",
                null
            )
        )

        question.add(
            QuestionModel(
                5,
                "Which ocean is the largest in the world?",
                "Atlantic Ocean",
                "Indian Ocean",
                "Pacific Ocean",
                "Arctic Ocean",
                "c",
                5,
                "q_5",
                null
            )
        )

        question.add(
            QuestionModel(
                6,
                "What is the hardest natural substance on Earth?",
                "Gold",
                "Iron",
                "Diamond",
                "Platinum",
                "c",
                5,
                "q_6",
                null
            )
        )

        question.add(
            QuestionModel(
                7,
                "Which element has the chemical symbol 'O'?",
                "Oxygen",
                "Osmium",
                "Oxide",
                "Opium",
                "a",
                5,
                "q_7",
                null
            )
        )

        question.add(
            QuestionModel(
                8,
                "Which continent is known as the 'Dark Continent'?",
                "Asia",
                "Africa",
                "South America",
                "Europe",
                "b",
                5,
                "q_8",
                null
            )
        )

        question.add(
            QuestionModel(
                9,
                "How many players are there in a football (soccer) team on the field?",
                "9",
                "10",
                "11",
                "12",
                "c",
                5,
                "q_9",
                null
            )
        )

        question.add(
            QuestionModel(
                10,
                "Which instrument is used to measure temperature?",
                "Barometer",
                "Thermometer",
                "Hygrometer",
                "Altimeter",
                "b",
                5,
                "q_10",
                null
            )
        )

        return question
    }
}