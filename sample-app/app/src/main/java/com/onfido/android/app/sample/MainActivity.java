//This script is to implement Onfido Studio in Androd using Java language.

package com.onfido.android.app.sample;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.onfido.android.sdk.capture.ExitCode;
import com.onfido.android.sdk.capture.Onfido;
import com.onfido.android.sdk.capture.OnfidoFactory;
import com.onfido.workflow.OnfidoWorkflow;
import com.onfido.workflow.WorkflowConfig;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.next).setOnClickListener(v -> startFlow());
    }
    OnfidoWorkflow onfidoWorkflow;
    private void startFlow() {
        String sdkToken = "eyJhbGciOiJFUzUxMiJ9.eyJleHAiOjE3Mjg2NTUwMDUsInBheWxvYWQiOnsiYXBwIjoiYzY1NjEyNjgtNjJhMC00ZGRkLWJiZWEtZGVlNjZlMmMwOGEwIiwiY2xpZW50X3V1aWQiOiJiOTA0NTkzOS0zZjhhLTQ1Y2YtOTJhMi1mMGVhYWQ5MmYxOTAiLCJpc19zYW5kYm94IjpmYWxzZSwiaXNfc2VsZl9zZXJ2aWNlX3RyaWFsIjpmYWxzZSwiaXNfdHJpYWwiOmZhbHNlLCJzYXJkaW5lX3Nlc3Npb24iOiIwMDZjNTYzYi0wODc5LTQ0MzItYTc2YS1iNmZjNWUwMjcyMzQifSwidXVpZCI6InBsYXRmb3JtX3N0YXRpY19hcGlfdG9rZW5fdXVpZCIsImVudGVycHJpc2VfZmVhdHVyZXMiOnsiY29icmFuZCI6dHJ1ZSwibG9nb0NvYnJhbmQiOnRydWUsInVzZUN1c3RvbWl6ZWRBcGlSZXF1ZXN0cyI6dHJ1ZSwidmFsaWRDcm9zc0RldmljZVVybHMiOlsiIl19LCJ1cmxzIjp7ImRldGVjdF9kb2N1bWVudF91cmwiOiJodHRwczovL3Nkay5vbmZpZG8uY29tIiwic3luY191cmwiOiJodHRwczovL3N5bmMub25maWRvLmNvbSIsImhvc3RlZF9zZGtfdXJsIjoiaHR0cHM6Ly9pZC5vbmZpZG8uY29tIiwiYXV0aF91cmwiOiJodHRwczovL2FwaS5vbmZpZG8uY29tIiwib25maWRvX2FwaV91cmwiOiJodHRwczovL2FwaS5vbmZpZG8uY29tIiwidGVsZXBob255X3VybCI6Imh0dHBzOi8vYXBpLm9uZmlkby5jb20ifX0.MIGIAkIAuA27xjEyjT1U_pJEjAfwqWSGMDhRsREW16KJ6_wmXMVYK99eU52H4USu0gmsIlCMHXh4N3oryTFePCwSOzrTowACQgF5wk1q3ws1vS3jjcNyN9AUu3sLYHp-tN2FyzbyH57kDAU1shDVinS2J1cLu72MIqXhqVwxH-6If78ldnu2Haijjw";
        String workflowRunId = "d8fde377-9972-495c-99d4-7b27762e007a";
        WorkflowConfig workflowConfig = new WorkflowConfig.Builder(sdkToken, workflowRunId).build();
        onfidoWorkflow = OnfidoWorkflow.create(this);
        startActivityForResult(onfidoWorkflow.createIntent(workflowConfig), 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onfidoWorkflow.handleActivityResult(resultCode, data, new OnfidoWorkflow.ResultListener() {
            @Override
            public void onUserCompleted() {
                // Called when the entire workflow run has reached the terminal node.
            }
            @Override
            public void onUserExited(ExitCode exitCode) {
                // Called when the user has exited the flow prematurely.
                showToast("User cancelled the flow.");
                Log.d("Testing", "onUserExited: $exitCode");
            }
            @Override
            public void onException(OnfidoWorkflow.WorkflowException exception) {
                // Called when the flow has ended with an exception
                exception.printStackTrace();
                showToast("Unknown error");
                Log.d("Testing", "onException: $exception");
            }
        });
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
