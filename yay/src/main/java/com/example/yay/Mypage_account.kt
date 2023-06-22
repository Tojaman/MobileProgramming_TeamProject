package com.example.yay

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yay.databinding.ActivityMypageAccountBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth


class Mypage_account : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_account)
        title = "계정정보"

        // Firebase 컴포넌트 초기화
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val binding = ActivityMypageAccountBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 사용자가 로그인되어 있는지 확인
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // 사용자가 로그인되어 있을 경우, Firestore에서 사용자 정보 가져오기
            val email = currentUser.email

            db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val document = querySnapshot.documents[0]
                        // 사용자 정보 가져오기
                        val name = document.getString("name")
                        val id = document.getString("id")
                        val password = document.getString("password")

                        // EditText에 정보 설정
                        binding.MypageChecknameEdt.setText(name)
                        binding.MypageViewIDEdt.setText(id)
                        binding.MypageChangepwdEdt.setText(password)
                        binding.MypageViewemailEdt.setText(email)
                    } else {
                        // 사용자 정보 없음
                        // 처리 방법에 따라 적절히 처리
                    }
                }
                .addOnFailureListener { exception ->
                    // Firestore에서 사용자 정보 가져오기 실패
                    // 에러 처리 방법에 따라 적절히 처리
                }
        } else {
            // 사용자가 로그인되어 있지 않음
            // 처리 방법에 따라 적절히 처리
        }

        // 비밀번호 변경 버튼 인텐트
        binding.MypageChangepwdBtn.setOnClickListener {
            val intent = Intent(applicationContext, Mypage_changepwd::class.java)
            startActivity(intent) // 비밀번호 변경에 대한 부분은 버튼으로 인텐트하여 새로운 액티비티로 변경하는 것이아닌
            //그냥 account 에서 모두 할 수 있게 하는걸로 합시다. (비효율적인거같아요 죄송합니다 ㅠㅠ)
        }

        binding.MypageModifyinformBtn.setOnClickListener {
            val name = binding.MypageChecknameEdt.text.toString()
            val id = binding.MypageViewIDEdt.text.toString()
            val password = binding.MypageChangepwdEdt.text.toString()
            val email = binding.MypageViewemailEdt.text.toString()

            val user = hashMapOf(
                "name" to name,
                "id" to id,
                "password" to password,
                "email" to email
            )

            db.collection("users")
                .document(email)
                .set(user)
                .addOnSuccessListener {
                    // 사용자 정보 업데이트 성공
                    finish() // 정보 저장 후 액티비티 종료
                }
                .addOnFailureListener { exception ->
                    // 사용자 정보 업데이트 실패
                    // 에러 처리 방법에 따라 적절히 처리
                }
        }

        binding.MypageCancelformBtn.setOnClickListener {
            // 정보 수정 안했으니까 그냥 돌아가면 된다.
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
