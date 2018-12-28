package theseinitiatives.atma.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import theseinitiatives.atma.client.R;
import theseinitiatives.atma.client.model.BankDarahmodel;

import static theseinitiatives.atma.client.Utils.StringUtil.humanizes;

public class BankDarahCursorAdapter extends BaseAdapter {

    Context c;
    ArrayList<BankDarahmodel> bankDarahmodels;
    LayoutInflater inflater;

    public BankDarahCursorAdapter(Context c, ArrayList<BankDarahmodel> bankDarahmodels) {
        this.c = c;
        this.bankDarahmodels = bankDarahmodels;
    }

    @Override
    public int getCount() {
        return bankDarahmodels.size();
    }

    @Override
    public Object getItem(int position) {
        return bankDarahmodels.get(position);
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
            convertView=inflater.inflate(R.layout.bankdarah_content_layout,parent,false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView gold = (TextView) convertView.findViewById(R.id.gol_d);
        TextView telp = (TextView) convertView.findViewById(R.id.notelp);
        TextView dusun = (TextView) convertView.findViewById(R.id.bank_darah_dusun);

        name.setText("Nama: "+humanizes( bankDarahmodels.get(position).getNama()));
        gold.setText("Gol Darah: "+humanizes(bankDarahmodels.get(position).getGolds()));
        telp.setText(humanizes("No HP: "+humanizes( bankDarahmodels.get(position).getNomor())));
        dusun.setText(humanizes("Alamat: "+bankDarahmodels.get(position).getDusun()));
      //  nama_donor.setText(bankDarahmodels.get(position).getPendonor());

        final int pos=position;


        return convertView;
    }
}
