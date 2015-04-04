package com.danieljgmaclean.proxodroid;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.danieljgmaclean.proxodroid.contracts.CouchAPI;
import com.danieljgmaclean.proxodroid.events.GetTaskEvent;
import com.danieljgmaclean.proxodroid.events.MyBus;
import com.squareup.otto.Subscribe;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Database.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Database#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Database extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "Database";

    // TODO: Rename and change types of parameters
    private String URI;
    private String dbName;

    private EditText couchdbName;
    private EditText couchdbIP;
    private Button connectDBButton;
    private Button testMessageButton;

    private boolean connectedToDB = false;

    private CouchAPI couchApi;

    private OnFragmentInteractionListener mListener;
    private Activity ctx;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Database.
     */
    // TODO: Rename and change types and number of parameters
    public static Database newInstance(String param1, String param2) {
        Database fragment = new Database();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Database() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            URI = getArguments().getString(ARG_PARAM1);
            dbName = getArguments().getString(ARG_PARAM2);
        }
        MyBus.getInstance().register(this);
        this.ctx = this.getActivity();
    }

    private void wireUI(){
        couchdbIP = (EditText)this.getActivity().findViewById(R.id.couchdbIP);
        couchdbName = (EditText)this.getActivity().findViewById(R.id.couchdbName);

        testMessageButton = (Button)this.getActivity().findViewById(R.id.testMessageButton);
        testMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CouchAPI.postDocument(ctx, URI, dbName, getString(R.string.testMessage));
            }
        });
    }

    private void setDefaultValues(){
        couchdbIP.setText(URI);
        couchdbName.setText(dbName);
        setConnectedUI(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_database, container, false);
    }

    @Override
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);
        wireUI();
        setDefaultValues();
        CouchAPI.connect(this.getActivity(), couchdbIP.getText().toString(), couchdbName.getText().toString());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Subscribe
    public void onGetTaskResult(GetTaskEvent event) {
        Log.i(TAG, "A GetTaskEvent occured");

        if(event.getAction().equals(ctx.getString(R.string.action_connected))){
            setConnectedUI(true);
        }
    }

    private void setConnectedUI(boolean state) {
        testMessageButton.setEnabled(state);
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
