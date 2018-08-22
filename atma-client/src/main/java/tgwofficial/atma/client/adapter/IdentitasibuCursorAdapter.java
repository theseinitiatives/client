package tgwofficial.atma.client.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import tgwofficial.atma.client.R;

public class IdentitasibuCursorAdapter extends CursorAdapter {
    public IdentitasibuCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.identitas_ibu_content_layout, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView spousename = (TextView) view.findViewById(R.id.spousename);
        TextView status = (TextView) view.findViewById(R.id.status);


        String datas = cursor.getString(cursor.getColumnIndexOrThrow("data"));
        String[] datasList = datas.split(",");

        String name_split = datasList[0];
        String spouse_split = datasList[1];
        String status_split = datasList[2];
        
        name.setText("NAMA :"+name_split);
        spousename.setText("NAMA PASANGAN :"+spouse_split);
        status.setText("STATUS:"+status_split);
    }
}
