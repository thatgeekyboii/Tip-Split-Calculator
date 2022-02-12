//vaibhav patil
package com.example.tipsplit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText billAmountValue;
    private EditText numberOfPeopleValue;
    private RadioGroup radioGroup;
    private TextView totalAmountPerPersonValue;
    private TextView tipAmountValue;
    private TextView totalWithTipValue;
    private TextView overageValue;


    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        billAmountValue = findViewById(R.id.billAmount);
        tipAmountValue = findViewById(R.id.tipAmount);
        totalWithTipValue = findViewById(R.id.totalWithTip);
        numberOfPeopleValue = findViewById(R.id.noOfPeople);
        totalAmountPerPersonValue = findViewById(R.id.perPerson);
        overageValue = findViewById(R.id.overage);
        radioGroup = findViewById(R.id.radioGroupPercen);
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("tipAmountValue", tipAmountValue.getText().toString());
        outState.putString("totalWithTipValue", totalWithTipValue.getText().toString());
        outState.putString("totalAmountPerPersonValue", totalAmountPerPersonValue.getText().toString());
        outState.putString("overageValue", overageValue.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        totalWithTipValue.setText(savedInstanceState.getString("totalWithTipValue"));
        tipAmountValue.setText(savedInstanceState.getString("tipAmountValue"));
        totalAmountPerPersonValue.setText(savedInstanceState.getString("totalAmountPerPersonValue"));
        overageValue.setText(savedInstanceState.getString("overageValue"));
    }
    // Calculating Tip and Total Amount
    public void calculateTipTotal(View v) {
        double tipAmount = 0.0;
        double totalAmount;
        String billTotal = billAmountValue.getText().toString();
        DecimalFormat f = new DecimalFormat("##.00");
        NumberFormat currFormat = NumberFormat.getCurrencyInstance(Locale.US);
        // if billAmountValue empty do not check the box
        if(billTotal.matches("") || billTotal.matches("0")){
            radioGroup.clearCheck();
            return;
        }
        // Calculating Tip amount
        if(v.getId() == R.id.radioButton12) {
            tipAmount = Double.parseDouble(f.format((Double.parseDouble(billTotal)) * 0.12));
        }
        else if(v.getId() == R.id.radioButton15) {
            tipAmount = Double.parseDouble(f.format((Double.parseDouble(billTotal)) * 0.15));
        }
        else if(v.getId() == R.id.radioButton18) {
            tipAmount = Double.parseDouble(f.format((Double.parseDouble(billTotal)) * 0.18));
        }
        else if(v.getId() == R.id.radioButton20) {
            tipAmount = Double.parseDouble(f.format((Double.parseDouble(billTotal)) * 0.20));
        }
        // Calculating Total amount with tip
        totalAmount = (Double.parseDouble(billTotal)) + tipAmount;
        // Setting the screen values
        tipAmountValue.setText(currFormat.format(tipAmount));
        totalWithTipValue.setText(currFormat.format(totalAmount));

    }
    // Method to calculate the Total Amount Per Person and Overage Value
    public void goBtn(View v) {
        // Format values as currency
        numberOfPeopleValue.onEditorAction(EditorInfo.IME_ACTION_DONE);
        NumberFormat currFormat = NumberFormat.getCurrencyInstance();
        String billTotal = billAmountValue.getText().toString();
        String numOfPpl = numberOfPeopleValue.getText().toString();
        String totAmountWithTip = totalWithTipValue.getText().toString();
        if (billTotal.isEmpty() || billTotal.equals("0")) {
            Toast.makeText(getApplicationContext(), "Bill field cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(numOfPpl.isEmpty() || numOfPpl.equals("0")) {
            Toast.makeText(getApplicationContext(), "Number of people cannot be empty or 0.", Toast.LENGTH_SHORT).show();
            return;
        }
        // Extracting total amount with tip.
        totAmountWithTip = totAmountWithTip.substring(1);
        // Multiplying by 100 to remove decimals before dividing
        double totalAmount = Double.parseDouble(totAmountWithTip) * 100;
        //Taking in number of people as int value
        int numOfPeople = Integer.parseInt(numOfPpl);
        //Total per person
        double tAppCValue = Math.ceil(totalAmount/numOfPeople);
        double tAppVal = (totalAmount/numOfPeople);
        double overage = ((tAppCValue - tAppVal) * numOfPeople);
        //Total Amount Per Person Text Field
        double finalValue = tAppCValue/100;
        totalAmountPerPersonValue.setText(currFormat.format(finalValue));
        // Overage Text Field
        double finalOvgValue = overage/100;
        overageValue.setText(currFormat.format(finalOvgValue));
    }
    public void clearAllFields(View v) {
        billAmountValue.setText("");
        tipAmountValue.setText("");
        totalWithTipValue.setText("");
        numberOfPeopleValue.setText("");
        totalAmountPerPersonValue.setText("");
        overageValue.setText("");
        radioGroup.clearCheck();
    }
}