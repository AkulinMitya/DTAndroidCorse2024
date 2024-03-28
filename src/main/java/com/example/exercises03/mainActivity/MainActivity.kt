package com.example.exercises03.mainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.exercises03.MAIN
import com.example.exercises03.R
import com.example.exercises03.databinding.LayoutBinding
import com.example.exercises03.habitModel.Habit
import com.example.exercises03.mainActivity.fragments.EditHabitFragment
import com.example.exercises03.mainActivity.fragments.HabitListFragment
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener

class MainActivity : AppCompatActivity(), ColorPickerDialogListener {
    //TODO при изменении цвета у любой привычки меняется цвет первой
    //TODO ViewPager
    //TODO Nav Drawer
    private lateinit var binding: LayoutBinding
    lateinit var navController: NavController
    var habits = ArrayList<Habit>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MAIN = this
        binding = LayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val currentFragment = navHostFragment?.childFragmentManager?.fragments?.firstOrNull { it.isVisible }
        if (currentFragment is EditHabitFragment) {
            currentFragment.onColorSelected(dialogId, color)
        }
    }

    override fun onDialogDismissed(dialogId: Int) {}
}
