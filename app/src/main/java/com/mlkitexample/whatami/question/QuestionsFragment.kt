package com.mlkitexample.whatami.question

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mlkitexample.whatami.Helper

import com.mlkitexample.whatami.R
import com.mlkitexample.whatami.databinding.FragmentQuestionsBinding

/**
 * A simple [Fragment] subclass.
 */
class QuestionsFragment : Fragment() {
    lateinit var binding: FragmentQuestionsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_questions,container,false)
        Helper.assignListByShuffling()
        val adapter = ElementListAdapter(requireContext(),Helper.mutableList!!)
        binding.itemsRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        binding.itemsRecyclerView.adapter = adapter
        binding.goToAnswerBtn.setOnClickListener { view: View ->
            view.findNavController().navigate(QuestionsFragmentDirections.actionQuestionsFragmentToAnswerFragment())
        }

        return binding.root
    }



}
