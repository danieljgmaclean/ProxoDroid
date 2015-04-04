package com.danieljgmaclean.proxodroid;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.danieljgmaclean.proxodroid.contracts.CouchAPI;
import com.danieljgmaclean.proxodroid.events.EstimotesFoundEvent;
import com.danieljgmaclean.proxodroid.events.GetTaskEvent;
import com.danieljgmaclean.proxodroid.events.MyBus;
import com.danieljgmaclean.proxodroid.utils.Utils;
import com.estimote.sdk.Beacon;
import com.squareup.otto.Subscribe;

import java.util.List;

import static com.danieljgmaclean.proxodroid.R.string.action_connected;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Estimote.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Estimote#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Estimote extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "Estimote";

    private OnFragmentInteractionListener mListener;
    private Context ctx;

    private List<Beacon> beacons;
    private int numberOfEstimotes = 0;

    private TextView estimotesCountTextView;
    private Switch welcomeMessageSwitch;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment estimote.
     */
    // TODO: Rename and change types and number of parameters
    public static Estimote newInstance() {
        Estimote fragment = new Estimote();

        return fragment;
    }

    public Estimote() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyBus.getInstance().register(this);
        this.ctx = this.getActivity();
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        wireUI();
    }

    private void wireUI() {
        estimotesCountTextView = (TextView)this.getActivity().findViewById(R.id.numberOfEstimotes);
        estimotesCountTextView.setText("" + numberOfEstimotes);

        welcomeMessageSwitch = (Switch)this.getActivity().findViewById(R.id.welcomeMessageSwitch);
    }

    @Subscribe
    public void onEstimotesFoundResult(EstimotesFoundEvent event) {
        Log.i(TAG, "A EstimotesFoundEvent occured");

        if(event.getAction().equals(ctx.getString(R.string.action_estimotes_found))){
            Log.i(TAG, "Number of estimotes in range: " + event.getBeacons().size());

            numberOfEstimotes = event.getBeacons().size();

            if(beacons != event.getBeacons()) {
                Log.i(TAG, "List of beacons has changed");
                beacons = event.getBeacons();
                //printBeacons(beacons);
                updateUI();

                if(beacons.size() > 0) {
                    if (welcomeMessageSwitch.isChecked() && beacons.get(0).getMacAddress().equals("DB:01:10:94:85:4C")) {
                        CouchAPI.postDocument(ctx, Utils.getCouchDBIP(), Utils.getCouchDBName(), "Welcome to the office Daniel");
                    }
                }
            }else{
                Log.i(TAG, "List of beacons is the same");
            }
        }
    }

    private void updateUI() {
        estimotesCountTextView.setText("" + numberOfEstimotes);
    }

    private void printBeacons(List<Beacon> beacons){
        Beacon beacon;
        for(int i=0;i<beacons.size();i++){
            beacon = beacons.get(i);
            Log.i(TAG, "BEACON " + i);
            Log.i(TAG, "Name: " + beacon.getName());
            Log.i(TAG, "Mac Address: " + beacon.getMacAddress());
            Log.i(TAG, "Proximity UUID: " + beacon.getProximityUUID());
            Log.i(TAG, "Major: " + beacon.getMajor());
            Log.i(TAG, "Measured Power: " + beacon.getMeasuredPower());
            Log.i(TAG, "Minor: " + beacon.getMinor());
            Log.i(TAG, "RSSI: " + beacon.getRssi());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estimote, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
