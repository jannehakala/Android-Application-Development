package fi.jamk.shoppinglist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by H3298 on 10/5/2016.
 */

public class ShoppingListDialogFragment extends DialogFragment{

    public interface DialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String product, int count, float price);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    DialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.shoppinglist_dialog, null);
        builder.setView(dialogView)
                .setTitle("Add a new Product")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText editProduct = (EditText) dialogView.findViewById(R.id.productName);
                        String product = editProduct.getText().toString();

                        EditText editCount = (EditText) dialogView.findViewById(R.id.productCount);
                        int count = Integer.valueOf(editCount.getText().toString());

                        EditText editPrice = (EditText) dialogView.findViewById(R.id.productPrice);
                        float price = Float.valueOf(editPrice.getText().toString());
                        mListener.onDialogPositiveClick(ShoppingListDialogFragment.this,product,count,price);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(ShoppingListDialogFragment.this);
                    }
                });
        return builder.create();
    }
}

