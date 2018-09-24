package tgwofficial.atma.client.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import tgwofficial.atma.client.R;

public class BankDarahCursorAdapter extends CursorAdapter {
    public BankDarahCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.bankdarah_content_layout, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView gold = (TextView) view.findViewById(R.id.gol_d);
        TextView telp = (TextView) view.findViewById(R.id.notelp);
        TextView nama_donor = (TextView) view.findViewById(R.id.donor);

        String nama = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String golds = cursor.getString(cursor.getColumnIndexOrThrow("gol_darah"));
        String nomor = cursor.getString(cursor.getColumnIndexOrThrow("telp"));
        String pendonor = cursor.getString(cursor.getColumnIndexOrThrow("name_pendonor"));
        name.setText("NAMA :"+nama);
        nama_donor.setText("NAMA Donor :"+pendonor);
        gold.setText("Gol Darah :"+golds);
        telp.setText("No Telp :"+nomor);
    }
}
