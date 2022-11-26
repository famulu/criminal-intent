package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.bignerdranch.android.criminalintent.databinding.FragmentDetailDisplayBinding
import java.io.File

class DetailDisplayFragment : DialogFragment() {
    private val detailFragmentArgs: DetailDisplayFragmentArgs by navArgs()
    private var _binding: FragmentDetailDisplayBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailDisplayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photoFile =
            File(requireContext().applicationContext.filesDir, detailFragmentArgs.photoPath)

        if (photoFile.exists()) {
            binding.detailedCrimePhoto.apply {
                doOnLayout {
                    val scaledBitmap = getScaledBitmap(
                        photoFile.path, it.width, it.height
                    )
                    this.setImageBitmap(scaledBitmap)
                }
            }
        }
    }
}

