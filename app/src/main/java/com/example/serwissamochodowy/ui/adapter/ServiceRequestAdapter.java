
package com.example.serwissamochodowy.ui.adapter;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serwissamochodowy.R;
import com.example.serwissamochodowy.model.ServiceRequest;
import com.example.serwissamochodowy.dao.ServiceRequestDao;
import com.example.serwissamochodowy.model.Vehicle;
import com.example.serwissamochodowy.dao.VehicleDao;
import com.example.serwissamochodowy.ui.activity.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ServiceRequestAdapter extends RecyclerView.Adapter<ServiceRequestAdapter.RequestViewHolder> {

    private final List<ServiceRequest> requests;
    private final VehicleDao vehicleDao;
    private final ServiceRequestDao serviceRequestDao;
    private final MainActivity context;

    public ServiceRequestAdapter(MainActivity context,
                                 List<ServiceRequest> requests,
                                 VehicleDao vehicleDao,
                                 ServiceRequestDao serviceRequestDao) {
        this.context = context;
        this.requests = requests;
        this.vehicleDao = vehicleDao;
        this.serviceRequestDao = serviceRequestDao;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service_request, parent, false);

        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {

        ServiceRequest request = requests.get(position);
        String status = request.getStatus();

        holder.tvServiceName.setText(request.getServiceName());

        Vehicle vehicle = vehicleDao.getVehicleById(request.getVehicleId());

        if (vehicle != null) {
            String vehicleInfo = vehicle.getBrand() + " " +
                    vehicle.getModel() + " (" +
                    vehicle.getRegistration() + ")";

            holder.tvVehicleInfo.setText(vehicleInfo);
        } else {
            holder.tvVehicleInfo.setText(R.string.request_vehicle_unknown);
        }

        String formattedDate = formatDate(request.getVisitDate());

        holder.tvVisitDate.setText(
                context.getString(
                        R.string.request_date_label,
                        formattedDate,
                        request.getVisitTime()
                )
        );

        holder.tvRequestStatus.setText(getStatusText(status));

        boolean canCancel =
                !"POTWIERDZONA".equals(status) &&
                        !"ANULOWANA".equals(status);

        holder.btnDeleteRequest.setEnabled(canCancel);
        holder.btnDeleteRequest.setAlpha(canCancel ? 1.0f : 0.35f);

        holder.btnDeleteRequest.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.cancel_request_title)
                    .setMessage(R.string.cancel_request_message)
                    .setNegativeButton(R.string.cancel, null)
                    .setPositiveButton(R.string.confirm, (dialog, which) -> {

                        int result = serviceRequestDao.cancelRequest(request.getId());

                        if (result > 0) {
                            request.setStatus("ANULOWANA");
                            int currentPosition = holder.getAdapterPosition();

                            if (currentPosition != RecyclerView.NO_POSITION) {
                                notifyItemChanged(currentPosition);
                            }
                        }
                    })
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    private String formatDate(String date) {
        try {
            SimpleDateFormat inputFormat =
                    new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            SimpleDateFormat outputFormat =
                    new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

            Date parsedDate = inputFormat.parse(date);

            if (parsedDate != null) {
                return outputFormat.format(parsedDate);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    private String getStatusText(String status) {

        if ("OCZEKUJE_NA_POTWIERDZENIE".equals(status)) {
            return context.getString(R.string.request_status_pending);
        }

        if ("POTWIERDZONA".equals(status)) {
            return context.getString(R.string.request_status_confirmed);
        }

        if ("ANULOWANA".equals(status)) {
            return context.getString(R.string.request_status_cancelled);
        }

        return context.getString(R.string.request_status_unknown);
    }

    static class RequestViewHolder extends RecyclerView.ViewHolder {

        TextView tvServiceName;
        TextView tvVehicleInfo;
        TextView tvVisitDate;
        TextView tvRequestStatus;
        ImageButton btnDeleteRequest;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);

            tvServiceName = itemView.findViewById(R.id.tvServiceName);
            tvVehicleInfo = itemView.findViewById(R.id.tvVehicleInfo);
            tvVisitDate = itemView.findViewById(R.id.tvVisitDate);
            tvRequestStatus = itemView.findViewById(R.id.tvRequestStatus);
            btnDeleteRequest = itemView.findViewById(R.id.btnDeleteRequest);
        }
    }
}
