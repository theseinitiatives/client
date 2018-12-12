package tgwofficial.atma.client.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tgwofficial.atma.client.R;
import tgwofficial.atma.client.model.IdentitasModel;

public class IdentitasibuCursorAdapter extends BaseAdapter {

    Context c;
    ArrayList<IdentitasModel> identitasModels;
    LayoutInflater inflater;

    public IdentitasibuCursorAdapter(Context c, ArrayList<IdentitasModel> identitasModels) {
        this.c = c;
        this.identitasModels = identitasModels;
    }

    @Override
    public int getCount() {
        return identitasModels.size();
    }

    @Override
    public Object getItem(int position) {
        return identitasModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null)
        {
            inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.identitas_ibu_content_layout,parent,false);
        }

        TextView nameTxt= (TextView) convertView.findViewById(R.id.name);
        TextView spousename = (TextView) convertView.findViewById(R.id.spousename);
        TextView status = (TextView) convertView.findViewById(R.id.status);
        TextView dusunss = (TextView) convertView.findViewById(R.id.dusun);

        nameTxt.setText("Nama : "+identitasModels.get(position).getNama());
        spousename.setText("Nama Suami : "+identitasModels.get(position).getPasangan());

        status.setText("HTP : "+identitasModels.get(position).getStatus1());
        dusunss.setText( "Dusun : "+identitasModels.get(position).getDusuns());
        if(identitasModels.get(position).getResiko()!=null) {
            if (identitasModels.get(position).getResiko().length() > 2)
                convertView.setBackgroundColor(0xFFFF0000);
        }

        final int pos=position;


        return convertView;
    }
}


