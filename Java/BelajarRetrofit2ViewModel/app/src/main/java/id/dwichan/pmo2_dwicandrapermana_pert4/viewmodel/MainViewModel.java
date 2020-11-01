package id.dwichan.pmo2_dwicandrapermana_pert4.viewmodel;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import id.dwichan.pmo2_dwicandrapermana_pert4.R;
import id.dwichan.pmo2_dwicandrapermana_pert4.api.ApiClient;
import id.dwichan.pmo2_dwicandrapermana_pert4.api.contact.ContactApiInterface;
import id.dwichan.pmo2_dwicandrapermana_pert4.api.contact.requests.GetContact;
import id.dwichan.pmo2_dwicandrapermana_pert4.data.Contact;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Contact>> mutableLiveData = new MutableLiveData<>();

    public void setContact(Context context, ProgressBar progressBar, TextView error) {
        ContactApiInterface apiInterface = new ApiClient().getClient().create(ContactApiInterface.class);

        Call<GetContact> contactCall = apiInterface.getContacts();
        contactCall.enqueue(new Callback<GetContact>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<GetContact> call, Response<GetContact> response) {
                progressBar.setVisibility(View.GONE);
                assert response.body() != null;
                ArrayList<Contact> contacts = response.body().getListContact();
                Log.d("RETROFIT_OK_GET", "Jumlah Kontak Terdata: " + contacts.size());
                if (contacts.size() > 0) {
                    mutableLiveData.postValue(contacts);
                    error.setVisibility(View.GONE);
                } else {
                    mutableLiveData.setValue(new ArrayList<>());
                    error.setVisibility(View.VISIBLE);
                    error.setText(context.getString(R.string.no_data));
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<GetContact> call, Throwable t) {
                //mutableLiveData.setValue(new ArrayList<>());
                progressBar.setVisibility(View.GONE);
                Log.e("RETROFIT_ERROR_GET", t.getLocalizedMessage());
                error.setVisibility(View.VISIBLE);
                error.setText(t.getLocalizedMessage());
            }
        });
    }

    public void setContact(Context context, SwipeRefreshLayout srl, TextView error) {
        ContactApiInterface apiInterface = new ApiClient().getClient().create(ContactApiInterface.class);

        Call<GetContact> contactCall = apiInterface.getContacts();
        contactCall.enqueue(new Callback<GetContact>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<GetContact> call, Response<GetContact> response) {
                srl.setRefreshing(false);
                assert response.body() != null;
                ArrayList<Contact> contacts = response.body().getListContact();
                Log.d("RETROFIT_OK_GET", "Jumlah Kontak Terdata: " + contacts.size());
                if (contacts.size() > 0) {
                    mutableLiveData.postValue(contacts);
                    error.setVisibility(View.GONE);
                } else {
                    mutableLiveData.setValue(new ArrayList<>());
                    error.setVisibility(View.VISIBLE);
                    error.setText(context.getString(R.string.no_data));
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<GetContact> call, Throwable t) {
                srl.setRefreshing(false);
                //mutableLiveData.setValue(new ArrayList<>());
                Log.e("RETROFIT_ERROR_GET", t.getLocalizedMessage());
                error.setVisibility(View.VISIBLE);
                error.setText(t.getLocalizedMessage());
            }
        });
    }

    public LiveData<ArrayList<Contact>> getContacts() {
        return this.mutableLiveData;
    }

}
