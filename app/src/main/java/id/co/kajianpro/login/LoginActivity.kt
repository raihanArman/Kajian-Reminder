package id.co.kajianpro.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import id.co.dhanapps.view.login.LoginViewModel
import id.co.dhanapps.view.login.LoginViewModelProviderFactory
import id.co.kajianpro.R
import id.co.kajianpro.data.repository.LoginRepository
import id.co.kajianpro.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {


    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_login
        )

        val loginRepository = LoginRepository()
        val viewModelFactory = LoginViewModelProviderFactory(application, loginRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        setFragment(SignInFragment())

    }


    private fun setFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(binding.frameLogin.id, fragment).commit()
    }
}