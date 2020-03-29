package com.example.project2

import androidx.core.content.*
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListView
import android.R.layout.*



/**
 * A simple [Fragment] subclass.
 */
class CourseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_course, container, false)
        val listView = view.findViewById<ListView>(R.id.course_list_view)
        val listViewAdapter = MyListAdapter(
            view.context,
            simple_list_item_1,
            availableCourses
        )

        listView.adapter = listViewAdapter


        listView.setOnItemClickListener { parent, view, position, id ->
            availableCourses[id.toInt()].selected = !availableCourses[id.toInt()].selected
            println("${availableCourses[id.toInt()]} chosen: ${availableCourses[id.toInt()].selected}")
            if (availableCourses[id.toInt()].selected) {
                view.setBackgroundColor(ContextCompat.getColor(context as Context, R.color.selected))
            } else {
                view.setBackgroundColor(Color.TRANSPARENT)
            }
            listViewAdapter.notifyDataSetChanged()
        }

        return view
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        return
    }

}
