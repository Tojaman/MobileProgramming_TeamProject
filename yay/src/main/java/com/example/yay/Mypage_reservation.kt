package com.example.yay

import MypageReservationAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.example.yay.databinding.ActivityMypageReservationBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class Mypage_reservation : AppCompatActivity() , CustomDialogInterface{
    private lateinit var binding : ActivityMypageReservationBinding
    private lateinit var mypageLogin: Mypage_login
    lateinit var MypageReservationAdapter: MypageReservationAdapter // 어댑터
    val datas = mutableListOf<FirebaseData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_reservation)

        title = "나의 예약 현황"

        binding = ActivityMypageReservationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 툴바 설정
        setSupportActionBar(binding.toolbar) // 액션바 설정
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true) // 왼쪽 버튼 사용 여부 true
            setHomeAsUpIndicator(R.drawable.ic_menu) // 왼쪽 버튼 이미지 설정
        }

        // 어댑터 설정
        MypageReservationAdapter = MypageReservationAdapter(this)
        binding.programRecyclerView.adapter = MypageReservationAdapter

        // 이제 해야할 일 : 서버에서 데이터 가져온 다음 적용해야함
        datas.apply {
            add(FirebaseData(program = R.drawable.home ,title = "프로그램 제목", time = "오후 3시", location = "청년스테이션"))
/*                    add(FirebaseData(img = R.drawable.profile3, name = "jenny", age = 26))
                    add(FirebaseData(img = R.drawable.profile2, name = "jhon", age = 27))
                    add(FirebaseData(img = R.drawable.profile5, name = "ruby", age = 21))
                    add(FirebaseData(img = R.drawable.profile4, name = "yuna", age = 23))*/


            MypageReservationAdapter.datas = datas
            MypageReservationAdapter.notifyDataSetChanged()
        }

        /*val Program5_Title = findViewById<TextView>(R.id.Program5_Title)
        val Program5_time = findViewById<TextView>(R.id.Program5_time)
        val Program5_location = findViewById<TextView>(R.id.Program5_location)*/


        // 이메일로 예약된 프로그램 찾기
        // 이건 서버에서 데이터로 출력하는 건데 회원가입이 안돼서 못함
        val email = "user@example.com" // 로그인된 이메일 정보
        val db = FirebaseFirestore.getInstance()
        val programsCollection = db.collection("programs")

        programsCollection.whereEqualTo("email", email).get().addOnSuccessListener { documents ->
            for (document in documents) {
                // 예약된 프로그램 정보 가져오기
                /*val title = document.getString("title")*/
                val title: String = document.getString("title") ?: ""
                /*val time = document.getString("time")*/
                val time: String = document.getString("time") ?: ""
                val participationFee = document.getString("participationFee")

                // XML에 프로그램 정보 업데이트
                datas.apply {
                    add(FirebaseData(program = R.drawable.home ,title = title, time = time, location = "청년스테이션"))
/*                    add(FirebaseData(img = R.drawable.profile3, name = "jenny", age = 26))
                    add(FirebaseData(img = R.drawable.profile2, name = "jhon", age = 27))
                    add(FirebaseData(img = R.drawable.profile5, name = "ruby", age = 21))
                    add(FirebaseData(img = R.drawable.profile4, name = "yuna", age = 23))*/

                    MypageReservationAdapter.datas = datas
                    MypageReservationAdapter.notifyDataSetChanged()
                }

                /*Program5_Title.text = title
                Program5_time.text = time
                Program5_location.text = "청년스테이션 / $participationFee"*/
            }
        }.addOnFailureListener { e ->
            // 쿼리 실행 실패 시 처리할 코드
        }




        //**
        // fun getUserUIDByEmail(email: String, callback: (String?) -> Unit) {
        //    val usersRef = db.collection("users")
        //    val query = usersRef.whereEqualTo("email", email)
        //
        //    query.get()
        //        .addOnSuccessListener { querySnapshot ->
        //            if (!querySnapshot.isEmpty) {
        //                val userUid = querySnapshot.documents[0].id
        //                callback(userUid)
        //            } else {
        //                callback(null)
        //            }
        //        }
        //        .addOnFailureListener { exception ->
        //            // 쿼리 실패 시 처리할 작업
        //            callback(null)
        //        }
        //}


        //
        // fun getProgramsByUserEmail(email: String, callback: (List<DocumentSnapshot>) -> Unit) {
        //    getUserUIDByEmail(email) { userUid ->
        //        if (userUid != null) {
        //            val programsRef = db.collection("program")
        //            val query = programsRef.whereEqualTo("email", userUid)
        //
        //            query.get()
        //                .addOnSuccessListener { querySnapshot ->
        //                    val programDocs = querySnapshot.documents
        //                    callback(programDocs)
        //                }
        //                .addOnFailureListener { exception ->
        //                    // 쿼리 실패 시 처리할 작업
        //                    callback(emptyList())
        //                }
        //        } else {
        //            callback(emptyList())
        //        }
        //    }
        //}
        //val userEmail = "사용자 이메일 주소"
        //
        //getProgramsByUserEmail(userEmail) { programDocs ->
        //    // 프로그램 문서에 대한 작업 수행
        //    for (doc in programDocs) {
        //        val programData = doc.data
        //        // 원하는 작업 수행
        //    }
        //}
        // **//



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