//package com.example.a30androidwithkotlie
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.core.widget.addTextChangedListener
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase
//
//class Ch13_LoginActivity : AppCompatActivity() {
//
//    private var auth : FirebaseAuth? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_ch13_login)
//
//        auth = Firebase.auth // Firebase.auth == FirebaseAuth.getInstance()
//
//        initLoginButton()
//        initSignUpButton()
//        initEmailAndPasswordEditText()
//
//    }
//
//    private fun initLoginButton() {
//        val loginButton = findViewById<Button>(R.id.loginButton)
//        loginButton.setOnClickListener {
//            val email = getInputEmail()
//            val password = getInputPassword()
//
//            auth?.signInWithEmailAndPassword(email, password)
//                ?.addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        finish()
//                    } else {
//                        Toast.makeText(this, "로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//        }
//    }
//
//    private fun initSignUpButton() {
//        val signUpButton = findViewById<Button>(R.id.signUpButton)
//        signUpButton.setOnClickListener {
//            val email = getInputEmail()
//            val password = getInputPassword()
//
//            auth?.createUserWithEmailAndPassword(email, password)
//                ?.addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        Toast.makeText(this, "회원가입에 성공했습니다. 로그인 버튼을 눌러 로그인해주세요.", Toast.LENGTH_SHORT).show()
//                    } else {
//                        Toast.makeText(this, "이미 가입한 이메일이거나, 회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//        }
//    }
//
//    private fun initEmailAndPasswordEditText() {
//        val emailEditText = findViewById<EditText>(R.id.emailEditText)
//        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
//        val loginButton = findViewById<Button>(R.id.loginButton)
//        val signUpButton = findViewById<Button>(R.id.signUpButton)
//
//        emailEditText.addTextChangedListener {
//            val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
//            loginButton.isEnabled = enable
//            signUpButton.isEnabled = enable
//        }
//
//        passwordEditText.addTextChangedListener {
//            val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
//            loginButton.isEnabled = enable
//            signUpButton.isEnabled = enable
//        }
//    }
//
//    private fun getInputEmail() : String {
//        return findViewById<EditText>(R.id.emailEditText).text.toString()
//    }
//
//    private fun getInputPassword() : String {
//        return findViewById<EditText>(R.id.passwordEditText).text.toString()
//    }
//
//}