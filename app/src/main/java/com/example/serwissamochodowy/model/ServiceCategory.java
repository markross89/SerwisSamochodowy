
package com.example.serwissamochodowy.model;

import com.example.serwissamochodowy.R;

public enum ServiceCategory {

    PERIODIC_SERVICE(
            R.string.category_periodic,
            R.string.category_periodic_description,
            R.string.category_periodic_examples,
            new int[]{
                    R.string.sub_oil_filters,
                    R.string.sub_fluids,
                    R.string.sub_periodic_inspection,
                    R.string.sub_technical_check,
                    R.string.sub_other_periodic
            }
    ),

    DIAGNOSTICS(
            R.string.category_diagnostics,
            R.string.category_diagnostics_description,
            R.string.category_diagnostics_examples,
            new int[]{
                    R.string.sub_computer_diagnostics,
                    R.string.sub_engine_diagnostics,
                    R.string.sub_electrical_diagnostics,
                    R.string.sub_suspension_diagnostics,
                    R.string.sub_brake_diagnostics,
                    R.string.sub_other_diagnostics
            }
    ),

    VULCANIZATION(
            R.string.category_vulcanization,
            R.string.category_vulcanization_description,
            R.string.category_vulcanization_examples,
            new int[]{
                    R.string.sub_tire_change,
                    R.string.sub_wheel_balancing,
                    R.string.sub_tire_repair,
                    R.string.sub_valve_replacement,
                    R.string.sub_tire_inspection,
                    R.string.sub_other_vulcanization
            }
    ),

    REPAIR(
            R.string.category_repair,
            R.string.category_repair_description,
            R.string.category_repair_examples,
            new int[]{
                    R.string.sub_brake_repair,
                    R.string.sub_suspension_repair,
                    R.string.sub_engine_repair,
                    R.string.sub_exhaust_repair,
                    R.string.sub_gearbox_repair,
                    R.string.sub_ac_repair,
                    R.string.sub_electrical_repair,
                    R.string.sub_other_repair
            }
    ),

    OTHER(
            R.string.category_other,
            R.string.category_other_description,
            R.string.category_other_examples,
            new int[]{
                    R.string.sub_wheel_alignment,
                    R.string.sub_pre_purchase_inspection,
                    R.string.sub_trip_preparation,
                    R.string.sub_underbody_protection,
                    R.string.sub_extra_equipment,
                    R.string.sub_detailing,
                    R.string.sub_other_service
            }
    );

    private final int nameResId;
    private final int descriptionResId;
    private final int examplesResId;
    private final int[] subcategories;

    ServiceCategory(
            int nameResId,
            int descriptionResId,
            int examplesResId,
            int[] subcategories
    ) {
        this.nameResId = nameResId;
        this.descriptionResId = descriptionResId;
        this.examplesResId = examplesResId;
        this.subcategories = subcategories;
    }

    public int getNameResId() {
        return nameResId;
    }

    public int getDescriptionResId() {
        return descriptionResId;
    }

    public int getExamplesResId() {
        return examplesResId;
    }

    public int[] getSubcategories() {
        return subcategories.clone();
    }
}
