
package com.example.serwissamochodowy.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serwissamochodowy.R;
import com.example.serwissamochodowy.model.Vehicle;

import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder> {

    private final List<Vehicle> vehicles;
    private final OnVehicleDeleteListener deleteListener;

    public VehicleAdapter(List<Vehicle> vehicles, OnVehicleDeleteListener deleteListener) {
        this.vehicles = vehicles;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vehicle, parent, false);

        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {

        Vehicle vehicle = vehicles.get(position);
        Context context = holder.itemView.getContext();

        holder.tvVehicleName.setText(
                context.getString(
                        R.string.vehicle_name,
                        vehicle.getBrand(),
                        vehicle.getModel()
                )
        );
        holder.tvRegistration.setText(
                context.getString(R.string.vehicle_registration, vehicle.getRegistration())
        );

        holder.tvVin.setText(
                context.getString(R.string.vehicle_vin,
                        vehicle.getVin() == null || vehicle.getVin().isEmpty()
                                ? context.getString(R.string.vehicle_not_provided)
                                : vehicle.getVin())
        );

        holder.tvColor.setText(
                context.getString(R.string.vehicle_color,
                        vehicle.getColor() == null || vehicle.getColor().isEmpty()
                                ? context.getString(R.string.vehicle_not_provided)
                                : vehicle.getColor())
        );

        holder.tvRegistrationDate.setText(
                context.getString(R.string.vehicle_registration_date,
                        vehicle.getRegistrationDate() == null || vehicle.getRegistrationDate().isEmpty()
                                ? context.getString(R.string.vehicle_not_provided)
                                : vehicle.getRegistrationDate())
        );

        holder.btnDeleteVehicle.setOnClickListener(v ->
                deleteListener.onVehicleDelete(vehicle)
        );
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }

    public static class VehicleViewHolder extends RecyclerView.ViewHolder {

        TextView tvVehicleName;
        TextView tvRegistration;
        TextView tvVin;
        TextView tvColor;
        TextView tvRegistrationDate;
        ImageButton btnDeleteVehicle;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);

            tvVehicleName = itemView.findViewById(R.id.tvVehicleName);
            tvRegistration = itemView.findViewById(R.id.tvRegistration);
            tvVin = itemView.findViewById(R.id.tvVin);
            tvColor = itemView.findViewById(R.id.tvColor);
            tvRegistrationDate = itemView.findViewById(R.id.tvRegistrationDate);
            btnDeleteVehicle = itemView.findViewById(R.id.btnDeleteVehicle);
        }
    }

    public interface OnVehicleDeleteListener {
        void onVehicleDelete(Vehicle vehicle);
    }
}
