package com.example.databindingintent

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.databindingintent.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
            private val myContact = Contact("see",
                "012345678")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main)
        //setContentView(R.layout.activity_main)

        binding.contact = myContact

        binding.buttonSave.setOnClickListener{
            with(binding){
                contact?.name = editTextName.text.toString()
                contact?.phone = editTextPhone.text.toString()
                invalidateAll()
            }
        }

        binding.buttonSend.setOnClickListener{
            //Create an Explicit Intent
            val intent = Intent(this, SecondActivity::class.java)

            //Prepare Extra data for the intent
            intent.putExtra(EXTRA_NAME, binding.contact?.name)
            intent.putExtra(EXTRA_PHONE, binding.contact?.phone)

            //Your parent expect no return value
            //startActivity(intent)

            //PTPTN expects result
            startActivityForResult(intent, REQUEST_REPLY)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_REPLY){
            if(resultCode == Activity.RESULT_OK){
                val reply = data?.getStringExtra(EXTRA_REPLY)
                textViewReply.text = String.format("Reply : %s", reply)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object{
        const val EXTRA_NAME = "com.example.databindingintent.NAME"
        const val EXTRA_PHONE = "com.example.databindingintent.PHONE"
        const val REQUEST_REPLY = 1
        const val EXTRA_REPLY = "com.example.databindingintent.REPLY"
    }
}
