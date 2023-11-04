package edu.uga.cs.p4statescapitalsquiz;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an adapter class for the RecyclerView to show all job leads.
 */
public class QuestionRecyclerAdapter
        extends RecyclerView.Adapter<QuestionRecyclerAdapter.QuestionHolder>
        implements Filterable {

    public static final String DEBUG_TAG = "QuestionRecyclerAdapter";

    private final Context context;

    private List<Question> values;
    private List<Question> originalValues;

    public QuestionRecyclerAdapter( Context context, List<Question> questionList ) {
        this.context = context;
        this.values = questionList;
        this.originalValues = new ArrayList<Question>( questionList );
    }

    // reset the originalValues to the current contents of values
    public void sync()
    {
        originalValues = new ArrayList<Question>( values );
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    public static class QuestionHolder extends RecyclerView.ViewHolder {

        TextView stateName;
        TextView capitalCity;
        TextView secondCity;
        TextView thirdCity;

        public QuestionHolder( View itemView ) {
            super( itemView );

            stateName = itemView.findViewById( R.id.stateName );
            capitalCity = itemView.findViewById( R.id.capitalCity );
            secondCity = itemView.findViewById( R.id.secondCity );
            thirdCity = itemView.findViewById( R.id.thirdCity );
        }
    }

    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        // We need to make sure that all CardViews have the same, full width, allowed by the parent view.
        // This is a bit tricky, and we must provide the parent reference (the second param of inflate)
        // and false as the third parameter (don't attach to root).
        // Consequently, the parent view's (the RecyclerView) width will be used (match_parent).
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.question, parent, false );
        return new QuestionHolder( view );
    }

    // This method fills in the values of a holder to show a Question.
    // The position parameter indicates the position on the list of jobs list.
    @Override
    public void onBindViewHolder( QuestionHolder holder, int position ) {

        Question jobLead = values.get( position );

        Log.d( DEBUG_TAG, "onBindViewHolder: " + jobLead );

        holder.stateName.setText( jobLead.getStateName());
        holder.capitalCity.setText( jobLead.getCapitalCity() );
        holder.secondCity.setText( jobLead.getSecondCity() );
        holder.thirdCity.setText( jobLead.getThirdCity() );
    }

    @Override
    public int getItemCount() {
        if( values != null )
            return values.size();
        else
            return 0;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Question> list = new ArrayList<Question>( originalValues );
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0) {
                    filterResults.count = list.size();
                    filterResults.values = list;
                }
                else{
                    List<Question> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for( Question jobLead : list ) {
                        // check if either the company name or the thirdCity contain the search string
                        if( jobLead.getStateName().toLowerCase().contains( searchStr )
                                || jobLead.getThirdCity().toLowerCase().contains( searchStr ) ) {
                            resultsModel.add( jobLead );
                        }
/*
                        // this may be a faster approach with a long list of items to search
                        if( jobLead.getCompanyName().regionMatches( true, i, searchStr, 0, length ) )
                            return true;

 */
                    }

                    filterResults.count = resultsModel.size();
                    filterResults.values = resultsModel;
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                values = (ArrayList<Question>) results.values;
                notifyDataSetChanged();
                if( values.size() == 0 ) {
                    Toast.makeText( context, "Not Found", Toast.LENGTH_LONG).show();
                }
            }

        };
        return filter;
    }
}