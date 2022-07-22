package com.gyunni.trackbox.view.ui.lookup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.gyunni.trackbox.Delivery
import com.gyunni.trackbox.DeliveryResponse
import com.gyunni.trackbox.R
import com.gyunni.trackbox.RetrofitClient
import com.gyunni.trackbox.databinding.FragmentLookUpBinding
import com.gyunni.trackbox.view.ui.base.BaseBottomSheetDialogFragment
import com.gyunni.trackbox.view.ui.main.MainAdapter
import com.gyunni.trackbox.view.ui.main.MainViewModel
import com.gyunni.trackbox.view.util.CarrierIdUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LookUpFragment : BaseBottomSheetDialogFragment<FragmentLookUpBinding>(R.layout.fragment_look_up) {

    private lateinit var lookUpAdapter: LookUpAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val testName = arguments?.getString("name")
        val testId = arguments?.getString("id")
        val carrierId : String? = CarrierIdUtil.convertId(testName!!)
        initRv()

        Log.d("LookUpFragment", "$testName // $carrierId // $testId");

        RetrofitClient.service.getData(carrierId, testId).enqueue(object : Callback<DeliveryResponse>{
            override fun onResponse(
                call: Call<DeliveryResponse>,
                response: Response<DeliveryResponse>
            ) {
                if(response.isSuccessful){
                    val result: DeliveryResponse? = response.body()
                    Log.d("LookUpFragment", "onResponse 성공: " + result?.toString());
                }else{
                    Log.d("LookUpFragment", "onResponse 실패");
                }
            }

            override fun onFailure(call: Call<DeliveryResponse>, t: Throwable) {
                Log.d("LookUpFragment", "onFailure 에러: " + t.message.toString());
            }

        })

    }

    private fun initRv(){
        lookUpAdapter = LookUpAdapter(mainActivity)
        binding.timelineDialogRecyclerView.adapter = lookUpAdapter
    }
}