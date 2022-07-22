package com.gyunni.trackbox.view.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gyunni.trackbox.Delivery
import com.gyunni.trackbox.R
import com.gyunni.trackbox.databinding.ActivityMainBinding
import com.gyunni.trackbox.view.ui.add.AddDeliveryFragment
import com.gyunni.trackbox.view.ui.base.BaseActivity
import com.gyunni.trackbox.view.ui.lookup.LookUpFragment
import com.gyunni.trackbox.view.util.VerticalItemDecorator

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var mainAdapter: MainAdapter
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this, MainViewModel.Factory(application))[MainViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            addDeliveryBtn.setOnClickListener {
                val bottomSheet = AddDeliveryFragment()
                bottomSheet.show(supportFragmentManager, bottomSheet.tag)
            }
        }

        initRv()

        mainAdapter.setOnItemClickListener(object : MainAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: Delivery, pos: Int) {
                val bottomSheetDialog = LookUpFragment()
                var bundle = Bundle()
                bundle.putString("name", data.carrierName)
                bundle.putString("id",data.trackId)
                bottomSheetDialog.arguments = bundle
                bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)
            }

        })
    }

    private fun initRv(){
        mainAdapter = MainAdapter(this)
        binding.rvMain.adapter = mainAdapter
        binding.rvMain.addItemDecoration(VerticalItemDecorator(10))

        viewModel.getList().observe(this, Observer{
            mainAdapter.setList(it)
            mainAdapter.notifyDataSetChanged()
        })
    }
}