package com.example.yay



import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.yay.databinding.ActivityMypageJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class Mypage_join : Activity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var mypageLogin: Mypage_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMypageJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.MypageJoincheckBtn.setOnClickListener {
            val name = binding.MypageJoinNameEdt.text.toString()
            val password = binding.MypageJoinpwdEdt.text.toString()
            val email = binding.MypageJoinemailEdt.text.toString()
            val address = binding.MypageJoinaddressEdt.text.toString()
            val confirmPassword = binding.MypageCheckjoinpwdEdt.text.toString()
            //val num = binding.MypageJoinphonenumEdt.text.toString().toLong() 폰번호 Edt
            //val age = binding.MypageJoinageEdt.text.toString().toShort() 나이 Edt 필요

            if (name.isEmpty() || password.isEmpty() || email.isEmpty() || address.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 디버그용 코드: 입력된 정보 출력
            Log.d("Debug", "Name: $name")
            Log.d("Debug", "Password: $password")
            Log.d("Debug", "Email: $email")
            Log.d("Debug", "Address: $address")
            Log.d("Debug", "Confirm Password: $confirmPassword")


            if (password != confirmPassword) { // 비밀번호 불일치 시
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                // Firebase Authentication을 사용하여 회원가입 처리
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // 회원가입 성공
                            val user = hashMapOf(
                                "name" to name,
                                "email" to email,
                                "createdAt" to Date(),
                                // "num" to num,
                                //"age" to age
                            )

                            // Firestore에 회원 정보 저장
                            db.collection("users")
                                .document(auth.currentUser?.uid!!)
                                .set(user)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this, "회원 가입이 완료되었습니다.", Toast.LENGTH_SHORT
                                    ).show()

                                    val intent = Intent(this, Mypage_login::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                .addOnFailureListener { exception ->
                                    // Firestore에 회원 정보 저장 실패
                                    // 에러 처리 방법에 따라 적절히 처리
                                }
                        } else {
                            // 회원가입 실패
                            Log.e("Firebase", "회원가입 실패", task.exception)
                            Toast.makeText(
                                this, "회원 가입에 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }.addOnFailureListener { exception ->
                        // Firestore에 회원 정보 저장 실패
                        Log.e("Firebase", "Firestore 저장 실패", exception)
                    }
            }
        }

        binding.MypageCanceljoinBtn.setOnClickListener {
            // 회원가입 취소 시 동작
            finish()
        }
    }
}