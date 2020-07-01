package com.mlkitexample.whatami.result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import com.mlkitexample.whatami.Helper
import com.mlkitexample.whatami.R
import com.mlkitexample.whatami.result.ResultFragment
import com.mlkitexample.whatami.result.ResultFragmentDirections
import com.mlkitexample.whatami.databinding.FragmentLostBinding

/**
 * A simple [Fragment] subclass.
 */
class ResultFragment : Fragment() {
    lateinit var binding: FragmentLostBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_lost,container,false)
        val imageViewTarget = GlideDrawableImageViewTarget(binding.gifImageView)
        val args =
            ResultFragmentArgs.fromBundle(
                requireArguments()
            )
        setUpFragmentUI(args.userWon)
        Glide.with(requireActivity()).load(getRandomGif(args.userWon)).into(imageViewTarget)
        binding.playAgainBtn.setOnClickListener {view: View ->
            view.findNavController().navigate(ResultFragmentDirections.actionLostFragmentToQuestionsFragment())
        }
        return binding.root
    }

    private fun getRandomGif(won: Boolean) = if(won)
            Helper.listOfWonGifs[0]
        else
            Helper.listOfLostGifs[0]


    private fun setUpFragmentUI(won: Boolean){
        if (won)
            setupAsWin()
        else
            setupAsLoss()
    }

    private fun setupAsWin(){
        binding.lostTV.text = getString(R.string.won_txt)
        binding.fragmentWrapper.setBackgroundColor(resources.getColor(R.color.greenYellow))
    }

    private fun setupAsLoss(){
        binding.lostTV.text = getString(R.string.lost_txt)
        binding.fragmentWrapper.setBackgroundColor(resources.getColor(R.color.indianRed))
    }

}
