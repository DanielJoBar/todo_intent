package com.alanturing.cpifp.todo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import com.alanturing.cpifp.todo.data.TaskLocalRepository
import com.alanturing.cpifp.todo.databinding.ActivityEditBinding
import com.alanturing.cpifp.todo.model.Task
class EditActivity : AppCompatActivity(){
    private lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = TaskLocalRepository.getInstance()
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = this.intent.extras;
        val task1:Task = if(bundle!=null) bundle.getSerializable("task") as Task
                         else Task(-1,"","",false)
        binding.titleInputEditable.setText(task1.title)
        binding.descriptionInputEditable.setText(task1.description)
        binding.checkbox.isChecked = task1.isCompleted
        binding.BotonActualizarEdit.setOnClickListener{
            val viewTask1:Task = Task(task1.id,
                binding.titleInputEditable.text.toString(),
                binding.descriptionInputEditable.text.toString(),
                binding.checkbox.isChecked);
            repository.update(viewTask1)
            setResult(Activity.RESULT_OK)
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        //configuracion cancel button
        binding.BotonCancelarEdit.setOnClickListener{
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
        }
}