package dev.hijikata.sampletaskmanagementapp.ui.task_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.hijikata.sampletaskmanagementapp.databinding.ItemTaskBinding
import dev.hijikata.sampletaskmanagementapp.model.task.Task
import dev.hijikata.sampletaskmanagementapp.util.toDeadLineString
import java.time.format.DateTimeFormatter

class TaskListAdapter: ListAdapter<Task, TaskListAdapter.ViewHolder>(DiffCallback) {
    private val dateFormatter = DateTimeFormatter.ofPattern("MM月dd日 HH時mm分")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = getItem(position)
        holder.binding.apply {
            taskTitleText.text = task.title
            taskDescriptionText.text = task.description
            taskDeadlineText.text = task.deadline?.toDeadLineString() ?: "なし"
        }
    }

    class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<Task>() {

            /** 2つのアイテム自体が同じものであるかを判定する。 */
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                /** 内容が変更されていても存在が一緒ならOK */
                return oldItem.id == newItem.id
            }

            /** 2つのアイテムの内容が同じものであるかを判定する。 */
            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                /** data class は内部のプロパティを勘定して比較してくれるのこれでOK */
                return oldItem == newItem
            }

        }
    }
}