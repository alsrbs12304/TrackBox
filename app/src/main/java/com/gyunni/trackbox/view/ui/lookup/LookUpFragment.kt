package com.gyunni.trackbox.view.ui.lookup

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyunni.trackbox.data.model.DeliveryResponse
import com.gyunni.trackbox.R
import com.gyunni.trackbox.data.retrofit.DeliveryService
import com.gyunni.trackbox.databinding.FragmentLookUpBinding
import com.gyunni.trackbox.view.ui.base.BaseBottomSheetDialogFragment
import com.gyunni.trackbox.view.util.CarrierIdUtil
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LookUpFragment : BaseBottomSheetDialogFragment<FragmentLookUpBinding>(R.layout.fragment_look_up) {

    private var testName : String? = null
    private var testId : String? = null
    private var carrierId : String? = null

    private val deliveryService : DeliveryService by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        testName = arguments?.getString("name")
        testId = arguments?.getString("id")
        carrierId = CarrierIdUtil.convertId(testName!!)
        Log.d("LookUpFragment","$testName // $testId // $carrierId")

        loadData()
    }

    private fun setAdapter(progressList : ArrayList<DeliveryResponse.Progresses>){
        val mAdapter = LookUpAdapter(progressList, mainActivity)
        binding.timelineDialogRecyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(mainActivity)
        }
    }

    private fun loadData(){
        deliveryService.getData(carrierId, testId).enqueue(object : Callback<DeliveryResponse>{
            override fun onResponse(
                call: Call<DeliveryResponse>,
                response: Response<DeliveryResponse>
            ) {
                if(response.isSuccessful){
                    val result: DeliveryResponse? = response.body()
                    Log.d("LookUpFragment", "onResponse 성공: " + result?.toString());
                    result?.let{
                        setAdapter(it.progresses)
                    }

                }else{
                    Log.d("LookUpFragment", "onResponse 실패");
                }
            }

            override fun onFailure(call: Call<DeliveryResponse>, t: Throwable) {
                Log.d("LookUpFragment", "onFailure 에러: " + t.message.toString());
            }

        })
    }
}