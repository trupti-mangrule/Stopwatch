package com.example.truptimangrule.kotlinfirsttry

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_stop_watch.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class StopWatchActivity : AppCompatActivity() {


    var handler: Handler? = null
    var i:Int=0

  //  var milli_seconds: TextView? = null

    internal var MillisecondTime: Long = 0
    internal var StartTime: Long = 0
    internal var TimeBuff: Long = 0
    internal var UpdateTime = 0L


    internal var Seconds: Int = 0
    internal var Minutes: Int = 0
    internal var MilliSeconds: Int = 0

    var tempHour:Int=0
    var tempMin:Int=0
    var tempSec:Int=0

   // var a :Int=0
    val lapTimeList = ArrayList<String>()
    val miliSecondsList = ArrayList<Long>()

    internal var flag:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop_watch)
        rv_leap.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)


        this.actionBar?.setTitle("A stopwatch")

        lapTimeList.add("")
        lapTimeList.add("")
        lapTimeList.add("")
        lapTimeList.add("")
        lapTimeList.add("")
        lapTimeList.add("")
        lapTimeList.add("")
        lapTimeList.add("")

        btn_reset.bringToFront()
        btn_start.bringToFront()
        btn_stop.bringToFront()
        //creating our adapter
        val adapter = CustomAdapter(lapTimeList)


        //now adding the adapter to recyclerview




        rv_leap.adapter = adapter


        btn_start.setOnClickListener(){
            StartTime = SystemClock.uptimeMillis()
            handler?.postDelayed(runnable, 0)
            flag=true
            btn_reset.setVisibility(INVISIBLE)
            btn_stop.setVisibility(VISIBLE)
            btn_start.setVisibility(INVISIBLE)

        }
        handler = Handler()

        btn_stop.setOnClickListener(){
            handler?.removeCallbacks(runnable)
            flag=false
            miliSecondsList.add(UpdateTime)
            lapTimeList.add(0,""+tv_hour_lap.getText()+":"+tv_minute_lap.getText()+":"+tv_seconds_lap.getText())
            println("LAP is "+tv_hour.getText()+":"+tv_minute.getText()+":"+tv_seconds.getText())
            adapter.notifyDataSetChanged();
            tempHour=tv_hour.getText().toString().toInt()
            tempMin=tv_minute.getText().toString().toInt()
            tempSec=tv_seconds.getText().toString().toInt()
            println("tempHour "+tv_hour.getText().toString().toInt()+":"+tv_minute.getText().toString().toInt()+":"+tv_seconds.getText().toString().toInt())
            TimeBuff=UpdateTime

            btn_reset.setVisibility(VISIBLE)
            btn_stop.setVisibility(INVISIBLE)
            btn_start.setVisibility(VISIBLE)
        }

        btn_reset.setOnClickListener(){
            StartTime=0
            UpdateTime=0
            MillisecondTime=0
            Seconds=0
            Minutes=0
            MilliSeconds=0
            TimeBuff=0
            tempSec=0
            tempHour=0
            tempMin=0
            lapTimeList.clear()
            miliSecondsList.clear()
            tv_hour.setText("00")
            tv_minute.setText("00")
            tv_seconds.setText("00")
            tv_hour_lap.setText("00")
            tv_minute_lap.setText("00")
            tv_seconds_lap.setText("00")
            for(i in lapTimeList.indices){
                lapTimeList.set(i,"")
            }
            miliSecondsList.clear()
            adapter.notifyDataSetChanged()
            btn_reset.setVisibility(INVISIBLE)
            btn_stop.setVisibility(VISIBLE)
        }

    }

    var runnable: Runnable = object : Runnable {

        override fun run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime

            UpdateTime = TimeBuff + MillisecondTime

            Seconds = (UpdateTime / 1000).toInt()

            Minutes = Seconds / 60

            Seconds = Seconds % 60

            MilliSeconds = (UpdateTime % 100).toInt()

            if (MilliSeconds.toString().length < 2) {
                tv_seconds?.text = "0" + MilliSeconds.toString()
            } else {
                tv_seconds?.text = MilliSeconds.toString()
            }
            if (Seconds.toString().length < 2) {
                tv_minute?.text = "0" + Seconds.toString()
            } else {
                tv_minute?.text = Seconds.toString()
            }
            if (Minutes.toString().length < 2) {
                tv_hour?.text = "0" + Minutes.toString()
            } else {
                tv_hour?.text = Minutes.toString()
            }


           /* if(miliSecondsList.size!=0){


                var tempHourLap:Int=tv_hour.getText().toString().toInt()-tempHour
                var tempMinLap:Int=tv_minute.getText().toString().toInt()-tempMin
                var tempSecLap:Int=tv_seconds.getText().toString().toInt()-tempSec
                println("lap time is "+tempHourLap+":"+tempMinLap+":"+tempSecLap)

                if (tempHourLap.toString().length < 2) {
                    tv_hour_lap?.text = "0" + tempHourLap.toString()
                } else {
                    tv_hour_lap?.text = tempHourLap.toString()
                }
                if (tempMinLap.toString().length < 2) {
                    tv_minute_lap?.text = "0" + tempMinLap.toString()
                } else {
                    tv_minute_lap?.text = tempMinLap.toString()
                }
                if (tempSecLap.toString().length < 2) {
                    tv_seconds_lap?.text = "0" + tempSecLap.toString()
                } else {
                    tv_seconds_lap?.text = tempSecLap.toString()
                }
            }*/
            if(miliSecondsList.size!=0){
               println("miliSecondsList.get(0) "+ miliSecondsList.get(0))
               println("UpdateTime-miliSecondsList.get(0).toInt() "+(UpdateTime-miliSecondsList.get(0).toInt()))
               println("UpdateTime "+UpdateTime)

                var diff:Long=UpdateTime-miliSecondsList.get(0).toInt()
               var tempHourLap:Int=(diff.toInt()/1000)%60
               var tempMinLap:Int=0
               var tempSecLap:Int=0


                if(tempHourLap>60){
                    tempHourLap=tempHourLap%60
                    tempMinLap=tempHourLap/60
                    tempSecLap=((diff%1000)%100).toInt()
                }else{
                    tempHourLap=0
                    tempMinLap=tempHourLap%60
                    tempSecLap=(diff%100).toInt()
                }
                if (tempHourLap.toString().length < 2) {
                    tv_hour_lap?.text = "0" + tempHourLap.toString()
                } else {
                    tv_hour_lap?.text = tempHourLap.toString()
                }
                if (tempMinLap.toString().length < 2) {
                    tv_minute_lap?.text = "0" + tempMinLap.toString()
                } else {
                    tv_minute_lap?.text = tempMinLap.toString()
                }
                if (tempSecLap.toString().length < 2) {
                    tv_seconds_lap?.text = "0" + tempSecLap.toString()
                } else {
                    tv_seconds_lap?.text = tempSecLap.toString()
                }

                //println("lap time is test "+tempHourLap+":"+tempMinLap+":"+tempSecLap)


            }else{

                var tempHourLap:Int=tv_hour.getText().toString().toInt()
                var tempMinLap:Int=tv_minute.getText().toString().toInt()
                var tempSecLap:Int=tv_seconds.getText().toString().toInt()
                if (tempHourLap.toString().length < 2) {
                    tv_hour_lap?.text = "0" + tempHourLap.toString()
                } else {
                    tv_hour_lap?.text = tempHourLap.toString()
                }
                if (tempMinLap.toString().length < 2) {
                    tv_minute_lap?.text = "0" + tempMinLap.toString()
                } else {
                    tv_minute_lap?.text = tempMinLap.toString()
                }
                if (tempSecLap.toString().length < 2) {
                    tv_seconds_lap?.text = "0" + tempSecLap.toString()
                } else {
                    tv_seconds_lap?.text = tempSecLap.toString()
                }

               // println("lap time is test "+tempHourLap+":"+tempMinLap+":"+tempSecLap)

            }

            handler?.postDelayed(this, 0)
        }

    }



    class CustomAdapter(val lapList: ArrayList<String>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

        //this method is returning the view for each item in the list
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_lap_list, parent, false)
            return ViewHolder(v)
        }

        //this method is binding the data on the list
        override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
           holder.tv_numbers.setText(lapList.get(position))
        }

        //this method is giving the size of the list
        override fun getItemCount(): Int {
            return lapList.size
        }

        //the class is hodling the list view
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tv_numbers: TextView=itemView.findViewById(R.id.tv_numbers)
        }
    }
}
