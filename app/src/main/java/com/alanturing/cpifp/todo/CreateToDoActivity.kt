package com.alanturing.cpifp.todo

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alanturing.cpifp.todo.data.TaskLocalRepository
import com.alanturing.cpifp.todo.databinding.ActivityCreateToDoBinding
import com.alanturing.cpifp.todo.databinding.ActivityMainBinding
import com.alanturing.cpifp.todo.model.Task

class CreateToDoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateToDoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        //Recupero el repositorio en la misma instancia que el Main activity
        val repository = TaskLocalRepository.getInstance()
        super.onCreate(savedInstanceState)
        binding = ActivityCreateToDoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.BotonCrear.setOnClickListener{
            val task = Task(repository.getNextTaskId(),//Crea una tarea con el siguiente id
            binding.titleInput.text.toString(), //cambia el texto con de titleLabel con el texto obtenido
            binding.descriptionInput.text.toString(),//Cambia el texto de la descripcion
            false)
            repository.add(task)//Añade la tarea al repositorio
            setResult(Activity.RESULT_OK)
            finish()//Cierra la actividad
        }
        //Cancel Button
        binding.BotonCancelar.setOnClickListener{
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}