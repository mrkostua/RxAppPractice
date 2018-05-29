package com.example.user.rxapp.wikiSearch

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.user.rxapp.R
import com.example.user.rxapp.data.local.dbRoom.TaskDo
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * @author Kostiantyn Prysiazhnyi on 5/28/2018.
 */
class TasksRecycleViewAdapter(private val data: List<TaskDo>) : RecyclerView.Adapter<TasksRecycleViewAdapter.ViewHolder>() {
    private val bSearchWikiSubject = PublishSubject.create<TaskDo>()
    fun getSearchWikiButtonObservable(): Observable<TaskDo> = bSearchWikiSubject.hide()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.wiki_search_row_item, parent, false))

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTaskName = view.findViewById(R.id.tvTaskNameRow) as TextView
        private val tvTaskDescription = view.findViewById(R.id.tvDescriptionRow) as TextView
        private val bSearchWiki = view.findViewById(R.id.bSearchInWikiRow) as Button
        private var cutDescription = ""
        fun bind(taskDo: TaskDo) {
            tvTaskName.text = taskDo.taskName
            cutDescription = "Description : " + taskDo.taskDescription.substring(0..taskDo.taskDescription.length / 2) + "\n\t\t..."
            tvTaskDescription.text = cutDescription

            RxView.clicks(bSearchWiki).subscribe { bSearchWikiSubject.onNext(taskDo) }
            RxView.clicks(tvTaskDescription).subscribe { tvTaskDescription.text = taskDo.taskDescription }

        }
    }
}