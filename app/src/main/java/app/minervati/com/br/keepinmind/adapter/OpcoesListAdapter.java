package app.minervati.com.br.keepinmind.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.minervati.com.br.keepinmind.R;

/**
 * Created by victorminerva on 17/08/2016.
 */
public class OpcoesListAdapter extends ArrayAdapter<String> {

    private final Activity      context;
    private final List<String>  itemname;
    private final List<Integer> itemimage;

    private TextView    mTvOption;
    private ImageView   mIvOption;

    public OpcoesListAdapter(Activity context, List<String> itemname, List<Integer> itemimage) {
        super(context, R.layout.list_options, itemname);
        this.context = context;
        this.itemname = itemname;
        this.itemimage = itemimage;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_options, null, true);

        mTvOption = (TextView) rowView.findViewById(R.id.item_option);
        mTvOption.setText(itemname.get(position));

        mIvOption = (ImageView) rowView.findViewById(R.id.img_option);
        mIvOption.setImageResource(itemimage.get(position));

        return rowView;
    };

}