package com.mlkitexample.whatami.answer

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.media.Image
import android.media.RingtoneManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.mlkitexample.whatami.Helper

import com.mlkitexample.whatami.R
import com.mlkitexample.whatami.databinding.FragmentAnswerBinding
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * A simple [Fragment] subclass.
 */
class AnswerFragment : Fragment() {
    var preview: Preview? = null
    var camera: Camera? = null
    val TAG = AnswerFragment::class.java.simpleName
    lateinit var fragmentViewModel: AnswerFragmentViewModel
    lateinit var binding: FragmentAnswerBinding
    private lateinit var cameraExecutor: ExecutorService
    private var imageAnalyzer: ImageAnalysis? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_answer,container,false)
        fragmentViewModel = ViewModelProviders.of(this).get(AnswerFragmentViewModel::class.java)
        fragmentViewModel.mutableLiveDataOfListOfElements.observe(viewLifecycleOwner, Observer {
            if(it.size == Helper.mutableList?.size){
                Toast.makeText(requireContext(),"Start Finding",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(),"${it.size} elements left to find",Toast.LENGTH_SHORT).show()
                if(it.size == 0){
                    goToWinFragment()
                }
            }
        })
        if(allPermissionsGranted(requireContext())){
            startCamera(requireContext())
        }else{
            ActivityCompat.requestPermissions(requireActivity(), Helper.REQUIRED_PERMISSIONS, Helper.REQUEST_CODE_FOR_PERMISSIONS)
        }
        cameraExecutor = Executors.newSingleThreadExecutor()

        binding.giveUpBtn.setOnClickListener {
            it.findNavController().navigate(AnswerFragmentDirections.actionAnswerFragmentToLostFragment(false))
        }
        
        return binding.root
    }

    private fun goToWinFragment(){
        findNavController().navigate(AnswerFragmentDirections.actionAnswerFragmentToLostFragment(true))
    }


    private fun allPermissionsGranted(context: Context) = Helper.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(context,it)== PackageManager.PERMISSION_GRANTED
    }

    private fun startCamera(context: Context){

        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener(Runnable {

            val cameraProvider = cameraProviderFuture.get()

            preview = Preview.Builder().build()

            imageAnalyzer = ImageAnalysis.Builder().setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build().also {
                it.setAnalyzer(cameraExecutor, AnswerFragment.CustomImageAnalyzer(fragmentViewModel))
            }

            val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

            try{
                cameraProvider.unbindAll()

                camera = cameraProvider.bindToLifecycle(this,cameraSelector,preview, imageAnalyzer)

                preview?.setSurfaceProvider(binding.viewFinder.createSurfaceProvider(camera?.cameraInfo))
            }catch (exception: Exception){
                Log.d(TAG, "Use case binding failed",exception)
            }

        },ContextCompat.getMainExecutor(context))


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == Helper.REQUEST_CODE_FOR_PERMISSIONS){
            if(allPermissionsGranted(requireContext())){
                startCamera(requireContext())
            }else{
                Toast.makeText(requireContext(),"Permissions not granted by the user.", Toast.LENGTH_SHORT).show()
                ActivityCompat.requestPermissions(requireActivity(), Helper.REQUIRED_PERMISSIONS, Helper.REQUEST_CODE_FOR_PERMISSIONS)
            }
        }
    }

    private class CustomImageAnalyzer(val viewModel: AnswerFragmentViewModel): ImageAnalysis.Analyzer{
        private val TAG = CustomImageAnalyzer::class.java.simpleName
        @SuppressLint("UnsafeExperimentalUsageError")
        override fun analyze(imageProxy: ImageProxy) {
            val mediaImage: Image? = imageProxy.image
            if(mediaImage!=null){
                val image = InputImage.fromMediaImage(mediaImage,imageProxy.imageInfo.rotationDegrees)
                val options = ImageLabelerOptions.Builder()
                    .setConfidenceThreshold(0.80f)
                    .build()
                val labeler = ImageLabeling.getClient(options)
                labeler.process(image)
                    .addOnSuccessListener { labels ->
                        var elementMap: List<String> = labels.map { it.text }
                        viewModel.popElements(elementMap)
                    }
                    .addOnFailureListener { e->
                        Log.d(TAG,"Exception: "+e.cause?.localizedMessage)
                    }
                    .addOnCompleteListener {
                        imageProxy.close()
                    }

            }
        }

    }

}
