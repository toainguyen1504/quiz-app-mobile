package com.example.quizapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.databinding.ViewholderQuestionBinding

// Hiển thị đáp án và xử lý chọn đáp án
class QuestionAdapter (
    val correctAnswer: String,
    val users: MutableList<String> = mutableListOf(),
    val returnScore: score
): RecyclerView.Adapter<QuestionAdapter.Viewholder>() {
//    Adapter chịu trách nhiệm onCreateViewHolder, onBindViewHolder, getItemCount
//    ViewHolder dùng để “gói” layout con (ViewholderQuestionBinding)
//    -> giúp tái sử dụng view thay vì inflate mới liên tục

    // interface callback để báo điểm về Activity/Fragment
    interface score {
        fun amount(number: Int, clickedAnswer: String)
    }

    private lateinit var binding: ViewholderQuestionBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuestionAdapter.Viewholder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ViewholderQuestionBinding.inflate(inflater, parent, false)
        return Viewholder()
    }

//    Xác định đáp án đúng (currectPos) dựa trên "a" | "b" | "c" | "d"
    override fun onBindViewHolder(holder: QuestionAdapter.Viewholder, position: Int) {
        val binding = ViewholderQuestionBinding.bind(holder.itemView)
        binding.answerTxt.text = differ.currentList[position]
        var currectPos = 0
        when(correctAnswer) {
            "a" -> {
                currectPos = 0
            }
            "b" -> {
                currectPos = 1
            }
            "c" -> {
                currectPos = 2
            }
            "d" -> {
                currectPos = 3
            }
        }

        //    Nếu đáp án đúng: tô xanh, hiển thị tick ✅.
        if (differ.currentList.size == 5 && currectPos == position ) {
            binding.answerTxt.setBackgroundResource(R.drawable.green_background)
            binding.answerTxt.setTextColor (
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.white
                )
            )

            val drawable = ContextCompat.getDrawable(binding.root.context, R.drawable.tick)
            binding.answerTxt.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
        }

        if (differ.currentList.size == 5) {
            var clickedPos = 0
            when (differ.currentList[4]){
                "a" -> {
                    clickedPos = 0
                }
                "b" -> {
                    clickedPos = 1
                }
                "c" -> {
                    clickedPos = 2
                }
                "d" -> {
                    clickedPos = 3
                }
            }

            //Nếu đáp án sai: tô đỏ, hiển thị icon ❌
            if (clickedPos == position && clickedPos != currectPos) {
                binding.answerTxt.setBackgroundResource(R.drawable.red_background)
                binding.answerTxt.setTextColor (
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white
                    )
                )

                val drawable = ContextCompat.getDrawable(binding.root.context, R.drawable.thieves)
                binding.answerTxt.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
            }
        }

        // item thứ 5 bị ẩn (do list có thêm phần tử phụ lưu state)
        if (position == 4) {
            binding.root.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            var str = ""

            when (position) {
                0 -> {
                    str = "a"
                }
                1 -> {
                    str = "b"
                }
                2 -> {
                    str = "c"
                }
                3 -> {
                    str = "d"
                }
            }

            users.add(4, str)

            // notifyDataSetChanged() reload toàn bộ list -> không tối ưu.
            // Với AsyncListDiffer, nên gọi differ.submitList(newList) thay vì notify.
            notifyDataSetChanged()

            if (currectPos == position) {
                binding.answerTxt.setBackgroundResource(R.drawable.green_background)
                binding.answerTxt.setTextColor (
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white
                    )
                )

                val drawable = ContextCompat.getDrawable(binding.root.context, R.drawable.tick)
                binding.answerTxt.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
                returnScore.amount(5, str)
            } else {
                binding.answerTxt.setBackgroundResource(R.drawable.red_background)
                binding.answerTxt.setTextColor (
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white
                    )
                )

                val drawable = ContextCompat.getDrawable(binding.root.context, R.drawable.thieves)
                binding.answerTxt.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
                returnScore.amount(0, str)
            }
        }

        if (differ.currentList.size == 5) {
            holder.itemView.setOnClickListener(null)
        }

    }

    override fun getItemCount() = differ.currentList.size

    inner class Viewholder: RecyclerView.ViewHolder(binding.root)

//    so sánh danh sách cũ và mới tự động, thay vì phải notifyDataSetChanged() mỗi lần
    private val differCallback = object :DiffUtil.ItemCallback<String>() {
        //  check id hoặc unique key
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
        //  check nội dung có thay đổi hay không
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    //  AsyncListDiffer giúp update list hiệu quả, tránh reload toàn bộ UI
    val differ= AsyncListDiffer(this,differCallback)
}


//1. Kotlin cần nắm trong code này
//lateinit: khai báo biến sẽ được khởi tạo sau (binding). Nếu chưa init mà gọi → crash.
//inner class ViewHolder: cho phép ViewHolder truy cập biến bên ngoài Adapter.
//when: thay cho switch-case.
//val vs var: val bất biến, var thay đổi.
//interface: định nghĩa callback.
//
// 2. Điểm cần cải thiện (best practice)
//Không nên dùng private lateinit var binding ở cấp Adapter → dễ bị “reuse” sai.
//Thay vào đó: tạo binding trong ViewHolder.
//Không nên hardcode "a", "b", "c", "d" → có thể map bằng position.
//Không nên notifyDataSetChanged() → dùng submitList().
//Tách logic so sánh đáp án ra hàm riêng để code gọn hơn.