package com.example.yay

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.yay.databinding.ActivitySpaceInformationBinding

class SpaceInformationActivity(context: Context, Interface: CustomDialogInterface) : Dialog(context) {
    // 액티비티에서 인터페이스를 받아옴
    private var customDialogInterface: CustomDialogInterface = Interface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_program_infomation_actitivty)

        // SpaceInformation 바인딩 객체 생성
        val binding = ActivitySpaceInformationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 배경을 투명하게함
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 확인 버튼 클릭 시 onAddButtonClicked 호출 후 종료
        binding.button12.setOnClickListener {
            customDialogInterface.onCheckButtonClicked()
            dismiss() // 다이얼로그 닫는 메서드(팝업 메시지 사라짐)
            // 대관 신청서로 넘어감
            val intent = Intent(context, SpaceFormActivity::class.java)
            context.startActivity(intent)
        }
    }
}