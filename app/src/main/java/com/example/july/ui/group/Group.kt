package com.example.july.ui.group

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.july.R
import com.example.july.databinding.FragmentGroupBinding
import com.example.july.utils.PUBLIC_GROUP_KEY
import com.example.july.utils.getGroupList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Group : Fragment(R.layout.fragment_group) {
    private lateinit var binding: FragmentGroupBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGroupBinding.bind(view)
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            getGroupList()
        )
        binding.groupList.adapter = adapter

        binding.groupList.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val group = binding.groupList.getItemAtPosition(position) as String
                val bundle = Bundle()
                bundle.putString(PUBLIC_GROUP_KEY, group)


                findNavController().navigate(R.id.action_group_to_chat, bundle)

            }


    }
}