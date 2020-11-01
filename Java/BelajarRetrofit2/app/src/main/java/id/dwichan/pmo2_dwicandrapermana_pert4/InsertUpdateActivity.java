package id.dwichan.pmo2_dwicandrapermana_pert4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
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

public class InsertUpdateActivity extends AppCompatActivity {

    // public static variables
    public static final String EXTRA_PARCEL_CONTACT = "extra_parcel_contact";
    public static final int REQUEST_UPDATE = 11;
    public static final int REQUEST_SAVE = 10;

    // private variables
    private String id = null;

    // Retrofit classes
    private ContactApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_update);

        // Integration with Views
        TextView tvTitle = findViewById(R.id.tvTitle);
        Toolbar tb = findViewById(R.id.toolbar);
        EditText edtContactName = findViewById(R.id.edtContactName);
        EditText edtContactNumber = findViewById(R.id.edtContactNumber);
        MaterialButton btnAddUpdate = findViewById(R.id.btnAddUpdate);

        setSupportActionBar(tb);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        apiInterface = new ApiClient().getClient().create(ContactApiInterface.class);

        if (bundle != null) {
            Contact contact = bundle.getParcelable(EXTRA_PARCEL_CONTACT);
            edtContactName.setText(contact.getNama());
            edtContactNumber.setText(contact.getNomor());
            id = contact.getId();
            tvTitle.setText(getResources().getString(R.string.edit_contact));
            btnAddUpdate.setText(getResources().getString(R.string.edit_contact));
        } else {
            tvTitle.setText(getResources().getString(R.string.add_contact));
            btnAddUpdate.setText(getResources().getString(R.string.add_contact));
        }

        btnAddUpdate.setOnClickListener(v -> {
            if ((id == null) || (id.equals(""))) {
                // Retrofit INSERT request
                Call<PostPutDeleteContact> insertContact = apiInterface.postContact(
                        edtContactName.getText().toString(),
                        edtContactNumber.getText().toString()
                );
                insertContact.enqueue(new Callback<PostPutDeleteContact>() {
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
                                InsertUpdateActivity.this,
                                "Error: " + t.getLocalizedMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
            } else {
                // Retrofit EDIT Request
                Call<PostPutDeleteContact> putContact = apiInterface.putContact(
                        id,
                        edtContactName.getText().toString(),
                        edtContactNumber.getText().toString()
                );
                putContact.enqueue(new Callback<PostPutDeleteContact>() {
                    @Override
                    @EverythingIsNonNull
                    public void onResponse(Call<PostPutDeleteContact> call, Response<PostPutDeleteContact> response) {
                        Intent i = new Intent();
                        Contact contact1 = new Contact();
                        contact1.setId(id);
                        contact1.setNama(edtContactName.getText().toString());
                        contact1.setNomor(edtContactNumber.getText().toString());
                        i.putExtra(InsertUpdateActivity.EXTRA_PARCEL_CONTACT, contact1);
                        setResult(RESULT_OK, i);
                        finish();
                    }

                    @Override
                    @EverythingIsNonNull
                    public void onFailure(Call<PostPutDeleteContact> call, Throwable t) {
                        Toast.makeText(
                                InsertUpdateActivity.this,
                                "Error: " + t.getLocalizedMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) super.onBackPressed();
        return true;
    }
}