package com.study.memo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class listviewadapter(val List: MutableList<DataModel>) : BaseAdapter() {
    override fun getCount(): Int {
        return List.size
    }

    override fun getItem(p0: Int): Any {
        return List[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val convertview = p1

        if(convertview==null){
            convertview== LayoutInflater.from(p2?.context).inflate(R.layout.listview_item,p2,false)

        }
        val date = convertview?.findViewById<TextView>(R.id.listviewdataarea)
        val memo =convertview?.findViewById<TextView>(R.id.listviewmemoarea)

        date!!.text =List[p0].date
        memo!!.text =List[p0].memo

        return convertview!!
    }
}