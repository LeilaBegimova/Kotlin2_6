package com.example.kotlin2_6

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.kotlin2_6.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var biding: ActivityMainBinding
    private lateinit var ivImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        biding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(biding.root)

        setupListeners()
    }

    private fun setupListeners() = with(biding) {
        var isHasPermission = PreferenceHelper(this@MainActivity).isHasPermission
        btnGetRequest.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
                    ivImage.setImageURI(it)
                }
                getContent.launch("image/*")

                Log.e("permission", "Разрешение есть")
            } else {
                if (!isHasPermission) {
                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 100)
                    isHasPermission = true
                } else {
                    createDialog()
                }
            }
        }

    }

    private fun createDialog() {
        val builder = AlertDialog.Builder(this)
            .setTitle("Разрешение на чтение данных")
            .setMessage("Перейти в настроики?")
            .setPositiveButton("Да, перейти") { dialog, k ->
                intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Нет я хочу отаться") { dialog, k ->
            }
            .show()
    }

}
