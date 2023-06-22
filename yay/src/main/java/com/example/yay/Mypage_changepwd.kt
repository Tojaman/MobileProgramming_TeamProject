package com.example.yay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.yay.databinding.ActivityMypageChangepwdBinding

class Mypage_changepwd : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_changepwd)

        val binding = ActivityMypageChangepwdBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 비밀번호 수정 완료 버튼 인텐트
        binding.MypageChangepwdmodifyBtn.setOnClickListener(){
            // 비밀번호를 데이터베이스에서 수정하는 코드 필요.
            finish()
        }

        // 비밀번호 수정 취소 버튼 인텐트
        binding.MypageChangepwdcancelBtn.setOnClickListener(){
            // 딱히 중간 과정이 없음.
            finish()
        }
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