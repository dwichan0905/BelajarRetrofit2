package id.dwichan.pmo2_dwicandrapermana_pert4.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.dwichan.pmo2_dwicandrapermana_pert4.DetailsActivity;
import id.dwichan.pmo2_dwicandrapermana_pert4.R;
import id.dwichan.pmo2_dwicandrapermana_pert4.data.Contact;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private final ArrayList<Contact> mData = new ArrayList<>();

    public void setData(ArrayList<Contact> contacts) {
        this.mData.clear();
        this.mData.addAll(contacts);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvContactName;
        private final TextView tvContactNumber;
        private final View view;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tvContactName = itemView.findViewById(R.id.tvContactName);
            tvContactNumber = itemView.findViewById(R.id.tvContactNumber);
        }

        public void bind(Contact contact) {
            tvContactName.setText(contact.getNama());
            tvContactNumber.setText(contact.getNomor());

            view.setOnClickListener(v -> {
                Intent i = new Intent(view.getContext(), DetailsActivity.class);
                i.putExtra(DetailsActivity.EXTRA_ID, contact.getId());
                i.putExtra(DetailsActivity.EXTRA_NAMA, contact.getNama());
                i.putExtra(DetailsActivity.EXTRA_NOMOR, contact.getNomor());
                ((Activity) view.getContext()).startActivityForResult(i, 99);
            });
        }
    }
}
