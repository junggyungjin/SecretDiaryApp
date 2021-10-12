package fastcampus.aop.part2.secretdiaryapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import fastcampus.aop.part2.secretdiaryapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val numberPicker1: NumberPicker by lazy {
        binding.numberPicker1.apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val numberPicker2: NumberPicker by lazy {
        binding.numberPicker2.apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val numberPicker3: NumberPicker by lazy {
        binding.numberPicker3.apply {
            minValue = 0
            maxValue = 9
        }
    }

    private var changePasswordMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        numberPicker1
        numberPicker2
        numberPicker3

        Log.d("changePasswordMode", changePasswordMode.toString())

        binding.openButton.setOnClickListener {
            if (changePasswordMode) {
                Log.d("changePasswordMode", changePasswordMode.toString())
                Toast.makeText(this@MainActivity, "비밀번호 변경 중입니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)

            val passwordFromUser =
                "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                startActivity(Intent(this@MainActivity, DiaryActivity::class.java))
            } else {
                showError()
            }
        }

        binding.changePasswordButton.setOnClickListener {
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser =
                "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
            if (changePasswordMode) {
                Log.d("changePasswordMode", changePasswordMode.toString())
                passwordPreferences.edit(true) {
                    putString("password", passwordFromUser)
                }
                Log.d("changePasswordMode", changePasswordMode.toString())
                changePasswordMode = false
                binding.changePasswordButton.setBackgroundColor(Color.BLACK)

            } else {
                Log.d("changePasswordMode", changePasswordMode.toString())
                if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                    changePasswordMode = true
                    Toast.makeText(this@MainActivity, "변경할 패스워드를 입력해주세요", Toast.LENGTH_SHORT).show()

                    binding.changePasswordButton.setBackgroundColor(Color.RED)
                } else {
                    showError()
                }
            }

        }

    }

    fun showError() {
        AlertDialog.Builder(this@MainActivity)
            .setTitle("실패")
            .setMessage("비밀번호가 잘못되었습니다")
            .setPositiveButton("확인") { _, _ -> }
            .create()
            .show()
    }
}