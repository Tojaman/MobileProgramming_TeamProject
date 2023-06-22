package com.example.yay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.yay.databinding.ActivityMypageFindPwdBinding
import com.google.firebase.auth.FirebaseAuth

class Mypage_find_pwd : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMypageFindPwdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "비밀번호 찾기"

        auth = FirebaseAuth.getInstance()

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

//        binding.MypageCheckphoneBtn.setOnClickListener {
//            val email = binding.MypageCheckIDEdt.text.toString()
//
//            if (email.isEmpty()) {
//                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            // 비밀번호 재설정 이메일 보내기
//            auth.sendPasswordResetEmail(email)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        // 이메일 전송 성공
//                        Toast.makeText(this, "임시 비밀번호가 발송되었습니다!", Toast.LENGTH_SHORT).show()
//                        val intent = Intent(applicationContext, Mypage_login::class.java)
//                        startActivity(intent)
//                    } else {
//                        // 이메일 전송 실패
//                        Toast.makeText(this, "이메일 전송에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//        }

        binding.MypageCheckphoneBtn.setOnClickListener {
            val email = binding.MypageCheckEmailEdt.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 비밀번호 재설정 이메일 보내기
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // 이메일 전송 성공
                        Toast.makeText(this, "임시 비밀번호가 발송되었습니다!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, Mypage_login::class.java)
                        startActivity(intent)
                    } else {
                        // 이메일 전송 실패
                        Toast.makeText(this, "이메일 전송에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }


        }
    }}
