package dragon.bakuman.iu.sqlitecourse;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import dragon.bakuman.iu.sqlitecourse.models.Contact;
import dragon.bakuman.iu.sqlitecourse.utils.ContactPropertyListAdapter;
import dragon.bakuman.iu.sqlitecourse.utils.UniversalImageLoader;

public class ContactFragment extends Fragment {

    private static final String TAG = "ContactFragment";

    public interface OnEditContactListener {

        public void onEditContactSelected(Contact contact);
    }

    OnEditContactListener mOnEditContactListener;

    //this will evade the nullpointerexception when adding to a new Bundle from a mainactivity
    public ContactFragment() {
        super();
        setArguments(new Bundle());
    }

    private Toolbar toolbar;
    private Contact mContact;
    private TextView mContactName;
    private CircleImageView mContactImage;
    private ListView mListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        toolbar = view.findViewById(R.id.contacttoolbar);
        mContactName = view.findViewById(R.id.tvName);
        mContactImage = view.findViewById(R.id.contactImage);
        mListView = view.findViewById(R.id.lvContactProperties);

        Log.d(TAG, "onCreateView: started.");
        mContact = getContactFromBundle();
       /* if (mContact != null){
            Log.d(TAG, "onCreateView: received contact: " + mContact.getName());
        }*/
        //required for setting up the toolbar

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        init();

        //navigation for the back arrow
        ImageView ivBackArrow = view.findViewById(R.id.ivBackarrowC);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked back arrow");

                //remove pervious fragment from the backstack therefore navigating back
                getActivity().getSupportFragmentManager().popBackStack();

            }
        });

        //navigate to the edit contact fragment to edit the selected fragment

        ImageView ivEdit = view.findViewById(R.id.ivEdit);
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked the edit icon");

                mOnEditContactListener.onEditContactSelected(mContact);


            }
        });


        return view;
    }

    private void init() {
        mContactName.setText(mContact.getName());
        UniversalImageLoader.setImage(mContact.getProfileImage(), mContactImage, null, "http://");
        ArrayList<String> properties = new ArrayList<>();
        properties.add(mContact.getPhonenumber());
        properties.add(mContact.getEmail());
        ContactPropertyListAdapter adapter = new ContactPropertyListAdapter(getActivity(), R.layout.layout_cardview, properties);
        mListView.setAdapter(adapter);
        mListView.setDivider(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.contact_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menuitem_delete:
                Log.d(TAG, "onOptionsItemSelected: deleting contact");
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *Retrieves the selected contact from the bundle coming from MainActivity
     *
     * @return
     */

    private Contact getContactFromBundle() {
        Log.d(TAG, "getContactFromBundle: arguments: " + getArguments());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            return bundle.getParcelable(getString(R.string.contact));
        } else {
            return null;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {


        } catch (ClassCastException e) {

            mOnEditContactListener = (OnEditContactListener) getActivity();

            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());


        }
    }
}
