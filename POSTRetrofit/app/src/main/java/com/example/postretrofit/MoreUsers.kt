package com.example.postretrofit

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoreUsers : AppCompatActivity() {
    lateinit var etName: EditText
    lateinit var etLoction: EditText
    lateinit var btnSave: Button
    lateinit var btnView: Button
    var name = ""
    var location = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_users)

        etName = findViewById(R.id.etName)
        etLoction = findViewById(R.id.etLocation)
        btnSave = findViewById(R.id.btnSave)
        btnView = findViewById(R.id.btnView)

        btnSave.setOnClickListener {

            name = etName.text.toString()
            location = etLoction.text.toString()
            addUser()
            Toast.makeText(this, "User added" , Toast.LENGTH_SHORT).show()
            etName.setText("")
            etLoction.setText("")

        }

        btnView.setOnClickListener {
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Please wait")
            progressDialog.show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addUser(){

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        apiInterface?.addUser(Users.UserDetails( pk = null,name, location ))?.enqueue(object :Callback<List<Users.UserDetails>>{

            override fun onFailure(call: Call<List<Users.UserDetails>>, t: Throwable) {
                progressDialog.dismiss()
                call.cancel()
            }

            override fun onResponse(
                call: Call<List<Users.UserDetails>>,
                response: Response<List<Users.UserDetails>>) {

                response.body()!!
                progressDialog.dismiss()
            }

        }
        )

    }


}






//    fun addUserSignalUser(userData: UserDetailsItem, onResult: (UserDetailsItem?) -> Unit){
//
//            apiInterface?.addUser(UserClass("jj", "ddd"))?.enqueue(
//                object : Callback<UserClass> {
//                    override fun onFailure(call: Call<UserClass>, t: Throwable) {
//                        onResult(null)
//                    }
//
//                    override fun onResponse( call: Call<UserClass>, response: Response<UserClass>) {
//                        val addedUser = response.body()
//
//                    }
//                }
//            )
//        }



