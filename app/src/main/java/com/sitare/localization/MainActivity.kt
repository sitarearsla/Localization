package com.sitare.localization

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.sitare.localization.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // binding.loginButton.text = "Continue"
        //binding.emailTextInput.setText("sarslanturk16@ku.edu.tr")

        binding.passwordTextInput.doOnTextChanged { text, start, before, count ->
            binding.passwordTextInputLayout.error = null
        }

        binding.emailTextInput.doOnTextChanged { text, start, before, count ->
            binding.emailTextInputLayout.error = null
        }

        binding.loginButton.setOnClickListener {
         validate()
        }
    }

    private fun validate(){
        if(!validateEmail() && validatePassword().not()) return
        if(!validateEmail() || validatePassword().not()) return
        val email:String? = binding.emailTextInput.text?.toString()
        val username = email?.substringBefore("@")
        showToast(getString(R.string.welcome, username))
    }

    private fun validateEmail():Boolean{
        var email:String? = binding.emailTextInput.text?.toString()
        // email?.let { showToast(it) }
        if(email.isNullOrEmpty()){
            binding.emailTextInputLayout.error = getString(R.string.empty_email_warning)
            return false
        }

        if (Patterns.EMAIL_ADDRESS.matcher(email).matches().not()){
            binding.emailTextInputLayout.error = getString(R.string.invalid_email_warning)
            return false
        }
        binding.emailTextInputLayout.error = null
        return true
    }

    private fun validatePassword(): Boolean{
        var password:String? = binding.passwordTextInput.text?.toString()
        val passwordLength = password?.length ?: 0
        if(passwordLength < 8){
            binding.passwordTextInputLayout.error = getString(R.string.empty_password_warning)
            return false
        }
        binding.passwordTextInputLayout.error = null
        return true
    }

    private fun showToast(text: String){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}