package com.example.database

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import com.example.database.adapter.RvAdapter
import com.example.database.databinding.ActivityMainBinding
import com.example.database.databinding.CustomDialogBinding
import com.example.database.db.MyDbHelper
import com.example.database.models.User

class MainActivity : AppCompatActivity(), RvAdapter.RvAction {
    private  val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var rvAdapter:RvAdapter
    lateinit var myDbHelper: MyDbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        myDbHelper = MyDbHelper(this)

        binding.addfloatingbutton.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            val customDialogBinding = CustomDialogBinding.inflate(layoutInflater)

            customDialogBinding.btnSave.setOnClickListener {
                val user = User(
                    1,
                    customDialogBinding.edtName.text.toString(),
                    customDialogBinding.edtNumber.text.toString()
                )
                myDbHelper.addUser(user)
                onResume()
                dialog.cancel()
            }
            dialog.setView(customDialogBinding.root)
            dialog.show()
        }
    }

    override fun onResume() {
        super.onResume()
        val list = myDbHelper.showUsers()
        rvAdapter = RvAdapter(list as ArrayList<User>, this)
        binding.rv.adapter = rvAdapter
    }

    override fun itemClick(user: User) {

    }

    override fun moreClick(user: User, imageView: ImageView) {
        val popupMenu = PopupMenu(this, imageView)
        popupMenu.inflate(R.menu.more_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_edit -> {
                    val dialog = AlertDialog.Builder(this).create()
                    val customDialog = CustomDialogBinding.inflate(layoutInflater)
                    dialog.setView(customDialog.root)
                    customDialog.edtName.setText(user.name)
                    customDialog.edtNumber.setText(user.number)
                    customDialog.btnSave.setOnClickListener {
                        user.name = customDialog.edtName.text.toString()
                        user.number = customDialog.edtNumber.text.toString()
                        myDbHelper.editUser(user)
                        onResume()
                        dialog.cancel()
                    }
                    dialog.show()
                }

                R.id.menu_delete -> {
                    myDbHelper.deleteUser(user)
                    onResume()
                }
            }
            true
        }
        popupMenu.show()
    }

}