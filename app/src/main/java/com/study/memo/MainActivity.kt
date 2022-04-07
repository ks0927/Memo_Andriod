package com.study.memo

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {

    val dataModelList = mutableListOf<DataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var datetext =""

        val database = Firebase.database
        val myRef = database.getReference("mymemo")
        val listview =findViewById<ListView>(R.id.mainlv)

        val listAdapter = listviewadapter(dataModelList)

        listview.adapter = listAdapter


        myRef.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                //dataModelList.clear()
                for(dataModel in snapshot.children){
                    Log.d("data",dataModel.toString())
                    dataModelList.add(dataModel.getValue(DataModel::class.java)!!)
                }
                //listAdapter.notifyDataSetChanged()
                Log.d("Datamodel",dataModelList.toString())
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        )

        val writebutton = findViewById<ImageView>(R.id.writeBtn)
        writebutton.setOnClickListener {
            val DialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog,null)
            val Builder = AlertDialog.Builder(this).setView(DialogView).setTitle("메모 다이얼로그")
            val alterdialog =Builder.show()

            var dateselctbtn =alterdialog.findViewById<Button>(R.id.dateselectbtn)

            dateselctbtn?.setOnClickListener{
                val today =GregorianCalendar()
                val year : Int = today.get(Calendar.YEAR)
                val month : Int = today.get(Calendar.MONTH)
                val date : Int = today.get(Calendar.DATE)
                val dlg = DatePickerDialog(this,object : DatePickerDialog.OnDateSetListener{
                    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                        Log.d("Date","${p1},${p2 +1},${p3}")
                        dateselctbtn.setText("${p1}년,${p2 +1}월,${p3}일")

                        datetext="${p1}년,${p2 +1}월,${p3}일"
                    }
                },year,month,date)
                dlg.show()
            }

            val savebtn = alterdialog.findViewById<Button>(R.id.savebtn)
            savebtn?.setOnClickListener {
                val Memo =alterdialog.findViewById<EditText>(R.id.memotext)?.text.toString()
                val database = Firebase.database
                val myRef = database.getReference("mymemo")
                val model = DataModel(datetext,Memo)

                myRef.push().setValue(model)
                alterdialog.dismiss()
            }
        }

    }
}