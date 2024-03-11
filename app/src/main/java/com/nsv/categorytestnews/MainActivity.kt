package com.nsv.categorytestnews

import android.app.AlertDialog
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
    private lateinit var netWorkErrorDialog: Dialog

    private var isNetworkAvailable = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        netWorkErrorDialog = Dialog(this)

        isNetworkAvailable = isNetworkAvailable(this)



        setNavToHome()
        registerNetworkReceiver()

    }

    private fun setNavToHome() {
        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
        newsViewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item_mainactivity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.savednews_menu -> {
                val intent = Intent(applicationContext, SavedNewsActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.setting_news -> {
                val intent = Intent(applicationContext, SettingActivity::class.java)
                startActivity(intent)
            }
            R.id.share_app -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "NewsToro")
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
            R.id.about_app ->{
                val intent = Intent(applicationContext, AboutActivity::class.java)
                startActivity(intent)
            }
            R.id.exit_app -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Confirm Exit")
                builder.setMessage("Are you sure you want to exit?")
                builder.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                    finish()
                }
                builder.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            }

            else -> return super.onOptionsItemSelected(item)

        }
        return  true
    }



    override fun onDestroy() {
        super.onDestroy()
        unregisterNetworkReceiver()
    }

    private fun registerNetworkReceiver() {
        networkReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val networkWasAvailable = isNetworkAvailable
                isNetworkAvailable = isNetworkAvailable(context)
                if (!isNetworkAvailable && !netWorkErrorDialog.isShowing) {
                    showNetworkErrorDialog()
                } else if (isNetworkAvailable && netWorkErrorDialog.isShowing && !networkWasAvailable) {
                    dismissNetworkErrorDialog()
                    setNavToHome()
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
        if (::netWorkErrorDialog.isInitialized && netWorkErrorDialog.isShowing) {
            netWorkErrorDialog.dismiss()
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return (networkInfo != null) && networkInfo.isConnected
    }

    private fun showNetworkErrorDialog() {
        // Show a dialog informing the user that the network is not available
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Network connection is not available. Please check your internet connection.")
            .setPositiveButton("OK") { dialog: DialogInterface, which: Int ->
                dialog.dismiss()
                // Recursively show the dialog until the network becomes available
                if (!isNetworkAvailable(this)) {
                    showNetworkErrorDialog()
                } else {
                    // Once the network becomes available, dismiss the dialog and proceed with navigation setup
                    dismissNetworkErrorDialog()
                    setNavToHome()
                }
            }
        netWorkErrorDialog = builder.create()
        netWorkErrorDialog.setCancelable(false)
        netWorkErrorDialog.show()
    }


}