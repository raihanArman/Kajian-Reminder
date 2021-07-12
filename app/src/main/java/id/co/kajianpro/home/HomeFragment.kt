package id.co.kajianpro.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.dhanapps.view.login.LoginViewModel
import id.co.kajianpro.MainActivity
import id.co.kajianpro.R
import id.co.kajianpro.adpater.KajianAdapter
import id.co.kajianpro.adpater.KajianAllAdapter
import id.co.kajianpro.data.model.Home
import id.co.kajianpro.databinding.FragmentHomeBinding
import id.co.kajianpro.data.model.Kajian
import id.co.kajianpro.data.model.User
import id.co.kajianpro.login.LoginActivity
import id.co.kajianpro.utils.Constant
import id.co.kajianpro.utils.Resource
import id.co.kajianpro.viewmodel.HomeViewModel
import java.text.DateFormat
import java.util.*


class HomeFragment : Fragment() {

    lateinit var dataBinding: FragmentHomeBinding
    lateinit var kajianAdapter: KajianAdapter
    lateinit var kajianAllAdapter: KajianAllAdapter
    private lateinit var viewModel: HomeViewModel
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = activity!!.getSharedPreferences(Constant.LOGIN_KEY, Context.MODE_PRIVATE)
        kajianAdapter = KajianAdapter(requireContext())
        kajianAllAdapter = KajianAllAdapter(requireContext())
        viewModel = (activity as MainActivity).viewModel

        dataBinding.rvKajianAll.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = kajianAllAdapter
        }

        dataBinding.rvKajianHariIni.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = linearLayoutManager
            adapter = kajianAdapter
        }


        viewModel.userProfilByIdMutable.observe(viewLifecycleOwner, androidx.lifecycle.Observer { response ->
            when(response){
                is Resource.Success ->{
                    setDataUser(response.data?.data)
                }
                is Resource.Loading ->{

                }
                is Resource.Error ->{
                    Toast.makeText(requireActivity(), "${response.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.homeMutable.observe(viewLifecycleOwner, androidx.lifecycle.Observer { response ->
            when(response){
                is Resource.Success ->{
                    response.data?.let {
                        setDataKajian(it)
                    }
                }
                is Resource.Error ->{
                    Toast.makeText(requireContext(), "${response.message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading ->{

                }
            }
        })



        val date = android.text.format.DateFormat.format("EEEE, dd MMM yyyy", Date()).toString()
        dataBinding.tvTanggal.text = date

        loadKajian()
        loadUserProfil()

    }

    private fun setDataUser(data: User?) {
        dataBinding.tvName.text = data?.nama
    }

    private fun setDataKajian(home: Home) {
        home.listKajiaAll?.let { kajianAllAdapter.setKajianList(it) }

        if(!home.listKajiaHariIni.isNullOrEmpty()){
            home.listKajiaHariIni.let {
                if (it != null) {
                    kajianAdapter.setKajianList(it)
                }
            }
        }else{
            dataBinding.tvKajianHariIni.visibility = View.GONE
        }

    }

    private fun loadKajian() {
        val hari = Constant.getdateToday().split("_")
        viewModel.getKajianAll(hari[0], hari[1])
    }

    private fun loadUserProfil() {
        val idUser = sharedPreferences.getString(Constant.ID_USER_KEY, "")
        viewModel.getUserProfil(idUser!!)
    }
}