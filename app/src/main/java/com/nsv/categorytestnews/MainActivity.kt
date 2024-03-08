package com.nsv.categorytestnews

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.nsv.categorytestnews.databinding.ActivityMainBinding
import com.nsv.categorytestnews.repository.NewsRepository
import com.nsv.testnews.db.ArticleDatabase

class MainActivity : AppCompatActivity() {

    lateinit var newsViewModel: NewsViewModel
    private lateinit var binding: ActivityMainBinding

    private lateinit var networkReceiver: BroadcastReceiver
    private lateinit var  netWorkErrorDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerNetworkReceiver()
        setNavToHome()

    }

    private fun setNavToHome() {
        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
        newsViewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterNetworkReceiver()
    }

    private fun registerNetworkReceiver() {
        networkReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (!isNetworkAvailable(context)) {
                    if (!netWorkErrorDialog.isShowing) {
                        showNetworkErrorDialog()
                    }
                } else {
                    dismissNetworkErrorDialog()
                }
            }
        }
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, filter)
    }

    private fun unregisterNetworkReceiver() {
        unregisterReceiver(networkReceiver)
    }

    private fun dismissNetworkErrorDialog() {
        if (netWorkErrorDialog != null && netWorkErrorDialog.isShowing) {
            netWorkErrorDialog.dismiss()
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun showNetworkErrorDialog() {
        // Show a dialog informing the user that the network is not available
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Network connection is not available. Please check your internet connection.")
            .setPositiveButton("OK") { dialog: DialogInterface, which: Int ->
                dialog.dismiss()
               setNavToHome()
            }
        netWorkErrorDialog = builder.create()
        netWorkErrorDialog.setCancelable(false)
        netWorkErrorDialog.show()
    }

}