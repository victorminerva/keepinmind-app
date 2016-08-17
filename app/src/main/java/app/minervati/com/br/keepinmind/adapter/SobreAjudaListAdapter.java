package app.minervati.com.br.keepinmind.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.util.KeepUtil;

/**
 * Created by victorminerva on 17/08/2016.
 */
public class SobreAjudaListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> itemname;
    private final List<String> subTitleItem;


    public SobreAjudaListAdapter(Activity context, List<String> itemname, List<String> subTitleItem) {
        super(context, R.layout.list_sobre_ajuda, itemname);
        this.context = context;
        this.itemname = itemname;
        this.subTitleItem = subTitleItem;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_sobre_ajuda, null, true);

        TextView txtTitle       = (TextView) rowView.findViewById(R.id.itemName);
        TextView txtSubtitle    = (TextView) rowView.findViewById(R.id.itemSubtitle);

        txtTitle.setText(itemname.get(position));
        if (!"".equals(subTitleItem.get(position))) {
            txtSubtitle.setText(subTitleItem.get(position));
        }
        return rowView;
    };

}