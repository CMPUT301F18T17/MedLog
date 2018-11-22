package cs.ualberta.ca.medlog.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.Record;

/**
 * <p>
 *     Description: <br>
 *         Custom Array Adapter for presenting one with Text Views based on provided Records.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.0
 * @see Record
 */
public class RecordAdapter extends ArrayAdapter<Record> {
    public RecordAdapter(Context context, ArrayList<Record> recordList) {
        super(context,0,recordList);
    }

    @Override
    public @NonNull
    View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_list_item,parent,false);
        }

        Record record = getItem(position);
        TextView textView = convertView.findViewById(R.id.fragmentListItem_TextView);
        textView.setText(record.getTimestamp().toString());

        return convertView;
    }
}
