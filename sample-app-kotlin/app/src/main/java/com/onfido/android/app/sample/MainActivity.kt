package com.onfido.android.app.sample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.onfido.android.sdk.capture.ExitCode
import com.onfido.workflow.OnfidoWorkflow
import com.onfido.workflow.WorkflowConfig
import com.onfido.workflow.OnfidoWorkflow.WorkflowException

class MainActivity : AppCompatActivity() {

    private var onfidoWorkflow: OnfidoWorkflow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        findViewById<View>(R.id.next).setOnClickListener { startFlow() }
    }

    private fun startFlow() {
        // TODO: Call your backend to get `sdkToken` and `workflowRunId`
        val sdkToken =  "eyJhbGciOiJFUzUxMiJ9.eyJleHAiOjE3Mjg2NjAyODMsInBheWxvYWQiOnsiYXBwIjoiYzY1NjEyNjgtNjJhMC00ZGRkLWJiZWEtZGVlNjZlMmMwOGEwIiwiY2xpZW50X3V1aWQiOiJiOTA0NTkzOS0zZjhhLTQ1Y2YtOTJhMi1mMGVhYWQ5MmYxOTAiLCJpc19zYW5kYm94IjpmYWxzZSwiaXNfc2VsZl9zZXJ2aWNlX3RyaWFsIjpmYWxzZSwiaXNfdHJpYWwiOmZhbHNlLCJzYXJkaW5lX3Nlc3Npb24iOiJhYTZiM2QyZC1mZDA5LTQ1ZWUtOTM5NS1kNjE2MDhkOThmODUifSwidXVpZCI6InBsYXRmb3JtX3N0YXRpY19hcGlfdG9rZW5fdXVpZCIsImVudGVycHJpc2VfZmVhdHVyZXMiOnsiY29icmFuZCI6dHJ1ZSwibG9nb0NvYnJhbmQiOnRydWUsInVzZUN1c3RvbWl6ZWRBcGlSZXF1ZXN0cyI6dHJ1ZSwidmFsaWRDcm9zc0RldmljZVVybHMiOlsiIl19LCJ1cmxzIjp7ImRldGVjdF9kb2N1bWVudF91cmwiOiJodHRwczovL3Nkay5vbmZpZG8uY29tIiwic3luY191cmwiOiJodHRwczovL3N5bmMub25maWRvLmNvbSIsImhvc3RlZF9zZGtfdXJsIjoiaHR0cHM6Ly9pZC5vbmZpZG8uY29tIiwiYXV0aF91cmwiOiJodHRwczovL2FwaS5vbmZpZG8uY29tIiwib25maWRvX2FwaV91cmwiOiJodHRwczovL2FwaS5vbmZpZG8uY29tIiwidGVsZXBob255X3VybCI6Imh0dHBzOi8vYXBpLm9uZmlkby5jb20ifX0.MIGIAkIAovsXbdJiu23fALMomhADnHgfkKNGF0kb5QPId0GvAzqcwG_8XfQHpoTbATcTMuHV-RJwCPBEdXT_4fdam2EsoQACQgDDNOp1HU3_v_faLmAZ_A-w7Qo9pxFLUHGSX-nu5_GsNFmBMRdllgeA_i2V4CbiBmcH5j-9ihPnEPMNgQqV8SrcSQ"
        val workflowRunId = "8c9aeb47-68ee-4137-9be1-0a30adeecbb4"

        val workflowConfig = WorkflowConfig.Builder(sdkToken, workflowRunId).build()
        onfidoWorkflow = OnfidoWorkflow.create(this)

        startActivityForResult(onfidoWorkflow!!.createIntent(workflowConfig), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        onfidoWorkflow?.handleActivityResult(resultCode, data, object : OnfidoWorkflow.ResultListener {
            override fun onUserCompleted() {
                // Called when the entire workflow run has reached the terminal node.
                showToast("User completed the flow.")
            }

            override fun onUserExited(exitCode: ExitCode) {
                // Called when the user has exited the flow prematurely.
                showToast("User cancelled the flow.")
                Log.d("Onfido", "User exited with exitCode: $exitCode")
            }

            override fun onException(exception: WorkflowException) {
                // Called when the flow has ended with an exception
                exception.printStackTrace()
                showToast("Unknown error")
                Log.d("Onfido", "Exception: ${exception.message}")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
