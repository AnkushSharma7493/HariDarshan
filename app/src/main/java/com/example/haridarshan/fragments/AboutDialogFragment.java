package com.example.haridarshan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.haridarshan.R;

public class AboutDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_about, container, false);

        TextView title = view.findViewById(R.id.about_title);
        TextView description = view.findViewById(R.id.about_description);
        Button closeButton = view.findViewById(R.id.close_button);

        title.setText("About Hari Darshan");
        description.setText("This app provides spiritual content for daily inspiration.");
        description.append("This app provides spiritual content for daily inspiration.");
        description.append("This app provides spiritual content for daily inspiration.");
        description.append("This app provides spiritual content for daily inspiration.");
        description.append("This app provides spiritual content for daily inspiration.");


        closeButton.setOnClickListener(v -> dismiss());

        return view;
    }
}

