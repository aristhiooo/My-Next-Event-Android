package com.aristiyo.mynextevent.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aristiyo.mynextevent.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val username = "12345"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtForgotPassword.setOnClickListener {
            Snackbar.make(binding.root, "Segera hadir.", Snackbar.LENGTH_SHORT).show()
        }

        binding.btnLogin.setOnClickListener {
            val paramUsername = binding.edtUsername.editText?.text
            val paramPassword = binding.edtPassword.editText?.text

            if (paramUsername.isNullOrEmpty()) {
                binding.edtUsername.error = "Tidak boleh kosong"
                return@setOnClickListener
            }

            if (paramPassword.isNullOrEmpty()) {
                binding.edtPassword.error = "Tidak boleh kosong"
                return@setOnClickListener
            }

            if (paramPassword.toString() != username || paramUsername.toString() != username) {
                Snackbar.make(
                    binding.root,
                    "Username atau Password yang Anda masukkan SALAH.",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }
}