
package com.example.serwissamochodowy.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serwissamochodowy.model.Address;
import com.example.serwissamochodowy.R;
import com.example.serwissamochodowy.model.Workshop;
import com.example.serwissamochodowy.dao.WorkshopDao;
import com.example.serwissamochodowy.dao.WorkshopPreferenceDao;

import java.util.List;

public class WorkshopActivity extends AppCompatActivity {

    private WorkshopDao workshopDao;
    private WorkshopPreferenceDao preferenceDao;

    private LinearLayout layoutWorkshops;
    private TextView tvNoWorkshops;

    private int preferredWorkshopId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop);

        workshopDao = new WorkshopDao(this);
        preferenceDao = new WorkshopPreferenceDao(this);

        layoutWorkshops = findViewById(R.id.layoutWorkshops);
        tvNoWorkshops = findViewById(R.id.tvNoWorkshops);

        workshopDao.insertSampleWorkshops();

        loadWorkshops();
    }

    private void loadWorkshops() {

        layoutWorkshops.removeAllViews();

        List<Workshop> workshops = workshopDao.getAllWorkshops();

        preferredWorkshopId = preferenceDao.getPreferredWorkshopId();

        if (workshops.isEmpty()) {
            tvNoWorkshops.setVisibility(View.VISIBLE);
            return;
        }

        tvNoWorkshops.setVisibility(View.GONE);

        for (Workshop workshop : workshops) {
            addWorkshopCard(workshop);
        }
    }

    private void addWorkshopCard(Workshop workshop) {

        View cardView = getLayoutInflater().inflate(
                R.layout.item_workshop,
                layoutWorkshops,
                false
        );

        TextView tvName = cardView.findViewById(R.id.tvWorkshopName);
        TextView tvAddress = cardView.findViewById(R.id.tvWorkshopAddress);
        TextView tvPhone = cardView.findViewById(R.id.tvWorkshopPhone);
        TextView tvOpeningHours = cardView.findViewById(R.id.tvWorkshopOpeningHours);
        TextView tvPreferred = cardView.findViewById(R.id.tvPreferredWorkshop);

        ImageButton btnNavigate = cardView.findViewById(R.id.btnWorkshopNavigate);
        ImageButton btnCall = cardView.findViewById(R.id.btnWorkshopCall);

        tvName.setText(workshop.getName());
        tvAddress.setText(getAddressText(workshop));
        tvPhone.setText(workshop.getPhone());

        tvOpeningHours.setText(
                getString(
                        R.string.workshop_opening_hours,
                        workshop.getOpeningHours()
                )
        );

        if (workshop.getId() == preferredWorkshopId) {
            tvPreferred.setVisibility(View.VISIBLE);
        } else {
            tvPreferred.setVisibility(View.GONE);
        }

        btnNavigate.setOnClickListener(v -> navigateToWorkshop(workshop));

        btnCall.setOnClickListener(v -> callWorkshop(workshop));

        cardView.setOnClickListener(v -> selectPreferredWorkshop(workshop));

        layoutWorkshops.addView(cardView);
    }

    private String getAddressText(Workshop workshop) {

        Address address = workshop.getAddress();

        if (address == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        appendAddressPart(result, address.getStreet());
        appendAddressPart(result, address.getHouseNumber());

        if (address.getFlatNumber() != null
                && !address.getFlatNumber().trim().isEmpty()) {
            result.append("/")
                    .append(address.getFlatNumber());
        }

        appendAddressPart(result, address.getPostalCode());
        appendAddressPart(result, address.getCity());
        appendAddressPart(result, address.getCountry());

        return result.toString();
    }

    private void appendAddressPart(StringBuilder builder, String value) {

        if (value == null || value.trim().isEmpty()) {
            return;
        }

        if (builder.length() > 0) {
            builder.append(" ");
        }

        builder.append(value);
    }


    private void navigateToWorkshop(Workshop workshop) {

        String address = getAddressText(workshop);

        if (address.isEmpty()) {
            Toast.makeText(
                    this,
                    R.string.workshop_address_missing,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        Uri uri = Uri.parse(
                "https://www.google.com/maps/dir/?api=1&destination="
                        + Uri.encode(address)
                        + "&travelmode=driving"
        );

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(
                    this,
                    "Nie można otworzyć nawigacji.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }


    private void callWorkshop(Workshop workshop) {

        String phone = workshop.getPhone();

        if (phone == null || phone.trim().isEmpty()) {
            Toast.makeText(
                    this,
                    R.string.workshop_phone_missing,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        Intent intent = new Intent(
                Intent.ACTION_DIAL,
                Uri.parse("tel:" + Uri.encode(phone))
        );

        startActivity(intent);
    }

    private void selectPreferredWorkshop(Workshop workshop) {

        preferenceDao.setPreferredWorkshop(workshop.getId());

        preferredWorkshopId = workshop.getId();

        Toast.makeText(
                this,
                getString(
                        R.string.preferred_workshop_selected,
                        workshop.getName()
                ),
                Toast.LENGTH_SHORT
        ).show();

        loadWorkshops();
    }
}
