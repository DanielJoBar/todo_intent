package com.alanturing.cpifp.todo

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.alanturing.cpifp.todo.adapter.TasksAdapter
import com.alanturing.cpifp.todo.data.TaskLocalRepository
import com.alanturing.cpifp.todo.databinding.ActivityMainBinding
import com.alanturing.cpifp.todo.model.Task
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        when(it.resultCode){
            Activity.RESULT_OK -> {
                binding.tasks.adapter = TasksAdapter(TaskLocalRepository.getInstance().tasks,::onShareItem,::onEditItem)
            }
            Activity.RESULT_CANCELED -> {
                Snackbar.make(this.binding.root,"Se ha cancelado",Snackbar.LENGTH_SHORT).show()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.tasks.adapter = TasksAdapter(TaskLocalRepository.getInstance().tasks,::onShareItem,::onEditItem)
        setContentView(binding.root)
        binding.createButton.setOnClickListener{
            val createIntent = Intent(this,CreateToDoActivity::class.java)
            //startActivity(createIntent)
            getResult.launch(createIntent)
        }

    }
    override fun onResume(){
        super.onResume()
        binding.tasks.adapter = TasksAdapter(TaskLocalRepository.getInstance().tasks,::onShareItem,::onEditItem)

    }
    fun onShareItem(task: Task){
        val statusText = if (task.isCompleted) "Completada"
                        else "Pendiente"
        val shareText = getString(R.string.share_text,task.title,task.description,statusText)
        val intent = Intent().apply {
            action= Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,shareText)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(intent,null)
        startActivity(shareIntent)
    }
    fun onEditItem(task: Task){
        val createIntent = Intent(this,EditActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("task",task)
        createIntent.putExtras(bundle)
        startActivity(createIntent)
        }
}
