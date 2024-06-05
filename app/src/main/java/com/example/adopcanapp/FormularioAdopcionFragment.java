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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class FormularioAdopcionFragment extends Fragment {

    private static final int PICK_PDF_REQUEST_DOMICILIO = 1;
    private static final int PICK_PDF_REQUEST_INE = 2;
    private static final int STORAGE_PERMISSION_CODE = 3;
    private Uri pdfUriDomicilio, pdfUriIne;
    private TextView pdfNameTextViewDomicilio, pdfNameTextViewIne, ingresos_Mensuales,obtenerid;
    private Spinner estado_civil, jardines, mejoras_hogar, disponibilidad_tiempo, ayudante_cuidador, cantidad_hijos;
    private Usuario usuario;
    private Mascotas mascota;



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
        cantidad_hijos = view.findViewById(R.id.cant_hijos);
        ingresos_Mensuales = view.findViewById(R.id.ingresos_monetarios);

        final Button enviarSolicitudes = view.findViewById(R.id.solicitar_adopcion);

        setupSpinners();
        setEstadoCivil();
        setHijos();

        Button selectPdfButtonDomicilio = view.findViewById(R.id.domicilio);
        pdfNameTextViewDomicilio = view.findViewById(R.id.pdf_nombre_domicilio);
        Button selectPdfButtonIne = view.findViewById(R.id.ine);
        pdfNameTextViewIne = view.findViewById(R.id.pdf_nombre_ine);
        obtenerid = view.findViewById(R.id.usuarioids);
        obtenerid.setText("ID Usuario: " );


        if (getArguments() != null) {
            usuario = (Usuario) getArguments().getSerializable("usuario");
            mascota = (Mascotas) getArguments().getSerializable("mascota");

            // Verificar que usuario no sea null y actualizar el TextView
            if (usuario != null) {
//                obtenerid = view.findViewById(R.id.usuarioids);
//                obtenerid.setText("ID Usuario: " + usuario.getId());
            }
        }



                // Hacer algo con el ID del usuario





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


        enviarSolicitudes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (validar()) {
                ejecutarServicio("https://adopcan.000webhostapp.com/insertar_solicitudes.php");
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
                pdfNameTextViewDomicilio.setText(pdfName); // Actualiza el TextView con el nombre del archivo
            } else if (requestCode == PICK_PDF_REQUEST_INE) {
                pdfUriIne = selectedPdfUri;
                String pdfNameIne = getFileName(pdfUriIne);
                pdfNameTextViewIne.setText(pdfNameIne); // Actualiza el TextView con el nombre del archivo
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

    public boolean validar() {
        int selectedPositionCasado = estado_civil.getSelectedItemPosition();
        int selectedPositionHijos = cantidad_hijos.getSelectedItemPosition();
        int selectedPositionJardin = jardines.getSelectedItemPosition();
        int selectedPositionAdecuaciones = mejoras_hogar.getSelectedItemPosition();
        int selectedPositionDisponibilidad = disponibilidad_tiempo.getSelectedItemPosition();
        int selectedPositionAyudante = ayudante_cuidador.getSelectedItemPosition();
        String entrada_ingresos = ingresos_Mensuales.getText().toString();
        String camponombreDomicilio = pdfNameTextViewDomicilio.getText().toString();
        String camponombreIne = pdfNameTextViewIne.getText().toString();

        if (selectedPositionCasado == 0) {
            Toast.makeText(getContext(), "Por favor, seleccione una opción en la pregunta 4", Toast.LENGTH_SHORT).show();
            return false;
        } else if (selectedPositionHijos == 0) {
            Toast.makeText(getContext(), "Por favor, seleccione una opción en la pregunta 5", Toast.LENGTH_SHORT).show();
            return false;
        } else if (selectedPositionJardin == 0) {
            Toast.makeText(getContext(), "Por favor, seleccione una opción en la pregunta 6", Toast.LENGTH_SHORT).show();
            return false;
        } else if (selectedPositionAdecuaciones == 0) {
            Toast.makeText(getContext(), "Por favor, seleccione una opción en la pregunta 7", Toast.LENGTH_SHORT).show();
            return false;
        } else if (selectedPositionDisponibilidad == 0) {
            Toast.makeText(getContext(), "Por favor, seleccione una opción en la pregunta 8", Toast.LENGTH_SHORT).show();
            return false;
        } else if (selectedPositionAyudante == 0) {
            Toast.makeText(getContext(), "Por favor, seleccione una opción en la pregunta 9", Toast.LENGTH_SHORT).show();
            return false;
        } else if (entrada_ingresos.isEmpty()) {
            Toast.makeText(getContext(), "conteste la pregunta 3", Toast.LENGTH_SHORT).show();
            return false;
        } else if (camponombreDomicilio.isEmpty()) {
            Toast.makeText(getContext(), "Debe seleccionar un archivo de comprobante de domicilio", Toast.LENGTH_SHORT).show();
            return false;
        } else if (camponombreIne.isEmpty()) {
            Toast.makeText(getContext(), "Debe seleccionar un archivo de ine", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void ejecutarServicio(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Aquí recibes la respuesta del servidor
                // Puedes mostrar un mensaje al usuario si la operación fue exitosa
                Toast.makeText(getContext(), "Solicitud de adopción enviada correctamente", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Aquí manejas los errores de la solicitud
                Toast.makeText(getContext(), "Error al enviar la solicitud: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Aquí agregamos todos los parámetros que queremos enviar al servidor
                Map<String, String> parametros = new HashMap<>();

                parametros.put("estado_civil", estado_civil.getSelectedItem().toString());
                parametros.put("ingresos_monetarios", ingresos_Mensuales.getText().toString());
                parametros.put("numero_hijos", cantidad_hijos.getSelectedItem().toString());
                parametros.put("jardin", jardines.getSelectedItem().toString());
                parametros.put("cambios_hogar", mejoras_hogar.getSelectedItem().toString());

                //  parametros.put("ayudante_cuidador", ayudante_cuidador.getSelectedItem().toString());

                // Aquí puedes agregar más parámetros según tus necesidades

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}