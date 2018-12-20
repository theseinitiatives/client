package tgwofficial.atma.client.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import static tgwofficial.atma.client.Utils.StringUtil.humanizes;


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

        ImageView fotoIbu = (ImageView) convertView.findViewById(R.id.foto_ibu);

        LinearLayout lin = (LinearLayout) convertView.findViewById(R.id.layout_data);
        nameTxt.setText("Nama : "+ humanizes(identitasModels.get(position).getNama()));
        spousename.setText("Nama Suami : "+humanizes(identitasModels.get(position).getPasangan()));
        status.setText("HTP : "+humanizes(identitasModels.get(position).getStatus1()));
        dusunss.setText( "Dusun : "+humanizes(identitasModels.get(position).getDusuns()));

        fotoIbu.setImageResource(
                identitasModels.get(position).getHaveBirth() ?
                R.drawable.icon_mother48 : R.drawable.icon_pregnant48
        );

        if(identitasModels.get(position).getResiko()!=null) {
            if (identitasModels.get(position).getResiko().length() > 2)
               // convertView.setBackgroundColor(0xFFFF0000);
                lin.setBackgroundColor(Color.MAGENTA);
        }

        final int pos=position;


        return convertView;
    }

}


