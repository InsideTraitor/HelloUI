package com.droidrocks.demos.helloui.notifications;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hollisinman.helloui.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlertDialogFragment.AlertDialogFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AlertDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 * <p>
 * <p>
 * Usage requires a TYPE and DIALOG_MESSAGE
 */
public class AlertDialogFragment extends DialogFragment {

    private AlertDialogFragmentInteractionListener mListener;
    public static final String TYPE = "type";
    public static final String DIALOG_MESSAGE = "dialogMessage";
    public static final String ALERT_DIALOG_TYPE_EDIT_PROFILE_PICTURE = "editProfilePicture";
    public static final String ALERT_DIALOG_TYPE_LOGOFF = "logOff";
    public static final String ALERT_DIALOG_TYPE_PHONE_CALL = "phoneCall";
    public static final String TAG_ALERT_DIALOG_FRAGMENT = "ALERT_DIALOG";

    public AlertDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AlertDialogFragment.
     */
    public static AlertDialogFragment newInstance(Bundle bundle) {
        AlertDialogFragment fragment = new AlertDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String type = getArguments().getString(TYPE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getArguments().getString(DIALOG_MESSAGE))
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        switch (type) {
                            case ALERT_DIALOG_TYPE_EDIT_PROFILE_PICTURE:
                                mListener.changeProfilePicture();
                                break;
                            case ALERT_DIALOG_TYPE_LOGOFF:
                                mListener.logOff();
                                break;
                            case ALERT_DIALOG_TYPE_PHONE_CALL:
                                mListener.makePhoneCall("867-5309");
                                break;
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        switch (type) {
                            case ALERT_DIALOG_TYPE_EDIT_PROFILE_PICTURE:
                                mListener.cancelChangeProfilePicture();
                                break;
                            case ALERT_DIALOG_TYPE_LOGOFF:
                                mListener.cancelLogOff();
                                break;
                            default:
                                // Do nothing
                                break;
                        }
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alert_dialog, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AlertDialogFragmentInteractionListener) {
            mListener = (AlertDialogFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AlertDialogFragmentInteractionListener");
        }
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface AlertDialogFragmentInteractionListener {
        void logOff();

        void cancelLogOff();

        void changeProfilePicture();

        void cancelChangeProfilePicture();

        void makePhoneCall(String phoneNumber);
    }
}
