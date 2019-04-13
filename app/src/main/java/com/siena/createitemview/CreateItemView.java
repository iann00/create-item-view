package com.siena.createitemview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siena.createitemview.events.OnItemAdded;

public class CreateItemView extends LinearLayout {
    /**
     * Represent the item amount counter
     */
    private int amountCounter;
    /**
     * Represent the item unity iterator
     */
    private int unityIterator;
    /**
     * Amount limit
     */
    private int amountLimit;
    /**
     * Unities options list
     */
    private String[] unities;
    /**
     * OnItemAdded listener
     */
    private OnItemAdded onItemAddedListener;


    /**
     * Item name
     */
    private EditText etItemName;
    /**
     * Add item button
     */
    private ImageButton btAddItem;
    /**
     * Increment item amount button
     */
    private ImageButton btIncrementAmount;
    /**
     * Amount label
     */
    private TextView tvMount;
    /**
     * Increment item unity iterator
     */
    private ImageButton btIncrementIterator;
    /**
     * Amount label
     */
    private TextView tvUnity;
    /**
     * Increment item amount button
     */
    private ImageButton btDecrementAmount;
    /**
     * Increment item unity iterator
     */
    private ImageButton btDecrementIterator;

    private String itemName;
    private String unity;


    public CreateItemView(Context context) {
        super(context);
        throwExceptionOnInit(context);
        init();
    }

    public CreateItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        throwExceptionOnInit(context);
        init();
    }

    public CreateItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        throwExceptionOnInit(context);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.create_item_view, null);

        btIncrementAmount   = view.findViewById(R.id.bt_civ_increment_amount);
        tvMount               = view.findViewById(R.id.tv_civ_amount);
        btDecrementAmount   = view.findViewById(R.id.bt_civ_decrement_amount);
        btIncrementIterator = view.findViewById(R.id.bt_civ_increment_iterator);
        tvUnity               = view.findViewById(R.id.tv_civ_unity);
        btDecrementIterator = view.findViewById(R.id.bt_civ_decrement_iterator);
        etItemName           = view.findViewById(R.id.et_civ_item_name);
        btAddItem            = view.findViewById(R.id.bt_civ_add_item);

        btIncrementAmount.setOnClickListener(v -> incrementAmount());
        btDecrementAmount.setOnClickListener(v -> decrementAmount());
        btIncrementIterator.setOnClickListener(v -> incrementIterator());
        btDecrementIterator.setOnClickListener(v -> decrementIterator());
        btAddItem.setOnClickListener(v -> addItem());
    }

    public void setOnItemAdded(OnItemAdded listener) {
        onItemAddedListener = listener;
    }

    public void setAmountLimit(int limit) {
        amountLimit = limit;
    }

    public void setUnities(String[] unitiesOptions) {
        unitiesOptions = unitiesOptions;
    }

    private void incrementAmount() {
        if (amountLimit != 0) {
            if (amountCounter < amountLimit) {
                tvMount.setText(String.valueOf(amountCounter++));
            }
        }
        else {
            tvMount.setText(String.valueOf(amountCounter++));
        }
    }

    private void decrementAmount() {
        if (amountCounter <= 0) return;
        else {
            tvMount.setText(String.valueOf(amountCounter--));
        }
    }

    private void incrementIterator() {
        if (unities == null) {
            throw new RuntimeException("You must set a unities list");
        }
        else {
            if (unityIterator <= unities.length) {
                itemName = unities[unityIterator++];
                tvUnity.setText(itemName);
            }
        }
    }

    private void decrementIterator() {
        if (unities == null) {
            throw new RuntimeException("You must set a unities list");
        }
        else {
            if (unityIterator <= 0) {
                tvUnity.setText(unities[0]);
            }
        }
    }

    private void addItem() {
        String item = etItemName.getText()
                .toString()
                .trim();

        onItemAddedListener.onItemAdded(
                item,
                unity,
                amountCounter
        );
    }

    public int getAmount() {
        return amountCounter;
    }

    public String getUnity() {
        return unity;
    }

    public String getItem() {
        return etItemName.getText()
                .toString()
                .trim();
    }

    private void throwExceptionOnInit(Context context) {
        if (context instanceof OnItemAdded) {
            onItemAddedListener = (OnItemAdded) context;
        }
        else {
            String className = context.getClass().getSimpleName();
            throw new RuntimeException(className + "must to implement the " +
                    "a OnItemAdded interface");
        }
    }
}
