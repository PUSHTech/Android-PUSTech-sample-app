package com.pushtech.android.example.fragments.metrics;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.pushtech.android.example.R;
import com.pushtech.android.example.activities.HomeActivity;
import com.pushtech.sdk.DataCollectorManager;
import com.pushtech.sdk.Products;
import com.pushtech.sdk.PurchaseProduct;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by crm27 on 28/6/16.
 */
public class PurchaseMetricsFragment extends Fragment implements View.OnClickListener {
    private ArrayList<PurchaseProduct> tempProducts, cartProducts;
    private View createProductButton, addCartButton, removeCartButton,
            numProductsButton, buyProductsButton;
    private EditText price_Et, name_Et;
    private AppCompatSpinner currency_SP;
    private int selectedItem;
    private DataCollectorManager dataCM;

    public PurchaseMetricsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_metrics_purchase, container,
                false);
        dataCM = DataCollectorManager.getInstance(getActivity());
        dataCM.contentView(this.getClass().getName());
        tempProducts = new ArrayList<>();
        cartProducts = new ArrayList<>();
        initViews(rootView);
        setListener();
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            setCurrencySpinner();
        } else {
            currency_SP.setVisibility(View.GONE);
        }

        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setCurrencySpinner() {
        List<String> list = new ArrayList<String>();
        Iterator<Currency> iterator = Currency.getAvailableCurrencies().iterator();
        int indexEUR = 0;
        while (iterator.hasNext()) {
            Currency c = iterator.next();
            if (c.equals(Currency.getInstance("EUR"))) {
                indexEUR = list.size();
            }
            list.add(c.getCurrencyCode());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currency_SP.setAdapter(dataAdapter);
        currency_SP.setSelection(indexEUR);

    }

    private void initViews(View rootView) {
        createProductButton = rootView.findViewById(R.id.fragment_metrics_purchase_create_product);
        addCartButton = rootView.findViewById(R.id.fragment_metrics_purchase_button_add_cart);
        removeCartButton = rootView.findViewById(R.id.fragment_metrics_purchase_button_remove_cart);
        numProductsButton = rootView.findViewById(R.id.fragment_metrics_purchase_button_num_of_products);
        buyProductsButton = rootView.findViewById(R.id.fragment_metrics_purchase_button_purchase);
        price_Et = (EditText) rootView.findViewById(R.id.fragment_metrics_purchase_product_price);
        name_Et = (EditText) rootView.findViewById(R.id.fragment_metrics_purchase_product_name);
        currency_SP = (AppCompatSpinner) rootView.findViewById(R.id.fragment_metrics_purchase_currency);
    }


    private void setListener() {
        createProductButton.setOnClickListener(this);
        addCartButton.setOnClickListener(this);
        removeCartButton.setOnClickListener(this);
        numProductsButton.setOnClickListener(this);
        buyProductsButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_metrics_purchase_button_add_cart:
                showDialogSelectProduct(
                        getString(R.string.fragment_metrics_purchase_add_to_cart), true);
                break;
            case R.id.fragment_metrics_purchase_button_remove_cart:
                showDialogSelectProduct(
                        getString(R.string.fragment_metrics_purchase_remove_from_cart), false);
                break;
            case R.id.fragment_metrics_purchase_button_num_of_products:
                numberOfProducts();
                break;
            case R.id.fragment_metrics_purchase_button_purchase:
                purchaseProducts();
                break;
            case R.id.fragment_metrics_purchase_create_product:
                saveProduct();
                break;
        }

    }

    public String[] getNameOfProducts(ArrayList<PurchaseProduct> temp) {
        String[] pr = new String[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            PurchaseProduct product = temp.get(i);
            pr[i] = product.getName() + " : " + product.getPrice() + product.getCurrency().getSymbol();
        }
        return pr;
    }

    private void showDialogSelectProduct(String title, final boolean add) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        String[] nameOfProducts;
        selectedItem = 0;
        String positiveButton;
        if (add) {
            nameOfProducts = getNameOfProducts(tempProducts);
            positiveButton = getString(R.string.dialog_add);
        } else {
            nameOfProducts = getNameOfProducts(cartProducts);
            positiveButton = getString(R.string.dialog_remove);
        }
        builder.setSingleChoiceItems(nameOfProducts, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedItem = which;
            }
        });
        builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (add) {
                    addProductInCart(tempProducts.get(selectedItem));
                } else {
                    removeProductFromCart(cartProducts.get(selectedItem));
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), null);
        builder.create().show();

    }

    private void clearFields() {
        price_Et.getText().clear();
        name_Et.getText().clear();
    }

    private void addProductInCart(PurchaseProduct p) {
        tempProducts.remove(p);
        cartProducts.add(p);
        dataCM.addProductToCart(p);
        dataCM.sendMetrics();
    }

    private void removeProductFromCart(PurchaseProduct p) {
        tempProducts.add(p);
        cartProducts.remove(p);
        dataCM.removeProductFromCart(p);
        dataCM.sendMetrics();

    }

    private Currency getCurrency() {
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            return Currency.getInstance((String) currency_SP.getSelectedItem());
        } else {
            return Currency.getInstance("EUR");
        }
    }

    private void purchaseProducts() {
        Products products = new Products();
        products.setProducts(cartProducts);
        dataCM.purchaseProduct(products);
        dataCM.sendMetrics();
        Double total = 0d;
        for (PurchaseProduct p : cartProducts) {
            tempProducts.add(p);
            total += p.getPrice();
        }
        String currency = getCurrency().getSymbol();
        ((HomeActivity) getActivity()).showDialogAccept(getString(R.string.dialog_success),
                String.format(getString(R.string.dialog_purchase_resume),
                        cartProducts.size(), total, currency));
        cartProducts.clear();
    }

    private void saveProduct() {
        PurchaseProduct p = new PurchaseProduct();
        p.setCurrency(getCurrency());
        p.setPrice(Double.valueOf(price_Et.getText().toString()));
        p.setProductId(String.valueOf(UUID.randomUUID()));
        p.setName(name_Et.getText().toString());
        tempProducts.add(p);
        clearFields();

    }

    public void numberOfProducts() {
        dataCM.setNumOfProductInCart(cartProducts.size());
        dataCM.sendMetrics();
        ((HomeActivity) getActivity()).showDialogAccept(getString(R.string.dialog_info),
                String.format(getString(R.string.dialog_number_products_cart), cartProducts.size()));

    }
}
