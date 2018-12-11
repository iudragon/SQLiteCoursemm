package dragon.bakuman.iu.sqlitecourse;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


import de.hdodenhof.circleimageview.CircleImageView;
import dragon.bakuman.iu.sqlitecourse.models.Contact;
import dragon.bakuman.iu.sqlitecourse.utils.UniversalImageLoader;

public class EditContactFragment extends Fragment {

    private static final String TAG = "EditContactFragment";

    public EditContactFragment() {
        super();
        setArguments(new Bundle());
    }

    private Contact mContact;
    private EditText mPhoneNumber, mName, mEmail;
    private CircleImageView mContactImage;
    private Spinner mSelectDevice;
    private Toolbar toolbar;
    private String mSelectedImagePath;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editcontact, container, false);

        mPhoneNumber = view.findViewById(R.id.etContactPhone);
        mName = view.findViewById(R.id.etContactName);
        mEmail = view.findViewById(R.id.etContactEmail);
        mContactImage = view.findViewById(R.id.contactImageCIV);
        mSelectDevice = view.findViewById(R.id.selectDevice);
        toolbar = view.findViewById(R.id.editcontacttoolbar);


        Log.d(TAG, "onCreateView: started.");
        //get the contact from the bundle
        mContact = getContactFromBundle();
        if (mContact != null){
            init();
        }
        return view;
    }

    private void init() {

        mPhoneNumber.setText(mContact.getPhonenumber());
            mName.setText(mContact.getName());
            mEmail.setText(mContact.getEmail());
        UniversalImageLoader.setImage(mContact.getProfileImage(), mContactImage, null, "http://");

        //setting the selected device from the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.device_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSelectDevice.setAdapter(adapter);
        int position = adapter.getPosition(mContact.getDevice());
        mSelectDevice.setSelection(position);
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
}
