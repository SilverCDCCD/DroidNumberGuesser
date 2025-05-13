package example.silver.numberguesser

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import example.silver.numberguesser.databinding.ActivityGameBinding
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
	private lateinit var binding: ActivityGameBinding

	private var guesses: Int = 0
	private var guessValue: Int = 128
	private var secretValue: Int = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityGameBinding.inflate(layoutInflater)
		setContentView(binding.root)

		resetValues()

		binding.btnDecreaseTen.setOnClickListener { adjustValue(-10) }
		binding.btnDecreaseOne.setOnClickListener { adjustValue(-1) }
		binding.btnIncreaseOne.setOnClickListener { adjustValue(1) }
		binding.btnIncreaseTen.setOnClickListener { adjustValue(10) }
		setSubmitButtonMode(true)
	}

	private fun adjustValue(amt: Int) {
		guessValue += amt
		guessValue = min(max(1, guessValue), 255)
		binding.txtGuessNumber.text = guessValue.toString()
	}

	private fun resetValues() {
		secretValue = (abs(Random.nextInt()) % 255) + 1
		guessValue = 128
		guesses = 0
		binding.txtGuessNumber.text = "128"
	}

	private fun setSubmitButtonMode(gameRunning: Boolean) {
		if (gameRunning) {
			binding.btnSubmitGuess.text = getString(R.string.submit)
			binding.btnSubmitGuess.setOnClickListener { submitGuess() }
		} else {
			binding.btnSubmitGuess.text = getString(R.string.reset)
			binding.btnSubmitGuess.setOnClickListener {
				resetValues()
				setSubmitButtonMode(true)
			}
		}
	}

	private fun submitGuess() {
		guesses++
		if (guessValue < secretValue) {
			Toast.makeText(this@GameActivity, "Too low!", Toast.LENGTH_SHORT).show()
		} else if (guessValue > secretValue) {
			Toast.makeText(this@GameActivity, "Too high!", Toast.LENGTH_SHORT).show()
		} else {
			Toast.makeText(this@GameActivity, "You got it in $guesses guess${if (guesses == 1) "es" else ""}!", Toast.LENGTH_SHORT).show()
			setSubmitButtonMode(false)
		}
	}
}