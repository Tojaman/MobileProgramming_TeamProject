package com.example.yay

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.example.yay.databinding.ActivitySpaceBinding
import com.example.yay.databinding.ActivitySpaceFormBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class SpaceFormActivity : AppCompatActivity(), CustomDialogInterface{
    private lateinit var binding : ActivitySpaceFormBinding
    private lateinit var mypageLogin: Mypage_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_space_form)

/*        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val timePicker = findViewById<TimePicker>(R.id.timePicker)*/

        binding = ActivitySpaceFormBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 툴바 설정
        setSupportActionBar(binding.toolbar) // 액션바 설정
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true) // 왼쪽 버튼 사용 여부 true
            setHomeAsUpIndicator(R.drawable.ic_menu) // 왼쪽 버튼 이미지 설정
        }


        // 날자 및 시간 선택 버튼 누르면 시간 선택, 날짜 선택 위젯 나옴
/*        binding.SelectDateBtn.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                // 선택된 날짜 처리 로직
                val selectedDate = "${year}년 ${month + 1}월 ${dayOfMonth}일"
                binding.SpaceTime.text = selectedDate

                // TimePickerDialog 생성
                val timePickerDialog = TimePickerDialog(
                    this, { _, hourOfDay, minute ->
                        val selectedTime = "${hourOfDay}시 ${minute}분"
                        binding.SpaceTime.text = "${binding.SpaceTime.text} $selectedTime"
                        // 여기에서 선택된 날짜와 시간을 처리할 수 있습니다.
                    },
                    0, 0, false
                ) // 초기 선택 시간 (0시 0분), 24시간 형식 사용 여부(false로 설정하면 AM/PM 형식 사용)

                timePickerDialog.show()
            }, 2023, 0, 1) // 초기 선택 날짜 (2023년 1월 1일)

            datePickerDialog.show()
        }*/
        binding.SelectDateBtn.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                // 선택된 날짜 처리 로직
                val selectedDate = "${year}년 ${month + 1}월 ${dayOfMonth}일"
                binding.SpaceTime.text = Editable.Factory.getInstance().newEditable(selectedDate)

                // TimePickerDialog 대신 Spinner 사용
                val timeValues = (10..22).map { "${it}시" }
                val minuteValues = arrayOf("0분", "15분", "30분", "45분")

                // 시 선택
                val timeSpinner = Spinner(this)
                val timeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, timeValues)
                timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                timeSpinner.adapter = timeAdapter

                // 분 선택
                val minuteSpinner = Spinner(this)
                // minuteValues 배열 데이터를 android.R.layout.simple_spinner_item 레이아웃 형식으로 저장한다.
                val minuteAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, minuteValues)
                // minuteAdapter를 드롭다운 형식 view로 바꿈
                minuteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                minuteSpinner.adapter = minuteAdapter

                val timePickerLayout = LinearLayout(this)
                timePickerLayout.orientation = LinearLayout.HORIZONTAL
                timePickerLayout.addView(timeSpinner)
                timePickerLayout.addView(minuteSpinner)

                AlertDialog.Builder(this)
                    .setTitle("시간 선택")
                    .setView(timePickerLayout)
                    .setPositiveButton("확인") { _, _ ->
                        val selectedTime = "${timeSpinner.selectedItem} ${minuteSpinner.selectedItem}"
                        binding.SpaceTime.append(" $selectedTime")
                        // 여기에서 선택된 날짜와 시간을 처리할 수 있습니다.
                    }
                    .setNegativeButton("취소", null)
                    .show()
            }, 2023, 0, 1) // 초기 선택 날짜 (2023년 1월 1일)

            datePickerDialog.show()
        }



        // 신청하기 버튼을 누르면 예약 완료 페이지로 넘어감
        binding.SpaceFormSubmitBtn.setOnClickListener {
            val intent = Intent(this, SpaceReserationActivity::class.java)
            startActivity(intent)
        }


        /*val intent = Intent(this, SpaceReserationActivity::class.java)
        startActivity(intent)*/


        // 버튼 클릭 이벤트 처리
        binding.MainHomebtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finishAffinity() // 현재 액티비티와 그 위에 쌓인 모든 액티비티 종료료
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