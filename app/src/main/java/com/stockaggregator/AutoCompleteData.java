package com.stockaggregator;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.Filter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.stockaggregator.Constant.*;

/**
 * Retrieve data to populate in autoComplete View
 * With CACHES
 * Created by ducho on 4/9/16.
 */
public class AutoCompleteData extends ArrayAdapter<String> implements Filterable{
    private Map<String, List<String>> symbolCaches = new HashMap<>(); //caches of query symbol

    //private final GetAutoDataTask getInputAsync = new GetAutoDataTask();

    public AutoCompleteData( Context context, int resource ) {
        super( context, resource );
    }

    private String formatJsonData( JSONObject json ) throws JSONException{
        return json.get(SYMBOL).toString() + "-" + json.get(NAME) + "-" + json.get(EXCHANGE);

    }

    private List<String> autocomplete( final String input )  {
        //check in caches
        if ( symbolCaches.containsKey(input) ){
            return symbolCaches.get( input );
        }
        //not in cache
        final List<String> list = new ArrayList<>();
        try {
            new StockJsonTask(){
                @Override
                protected void onPostExecute(JSONArray jsonArray) {
                    for( int i = 0 ; i < jsonArray.length(); i++ ) {
                        try {
                            list.add( formatJsonData( jsonArray.getJSONObject( i ) ) );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    symbolCaches.put( input, list );
                }
            }.execute( new URL( SERVER_URL + "?input=" + input)).get();//sitting in a different thread
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return list;
    }

    // connect to server and populate item
    // save item to caches
    /**
     * AutocompleteTextView calls this method to get the result
     * @return a filter list
     */

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // Retrieve the autocomplete results.
                    List<String> resultList = autocomplete(constraint.toString());
                    System.out.println( "in filter" );
                    for( String s : resultList )
                            System.out.println( s );
                    // Assign the data to the FilterResults
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                    System.out.println("size:" + filterResults.count);
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }

    //render the view
}
