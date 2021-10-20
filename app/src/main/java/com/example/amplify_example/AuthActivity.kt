package com.example.amplify_example

//https://dev.classmethod.jp/articles/amplify-android-cognito-auth/ 사이트 참고해서 aws cognito인증 만듬

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.mobile.client.*


class AuthActivity : AppCompatActivity() {

    private val TAG = AuthActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        AWSMobileClient.getInstance()
            .initialize(applicationContext, object : Callback<UserStateDetails?> {

                override fun onResult(result: UserStateDetails?) {
                    Log.i(TAG, result?.userState.toString())
                    when (result?.userState) {
                        UserState.SIGNED_IN -> {
                            val i = Intent(this@AuthActivity, MainActivity::class.java)
                            startActivity(i)
                        }
                        UserState.SIGNED_OUT -> showSignIn()
                        else -> {
                            AWSMobileClient.getInstance().signOut()
                            showSignIn()
                        }
                    }
                }

                override fun onError(e: Exception) {
                    Log.e(TAG, e.toString())
                }
            })
    }


    private fun showSignIn() {
        try {
            AWSMobileClient.getInstance().showSignIn(
                this,
                SignInUIOptions.builder().nextActivity(MainActivity::class.java).build()
            )
        } catch (e: java.lang.Exception) {
            Log.e(TAG, e.toString())
        }
    }





}
