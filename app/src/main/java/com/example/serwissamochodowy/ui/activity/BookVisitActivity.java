
package com.example.serwissamochodowy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serwissamochodowy.R;
import com.example.serwissamochodowy.model.ServiceCategory;

import java.util.ArrayList;
import java.util.List;

public class BookVisitActivity extends AppCompatActivity {

    private static final String EXTRA_CATEGORY = "category";
    private static final String EXTRA_SUBCATEGORY = "subcategory";
    private static final String EXTRA_SERVICE_NAME = "serviceName";
    private static final String EXTRA_PROBLEM_DESCRIPTION = "problemDescription";

    private Spinner spinnerCategory;
    private Spinner spinnerSubcategory;

    private EditText etServiceName;
    private EditText etProblemDescription;

    private Button btnNext;

    private ServiceCategory[] categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_visit);

        initializeViews();
        setupCategorySpinner();
        setupButton();
    }

    private void initializeViews() {
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerSubcategory = findViewById(R.id.spinnerSubcategory);

        etServiceName = findViewById(R.id.etServiceName);
        etProblemDescription = findViewById(R.id.etProblemDescription);

        btnNext = findViewById(R.id.btnNext);
    }

    private void setupCategorySpinner() {

        categories = ServiceCategory.values();

        List<String> categoryNames = new ArrayList<>();

        categoryNames.add(getString(R.string.book_visit_no_selection));

        for (ServiceCategory category : categories) {
            categoryNames.add(getString(category.getNameResId()));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categoryNames
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerCategory.setAdapter(adapter);

        spinnerCategory.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        if (position == 0) {
                            setupEmptySubcategorySpinner();
                        } else {
                            ServiceCategory selectedCategory =
                                    categories[position - 1];

                            updateSubcategorySpinner(selectedCategory);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );
    }

    private void setupEmptySubcategorySpinner() {

        List<String> subcategoryNames = new ArrayList<>();

        subcategoryNames.add(getString(R.string.book_visit_no_selection));

        setSubcategoryAdapter(subcategoryNames);

        spinnerSubcategory.setEnabled(false);
        spinnerSubcategory.setAlpha(0.5f);

        etServiceName.setText("");
    }

    private void updateSubcategorySpinner(ServiceCategory category) {

        // Zmiana kategorii oznacza rozpoczęcie wyboru usługi od nowa.
        etServiceName.setText("");

        spinnerSubcategory.setEnabled(true);
        spinnerSubcategory.setAlpha(1.0f);

        List<String> subcategoryNames = new ArrayList<>();

        subcategoryNames.add(getString(R.string.book_visit_no_selection));

        int[] subcategoryIds = category.getSubcategories();

        for (int id : subcategoryIds) {
            subcategoryNames.add(getString(id));
        }

        setSubcategoryAdapter(subcategoryNames);

        spinnerSubcategory.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        if (position == 0) {
                            etServiceName.setText("");
                            return;
                        }

                        int selectedResourceId = subcategoryIds[position - 1];

                        if (isOtherSubcategory(selectedResourceId)) {
                            etServiceName.setText("");
                        } else {
                            etServiceName.setText(
                                    getString(selectedResourceId)
                            );
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );
    }

    private boolean isOtherSubcategory(int resourceId) {

        return resourceId == R.string.sub_other_periodic
                || resourceId == R.string.sub_other_diagnostics
                || resourceId == R.string.sub_other_vulcanization
                || resourceId == R.string.sub_other_repair
                || resourceId == R.string.sub_other_service;
    }

    private void setSubcategoryAdapter(List<String> subcategoryNames) {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                subcategoryNames
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerSubcategory.setAdapter(adapter);
    }

    private void setupButton() {
        btnNext.setOnClickListener(v -> validateForm());
    }

    private void validateForm() {

        String serviceName = etServiceName.getText().toString().trim();
        String problemDescription =
                etProblemDescription.getText().toString().trim();

        etServiceName.setError(null);
        etProblemDescription.setError(null);

        if (serviceName.isEmpty()) {
            etServiceName.setError(
                    getString(R.string.book_visit_service_name_error)
            );
            etServiceName.requestFocus();
            return;
        }

        if (problemDescription.isEmpty()) {
            etProblemDescription.setError(
                    getString(R.string.book_visit_problem_error)
            );
            etProblemDescription.requestFocus();
            return;
        }

        goToDetailsActivity(serviceName, problemDescription);
    }

    private void goToDetailsActivity(
            String serviceName,
            String problemDescription) {

        String categoryName = "";
        String subcategoryName = "";

        int categoryPosition = spinnerCategory.getSelectedItemPosition();

        if (categoryPosition > 0) {
            categoryName = getString(
                    categories[categoryPosition - 1].getNameResId()
            );
        }

        int subcategoryPosition =
                spinnerSubcategory.getSelectedItemPosition();

        if (categoryPosition > 0 && subcategoryPosition > 0) {
            subcategoryName =
                    spinnerSubcategory.getSelectedItem().toString();
        }

        Intent intent = new Intent(
                BookVisitActivity.this,
                BookVisitDetailsActivity.class
        );

        intent.putExtra(EXTRA_CATEGORY, categoryName);
        intent.putExtra(EXTRA_SUBCATEGORY, subcategoryName);
        intent.putExtra(EXTRA_SERVICE_NAME, serviceName);
        intent.putExtra(EXTRA_PROBLEM_DESCRIPTION, problemDescription);

        startActivity(intent);
    }
}
