package id.co.kajianpro.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.kajianpro.OnClickReminderListener
import id.co.kajianpro.R
import id.co.kajianpro.adpater.ReminderAdapter
import id.co.kajianpro.data.db.AppDatabase
import id.co.kajianpro.databinding.FragmentReminderBinding
import id.co.kajianpro.data.model.Kajian
import id.co.kajianpro.data.repository.RemiderRepository
import id.co.kajianpro.viewmodel.ReminderViewModel
import id.co.kajianpro.viewmodel.ReminderViewModelFactory
import java.util.*

class ReminderFragment : Fragment(), OnClickReminderListener {

    lateinit var dataBinding : FragmentReminderBinding
    lateinit var reminderAdapter: ReminderAdapter
    lateinit var viewModel: ReminderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reminder, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reminderAdapter = ReminderAdapter(requireContext(), this)
        val reminderRepository = RemiderRepository(AppDatabase(requireContext()))
        val viewModelFactory = ReminderViewModelFactory(requireActivity().application, reminderRepository)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(ReminderViewModel::class.java)

        dataBinding.rvReminder.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = reminderAdapter
        }

        viewModel.deleteKajianMutable.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Toast.makeText(context, "${it}", Toast.LENGTH_SHORT).show()
            loadKajian()
        })

        loadKajian()

    }


    private fun loadKajian() {
        val listReminder = viewModel.getAllKajianReminder("1")
        if(listReminder.size > 0) {
            dataBinding.tvKosong.visibility = View.GONE
            reminderAdapter.setListReminder(listReminder)
        }else{
            dataBinding.tvKosong.visibility = View.VISIBLE
        }
    }

    override fun onClickReminder(kajian: Kajian) {
        kajian.idkajian?.let {
            viewModel.deleteKajianReminder(it, "1")
        }
    }
}