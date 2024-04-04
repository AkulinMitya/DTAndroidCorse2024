package com.example.exercises03.mainActivity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.exercises03.MAIN
import com.example.exercises03.R
import com.example.exercises03.databinding.LayoutBinding
import com.example.exercises03.habitModel.Habit
import com.example.exercises03.mainActivity.fragments.EditHabitFragment
import com.example.exercises03.mainActivity.fragments.HabitListFragment
import com.google.android.material.navigation.NavigationView
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener

class MainActivity : AppCompatActivity(), ColorPickerDialogListener {
    private lateinit var binding: LayoutBinding
    lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    var habits = ArrayList<Habit>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MAIN = this
        binding = LayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        setupDrawer()
        setupMenu()
    }

    private fun setupNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    private fun setupDrawer() {
        drawerLayout = binding.navigationDrawerLayout
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun setupMenu() {
        binding.navigationDrawer.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    if (navController.currentDestination?.id == R.id.editHabitFragment) {
                        navController.navigate(R.id.action_editHabitFragment_to_habitListFragment)
                    }
                    if (navController.currentDestination?.id == R.id.infoFragment) {
                        navController.navigate(R.id.action_infoFragment_to_habitListFragment2)
                    }
                    drawerLayout.closeDrawers()
                    true
                }

                R.id.nav_app_info -> {
                    if (navController.currentDestination?.id == R.id.editHabitFragment) {
                        navController.navigate(R.id.action_editHabitFragment_to_infoFragment)
                    }
                    if (navController.currentDestination?.id == R.id.habitListFragment) {
                        navController.navigate(R.id.action_habitListFragment_to_infoFragment)
                    }
                    drawerLayout.closeDrawers()
                    true
                }

                else -> false
            }
        }
    }

//    override fun onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START)
//        }
//        else {
//            onBackPressedDispatcher.onBackPressed()
//        }
//    }
    override fun onColorSelected(dialogId: Int, color: Int) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val currentFragment =
            navHostFragment?.childFragmentManager?.fragments?.firstOrNull { it.isVisible }
        if (currentFragment is EditHabitFragment) {
            currentFragment.onColorSelected(dialogId, color)
        }
    }

    override fun onDialogDismissed(dialogId: Int) {}
}
