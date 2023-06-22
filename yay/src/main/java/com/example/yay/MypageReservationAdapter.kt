import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yay.FirebaseData
import com.example.yay.R

class MypageReservationAdapter(private val context: Context) : RecyclerView.Adapter<MypageReservationAdapter.ViewHolder>() {

    // ViewHolder 클래스 정의
    var datas = mutableListOf<FirebaseData>()

    // onCreateViewHolder : 해당 뷰그룹의 LayoutInflater로 inflate한 뷰홀더 레이아웃(아이템뷰)을 뷰홀더에 넣어준다
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    // onBindViewHolder : 뷰홀더에 데이터를 넣어준다
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 파이어 베이스에서 가져온 사용자 데이터를 아이템뷰 객체에 바인딩 해준다.
        holder.bind(datas[position])

        val reservationBtn = holder.itemView.findViewById<Button>(R.id.reservation_btn)
    }

    // getItemCount : 아이템뷰 갯수를 반환
    override fun getItemCount(): Int = datas.size

    // ViewHolder : 아이템뷰 객체 생성
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txttitle: TextView = itemView.findViewById(R.id.ProgramTitle)
        private val txttime: TextView = itemView.findViewById(R.id.notifyProgramDate)
        private val txtlocation: TextView = itemView.findViewById(R.id.notifyProgramSpace)
        private val imgprogram: ImageView = itemView.findViewById(R.id.Program5_Image)

        fun bind(item: FirebaseData) {
            txttitle.text = item.title
            txttime.text = item.time.toString()
            txtlocation.text = item.location

            // Glide : 이미지뷰를 쉽게 띄우기 위해 쓰는 거라는데 뭔진 잘 모르겠음
            Glide.with(itemView).load(item.program).into(imgprogram)

        }
    }
}

