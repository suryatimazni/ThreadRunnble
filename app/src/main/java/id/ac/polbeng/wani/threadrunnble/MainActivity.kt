package id.ac.polbeng.wani.threadrunnable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import id.ac.polbeng.wani.threadrunnable.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val workManager by lazy {
        WorkManager.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val maxCounter = workDataOf(MyWorker.COUNTER to 20)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .build()
        val myWorker = OneTimeWorkRequestBuilder<MyWorker>()
            .setInputData(maxCounter)
            .setConstraints(constraints)
            .build()
        binding.button.setOnClickListener {
            workManager.enqueueUniqueWork(
                "oneTimeRequest",
                ExistingWorkPolicy.KEEP,
                myWorker
            )
        }
    }
}

