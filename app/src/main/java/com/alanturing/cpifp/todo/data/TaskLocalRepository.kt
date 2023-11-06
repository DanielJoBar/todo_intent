package com.alanturing.cpifp.todo.data

import android.util.Log
import com.alanturing.cpifp.todo.model.Task

class TaskLocalRepository() {
    companion object {
        private var _INSTANCE:TaskLocalRepository? = null
        fun getInstance():TaskLocalRepository {
            if(_INSTANCE==null){
                _INSTANCE = TaskLocalRepository()
            }
            return _INSTANCE!!
        }
    }
    private val _tasks = mutableListOf<Task>()
    private var contador=_tasks.size;
    fun getNextTaskId():Int{
        return ++contador;
    }

    val tasks:List<Task>
        get() = _tasks

    fun add(task:Task) {
        _tasks.add(task)
    }
    fun delete(id:Int) {
        var delTask = this._tasks.find{it.id==id}
        if(delTask!=null) this._tasks.remove(delTask)
    }
    fun update(task:Task) {
        var upTask = this._tasks.find{it.id==task.id}
        if(upTask!=null) {
            var index = this._tasks.indexOf(upTask)
            if(index>=0) this._tasks[index]=task
        }
    }
}