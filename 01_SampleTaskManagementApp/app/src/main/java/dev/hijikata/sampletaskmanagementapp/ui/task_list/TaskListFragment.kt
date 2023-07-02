package dev.hijikata.sampletaskmanagementapp.ui.task_list

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import dev.hijikata.sampletaskmanagementapp.R
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TaskListFragment : Fragment() {
    private lateinit var taskListRecyclerView: RecyclerView
    private val adapter: TaskListAdapter by lazy { TaskListAdapter() }
    private val viewModel: TaskListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /** アンチパターンとまでは言わないけど古いやり方なので絶対マネしないでね */
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)

        /** アンチパターンとまでは言わないけど古いやり方なので絶対マネしないでね */
        taskListRecyclerView = view.findViewById(R.id.task_list_recycler_view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskListRecyclerView.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(context)
        }
        lifecycleScope.launch {
            viewModel.taskListStateFlow.collect {
                Log.d("DEBUG", it.toString())
                adapter.submitList(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchTaskList()
    }

}