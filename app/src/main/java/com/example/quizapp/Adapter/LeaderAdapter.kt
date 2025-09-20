package com.example.quizapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quizapp.Domain.UserModel
import com.example.quizapp.databinding.ViewholderLeadersBinding

// hiển thị danh sách user
class LeaderAdapter: RecyclerView.Adapter<LeaderAdapter.ViewHolder>() {
    // Adapter = cầu nối dữ liệu → UI (RecyclerView)
    private lateinit var binding: ViewholderLeadersBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeaderAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ViewholderLeadersBinding.inflate(inflater, parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: LeaderAdapter.ViewHolder, position: Int) {
       val binding = ViewholderLeadersBinding.bind(holder.itemView)
        binding.titleTxt.text = differ.currentList[position].name

        // Lấy id của ảnh drawable theo tên (string).
        val drawableResourceId:Int = binding.root.resources.getIdentifier(
            differ.currentList[position].pic,
            "drawable", binding.root.context.packageName
        )

        // thư viện Glide để load ảnh nhanh + tiết kiệm bộ nhớ
        // cần import trong gradle/ module:app để sử dụng
        Glide.with(binding.root.context)
            .load(drawableResourceId)
            .into(binding.pic)
        // Cộng số rồi ép kiểu thành String
        binding.rowTxt.text = (position + 4).toString()
        binding.scoreTxt.text = differ.currentList[position].score.toString()

    }

    // Cú pháp viết tắt cho hàm 1 dòng
    override fun getItemCount() = differ.currentList.size

    //    đại diện cho 1 item trong danh sách
    //    Vì là inner class nên có thể truy cập biến binding của LeaderAdapter
    //    ViewHolder = nơi giữ các View con (TextView, ImageView) cho từng item.
    inner class ViewHolder: RecyclerView.ViewHolder(binding.root)

    //    DiffUtil.ItemCallback<UserModel>: so sánh 2 item để xem có khác nhau không
    private val differCallback = object :DiffUtil.ItemCallback<UserModel>() {
        // so sánh theo id
        override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem.id==newItem.id
        }
        // so sánh toàn bộ object
        override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem==newItem
        }
    }

    //    AsyncListDiffer giúp cập nhật danh sách tự động & mượt mà (animation) khi dữ liệu thay đổi
    val differ= AsyncListDiffer(this,differCallback)

}