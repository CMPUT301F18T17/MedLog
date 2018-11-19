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
import cs.ualberta.ca.medlog.entity.Problem;

/**
 * <p>
 *     Description: <br>
 *         Custom Array Adapter for presenting one with Text Views based on provided Problem results.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.0
 */
public class SearchAdapter extends ArrayAdapter<Problem> {
    public SearchAdapter(Context context, ArrayList<Problem> problemsList) {
        super(context,0,problemsList);
    }

    @Override
    public @NonNull
    View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_list_item,parent,false);
        }

        TextView textView = convertView.findViewById(R.id.fragmentListItem_TextView);
        String resultText = "SEARCH RESULT: " +Integer.toString(position);
        textView.setText(resultText);

        return convertView;
    }
}
