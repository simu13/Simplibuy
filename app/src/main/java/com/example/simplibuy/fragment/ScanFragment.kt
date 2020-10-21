package com.example.simplibuy.fragment

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplibuy.database.ShoppingDatabase
import com.example.simplibuy.R
import com.example.simplibuy.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.fragment_scan.*
import kotlinx.android.synthetic.main.fragment_scan.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanFragment : Fragment(), ZXingScannerView.ResultHandler {
    private val personCollectionRef = Firebase.firestore.collection("products")
    lateinit var viewModel: ShoppingViewModel
    lateinit var adapter: ShoppingItemAdapter
 override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      val root = inflater.inflate(R.layout.fragment_scan, container, false)
     val database = activity?.let { ShoppingDatabase(it) }
     val repository =
         database?.let { ShoppingRepository(it) }
     val factory =
         repository?.let { ShoppingViewModelFactory(it) }

     viewModel = factory?.let { ViewModelProvider(this, it).get(ShoppingViewModel::class.java) }!!
     adapter =
         ShoppingItemAdapter(listOf(), viewModel)
     shopping_cart_recycler.adapter = adapter
     shopping_cart_recycler.layoutManager = LinearLayoutManager(activity)
     // val total = viewModel.getSubTotal().toString()
//textView.text = total
     viewModel.getAllShoppingItems().observe(this, Observer {
         adapter.items = it
         adapter.notifyDataSetChanged()
         val myformat: MutableList<BarcodeFormat> = ArrayList()
         myformat.add(BarcodeFormat.EAN_13)


         root.z_xing_scanner!!.setFormats(myformat)
     })
     //root.z_xing_scanner!!.startCamera()
     (activity as AppCompatActivity).supportActionBar?.show()
     Dexter.withActivity(activity)
         .withPermissions(
             Manifest.permission.CAMERA,
             Manifest.permission.RECORD_AUDIO)
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
        retrievePersons(rawResult)

    }
    private fun createDialog(boards : Product?){
        val builder= activity?.let { AlertDialog.Builder(it) }
        builder?.setMessage("Name:"+boards?.Name+"                                           "+"" +
                "Price:"+"${boards?.Price}rs"+"                                                 "+
                "Weight:"+"${boards?.Weight}wg")
        builder?.setPositiveButton("ok"){ dialogInterface: android.content.DialogInterface, _: Int ->
            val names = boards?.Name
            val price = boards?.Price
            val weight = boards?.Weight
            val items = Product(names!!,price!!,weight!!)
            viewModel.upsert(items)
            z_xing_scanner!!.resumeCameraPreview(this) }
        val alertDialog: AlertDialog =builder!!.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun retrievePersons(rawResult: Result) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot = personCollectionRef.document("$rawResult")
            querySnapshot.get()

                .addOnSuccessListener { document ->
                    if (document != null) {
                        //Toast.makeText(this@ZxingScanner,"DocumentSnapshot data: ${document.data}", Toast.LENGTH_SHORT).show()
                        val boards = document.toObject(Product::class.java)
                        createDialog(boards)

                    } else {

                    }
                }
                .addOnFailureListener {
                }
            withContext(Dispatchers.Main) {

            }
        }

        catch(e: Exception) {
            withContext(Dispatchers.Main) {
                //Toast.makeText(this@ZxingScanner, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}