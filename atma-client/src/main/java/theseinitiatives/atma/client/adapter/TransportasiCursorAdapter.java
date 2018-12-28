package theseinitiatives.atma.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import theseinitiatives.atma.client.R;
import theseinitiatives.atma.client.model.TransportasiModel;

import static theseinitiatives.atma.client.Utils.StringUtil.humanizes;

public class TransportasiCursorAdapter extends BaseAdapter {

    Context c;
    ArrayList<TransportasiModel> transportasiModels;
    LayoutInflater inflater;

    public TransportasiCursorAdapter(Context c, ArrayList<TransportasiModel> transportasiModels) {
        this.c = c;
        this.transportasiModels = transportasiModels;
    }

    @Override
    public int getCount() {
        return transportasiModels.size();
    }

    @Override
    public Object getItem(int position) {
        return transportasiModels.get(position);
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
            convertView=inflater.inflate(R.layout.transportasi_content_layout,parent,false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView dusunss = (TextView) convertView.findViewById(R.id.dusuns);
        TextView kend = (TextView) convertView.findViewById(R.id.kendaraan);

        name.setText("Nama: "+humanizes(transportasiModels.get(position).getNama()));
        dusunss.setText("Dusun: "+humanizes(transportasiModels.get(position).getDusuns()));
        kend.setText("Jenis Kendaraan: "+humanizes(transportasiModels.get(position).getKendaraan()));


        final int pos=position;


        return convertView;
    }
}
/*extends CursorAdapter {
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

        name.setText("NAMA:"+nama);
        kend.setText("Jenis Kendaraan:"+jenis);
        dusunss.setText("Dusun:"+dusun);
    }
}
*/