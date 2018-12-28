package theseinitiatives.atma.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import theseinitiatives.atma.client.R;
import theseinitiatives.atma.client.model.KaderViewModel;
import theseinitiatives.atma.client.model.TransportasiModel;

import static theseinitiatives.atma.client.Utils.StringUtil.humanizes;

public class KaderCursorAdapter extends BaseAdapter {

    Context c;
    ArrayList<KaderViewModel> kaderViewModels;
    LayoutInflater inflater;

    public KaderCursorAdapter(Context c, ArrayList<KaderViewModel> kaderViewModels) {
        this.c = c;
        this.kaderViewModels = kaderViewModels;
    }

    @Override
    public int getCount() {
        return kaderViewModels.size();
    }

    @Override
    public Object getItem(int position) {
        return kaderViewModels.get(position);
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
            convertView=inflater.inflate(R.layout.kader_content_layout,parent,false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView dusunss = (TextView) convertView.findViewById(R.id.dusuns);
        TextView username = (TextView) convertView.findViewById(R.id.username);
        TextView pass = (TextView) convertView.findViewById(R.id.passw);

        name.setText("Nama: "+humanizes(kaderViewModels.get(position).getNama()));
        dusunss.setText("Dusun: "+humanizes(kaderViewModels.get(position).getDusuns()));
        username.setText("Username: "+humanizes(kaderViewModels.get(position).getUsername()));
        pass.setText("Password: "+humanizes(kaderViewModels.get(position).getPassword()));


        final int pos=position;


        return convertView;
    }
}
