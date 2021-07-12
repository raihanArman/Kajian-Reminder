package id.co.kajianpro.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import id.co.kajianpro.MainActivity
import id.co.kajianpro.R
import id.co.kajianpro.data.model.User
import id.co.kajianpro.databinding.FragmentAkunBinding
import id.co.kajianpro.login.LoginActivity
import id.co.kajianpro.utils.Constant
import id.co.kajianpro.utils.Resource
import id.co.kajianpro.viewmodel.HomeViewModel

class AkunFragment : Fragment() {

    lateinit var dataBinding: FragmentAkunBinding
    lateinit var sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor? = null
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_akun, container, false)
        return dataBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        sharedPreferences = activity!!.getSharedPreferences(Constant.LOGIN_KEY, Context.MODE_PRIVATE)
        editor = sharedPreferences?.edit()

        loadUserProfil()

        viewModel.userProfilByIdMutable.observe(viewLifecycleOwner, Observer { response ->
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

        dataBinding.btnLogOut.setOnClickListener {

            editor!!.putString(Constant.ID_USER_KEY, "")
            editor!!.putBoolean(Constant.LOGIN_STATUS, false)
            editor!!.commit()
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity!!.startActivity(intent)
        }

    }

    private fun setDataUser(data: User?) {
        dataBinding.user = data
    }

    private fun loadUserProfil() {
        val idUser = sharedPreferences.getString(Constant.ID_USER_KEY, "")
        viewModel.getUserProfil(idUser!!)
    }
}