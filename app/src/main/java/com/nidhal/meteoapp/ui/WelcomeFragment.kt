package com.nidhal.meteoapp.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.nidhal.meteoapp.R
import com.nidhal.meteoapp.databinding.FragmentWelcomeBinding
import com.nidhal.meteoapp.utils.AlertUtility.showNeutralAlert
import com.nidhal.meteoapp.utils.NetworkHelper
import com.nidhal.meteoapp.utils.NoticeDialogListener
import com.nidhal.meteoapp.utils.loadFragment


class WelcomeFragment constructor(
    private val networkHelper: NetworkHelper
) : Fragment(R.layout.fragment_welcome), View.OnClickListener {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding: FragmentWelcomeBinding get() = requireNotNull(_binding)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentWelcomeBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        binding.btnStart.setOnClickListener(this)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnStart -> {

                if (networkHelper.isNetworkConnected()) {
                    /**
                     * replace Welcome fragment by Weather Details fragment and add it to back Stack
                     */
                    (activity as MainActivity).loadFragment(true) {
                        replace(
                            R.id.fragmentHost,
                            WeatherDetailsFragment(NetworkHelper(requireActivity()))
                        )
                    }
                } else {
                    showNeutralAlert(
                        activity,
                        getString(R.string.title_no_internet_connexion),
                        getString(R.string.msg_network_error),
                        getString(R.string.ok),
                        object : NoticeDialogListener {
                            override fun onButtonClick(dialogInterface: DialogInterface) {
                                dialogInterface.dismiss()
                            }
                        }
                    )
                }

            }
        }
    }
}