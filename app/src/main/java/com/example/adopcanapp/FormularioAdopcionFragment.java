package com.example.adopcanapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class FormularioAdopcionFragment extends Fragment {

    private static final int PICK_PDF_REQUEST_DOMICILIO = 1;
    private static final int PICK_PDF_REQUEST_INE = 2;
    private static final int STORAGE_PERMISSION_CODE = 3;
    private Uri pdfUriDomicilio, pdfUriIne;
    private TextView pdfNameTextViewDomicilio, pdfNameTextViewIne;
    private Spinner estado_civil, jardines, mejoras_hogar, disponibilidad_tiempo, ayudante_cuidador,cantidad_hijos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_formulario_adopcion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        estado_civil = view.findViewById(R.id.estado_civil);
        jardines = view.findViewById(R.id.jardin);
        mejoras_hogar = view.findViewById(R.id.adecuaciones);
        disponibilidad_tiempo = view.findViewById(R.id.tiempo);
        ayudante_cuidador = view.findViewById(R.id.ayudante);
        cantidad_hijos=view.findViewById(R.id.cant_hijos);

        setupSpinners();
        setEstadoCivil();
        setHijos();

        Button selectPdfButtonDomicilio = view.findViewById(R.id.domicilio);
        pdfNameTextViewDomicilio = view.findViewById(R.id.pdf_nombre_domicilio);
        Button selectPdfButtonIne = view.findViewById(R.id.ine);
        pdfNameTextViewIne = view.findViewById(R.id.pdf_nombre_ine);

        selectPdfButtonDomicilio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    openFileChooser(PICK_PDF_REQUEST_DOMICILIO);
                } else {
                    requestStoragePermission();
                }
            }
        });

        selectPdfButtonIne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    openFileChooser(PICK_PDF_REQUEST_INE);
                } else {
                    requestStoragePermission();
                }
            }
        });
    }

    private void openFileChooser(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar PDF"), requestCode);
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(getContext(), "Permiso necesario para seleccionar un archivo PDF", Toast.LENGTH_SHORT).show();
        }
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Se otorgó el permiso, abrir la ventana de selección de archivos nuevamente
                openFileChooser(requestCode);
            } else {
                Toast.makeText(getContext(), "Permiso denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri selectedPdfUri = data.getData();
            long fileSize = getFileSize(selectedPdfUri);

            if (fileSize > 1024 * 1024) { // 1 MB en bytes
                Toast.makeText(getContext(), "El archivo seleccionado supera el límite de tamaño (1 MB)", Toast.LENGTH_SHORT).show();
                return;
            }

            if (requestCode == PICK_PDF_REQUEST_DOMICILIO) {
                pdfUriDomicilio = selectedPdfUri;
                String pdfName = getFileName(pdfUriDomicilio);
                pdfNameTextViewDomicilio.setText("Nombre del archivo: " + pdfName);
            } else if (requestCode == PICK_PDF_REQUEST_INE) {
                pdfUriIne = selectedPdfUri;
                String pdfNameIne = getFileName(pdfUriIne);
                pdfNameTextViewIne.setText("Nombre del archivo INE: " + pdfNameIne);
            }
        }
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private long getFileSize(Uri uri) {
        Cursor cursor = null;
        try {
            cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                if (!cursor.isNull(sizeIndex)) {
                    return cursor.getLong(sizeIndex);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return 0;
    }

    private void setupSpinners() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.opciones, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        estado_civil.setAdapter(adapter);
        jardines.setAdapter(adapter);
        mejoras_hogar.setAdapter(adapter);
        disponibilidad_tiempo.setAdapter(adapter);
        ayudante_cuidador.setAdapter(adapter);
    }

    private void setEstadoCivil() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.opciones_estado_civil, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        estado_civil.setAdapter(adapter);

    }

    private void setHijos() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.opciones_numero_hijos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cantidad_hijos.setAdapter(adapter);




    }
}




