package com.example.project2


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlin.collections.*
import android.widget.EditText
import android.widget.ListView
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {


    private lateinit var studentNameEdit: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        studentNameView.text = studentInfo.name
        redIdView.text = studentInfo.redID
        emailView.text = studentInfo.email

        val listView = view.findViewById<ListView>(R.id.registered_courses)
        var registeredCourses : MutableList<Course> = mutableListOf<Course>()
        for (course in availableCourses) {
            if (course.selected) {
                registeredCourses.add(course)
            }
        }
        val listViewAdapter = MyListAdapterHome(
            view.context,
            android.R.layout.simple_list_item_1,
            registeredCourses
        )

        listView.adapter = listViewAdapter

    }


}
