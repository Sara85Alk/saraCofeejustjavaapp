package com.hazimfenjan.saracofee;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * this method will called when the Plus button clicked
     */
    public void increment(View view) {
        if (quantity == 100){
            Toast.makeText(this, "more than 100 is not valid ", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity +=1;
        displayQuantity(quantity);
    }

    /**
     * this method will called when the Minus button clicked
     */
    public void decrement(View view) {
        if (quantity == 1 ){
            Toast.makeText(this, "Less 1 is not valid ", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -=1;
        displayQuantity(quantity);
    }

    private void displayQuantity(int number) {
        TextView quantityTextView;
        quantityTextView = (TextView) findViewById(R.id.quantity);
        quantityTextView.setText("" + number);
    }

    /**
     * this method called when Order button clicked
     */
    public void order(View view){
        EditText name = (EditText) findViewById(R.id.user_name);
        String names =name.getText().toString();

        CheckBox whipped = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        Boolean hasWhipped = whipped.isChecked();

        CheckBox choco = (CheckBox) findViewById(R.id.choco_checkbox);
        Boolean hasChoco = choco.isChecked();

        int price =  calcPrice( hasWhipped, hasChoco);

        String priceMessage = createOrderSummary(names,price,hasWhipped,hasChoco);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + names);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


        displayMessage(priceMessage);


    }

    /**
     *
     * @param names
     * @param price
     * @param addWhippedCream
     * @param addChocolate
     * @return text summery
     */
    private String createOrderSummary(String names,int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = getString(R.string.user_name, names);
        priceMessage += "\n" + getString(R.string.Add_whipped_cream ) + addWhippedCream;
        priceMessage += "\n" + getString(R.string.Add_chocolate) + addChocolate;
        priceMessage += "\n" + getString(R.string.Quantity) + quantity;
        priceMessage += "\n:" + getString(R.string.Total) + NumberFormat.getCurrencyInstance().format(price) ;
        priceMessage += "\n" + getString(R.string.Thank_you);
        return priceMessage;
    }


    public int calcPrice(boolean hasWhipped,boolean hasChoco){
        int basePrice = 5;
    // If the user wants whipped cream, add $1 per cup
        if (hasWhipped) {
            basePrice = basePrice + 1;
        }

        // If the user wants chocolate, add $2 per cup
        if (hasChoco) {
            basePrice = basePrice + 2;
        }

        // Calculate the total order price by multiplying by the quantity
        return quantity * basePrice;
    }

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.resultTxt);
        orderSummaryTextView.setText(message);
    }


}
