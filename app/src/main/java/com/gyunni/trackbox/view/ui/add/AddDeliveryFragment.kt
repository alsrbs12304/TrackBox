package com.gyunni.trackbox.view.ui.add

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.gyunni.trackbox.*
import com.gyunni.trackbox.databinding.FragmentAddDeliveryBinding
import com.gyunni.trackbox.view.ui.base.BaseBottomSheetDialogFragment
import com.gyunni.trackbox.view.util.CarrierIdUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddDeliveryFragment :
    BaseBottomSheetDialogFragment<FragmentAddDeliveryBinding>(R.layout.fragment_add_delivery) {

    private var carrierId: String? = null
    private var trackId: String? = null

    private val viewModel: AddDeliveryViewModel by lazy {
        ViewModelProvider(
            this,
            AddDeliveryViewModel.Factory(mainActivity.application)
        )[AddDeliveryViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setChipGroup(binding)

        binding.imageButtonAddCancel.setOnClickListener {
            dismiss()
        }

        binding.chipGroupAddCarrierName.setOnCheckedChangeListener { group, checkedId ->
            carrierId = CarrierIdUtil.convertId(group.findViewById<Chip>(checkedId).text.toString())
        }

        binding.buttonAddLookUp.setOnClickListener {
            trackId = binding.editTextTrackId.text.toString()

            RetrofitClient.service.getData(carrierId, trackId)
                .enqueue(object : Callback<DeliveryResponse> {
                    override fun onResponse(
                        call: Call<DeliveryResponse>,
                        response: Response<DeliveryResponse>
                    ) {
                        if(response.isSuccessful) {
                            val result: DeliveryResponse? = response.body()
                            Log.d("AddDeliveryFragment", "onResponse 성공: " + result?.toString());
                            val testResult: Delivery? = result?.toDelivery(
                                result.carrier.id?.toLong(),
                                result.carrier.name.toString(),
                                result.carrier.id.toString()
                            )
                            Log.d("AddDeliveryFragment", testResult.toString())
                            viewModel.insert(testResult!!)
                        }else{
                            Log.d("AddDeliveryFragment", "onResponse 실패");
                            Toast.makeText(mainActivity,"해당 운송장이 존재하지 않습니다.",Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<DeliveryResponse>, t: Throwable) {
                        Log.d("AddDeliveryFragment", "onFailure 에러: " + t.message.toString());
                    }
                })
        }

    }

    private fun setChipGroup(binding: FragmentAddDeliveryBinding) {
        for (name in CarrierIdUtil.getCarrierKeys()) {
            binding.chipGroupAddCarrierName.addView(
                Chip(activity).apply {
                    text = name
                    isCheckable = true
                    isClickable = true
                    setTextColor(Color.WHITE)
                    setEnsureMinTouchTargetSize(false)
                    checkedIcon = resources.getDrawable(R.drawable.truck)
                    chipBackgroundColor = resources.getColorStateList(R.color.color_add_chip_bg)
                }
            )
        }
        binding.chipGroupAddCarrierName.setOnCheckedChangeListener { group, checkedId ->
            group.findViewById<Chip>(checkedId)?.let {
                viewModel.carrierName.postValue(it.text.toString())
            } ?: viewModel.carrierName.postValue(null)
        }
    }
}