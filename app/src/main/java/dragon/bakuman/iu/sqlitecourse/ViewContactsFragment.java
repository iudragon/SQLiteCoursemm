package dragon.bakuman.iu.sqlitecourse;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import dragon.bakuman.iu.sqlitecourse.models.Contact;
import dragon.bakuman.iu.sqlitecourse.utils.ContactListAdapter;

public class ViewContactsFragment extends Fragment {

    private static final String TAG = "ViewContactsFragment";
    private String testImageUrl = "cnet4.cbsistatic.com/img/QJcTT2ab-sYWwOGrxJc0MXSt3UI=/2011/10/27/a66dfbb7-fdc7-11e2-8c7c-d4ae52e62bcc/android-wallpaper5_2560x1600_1.jpg";

public interface OnContactedSelectedListener{

    public void OnContactSelected(Contact con);

    }

    OnContactedSelectedListener mContactListener;

    private static final int STANDARD_APPBAR = 0;
    private static final int SEARCH_APPBAR = 1;
    private int mAppBarState;
    private AppBarLayout viewContactsBar, searchBar;
    private ListView contactsList;
    private ContactListAdapter adapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewcontacts, container, false);
        viewContactsBar = view.findViewById(R.id.viewContactsToolbar);
        searchBar = view.findViewById(R.id.searchToolbar);
        contactsList = view.findViewById(R.id.contactsList);

        Log.d(TAG, "onCreateView: started");
        setAppBarState(STANDARD_APPBAR);
        setupContactsList();

        //navigate to add contacts fragment
        FloatingActionButton fab = view.findViewById(R.id.fabAddContact);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked fab.");

            }
        });

        ImageView ivSearchContact = view.findViewById(R.id.ivSearchIcon);
        ivSearchContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked search icon");
                toggleToolbarState();
            }
        });

        ImageView ivBackArrow = view.findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked back arrow");
                toggleToolbarState();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mContactListener = (OnContactedSelectedListener) getActivity();
        } catch (ClassCastException e){
            Log.d(TAG, "onAttach: classcastexception " + e.getMessage());

        }
    }

    private void setupContactsList() {

        final ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("G-Dragon", "77774444411", "Home", "iud@gmail.com", testImageUrl));
        contacts.add(new Contact("G-Dragon", "77774444411", "Home", "iud@gmail.com", testImageUrl));
        contacts.add(new Contact("G-Dragon", "77774444411", "Home", "iud@gmail.com", testImageUrl));
        contacts.add(new Contact("G-Dragon", "77774444411", "Home", "iud@gmail.com", testImageUrl));
        contacts.add(new Contact("G-Dragon", "77774444411", "Home", "iud@gmail.com", testImageUrl));
        contacts.add(new Contact("G-Dragon", "77774444411", "Home", "iud@gmail.com", testImageUrl));
        contacts.add(new Contact("G-Dragon", "77774444411", "Home", "iud@gmail.com", testImageUrl));
        contacts.add(new Contact("G-Dragon", "77774444411", "Home", "iud@gmail.com", testImageUrl));
        contacts.add(new Contact("G-Dragon", "77774444411", "Home", "iud@gmail.com", testImageUrl));
        contacts.add(new Contact("G-Dragon", "77774444411", "Home", "iud@gmail.com", testImageUrl));

        adapter = new ContactListAdapter(getActivity(), R.layout.layout_contactslistitem, contacts, "https://");
        contactsList.setAdapter(adapter);

        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onClick: navigating to navigating fragment");

                //pass the contact to the interface and send it to mainactivity
                mContactListener.OnContactSelected(contacts.get(position));

            }
        });
    }

    /**
     * initiates the app bar state toggle
     */

    private void toggleToolbarState() {
        Log.d(TAG, "toggleToolbarState: toggling app bar state");

        if (mAppBarState == STANDARD_APPBAR) {
            setAppBarState(SEARCH_APPBAR);
        } else {
            setAppBarState(STANDARD_APPBAR);

        }
    }

    /**
     * sets the app bar state for either the search mode or standard
     *
     * @param state
     */

    private void setAppBarState(int state) {
        Log.d(TAG, "setAppBarState: changind app bar state to: " + state);
        mAppBarState = state;
        if (mAppBarState == STANDARD_APPBAR) {
            searchBar.setVisibility(View.GONE);
            viewContactsBar.setVisibility(View.VISIBLE);

            //hide the keyboard
            View view = getView();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            try {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            } catch (NullPointerException e) {

                Log.d(TAG, "setAppBarState: NUll Pointer Exception " + e.getMessage());


            }

        } else if (mAppBarState == SEARCH_APPBAR) {
            viewContactsBar.setVisibility(View.GONE);
            searchBar.setVisibility(View.VISIBLE);

            //open the keyboard
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        setAppBarState(STANDARD_APPBAR);
    }
}
