package com.example.bottomnavigationviewfragmentjava

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bottomnavigationviewfragmentjava.util.FragmentUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private var fragmentHashMap: HashMap<String, Fragment>? = null
    private val clacks = arrayOf(MainFragment::class.java.name, MyFragment::class.java.name)
    private var fragmentUtil: FragmentUtil? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentUtil = FragmentUtil.Companion.newInstance(this, R.id.menu_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        fragmentHashMap = fragmentUtil!!.getFragmentHashMap(savedInstanceState, clacks)
        fragmentUtil!!.setSelectItemId(savedInstanceState) { itemId: Int -> bottomNavigationView.selectedItemId = itemId }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fragmentUtil!!.saveSelectItemId(outState)
    }

    @SuppressLint("NonConstantResourceId")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val needShowFragment: Fragment?
        needShowFragment = when (item.itemId) {
            R.id.menu_my -> fragmentHashMap!![MyFragment::class.java.name]
            R.id.menu_main -> fragmentHashMap!![MainFragment::class.java.name]
            else -> fragmentHashMap!![MainFragment::class.java.name]
        }
        //切换显示的fragment
        fragmentUtil!!.switchFragment(item.itemId, R.id.fragment_view_container, needShowFragment)
        return true
    }
}