package fastcampus.aop.part2.secretdiaryapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import fastcampus.aop.part2.secretdiaryapp.databinding.ActivityDiaryBinding
import fastcampus.aop.part2.secretdiaryapp.databinding.ActivityMainBinding

class DiaryActivity : AppCompatActivity() {
    lateinit var binding: ActivityDiaryBinding

    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)

        binding.diaryEditText.setText(detailPreferences.getString("detail", ""))

        val runnable = Runnable {
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit {
                putString("detail", binding.diaryEditText.text.toString())
            }
            Log.d("DiaryActivity", "SAVE!!! ${binding.diaryEditText.text.toString()}")
        }

        binding.diaryEditText.addTextChangedListener {
            Log.d("DiaryActivity", "Textchanged : $it")
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 500)
        }
    }
}