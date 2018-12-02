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
import cs.ualberta.ca.medlog.entity.SearchResult;

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
 * @version 1.1
 */
public class SearchAdapter extends ArrayAdapter<SearchResult> {
    public SearchAdapter(Context context, ArrayList<SearchResult> searchResultsList) {
        super(context,0,searchResultsList);
    }

    @Override
    public @NonNull
    View getView(int position, View convertView, @NonNull ViewGroup parent) {
        SearchResult searchResult = getItem(position);

        if(convertView == null) {
            if (searchResult.getRecord() == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_problem_search_result, parent, false);
            }
            else {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_record_search_result, parent, false);
            }
        }

        if (searchResult.getRecord() == null) {
            TextView usernameView = convertView.findViewById(R.id.fragmentProblemSearchResult_OwnerTextView);
            usernameView.setText(searchResult.getPatient().getUsername());
            TextView problemView = convertView.findViewById(R.id.fragmentProblemSearchResult_ProblemTextView);
            problemView.setText(searchResult.getProblem().toString());
        }
        else {
            TextView usernameView = convertView.findViewById(R.id.fragmentRecordSearchResult_OwnerTextView);
            usernameView.setText(searchResult.getPatient().getUsername());
            TextView problemView = convertView.findViewById(R.id.fragmentRecordSearchResult_ProblemTextView);
            problemView.setText(searchResult.getProblem().toString());
            TextView recordView = convertView.findViewById(R.id.fragmentRecordSearchResult_RecordTextView);
            recordView.setText(searchResult.getRecord().getTimestamp().toString());
        }
        return convertView;
    }
}
