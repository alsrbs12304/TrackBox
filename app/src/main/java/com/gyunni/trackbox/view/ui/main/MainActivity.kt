package com.gyunni.trackbox.view.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyunni.trackbox.*
import com.gyunni.trackbox.data.model.Delivery
import com.gyunni.trackbox.view.util.SwipeHelperCallback
import com.gyunni.trackbox.databinding.ActivityMainBinding
import com.gyunni.trackbox.view.ui.add.AddDeliveryFragment
import com.gyunni.trackbox.view.ui.base.BaseActivity
import com.gyunni.trackbox.view.ui.lookup.LookUpFragment
import com.gyunni.trackbox.view.util.VerticalItemDecorator
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var mainAdapter: MainAdapter
    private val viewModel by viewModel<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val testMainAdapter = TestMainAdapter()
        binding.apply {
            addDeliveryBtn.setOnClickListener {
                val bottomSheet = AddDeliveryFragment()
                bottomSheet.show(supportFragmentManager, bottomSheet.tag)
            }
            swipeLayout.setOnRefreshListener {
                mainAdapter.notifyDataSetChanged()
                swipeLayout.isRefreshing = false
            }
            rvMain.adapter = testMainAdapter
            rvMain.addItemDecoration(VerticalItemDecorator(10))
            viewModel.getList().observe(lifecycleOwner!! , Observer{
                it.let { testMainAdapter.submitList(it) }
            })
        }

//        initRv()

//        mainAdapter.setOnItemClickListener(object : MainAdapter.OnItemClickListener{
//            override fun onItemClick(v: View, data: Delivery, pos: Int) {
//                val bottomSheetDialog = LookUpFragment()
//                var bundle = Bundle()
//                bundle.putString("name", data.carrierName)
//                bundle.putString("id",data.trackId)
//                bottomSheetDialog.arguments = bundle
//                bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)
//            }
//        })
//
//        mainAdapter.setOnRemoveClickListener(object : MainAdapter.OnRemoveClickListener{
//            override fun onRemoveClick(v: View, data: Delivery, pos: Int) {
//                viewModel.delete(data)
//                mainAdapter.removeData(pos)
//            }
//        })



    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initRv(){
        mainAdapter = MainAdapter(this)
        binding.rvMain.adapter = mainAdapter
        binding.rvMain.addItemDecoration(VerticalItemDecorator(10))

        val swipeHelperCallback = SwipeHelperCallback(mainAdapter).apply {
            setClamp(resources.displayMetrics.widthPixels.toFloat() / 4)
        }
        ItemTouchHelper(swipeHelperCallback).attachToRecyclerView(binding.rvMain)

        binding.rvMain.setOnTouchListener { _, _ ->
            swipeHelperCallback.removePreviousClamp(binding.rvMain)
            false
        }

        viewModel.getList().observe(this, Observer{
            mainAdapter.setList(it)
            if(it.isEmpty()){
                binding.textEmptyList.visibility = View.VISIBLE
            }else{
                binding.textEmptyList.visibility = View.INVISIBLE
            }
            mainAdapter.notifyDataSetChanged()
        })
    }
}