package id.dwichan.pmo2_dwicandrapermana_pert4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Objects;

import id.dwichan.pmo2_dwicandrapermana_pert4.adapter.ContactAdapter;
import id.dwichan.pmo2_dwicandrapermana_pert4.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final ContactAdapter contactAdapter = new ContactAdapter();

    private ProgressBar progressBar;
    private RecyclerView rvContact;
    private TextView tvError;
    private SwipeRefreshLayout pullToRefresh;

    // Retrofit classes
    //private ContactApiInterface apiInterface;

    // ViewModel
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Integrate views
        // private views
        progressBar = findViewById(R.id.progressBar);
        tvError = findViewById(R.id.tvError);
        ExtendedFloatingActionButton fabAddContact = findViewById(R.id.fabAddContact);
        rvContact = findViewById(R.id.rvContact);
        pullToRefresh = findViewById(R.id.pullToRefresh);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");

        progressBar.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);

        fabAddContact.setOnClickListener(this);

        rvContact.setLayoutManager(new LinearLayoutManager((this)));
        rvContact.setAdapter(contactAdapter);

        // Retrofit SELECT command
        //apiInterface = new ApiClient().getClient().create(ContactApiInterface.class);
        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory())
                .get(MainViewModel.class);
        mainViewModel.getContacts().observe(this, contacts -> {
            if (contacts.size() > 0) {
                contactAdapter.setData(contacts);
                tvError.setVisibility(View.GONE);
                rvContact.setVisibility(View.VISIBLE);
            } else {
                //contactAdapter.setData(new ArrayList<>());
                tvError.setVisibility(View.VISIBLE);
                tvError.setText(getString(R.string.no_data));
                rvContact.setVisibility(View.GONE);
            }
        });
        mainViewModel.setContact(this, progressBar, tvError);
        //refresh();

        pullToRefresh.setOnRefreshListener(() -> {
            // Retrofit SELECT command enqueue
            mainViewModel.setContact(this, pullToRefresh, tvError);
            /*Call<GetContact> contactCall = apiInterface.getContacts();
            contactCall.enqueue(new Callback<GetContact>() {
                @Override
                public void onResponse(Call<GetContact> call, Response<GetContact> response) {
                    ArrayList<Contact> contacts = response.body().getListContact();
                    Log.d("RETROFIT_OK_GET", "Jumlah Kontak Terdata: " + contacts.size());
                    contactAdapter.setData(contacts);
                    rvContact.setVisibility(View.VISIBLE);
                    tvError.setVisibility(View.GONE);
                    pullToRefresh.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<GetContact> call, Throwable t) {
                    Log.e("RETROFIT_ERROR_GET", t.getLocalizedMessage());
                    rvContact.setVisibility(View.GONE);
                    tvError.setVisibility(View.VISIBLE);
                    tvError.setText(t.getLocalizedMessage());
                    pullToRefresh.setRefreshing(false);
                }
            });*/
        });
    }

    //private void refresh() {
        /*Call<GetContact> contactCall = apiInterface.getContacts();
        contactCall.enqueue(new Callback<GetContact>() {
            @Override
            public void onResponse(Call<GetContact> call, Response<GetContact> response) {
                progressBar.setVisibility(View.GONE);
                ArrayList<Contact> contacts = response.body().getListContact();
                Log.d("RETROFIT_OK_GET", "Jumlah Kontak Terdata: " + contacts.size());
                if (contacts.size() > 0) {
                    contactAdapter.setData(contacts);
                    tvError.setVisibility(View.GONE);
                    rvContact.setVisibility(View.VISIBLE);
                } else {
                    contactAdapter.setData(new ArrayList<>());
                    tvError.setVisibility(View.VISIBLE);
                    tvError.setText(getString(R.string.no_data));
                    rvContact.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<GetContact> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("RETROFIT_ERROR_GET", t.getLocalizedMessage());
                rvContact.setVisibility(View.GONE);
                tvError.setVisibility(View.VISIBLE);
                tvError.setText(t.getLocalizedMessage());
            }
        });*/
    //}

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabAddContact) {
            Intent i = new Intent(this, InsertUpdateActivity.class);
            startActivityForResult(i, InsertUpdateActivity.REQUEST_SAVE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAbout) {
            Intent i = new Intent(this, AboutActivity.class);
            startActivity(i);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            //refresh();
            mainViewModel.setContact(this, progressBar, tvError);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}