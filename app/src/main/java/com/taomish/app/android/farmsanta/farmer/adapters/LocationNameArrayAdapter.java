package com.taomish.app.android.farmsanta.farmer.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.taomish.app.android.farmsanta.farmer.BuildConfig;
import com.taomish.app.android.farmsanta.farmer.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LocationNameArrayAdapter extends ArrayAdapter<LocationNameArrayAdapter.LocationAutocomplete> implements Filterable {

    private static final String TAG             = "skt";
    private final PlacesClient placesClient;
    private final RectangularBounds mBounds;
    private ArrayList<LocationAutocomplete> mResultList = new ArrayList<>();
    private final Context context;

    /**
     * Constructor
     *
     * @param context  Context
     * @param resource Layout resource
     * @param bounds   Used to specify the search bounds
     */
    public LocationNameArrayAdapter(Context context, int resource, RectangularBounds bounds) {
        super(context, resource);
        this.context                            = context;
        mBounds                                 = bounds;
        Places.initialize(context, BuildConfig.MAPS_API_KEY);
        placesClient                            = com.google.android.libraries.places.api.Places.createClient(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view                               = super.getView(position, convertView, parent);
        if (view instanceof TextView) {
            Typeface typeface                   = Typeface.createFromAsset(context.getAssets(), "fonts/DINPro_Bold.otf");
            ((TextView) view).setTypeface(typeface);
        }

        //view.setOnClickListener(v -> onItemClick(position));
        return view;
    }

    @Override
    public int getCount() {
        if (mResultList == null)
            return 0;
        else
            return mResultList.size();
    }

    @Override
    public LocationAutocomplete getItem(int position) {
        return mResultList.get(position);
    }

    private ArrayList<LocationAutocomplete> getPredictions(CharSequence constraint) {

        final ArrayList<LocationAutocomplete> resultList = new ArrayList<>();

        // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
        // and once again when the user makes a selection (for example when calling fetchPlace()).
        AutocompleteSessionToken token          = AutocompleteSessionToken.newInstance();


        // Use the builder to create a FindAutocompletePredictionsRequest.
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                // Call either setLocationBias() OR setLocationRestriction().
                // .setLocationBias(bounds)
                // .setLocationBias(mBounds)
                //.setCountry("au")
                //   .setTypeFilter(TypeFilter.ADDRESS)
                .setSessionToken(token)
                .setQuery(constraint.toString())
                .build();

        Task<FindAutocompletePredictionsResponse> autocompletePredictions = placesClient.findAutocompletePredictions(request);

        // This method should have been called off the main UI thread. Block and wait for at most
        // 60s for a result from the API.
        try {
            Tasks.await(autocompletePredictions, 60, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }

        if (autocompletePredictions.isSuccessful()) {
            FindAutocompletePredictionsResponse findAutocompletePredictionsResponse = autocompletePredictions.getResult();
            if (findAutocompletePredictionsResponse != null)
                for (com.google.android.libraries.places.api.model.AutocompletePrediction prediction : findAutocompletePredictionsResponse.getAutocompletePredictions()) {
                    Log.i(TAG, prediction.getPlaceId());
                    Log.i(TAG, prediction.getPrimaryText(null).toString());

                    resultList.add(new LocationAutocomplete(prediction.getPlaceId(), prediction.getFullText(null).toString()));

                }

        }
        return resultList;

    }

    @Override
    public Filter getFilter() {
        Filter filter                           = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint != null) {
                    // Query the autocomplete API for the entered constraint
                    Log.d(TAG, "Before Prediction");
                    mResultList                 = getPredictions(constraint);
                    Log.d(TAG, "After Prediction");
                    if (mResultList != null) {
                        // Results
                        results.values          = mResultList;
                        results.count           = mResultList.size();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    // The API returned at least one result, update the data.
                    notifyDataSetChanged();
                } else {
                    // The API did not return any results, invalidate the data set.
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    public static class LocationAutocomplete {

        public CharSequence placeId;
        public CharSequence description;

        LocationAutocomplete(CharSequence placeId, CharSequence description) {
            this.placeId                        = placeId;
            this.description                    = description;
        }

        @NonNull
        @Override
        public String toString() {
            return description.toString();
        }
    }
}
