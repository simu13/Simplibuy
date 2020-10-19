package com.example.simplibuy.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplibuy.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.fragment_scan.*
import kotlinx.android.synthetic.main.fragment_scan.view.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanFragment : Fragment(), ZXingScannerView.ResultHandler {
 override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      val root = inflater.inflate(R.layout.fragment_scan, container, false)
     val myformat: MutableList<BarcodeFormat> = ArrayList()
     myformat.add(BarcodeFormat.EAN_13)

     root.z_xing_scanner!!.setFormats(myformat)
     //root.z_xing_scanner!!.startCamera()
     (activity as AppCompatActivity).supportActionBar?.show()
     Dexter.withActivity(activity)
         .withPermissions(android.Manifest.permission.CAMERA,
             android.Manifest.permission.RECORD_AUDIO)
         .withListener(object: MultiplePermissionsListener {
             override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

             }

             override fun onPermissionRationaleShouldBeShown(
                 permissions: MutableList<PermissionRequest>?,
                 token: PermissionToken?
             ) {

             }


         }).check()
     return root
    }


    public override fun onResume() {
        super.onResume()
        z_xing_scanner!!.setResultHandler(this)
        z_xing_scanner!!.startCamera()
    }

    public override fun onPause() {
        super.onPause()
        z_xing_scanner!!.stopCamera()
    }

    override fun handleResult(rawResult: Result) {
        Log.v("YYYY", rawResult.text)
        Log.v(
            "YYYY",
            rawResult.barcodeFormat.toString()
        )

        //saveDataToFirebase(rawResult.text, rawResult.barcodeFormat.toString())

        // mScannerView!!.resumeCameraPreview(this)

        //createDialog(rawResult.toString())
        //retrievePersons(rawResult)

    }


}