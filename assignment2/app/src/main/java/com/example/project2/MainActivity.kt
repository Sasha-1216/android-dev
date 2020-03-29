package com.example.project2


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context
import java.io.*
import com.google.gson.*


class Course (val courseId: Int,
              val courseNum: String,
              val courseTitle: String,
              val courseRoom: String,
              val startTime: String,
              val coursePhoto: Int
)

{
    var selected = false

    override fun toString() : String {
        return "${courseNum}: $courseTitle ($startTime at $courseRoom)"
    }
}

class StudentInfo {
    var name = ""
    var email = ""
    var redID = ""
    lateinit var courses : List<Course>
}

var availableCourses = listOf(
    Course(1,"CS-646-1", "Android App Development", "HH-45", "7:00PM", R.drawable.ic_school_black_24dp),
    Course(2,"ART-344", "Design for Internet", "ARTN-101", "8:00AM", R.drawable.ic_school_black_24dp),
    Course(3,"MUS-345", "World Music", "MM-223", "12:00PM", R.drawable.ic_school_black_24dp),
    Course(4,"MATH-101", "Pre-calculus", "ENS-362", "2:30PM", R.drawable.ic_school_black_24dp)
)
var studentInfo = StudentInfo()
var gson = Gson()
var currentFragment = 0




class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            currentFragment = item.itemId
            when (item.itemId) {

                R.id.destination_course -> {
                    println("course pressed")
                    replaceFragment(CourseFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.destination_home -> {
                    println("home pressed")
                    replaceFragment(HomeFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.destination_student_info -> {
                    println("student pressed")
                    replaceFragment(StudentInfoFragment())
                    return@OnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

    private fun getFragmentById(id : Int) : Fragment {
        when (id) {

            R.id.destination_course -> {
                println("Getting course fragment")
                return CourseFragment()
            }
            R.id.destination_home -> {
                println("Getting home fragment")
                return HomeFragment()
            }
            R.id.destination_student_info -> {
                println("Getting studentinfo fragment")
                return StudentInfoFragment()
            }
            else -> {
                println("Defaulting to home fragment")
                return HomeFragment()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, StudentInfoFragment())
            .commit()
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, HomeFragment())
            .commit()

        replaceFragment(getFragmentById(currentFragment))

        try {
            val saveFile: String = getString(R.string.save_file)
            val inputStream = openFileInput(saveFile)
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder = StringBuilder()
            for (line in bufferedReader.readLines()) {
                stringBuilder.append(line)
            }
            inputStreamReader.close()
            studentInfo = gson.fromJson(stringBuilder.toString(), StudentInfo::class.java)
            availableCourses = studentInfo.courses
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, "CURRENT")
        fragmentTransaction.commit()
    }

    override fun onPause() {
        super.onPause()
        val savePath :String
        try {
            studentInfo.courses = availableCourses
            val saveFile: String = getString(R.string.save_file)
            val outputStream = openFileOutput(saveFile, Context.MODE_PRIVATE)
            println("saving:")
            println(gson.toJson(studentInfo))
            outputStream.write(gson.toJson(studentInfo).toByteArray())
            outputStream.flush()
            outputStream.close()



        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}







