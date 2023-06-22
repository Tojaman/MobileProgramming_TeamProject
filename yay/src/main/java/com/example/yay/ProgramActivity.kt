package com.example.yay

import MypageReservationAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.example.yay.databinding.ActivityProgramBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProgramActivity : AppCompatActivity(), CustomDialogInterface,
    ProgramAdapter.ItemClickListener {
    private lateinit var binding : ActivityProgramBinding
    private lateinit var mypageLogin: Mypage_login
    lateinit var ProgramAdapter: ProgramAdapter // 어댑터
    val datas = mutableListOf<FirebaseData>() // 파이어 베이스 데이터
    lateinit var customDialog: ProgramInfomationActitivty

    override fun onCreate(savedInstanceState: Bundle?) {
        /*val customDialog = ProgramInfomationActitivty(this,this)*/
        customDialog = ProgramInfomationActitivty(this, this)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_program)

        binding = ActivityProgramBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar) // 액션바 설정

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true) // 왼쪽 버튼 사용 여부 true
            setHomeAsUpIndicator(R.drawable.ic_menu) // 왼쪽 버튼 이미지 설정
        }

        // 어댑터 설정
        ProgramAdapter = ProgramAdapter(this@ProgramActivity, this)
        binding.programRecyclerView.adapter = ProgramAdapter

        // Firebase Firestore 참조
        val db = Firebase.firestore
        val collectionRef = db.collection("6month_events")

        // 데이터 조회
        // collectionRef.get()을 사용하여 데이터 조회 -> querySnapshot을 순회하면서 각 문서에 필요한 정보를 가져와 datas.apply 블록이 동적으로 생성됨
        collectionRef.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {

                // 아래 변수들은 데이터베이스에 있는 키값으로 설정하면 됨
                val recruitmentPeriod = document.getString("모집기간")
                val recruitmentPeople = document.getString("모집인원")
                val phoneNumber = document.getString("문의사항")
                val participationCost = document.getString("참가비용")
                val eventDateTime = document.getString("행사일시")

                recruitmentPeriod?.let { recruitPeriod ->
                    recruitmentPeople?.let { recruitPeople ->
                        phoneNumber?.let { phoneNum ->
                            participationCost?.let { cost ->
                                eventDateTime?.let { dateTime ->
                                    datas.apply {
                                        add(
                                            FirebaseData(
                                                // ★★★★★ 파란, 빨간 글씨 파이어 베이스 속성에 맞게 바꿔 주면 됨 ★★★★★
                                                program = R.drawable.home,
                                                title = recruitmentPeriod,
                                                time = recruitmentPeople,
                                                location = phoneNumber,
                                                participationCost = participationCost,
                                                eventDateTime = eventDateTime
                                            )
                                        )
                                    }
                                }
                                }
                            }
                        }
                    }
                }

                /*val title = document.getString("참가비용")
                val time = document.getString("행사일시")
                val location = document.getString("행사일시")*/

                /*title?.let { t ->
                    time?.let { tm ->
                        location?.let { loc ->
                            datas.apply {
                                add(FirebaseData(program = R.drawable.home, title = t, time = tm, location = loc))
                            }
                        }
                    }
                }*/


            // 어댑터에 데이터를 할당하고 변경 사항을 반영
            ProgramAdapter.datas = datas
            ProgramAdapter.notifyDataSetChanged()
        }.addOnFailureListener { exception ->
            // 데이터 조회가 실패한 경우 처리할 내용
        }
        /*collectionRef.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                val recruitmentPeriod = document.getString("모집기간")
                val recruitmentPeople = document.getString("모집인원")
                val phoneNumber = document.getString("전화번호")
                val participationCost = document.getString("참가비용")
                val eventDescription = document.getString("행사내용")
                val eventDateTime = document.getString("행사일시")

                recruitmentPeriod?.let { recruitPeriod ->
                    recruitmentPeople?.let { recruitPeople ->
                        phoneNumber?.let { phoneNum ->
                            participationCost?.let { cost ->
                                eventDescription?.let { description ->
                                    eventDateTime?.let { dateTime ->
                                        datas.apply {
                                            add(
                                                FirebaseData(
                                                    program = R.drawable.home,
                                                    title = participationCost,
                                                    time = eventDateTime,
                                                    location = eventDateTime
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 어댑터에 데이터를 할당하고 변경 사항을 반영
            ProgramAdapter.datas = datas
            ProgramAdapter.notifyDataSetChanged()
        }.addOnFailureListener { exception ->
            // 데이터 조회가 실패한 경우 처리할 내용
            // 예외 처리 코드 작성
        }*/



        /*datas.apply {
            add(FirebaseData(program = R.drawable.home ,title = "이름", time = "시간", location = "청년스테이션"))
*//*                    add(FirebaseData(img = R.drawable.profile3, name = "jenny", age = 26))
                    add(FirebaseData(img = R.drawable.profile2, name = "jhon", age = 27))
                    add(FirebaseData(img = R.drawable.profile5, name = "ruby", age = 21))
                    add(FirebaseData(img = R.drawable.profile4, name = "yuna", age = 23))*//*

            ProgramAdapter.datas = datas
            ProgramAdapter.notifyDataSetChanged()
        }*/

        // 예약하기 버튼 동작 -> 모르겠음
        /*val reservationBtn = findViewById(R.id.reservation_btn)

        // 대관 리스트에 있는 신청 버튼 누르면 안내 메시지 팝업 띄우기
        binding.reservationBtn.setOnClickListener {
            customDialog.show() // 대관 안내 팝업창 show()
            onCheckButtonClicked() // "추가" 메시지를 띄움
        }*/




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
            val intent = Intent(this, MainActivity::class.java)
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

    override fun onItemClick(position: Int) {
        customDialog.show() // 대관 안내 팝업창 show()
        onCheckButtonClicked() // "추가" 메시지를 띄움
    }

}