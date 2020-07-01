package com.mlkitexample.whatami.welcome

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.mlkitexample.whatami.Helper

import com.mlkitexample.whatami.R
import com.mlkitexample.whatami.databinding.FragmentWelcomeBinding

/**
 * A simple [Fragment] subclass.
 */
class WelcomeFragment : Fragment() {
    lateinit var binding: FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         //Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_welcome,container,false)
        if(!allPermissionsGranted(requireContext())){
            ActivityCompat.requestPermissions(requireActivity(), Helper.REQUIRED_PERMISSIONS, Helper.REQUEST_CODE_FOR_PERMISSIONS)
        }
        binding.startBtn.setOnClickListener {
            view: View ->
            view.findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToQuestionsFragment())
        }
        return binding.root
    }

    fun allPermissionsGranted(context: Context) = Helper.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(context,it)== PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == Helper.REQUEST_CODE_FOR_PERMISSIONS){
            if(!allPermissionsGranted(requireContext())){
                Toast.makeText(requireContext(),"Permissions not granted by the user.", Toast.LENGTH_SHORT).show()
                ActivityCompat.requestPermissions(requireActivity(), Helper.REQUIRED_PERMISSIONS, Helper.REQUEST_CODE_FOR_PERMISSIONS)
            }
        }
    }

}
