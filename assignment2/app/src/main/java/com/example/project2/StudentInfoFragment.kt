package com.example.project2


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.view.Gravity
import android.widget.Toast


/**
 * A simple [Fragment] subclass.
 */


class StudentInfoFragment : Fragment() {

    private lateinit var studentNameEdit: EditText
    private lateinit var studentRedIDEdit: EditText
    private lateinit var studentEmailEdit: EditText


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_info, container, false)
        studentNameEdit = view.findViewById(R.id.student_name_edit) as EditText
        studentRedIDEdit = view.findViewById(R.id.student_red_id_edit) as EditText
        studentEmailEdit = view.findViewById(R.id.student_email_edit) as EditText
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val let = activity?.let {
            /**
             *  create view model in activity scope
             */
            studentNameEdit.setText(studentInfo.name)
            studentRedIDEdit.setText(studentInfo.redID)
            studentEmailEdit.setText(studentInfo.email)
        }



    }
    override fun onStart() {
        super.onStart()
        var toast = Toast.makeText(activity, "", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0, 0)

        val studentNameWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }


            override fun onTextChanged (
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                studentInfo.name = sequence.toString()
            }


            override fun afterTextChanged(sequence: Editable?) {
                val messageResId = R.string.student_name_saved
                toast.setText(messageResId)
                toast.show()
            }

        }

        val studentRedIDWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }


            override fun onTextChanged (
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                studentInfo.redID = sequence.toString()
            }


            override fun afterTextChanged(sequence: Editable?) {
                val messageResId = R.string.student_red_id_saved
                toast.setText(messageResId)
                toast.show()
            }

        }

        val studentEmailWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged (
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                studentInfo.email = sequence.toString()
            }


            override fun afterTextChanged(sequence: Editable?) {
                val messageResId = R.string.student_email_saved
                toast.setText(messageResId)
                toast.show()
            }

        }

        studentNameEdit.addTextChangedListener(studentNameWatcher)
        studentRedIDEdit.addTextChangedListener(studentRedIDWatcher)
        studentEmailEdit.addTextChangedListener(studentEmailWatcher)


    }
}



