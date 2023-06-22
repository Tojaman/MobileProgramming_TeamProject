package com.example.yay

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.example.yay.databinding.ActivityProgramInfomationActitivtyBinding

class ProgramInfomationActitivty(context: Context, Interface: CustomDialogInterface) : Dialog(context) {
    // 액티비티에서 인터페이스를 받아옴
    private var customDialogInterface: CustomDialogInterface = Interface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_program_infomation_actitivty)

        val binding = ActivityProgramInfomationActitivtyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        // 배경을 투명하게함
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 추가 버튼 클릭 시 onAddButtonClicked 호출 후 종료
        binding.button12.setOnClickListener {
            customDialogInterface.onCheckButtonClicked()
            dismiss()

            val intent = Intent(context, ProgramFormActivity::class.java)
            context.startActivity(intent)
        }
    }
}