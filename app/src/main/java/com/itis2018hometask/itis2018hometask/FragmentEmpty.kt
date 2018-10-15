package com.itis2018hometask.itis2018hometask

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class FragmentEmpty : Fragment() {

    companion object {
        private const val MY_STRING = "my_string"
        fun newInstance(name: String): FragmentEmpty = FragmentEmpty().apply {
            this.arguments = Bundle(1).apply { putString(MY_STRING, name) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_one, container, false)
        val textView = view.findViewById<TextView>(R.id.fragment_name)
        textView.text = arguments?.getString(MY_STRING)
        return view
    }
}
