package com.example.postretrofit

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UpdateOrDeleteUser : AppCompatActivity() {

    lateinit var etID : EditText
    lateinit var et_name : EditText
    lateinit var et_location: EditText
    var id : Int = 0
    var name=""
    var location=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_or_delete_user)

        var deleteBtn = findViewById<Button>(R.id.deleteBtn)
        var updateBtn = findViewById<Button>(R.id.updateBtn)
        etID = findViewById(R.id.etId)
        et_name = findViewById(R.id.et_name)
        et_location = findViewById(R.id.et_location)

        deleteBtn.setOnClickListener {
            id = etID.text.toString().toInt()
            deleteUser()
            clearText()
        }
        updateBtn.setOnClickListener {
            id = etID.text.toString().toInt()
            name = et_name.text.toString()
            location= et_location.text.toString()
            updateUser()
            clearText() }
    }

    private fun clearText() {
        etID.setText("")
        et_name.setText("")
        et_location.setText("")
    }

    private fun updateUser() {

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()
        apiInterface?.updateUser(id,Users.UserDetails(id,name,location))?.enqueue(object :Callback<List<Users.UserDetails>>{
            override fun onResponse(
                call: Call<List<Users.UserDetails>>,
                response: Response<List<Users.UserDetails>>,
            ) {
                Toast.makeText(applicationContext, "User Updated Successfully!", Toast.LENGTH_SHORT).show()
                response.body()!!
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<List<Users.UserDetails>>, t: Throwable) {
                progressDialog.dismiss()
                call.cancel()
            }

        })


    }

    private fun deleteUser() {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()
        apiInterface?.deleteUser(id)?.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(applicationContext, "User deleted" , Toast.LENGTH_SHORT).show()
                response.body()!!
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                progressDialog.dismiss()
                call.cancel()

            }
        })
    }
}

