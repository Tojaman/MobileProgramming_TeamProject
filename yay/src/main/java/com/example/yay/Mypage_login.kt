package com.example.yay

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.example.yay.databinding.ActivityMypageLoginBinding
import com.example.yay.databinding.ActivityMypageReservationBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class Mypage_login : AppCompatActivity() , CustomDialogInterface{
    private lateinit var auth: FirebaseAuth
    private var loggedInEmail: String = ""
    private lateinit var binding : ActivityMypageLoginBinding
    private lateinit var mypageLogin: Mypage_login

    companion object {
        private var instance: Mypage_login? = null

        fun getInstance(): Mypage_login {
            if (instance == null) {
                instance = Mypage_login()
            }
            return instance!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 설정
        setSupportActionBar(binding.toolbar) // 액션바 설정
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true) // 왼쪽 버튼 사용 여부 true
            setHomeAsUpIndicator(R.drawable.ic_menu) // 왼쪽 버튼 이미지 설정
        }


        title = "로그인"

        auth = FirebaseAuth.getInstance()

        binding.MypageFindpwdBtn.setOnClickListener {
            val intent = Intent(this, Mypage_find_pwd::class.java)
            startActivity(intent)
        }

        binding.MypageLoginBtn.setOnClickListener {
            val email = binding.MypageIDEdt.text.toString()
            val password = binding.MypagePwdEdt.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // 로그인 성공
                        Toast.makeText(this, "로그인이 성공하였습니다!", Toast.LENGTH_SHORT).show()
                        loggedInEmail = email // 이메일 저장

                        val intent = Intent(this, Mypage_main::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // 로그인 실패
                        Toast.makeText(this, "로그인에 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        binding.MypageJoinBtn.setOnClickListener {
            val intent = Intent(this, Mypage_join::class.java)
            startActivity(intent)
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

        mypageLogin = Mypage_login.getInstance()
        val loggedInEmail = mypageLogin.getLoggedInEmail()

        if (loggedInEmail == "") {
            // 이건 지금 로그인이 되어있다는 가정
            binding.MainMypagebtn.setOnClickListener {
                val intent = Intent(this, Mypage_main::class.java)
                startActivity(intent)
            }
        }
        else {
            // 로그인이 되어있지 않다는 가정
            binding.MainMypagebtn.setOnClickListener {
                val intent = Intent(this, Mypage_login::class.java)
                startActivity(intent)
            }
        }

        //네비게이션뷰 아이템 선택 이벤트
        binding.navigationView.setNavigationItemSelectedListener(
            object: NavigationView.OnNavigationItemSelectedListener{
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    when(item.itemId){
                        R.id.know -> {
                            item.isChecked = true
                            displayMessage("알고 싶어 !")
                            binding.drawerLayout.closeDrawers()
                            return true
                        }

                        R.id.enjoy -> {
                            item.isChecked = true
                            displayMessage("즐기고 싶어 !")
                            binding.drawerLayout.closeDrawers()
                            return true
                        }
                        R.id.map -> {
                            item.isChecked = true
                            displayMessage("여기 지도 띄워주기")
                            binding.drawerLayout.closeDrawers()
                            return true
                        }
                        else -> {
                            return true
                        }
                    }//when
                }//onNavigationItemSelected
            }//NavigationView.OnNavigationItemSelectedListener
        )//setNavigationItemSelectedListener
    }

    override fun onCheckButtonClicked() {
        Toast.makeText(this, "추가", Toast.LENGTH_SHORT).show()
    }

/*    // 3.툴바 메뉴 버튼을 설정
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu_ver02, menu)       // menu 메뉴를 toolbar 메뉴 버튼으로 설정
        return true
    }*/

    // 4.툴바 메뉴 버튼이 클릭 됐을 때 콜백
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 클릭된 메뉴 아이템의 아이디 마다 when 구절로 클릭시 동작을 설정한다.
        when (item!!.itemId) {
            android.R.id.home -> { // 메뉴 버튼(왼쪽 - 3개짜리)
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
            /*R.id.menu_search -> { // 검색 버튼
                Snackbar.make(binding.toolbar, "Search menu pressed", Snackbar.LENGTH_SHORT).show()
            }*/
            R.id.menu_settings -> { // 설정 버튼
                Snackbar.make(binding.toolbar, "Account menu pressed", Snackbar.LENGTH_SHORT).show()
            }
            R.id.menu_help -> { // 도움말 버튼
                Snackbar.make(binding.toolbar, "Logout menu pressed", Snackbar.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(binding.navigationView))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

    //메시지 알림
    private fun displayMessage(message: String){
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }


    // 액티비티 전체에서 loggedInEmail 변수에 접근할 수 있는 메서드
    fun getLoggedInEmail(): String {
        return loggedInEmail
    }
}
