package com.example.richard.mudateapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EstateActivity extends AppCompatActivity {
    private DBManagerEstate manager;
    Spinner typeSpinner;
    TextView textCode, textName, textAddress, textValue, textRoms, textBaths, textKitchens;
    Button buttonSave, buttonCancel;
    RadioGroup radioGroupState;
    RadioButton radioSelectedState;
    Bundle extras;
    String code, name, value, address, type, rooms, baths, kitchens, codeToUpdate;
    int id, idToUpdate, selectedRadioIdx;
    Boolean habited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estate);

        manager = new DBManagerEstate(this);


        textCode = (TextView) findViewById(R.id.textEstateId);
        textName = (TextView) findViewById(R.id.textEstateName);
        textAddress = (TextView) findViewById((R.id.textEstateAddress));
        textValue = (TextView) findViewById(R.id.textEstateValue);
        textRoms = (TextView) findViewById(R.id.textEstateRooms);
        textBaths = (TextView) findViewById(R.id.textEstateBaths);
        textKitchens = (TextView) findViewById(R.id.textEstateKitchens);
        radioGroupState = (RadioGroup) findViewById(R.id.radioEstateState);
        typeSpinner = (Spinner) findViewById(R.id.estateTypeSpinner);

        buttonSave = (Button) findViewById(R.id.buttonEstateSave);
        buttonCancel = (Button) findViewById(R.id.buttonEstateCancel);

        extras = getIntent().getExtras();

        if(hasExtras()){
            id = idToUpdate = extras.getInt("id");
            System.out.println(id);
            if(id > 0){
                Cursor res = manager.getData(id);
                if(res != null && res.moveToFirst()){
                    getColumnData(res);
                }

                if(!res.isClosed()){
                    res.close();
                }

                fillFields();
                toggleFieldsInteraction(false);
                this.setButtonVisibility(View.INVISIBLE);
            }
        }

        this.setSpinnerAdapter(typeSpinner, R.array.estate_type_items);

    }

    private String getSelectedRadioText(RadioGroup radioGrp){
        int selectedRadioIdx = radioGrp.getCheckedRadioButtonId();
        radioSelectedState= (RadioButton) findViewById(selectedRadioIdx);
        return radioSelectedState.getText().toString();
    }

    private void setSpinnerAdapter(Spinner spinner, int arr){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arr, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void getColumnData(Cursor res){
        code = res.getString(res.getColumnIndex(DBManagerEstate.ID));
        name = res.getString(res.getColumnIndex(DBManagerEstate.NOMBRE));
        address = res.getString(res.getColumnIndex(DBManagerEstate.DIRECCION));
        value = res.getString(res.getColumnIndex(DBManagerEstate.VALOR));
        type = res.getString(res.getColumnIndex(DBManagerEstate.TIPO));
        rooms = res.getString(res.getColumnIndex(DBManagerEstate.HABITACIONES));
        baths = res.getString(res.getColumnIndex(DBManagerEstate.BANOS));
        kitchens = res.getString(res.getColumnIndex(DBManagerEstate.COCINAS));
    }


    private String getFieldText(TextView view){
        return view.getText().toString();
    }

    private void fillFields(){
        textCode.setText(code);
        textName.setText(name);
        textValue.setText(value);
        textAddress.setText(address);
        //typeSpinner.setSelection(((ArrayAdapter<String>)typeSpinner.getAdapter()).getPosition(type));
        textRoms.setText(rooms);
        textBaths.setText(baths);
        textKitchens.setText(kitchens);
    }

    private boolean hasExtras(){
        return extras != null;
    }

    private void setButtonVisibility(int visibility){
        buttonSave.setVisibility(visibility);
        buttonCancel.setVisibility(visibility);
    }

    private void toggleFieldsInteraction(Boolean state){
        fieldState(textAddress, state);
        fieldState(textName, state);
        fieldState(textCode, state);
        fieldState(textValue, state);
        fieldState(textRoms, state);
        fieldState(textBaths, state);
        fieldState(textKitchens, state);
        fieldState(typeSpinner, state);
        fieldState(radioGroupState, state);
    }

    private void fieldState(View view, Boolean state){
        view.setEnabled(state);
        view.setFocusableInTouchMode(state);
        view.setClickable(state);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(hasExtras()){
            if(id > 0){
                getMenuInflater().inflate(R.menu.menu_estate, menu);
            }else{
                getMenuInflater().inflate(R.menu.menu_main, menu);
            }
        }
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.actions_edit_estate:
                this.setButtonVisibility(View.VISIBLE);
                toggleFieldsInteraction(true);
                return true;
            case R.id.actions_delete_estate:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.dialog_eliminar).setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        manager.eliminar(idToUpdate);
                        Toast.makeText(getApplicationContext(), "Eliminado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton(R.string.dialog_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //actualizar(int id, String direccion, String nombre, String tipo, Boolean estado, int habitaciones, int banos, int cocinas)
    public void run(View view){
        Toast.makeText(getApplicationContext(), "Run: ", Toast.LENGTH_SHORT).show();
        if(hasExtras()){
            if(id > 0){
                if(textName.length() > 0 ){
                    if(manager.actualizar(
                            idToUpdate,
                            getFieldText(textAddress),
                            getFieldText(textName),
                            Double.parseDouble(getFieldText(textValue)),
                            typeSpinner.getSelectedItem().toString(),
                            Boolean.parseBoolean(getSelectedRadioText(radioGroupState)),
                            Integer.parseInt(getFieldText(textRoms)),
                            Integer.parseInt(getFieldText(textBaths)),
                            Integer.parseInt(getFieldText(textKitchens))
                        )){
                        Toast.makeText(getApplicationContext(), "Cargado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "No Cargado", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Agregar un nombre", Toast.LENGTH_SHORT).show();
                }
            }else {
                if (textName.length() > 0) {
                    if (manager.insertar(
                            getFieldText(textAddress),
                            getFieldText(textName),
                            Double.parseDouble(getFieldText(textValue)),
                            typeSpinner.getSelectedItem().toString(),
                            Boolean.parseBoolean(getSelectedRadioText(radioGroupState)),
                            Integer.parseInt(getFieldText(textRoms)),
                            Integer.parseInt(getFieldText(textBaths)),
                            Integer.parseInt(getFieldText(textKitchens))
                    )) {
                        Toast.makeText(getApplicationContext(), "Cargado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Cargado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Agregar un nombre", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
