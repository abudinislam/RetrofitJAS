package kz.abudinislam.retrofitjas.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kz.abudinislam.retrofitjas.R
import kz.abudinislam.retrofitjas.databinding.FragmentAccountBinding
import kz.abudinislam.retrofitjas.model.AccountInfo
import kz.abudinislam.retrofitjas.viewmodel.AccountViewModel
import kz.abudinislam.retrofitjas.viewmodel.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : Fragment(R.layout.fragment_account) {
    private lateinit var binding: FragmentAccountBinding
    private  val  viewModel by viewModel<AccountViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAccountBinding.bind(view)
        Picasso.get().load(R.drawable.empty_avatar).into(binding.imCamera)
        // проверяем есть ли разрешение
        binding.btnCamera.setOnClickListener {
            getCameraPermission()
        }
        binding.btnGallery.setOnClickListener {
            getGalleryPermission()
        }
    }

    private fun getCameraPermission(){
        if (ContextCompat.checkSelfPermission(
                this.requireActivity(), Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED){
            takePicture()
        }else{
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                PERMISSION_FOR_CAMERA
            )
        }
    }

    private fun getGalleryPermission(){
        if (ContextCompat.checkSelfPermission(
                this.requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED){
            pickImage()
            //запрашиваем разрешение если его нет
        }else{
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_FOR_GALLERY
            )
        }
    }


    //берем фотографии с галлереи
    private fun takePicture() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, PERMISSION_FOR_CAMERA)
    }

    //функция позволяющая сделать фотографию
    private fun pickImage() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PERMISSION_FOR_GALLERY)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_FOR_CAMERA ->{
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // можно делать что - то с камерой
                    takePicture()
                }

            }
            PERMISSION_FOR_GALLERY ->{
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImage()
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK) {
            val uri = data?.data
            when (requestCode) {
                PERMISSION_FOR_CAMERA -> {
                    binding.imCamera.setImageURI(uri)
                    val user = AccountInfo(userAvatar = uri.toString())
                    viewModel.insertUser(user)

//                    val parsedUri = Uri.parse(user.avatar_uri)
//                    binding.imCamera.setImageURI(parsedUri)
                }
                PERMISSION_FOR_GALLERY -> {
                    binding.imCamera.setImageURI(uri)
                }
            }
        }
    }

    companion object{
        private const val PERMISSION_FOR_CAMERA = 1
        private const val PERMISSION_FOR_GALLERY = 2
    }
}
