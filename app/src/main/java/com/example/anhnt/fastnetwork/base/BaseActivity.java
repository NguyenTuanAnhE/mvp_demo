package com.example.anhnt.fastnetwork.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.anhnt.fastnetwork.R;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.registerable.connection.Connectable;
import com.novoda.merlin.registerable.disconnection.Disconnectable;

import butterknife.ButterKnife;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;


public abstract class BaseActivity extends AppCompatActivity {

    ACProgressFlower dialog;
    Merlin merlin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(getContentLayout());
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeEnable());
            getSupportActionBar().setDisplayShowHomeEnabled(showHomeEnable());
        }
        merlin = new Merlin.Builder().withAllCallbacks().build(this);
        merlin.registerConnectable(connectable);
        merlin.registerDisconnectable(disconnectable);

        dialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text(getString(R.string.msg_loading))
                .fadeColor(Color.DKGRAY).build();
        super.onCreate(savedInstanceState);
    }

    private Connectable connectable = new Connectable() {
        @Override
        public void onConnect() {
            Toast.makeText(BaseActivity.this, getText(R.string.connect), Toast.LENGTH_SHORT).show();
        }
    };

    private Disconnectable disconnectable = new Disconnectable() {
        @Override
        public void onDisconnect() {
            Toast.makeText(BaseActivity.this, getText(R.string.disconnect), Toast.LENGTH_SHORT).show();
        }
    };

    public abstract int getContentLayout();

    protected abstract boolean showHomeEnable();

    protected void showProcessDialog() {
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    protected void hideProcessDialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        merlin.bind();
    }

    @Override
    protected void onPause() {
        merlin.unbind();
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
