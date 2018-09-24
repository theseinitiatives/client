package tgwofficial.atma.client.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import tgwofficial.atma.client.R;

public class TransportasiCursorAdapter extends CursorAdapter {
    public TransportasiCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.transportasi_content_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView dusunss = (TextView) view.findViewById(R.id.dusuns);
        TextView kend = (TextView) view.findViewById(R.id.kendaraan);
        String nama = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String jenis = cursor.getString(cursor.getColumnIndexOrThrow("jenis_kendaraan"));
        String dusun = cursor.getString(cursor.getColumnIndexOrThrow("dusun"));

        name.setText("NAMA :"+nama);
        kend.setText("Jenis Kendaraan :"+jenis);
        dusunss.setText("Dusun :"+dusun);
    }
}
