package com.example.yay

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yay.databinding.ActivityHomeBinding
import com.example.yay.databinding.ActivityMainBinding

class HomeActivity  : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ViewBinding 활성화
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        // 버튼 클릭 이벤트 처리
        binding.MainHomebtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.MainReservationbtn.setOnClickListener {
            val intent = Intent(this, SpaceActivity::class.java)
            startActivity(intent)
        }

        binding.MainProgrambtn.setOnClickListener {
            val intent = Intent(this, ProgramActivity::class.java)
            startActivity(intent)
        }

        binding.MainMypagebtn.setOnClickListener {
            val intent = Intent(this, Mypage_main::class.java)
            startActivity(intent)
        }
    }
}