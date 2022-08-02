package com.gyunni.trackbox.view.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import com.gyunni.trackbox.*
import com.gyunni.trackbox.data.model.Delivery
import com.gyunni.trackbox.data.model.DeliveryResponse
import com.gyunni.trackbox.data.retrofit.DeliveryService
import com.gyunni.trackbox.databinding.ActivityMainBinding
import com.gyunni.trackbox.view.ui.add.AddDeliveryFragment
import com.gyunni.trackbox.view.ui.base.BaseActivity
import com.gyunni.trackbox.view.ui.lookup.LookUpFragment
import com.gyunni.trackbox.view.util.CarrierIdUtil
import com.gyunni.trackbox.view.util.SwipeHelperCallback
import com.gyunni.trackbox.view.util.VerticalItemDecorator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private var carrierId: String? = null

    private val mainAdapter = MainAdapter()
    private val viewModel by viewModel<MainViewModel>()

    private val deliveryService : DeliveryService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            addDeliveryBtn.setOnClickListener {
                val bottomSheet = AddDeliveryFragment()
                bottomSheet.show(supportFragmentManager, bottomSheet.tag)
            }
            swipeLayout.setOnRefreshListener {
                mainAdapter.currentList.forEach {
                    carrierId = CarrierIdUtil.convertId(it.carrierName)
                    deliveryService.getData(carrierId, it.trackId).enqueue(object : Callback<DeliveryResponse>{
                        override fun onResponse(
                            call: Call<DeliveryResponse>,
                            response: Response<DeliveryResponse>
                        ) {
                            if (response.isSuccessful) {
                                val result: DeliveryResponse? = response.body()
                                Log.d("MainActivity", "onResponse 성공: " + result?.toString())
                                val testResult: Delivery? = result?.toDelivery(
                                    result.carrier.id?.toLong(),
                                    result.carrier.name.toString(),
                                    result.carrier.id.toString(),
                                    it.nickName!!
                                )
                                Log.d("MainActivity", testResult.toString())
                                viewModel.update(testResult!!)
                            } else {
                                Log.d("MainActivity", "onResponse 실패");
                                Toast.makeText(applicationContext, "해당 운송장이 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<DeliveryResponse>, t: Throwable) {
                            Log.d("MainActivity", "onFailure 에러: " + t.message.toString());
                        }
                    })
                }
                mainAdapter.notifyDataSetChanged()
                swipeLayout.isRefreshing = false
            }
            rvMain.adapter = mainAdapter
            rvMain.addItemDecoration(VerticalItemDecorator(10))
            viewModel.getList().observe(lifecycleOwner!! , Observer{
                it.let { mainAdapter.submitList(it) }
            })
        }

        initRv()

        mainAdapter.setOnItemClickListener(object : MainAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: Delivery, pos: Int) {
                val bottomSheetDialog = LookUpFragment()
                val bundle = Bundle()
                bundle.putString("name", data.carrierName)
                bundle.putString("id",data.trackId)
                bottomSheetDialog.arguments = bundle
                bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)
            }

        })

        mainAdapter.setOnRemoveClickListener(object : MainAdapter.OnRemoveClickListener {
            override fun onRemoveClick(v: View, data: Delivery, pos: Int) {
                viewModel.delete(data)
                mainAdapter.removeData(pos)
            }
        })

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
//        mainAdapter = MainAdapter(this)
//        binding.rvMain.adapter = mainAdapter
//        binding.rvMain.addItemDecoration(VerticalItemDecorator(10))
//
        val swipeHelperCallback = SwipeHelperCallback(mainAdapter).apply {
            setClamp(resources.displayMetrics.widthPixels.toFloat() / 4)
        }
        ItemTouchHelper(swipeHelperCallback).attachToRecyclerView(binding.rvMain)

        binding.rvMain.setOnTouchListener { _, _ ->
            swipeHelperCallback.removePreviousClamp(binding.rvMain)
            false
        }
//
//        viewModel.getList().observe(this, Observer{
//            mainAdapter.setList(it)
//            if(it.isEmpty()){
//                binding.textEmptyList.visibility = View.VISIBLE
//            }else{
//                binding.textEmptyList.visibility = View.INVISIBLE
//            }
//            mainAdapter.notifyDataSetChanged()
//        })

        binding.rvMain.adapter = mainAdapter
        binding.rvMain.addItemDecoration(VerticalItemDecorator(10))
        viewModel.getList().observe(this , Observer{
            it.let { mainAdapter.submitList(it) }
            if(it.isEmpty()){
                binding.textEmptyList.visibility = View.VISIBLE
            }else{
                binding.textEmptyList.visibility = View.INVISIBLE
            }
        })
    }
}