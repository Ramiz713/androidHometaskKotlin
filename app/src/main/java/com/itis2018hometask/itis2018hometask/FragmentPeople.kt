package com.itis2018hometask.itis2018hometask


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*


class FragmentPeople : Fragment() {

    fun newInstance(): FragmentPeople = FragmentPeople()

    private val adapter = PeopleAdapter()
    private var peopleList: ArrayList<PeopleItem>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_people, container, false)
        peopleList = ArrayList<PeopleItem>()
        initList()
        val recycler = view.findViewById(R.id.rv_people) as RecyclerView
        recycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = adapter
        adapter.submitList(peopleList)
        return view
    }

    private fun initList() {
        peopleList?.add(PeopleItem("Дмитрий", 78, R.drawable.dmitryi))
        peopleList?.add(PeopleItem("Дарья", 75, R.drawable.dasha))
        peopleList?.add(PeopleItem("Фёдор", 76, R.drawable.fedor))
        peopleList?.add(PeopleItem("Георгий", 67, R.drawable.georgiy))
        peopleList?.add(PeopleItem("Галина", 88, R.drawable.galina))
        peopleList?.add(PeopleItem("Ван Гог", 83, R.drawable.vangog))
        peopleList?.add(PeopleItem("Фрида", 81, R.drawable.frida))
        peopleList?.add(PeopleItem("Зиннатулла", 77, R.drawable.zinatulla))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return (when (item.itemId) {
            R.id.action_sort_name -> {
                peopleList?.sortBy { it.name }
                adapter.submitList(peopleList)
                true
            }
            R.id.action_sort_points -> {
                peopleList?.sortByDescending { it.points }
                adapter.submitList(peopleList)
                true
            }
            else -> super.onOptionsItemSelected(item)
        })
    }
}
