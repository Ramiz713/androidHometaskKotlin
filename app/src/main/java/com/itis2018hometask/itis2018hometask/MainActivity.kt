package com.itis2018hometask.itis2018hometask

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var peopleList: ArrayList<PeopleItem>
    private val adapter = PeopleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        peopleList = ArrayList<PeopleItem>()
        initList()
        val recycler = findViewById<RecyclerView>(R.id.rv_people)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = adapter
        adapter.submitList(peopleList)
    }

    private fun initList() {
        peopleList.add(PeopleItem("Дмитрий", 78, R.drawable.dmitryi))
        peopleList.add(PeopleItem("Дарья", 75, R.drawable.dasha))
        peopleList.add(PeopleItem("Фёдор", 76, R.drawable.fedor))
        peopleList.add(PeopleItem("Георгий", 67, R.drawable.georgiy))
        peopleList.add(PeopleItem("Галина", 88, R.drawable.galina))
        peopleList.add(PeopleItem("Ван Гог", 83, R.drawable.vangog))
        peopleList.add(PeopleItem("Фрида", 81, R.drawable.frida))
        peopleList.add(PeopleItem("Зиннатулла", 77, R.drawable.zinatulla))
        peopleList.add(PeopleItem("Зиннур", 72, R.drawable.zinatulla))
        peopleList.add(PeopleItem("Зигфрид", 64, R.drawable.zinatulla))
        peopleList.add(PeopleItem("Зейнал", 74, R.drawable.zinatulla))
        peopleList.add(PeopleItem("Збыслав", 73, R.drawable.zinatulla))
        peopleList.add(PeopleItem("Замир", 71, R.drawable.zinatulla))
        peopleList.add(PeopleItem("Зураб", 70, R.drawable.zinatulla))
        peopleList.add(PeopleItem("Зуфар", 73, R.drawable.zinatulla))
        peopleList.add(PeopleItem("Зульфат", 69, R.drawable.zinatulla))
        peopleList.add(PeopleItem("Закиржан", 78, R.drawable.zinatulla))
        peopleList.add(PeopleItem("Закир", 71, R.drawable.zinatulla))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val observable = Observable.fromIterable(peopleList)
                .take(12)
                .map {
                    it.name = it.name + it.name.length.toString()
                    return@map it
                }
                .doOnSubscribe { prgr_bar.visibility = ProgressBar.VISIBLE }
                .doOnTerminate { prgr_bar.visibility = ProgressBar.GONE }
        return when (item.itemId) {
            R.id.action_sort_name -> {
                observable
                        .toSortedList(({ p1, p2 -> p1.name.compareTo(p2.name) }))
                        .subscribe { list -> adapter.submitList(list) }
                true
            }
            R.id.action_sort_points -> {
                observable
                        .toSortedList(({ p1, p2 -> (-1) * p1.points.compareTo(p2.points) }))
                        .subscribe { list -> adapter.submitList(list) }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
