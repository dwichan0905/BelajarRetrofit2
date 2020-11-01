package id.dwichan.pmo2_dwicandrapermana_pert4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import id.dwichan.pmo2_dwicandrapermana_pert4.api.ApiClient;
import id.dwichan.pmo2_dwicandrapermana_pert4.api.contact.ContactApiInterface;
import id.dwichan.pmo2_dwicandrapermana_pert4.api.contact.requests.PostPutDeleteContact;
import id.dwichan.pmo2_dwicandrapermana_pert4.data.Contact;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class DetailsActivity extends AppCompatActivity {

    // static variables for Bundles
    public final static String EXTRA_ID = "extra_id";
    public final static String EXTRA_NAMA = "extra_nama";
    public final static String EXTRA_NOMOR = "extra_nomor";

    // private variables
    private String id;
    private String nama;
    private String nomor;

    // Retrofit Classes
    private ContactApiInterface apiInterface;

    // private view objects
    private TextView tvContactName;
    private TextView tvContactNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // integrate views with Java
        tvContactName = findViewById(R.id.tvContactName);
        tvContactNumber = findViewById(R.id.tvContactNumber);
        MaterialButton btnUpdate = findViewById(R.id.btnUpdate);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        apiInterface = new ApiClient().getClient().create(ContactApiInterface.class);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        id = bundle.getString(EXTRA_ID, "");
        nama = bundle.getString(EXTRA_NAMA, "");
        nomor = bundle.getString(EXTRA_NOMOR, "");

        tvContactName.setText(nama);
        tvContactNumber.setText(nomor);

        btnUpdate.setOnClickListener(v -> {
            Contact contact = new Contact();
            contact.setId(id);
            contact.setNama(nama);
            contact.setNomor(nomor);

            Intent i = new Intent(this, InsertUpdateActivity.class);
            i.putExtra(InsertUpdateActivity.EXTRA_PARCEL_CONTACT, contact);
            startActivityForResult(i, InsertUpdateActivity.REQUEST_UPDATE);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        } else if (item.getItemId() == R.id.menuDelete) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(getResources().getString(R.string.sure))
                    .setTitle(getResources().getString(R.string.delete_confirm) + nama + "\"?")
                    .setPositiveButton(getResources().getString(R.string.yes), (a, b) -> {
                        // Retrofit DELETE Command Enqueue
                        Call<PostPutDeleteContact> deleteContact = apiInterface.deleteContact(
                                id
                        );
                        deleteContact.enqueue(new Callback<PostPutDeleteContact>() {
                            @Override
                            @EverythingIsNonNull
                            public void onResponse(Call<PostPutDeleteContact> call, Response<PostPutDeleteContact> response) {
                                setResult(RESULT_OK);
                                finish();
                            }

                            @Override
                            @EverythingIsNonNull
                            public void onFailure(Call<PostPutDeleteContact> call, Throwable t) {
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Error: " + t.getLocalizedMessage()
                                        , Toast.LENGTH_LONG
                                ).show();
                            }
                        });
                    })
                    .setNegativeButton(getResources().getString(R.string.no), null)
                    .setCancelable(false)
                    .create().show();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            assert data != null;
            Contact contact = data.getParcelableExtra(InsertUpdateActivity.EXTRA_PARCEL_CONTACT);
            tvContactName.setText(contact.getNama());
            tvContactNumber.setText(contact.getNomor());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}