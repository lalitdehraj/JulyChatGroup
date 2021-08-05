package com.example.july.ui.group

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.july.R
import com.example.july.databinding.FragmentCreateBinding
import com.example.july.databinding.FragmentGroupBinding
import com.example.july.ui.GroupViewModel
import com.example.july.utils.PUBLIC_GROUP_KEY
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Group : Fragment(R.layout.fragment_group) {
    private lateinit var binding: FragmentGroupBinding
    private val viewModel: GroupViewModel by viewModels()
    var array: ArrayList<String> = ArrayList<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGroupBinding.bind(view)


        setUpList()

        binding.createGroup.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.fragment_create, null)
            val bindo = FragmentCreateBinding.bind(view)

            bindo.submitGroup.setOnClickListener {
                val groupName = bindo.groupName.text.toString()
                viewModel.sendGroupToDatabase(groupName)
                dialog.dismissWithAnimation=true
                dialog.dismiss()
            }
            dialog.setContentView(view)
            dialog.show()

        }

        binding.groupList.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val group = binding.groupList.getItemAtPosition(position) as String
                val bundle = Bundle()
                bundle.putString(PUBLIC_GROUP_KEY, group)


                findNavController().navigate(R.id.action_group_to_chat, bundle)

            }

        binding.groupList.setOnItemLongClickListener { parent, view, position, id ->
            Toast.makeText(requireContext(),"kuch bhi ", Toast.LENGTH_SHORT).show()
            true
        }

    }

    fun setUpList() {

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            array
        )
        binding.groupList.adapter = adapter

        viewModel.groups.observe(viewLifecycleOwner, { list ->
            array.clear()
            for (item in list) {
                array.add(item.groupName)
            }
            adapter.notifyDataSetChanged()

        })
    }
}