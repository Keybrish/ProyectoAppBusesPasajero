package dev.android.appbuses

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.Window
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import dev.android.appbuses.databinding.ActivityFileBinding

class FileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFileBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        val loadFile = registerForActivityResult(ActivityResultContracts.GetContent(), ActivityResultCallback {
            binding.txtFile.text = it?.let { it1 -> getName(it1, applicationContext) }
        })

        binding.btnUpload.setOnClickListener {
//            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//            intent.type = "image/*"
//            startActivityForResult(Intent.createChooser(intent, "Seleccione Aplicación"), 10)
//            val result = onActivityResult(CONTEXT_INCLUDE_CODE, RESULT_OK, intent)
//            var data = Intent(Intent.ACTION_OPEN_DOCUMENT)
//            data.type = "*/*"
//            data = Intent.createChooser(data , "Elegir Aplicación")
//            startActivityForResult(data, 10)
            loadFile.launch("*/*")
        }
    }

    @SuppressLint("Range")
    fun getName(uri: Uri, context: Context): String? {
        var result: String? = null
        if (uri != null) {
            if (uri.scheme.equals("content")) {
                val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                }finally {
                    cursor?.close()
                }
                if(result == null) {
                    result = uri.path.toString()
                    val cutt = result.lastIndexOf('/')
                    if(cutt != -1) {
                        result = result.substring(cutt +1)
                    }
                }
            }
        }
        return result
    }
}