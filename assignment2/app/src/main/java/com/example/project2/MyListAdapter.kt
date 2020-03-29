package com.example.project2

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat


class MyListAdapter(context: Context, @LayoutRes resource: Int, private val availableCourses: List<Course>):
    ArrayAdapter<Course>(context, resource, availableCourses) {
    private class ViewHolder(row: View?) {
        var courseNum: TextView? = null
        var courseTitle: TextView? = null
        var imgEmp: ImageView? = null
        var courseRoom: TextView? = null
        var startTime: TextView? = null

        init {
            this.courseNum = row?.findViewById<TextView>(R.id.course_num)
            this.courseTitle = row?.findViewById<TextView>(R.id.course_title)
            this.imgEmp = row?.findViewById<ImageView>(R.id.img_course)
            this.courseRoom = row?.findViewById<TextView>(R.id.course_room)
            this.startTime = row?.findViewById<TextView>(R.id.start_time)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater =
                context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.list_item, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var course = availableCourses[position]
        viewHolder.courseNum?.text = course.courseNum
        viewHolder.courseTitle?.text = course.courseTitle
        viewHolder.courseRoom?.text = course.courseRoom
        viewHolder.startTime?.text = course.startTime
        viewHolder.imgEmp?.setImageResource(course.coursePhoto!!)


        var course_id: Int? = 0
        var course_num: String? = null
        var course_title: String? = null
        var course_room: String? = null
        var start_time: String? = null
        var course_photo: Int? = null

        if (course.selected) {
            view.setBackgroundColor(ContextCompat.getColor(context as Context, R.color.selected))
        }
        else {
            view.setBackgroundColor(Color.TRANSPARENT)
        }
        return view
    }

    override fun getItem(i: Int): Course {
        return availableCourses[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return availableCourses.size
    }
}

class MyListAdapterHome(context: Context, @LayoutRes resource: Int, private val availableCourses: List<Course>):
    ArrayAdapter<Course>(context, resource, availableCourses) {
    private class ViewHolder(row: View?) {
        var courseNum: TextView? = null
        var courseTitle: TextView? = null
        var imgEmp: ImageView? = null
        var courseRoom: TextView? = null
        var startTime: TextView? = null

        init {
            this.courseNum = row?.findViewById<TextView>(R.id.course_num)
            this.courseTitle = row?.findViewById<TextView>(R.id.course_title)
            this.imgEmp = row?.findViewById<ImageView>(R.id.img_course)
            this.courseRoom = row?.findViewById<TextView>(R.id.course_room)
            this.startTime = row?.findViewById<TextView>(R.id.start_time)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater =
                context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.list_item, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var course = availableCourses[position]
        viewHolder.courseNum?.text = course.courseNum
        viewHolder.courseTitle?.text = course.courseTitle
        viewHolder.courseRoom?.text = course.courseRoom
        viewHolder.startTime?.text = course.startTime
        viewHolder.imgEmp?.setImageResource(course.coursePhoto!!)


        var course_id: Int? = 0
        var course_num: String? = null
        var course_title: String? = null
        var course_room: String? = null
        var start_time: String? = null
        var course_photo: Int? = null

        if (!course.selected) {
            view.visibility = View.GONE
        }

        return view
    }

    override fun getItem(i: Int): Course {
        return availableCourses[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return availableCourses.size
    }
}