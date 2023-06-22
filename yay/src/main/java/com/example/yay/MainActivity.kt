package com.example.yay

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.example.yay.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), CustomDialogInterface{

    private lateinit var binding: ActivityMainBinding
    private lateinit var mypageLogin: Mypage_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // ViewBinding 활성화
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 툴바 설정
        setSupportActionBar(binding.toolbar) // 액션바 설정
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true) // 왼쪽 버튼 사용 여부 true
            setHomeAsUpIndicator(R.drawable.ic_menu) // 왼쪽 버튼 이미지 설정
        }

        // WebView 바인딩
        val webView1 = binding.MainWebView1

        // URL
        webView1.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }
        webView1.loadUrl("https://blog.naver.com/0siheung21/223079166658")

        //main to main이 필요할까?
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

        mypageLogin = Mypage_login.getInstance()
        val loggedInEmail = mypageLogin.getLoggedInEmail()


        if (loggedInEmail != "") {
            // 이건 지금 로그인이 되어있다는 가정
            binding.MainMypagebtn.setOnClickListener {
                val intent = Intent(this, Mypage_main::class.java)
                startActivity(intent)
            }
        } else {
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
}