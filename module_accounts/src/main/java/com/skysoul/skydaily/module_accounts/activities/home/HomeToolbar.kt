package com.skysoul.skydaily.module_accounts.activities.home

import android.content.Context
import android.os.Build
import androidx.appcompat.widget.Toolbar
import android.util.AttributeSet
import android.view.MenuItem
import com.skysoul.skydaily.module_accounts.R

/**
 * Created by Administrator on 2018/4/11.
 */
class HomeToolbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : Toolbar(context, attrs, defStyleAttr) {


    lateinit var menu_search: MenuItem
    lateinit var menu_add: MenuItem
    lateinit var menu_edit: MenuItem

    init {
        init()
    }

    private fun init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setBackgroundResource(R.color.colorPrimary)
            elevation = resources.getDimension(R.dimen.dp4)
        } else {
            setBackgroundResource(R.mipmap.navigation_bar)
        }
        inflateMenu(R.menu.menu_home)
//        menu_add = menu.findItem(R.id.menu_add)
        menu_search = menu.findItem(R.id.menu_search)
        menu_edit = menu.findItem(R.id.menu_edit)

        setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_search ->
                    listerner?.onSearchClick()

//                R.id.menu_add -> listerner?.onAddClick()

                R.id.menu_edit -> listerner?.onEditClick()
            }
            return@setOnMenuItemClickListener true
        }
        setNavigationOnClickListener {
            listerner?.onNavigationClick()
        }
    }

    lateinit var listerner: HomeBarClickListerner


    interface HomeBarClickListerner {
        fun onSearchClick()
        fun onNavigationClick()
        fun onEditClick()
    }
}