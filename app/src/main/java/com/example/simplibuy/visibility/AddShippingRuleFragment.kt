package com.example.simplibuy.visibility

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.simplibuy.databinding.FragmentAddShippingRuleBinding
import org.json.JSONObject


class AddShippingRuleFragment : Fragment() {

    private lateinit var binding: FragmentAddShippingRuleBinding
    val shippingType: List<String> = listOf(
        "CUSTOMER",
        "BUSINESS"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddShippingRuleBinding.inflate(inflater)
        initUI()
        return binding.root
    }



    private fun initUI() {

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, shippingType
        )

        binding.spinShippingRuleType.adapter = adapter
        binding.spinShippingRuleType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when {
                        shippingType[position] === "CUSTOMER" -> {
                            binding.apply {
                                Toast.makeText(activity,"clicked",Toast.LENGTH_SHORT).show()
                                groupConditionFlatShipping.visibility = View.VISIBLE
                                groupFlatShipping.visibility = View.GONE
                            }
                        }
                        shippingType[position] === "BUSINESS" -> {
                            binding.apply {
                                groupConditionFlatShipping.visibility = View.GONE
                                groupFlatShipping.visibility = View.VISIBLE
                            }

                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
//                    TODO("Not yet implemented")
                }
            }

        binding.apply {
            /*btConditionFlatShipping.setOnClickListener {

                    val info = JSONObject()
                    val nestedData = JSONObject()
                    info.put("engine_type", shippingType[1])
                    info.put("shipping_rule_name", binding.tilShippingRuleName.editText!!.text)
                    info.put("delivery_time", binding.tilDeliveryTime.editText!!.text)
                    nestedData.put("Shipping_rule_type", "Conditional flat Shipping")
                    nestedData.put(
                        "min_required_amount",
                        binding.tilMinAmount.editText!!.text.toString().toInt()
                    )
                    nestedData.put(
                        "shipping_fee_above_min",
                        binding.tilShippingFeeAboveMinAmount.editText!!.text.toString().toInt()
                    )
                    nestedData.put(
                        "shipping_fee_below_min",
                        binding.tilShippingFeeBelowMinAmount.editText!!.text.toString().toInt()
                    )
                    info.put("data", nestedData)


            }




            btZonalShippingFee.setOnClickListener {

                    val info = JSONObject()
                    val nestedData = JSONObject()
                    info.put("engine_type", shippingType[2])
                    info.put("shipping_rule_name", binding.tilShippingRuleName.editText!!.text)
                    info.put("delivery_time", binding.tilDeliveryTime.editText!!.text)
                    nestedData.put("Shipping_rule_type", "Zonal Shipping Fee")
                    nestedData.put(
                        "shipping_fee_within_zone",
                        binding.etShippingFeeInsideZone.text.toString().toInt()
                    )
                    nestedData.put(
                        "shipping_fee_outside_zone",
                        binding.etShippingFeeOutsideZone.text.toString().toInt()
                    )




        }*/
        }

    }

}
