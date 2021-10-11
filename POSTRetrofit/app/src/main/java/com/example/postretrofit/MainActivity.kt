package com.example.postretrofit

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var newUserBtn: Button
    lateinit var rvMain: RecyclerView
    var myUserList = arrayListOf<Users.UserDetails>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newUserBtn = findViewById(R.id.newUserBtn)
        rvMain = findViewById(R.id.rvMain)

        rvMain.adapter =UserAdapter( myUserList)
        rvMain.layoutManager = LinearLayoutManager(this)

        getUsers()

        var updateDeleteBtn = findViewById<Button>(R.id.updateDeleteBtn)
        updateDeleteBtn.setOnClickListener {
            val intent = Intent(this, UpdateOrDeleteUser::class.java)
            startActivity(intent)}

        newUserBtn.setOnClickListener {
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Please wait")
            progressDialog.show()
            val intent = Intent(this, MoreUsers::class.java)
            startActivity(intent) }

    }

    private fun getUsers(){
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        apiInterface?.getUser()?.enqueue(object : Callback<List<Users.UserDetails>> {
            override fun onResponse(
                call: Call<List<Users.UserDetails>>,
                response: Response<List<Users.UserDetails>>
            ) {

                val resource = response.body()!!
                progressDialog.dismiss()
                for ( i in resource){
                    var uName = i.name.toString()
                    var uLocation = i.location.toString()
                    var uPk = i.pk
                    myUserList.add(Users.UserDetails( uPk,uLocation,uName))


                }
                rvMain.adapter?.notifyDataSetChanged()
                rvMain.scrollToPosition(myUserList.size - 1)

            }

            override fun onFailure(call: Call<List<Users.UserDetails>>, t: Throwable) {
                progressDialog.dismiss()
                call.cancel()
            }
        })

    }

//    fun add2(name: String , location:String){
//
//        apiInterface?.addUser(UserClass(name , location))?.enqueue(object : Callback<UserClass> {
//
//            override fun onFailure(call : Call<UserClass>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "Something went wrong" , Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(call: Call<UserClass>, response: Response<UserClass>) {
//                //myUserList.add(UserClass(name, location))
//                Toast.makeText(this@MainActivity, "User added" , Toast.LENGTH_SHORT).show()
//              //  myUserList.add(UserClass(name, location))
//            }
//
//
//        })
//    }


}